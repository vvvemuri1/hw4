package edu.cmu.lti.f13.hw4.hw4_vvv.casconsumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.ProcessTrace;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_vvv.utils.Utils;

public class RetrievalEvaluator extends CasConsumer_ImplBase 
{
  private static final Integer INCORRECT_TYPE = 0;
  private static final Integer CORRECT_TYPE = 1;
  private static final Integer QUERY_TYPE = 99;
  
	/** query id number **/
	public ArrayList<Integer> qIdList;

	/** query and text relevant values **/
	public ArrayList<Integer> relList;
	
	/** 
	 * Global word dictionary that keeps track of the words and 
	 * word frequencies in each sentence.
	 */
	public HashMap<String, HashMap<String, Integer>> globalWordDictionary;

	/**
	 * Map of sentence to map of relevance values to Documents.
	 */
	public HashMap<Integer, HashMap<Integer, HashSet<String>>> relevanceToDocumentMap;
	
	/**
	 * Initializes the list of query IDS, the list of relevance values and the
	 * Global word dictionary.
	 * @return void
	 */
	public void initialize() throws ResourceInitializationException 
	{
		qIdList = new ArrayList<Integer>();
		relList = new ArrayList<Integer>();
		globalWordDictionary = new HashMap<String, HashMap<String, Integer>>();
		relevanceToDocumentMap = new HashMap<Integer, HashMap<Integer, HashSet<String>>>();
	}

	/**
	 * Constructs the global word dictionary that maintains the list
	 * of all words and their frequencies in each sentence. Also populates the
	 * relevanceToDocumentMap that maps each relevance value to map of queryIDs and
	 * corresponding sentence texts.
	 * @param aCas CAS Object
	 * @return void
	 */
	@Override
	public void processCas(CAS aCas) throws ResourceProcessException 
	{
		JCas jcas;
		try 
		{
			jcas =aCas.getJCas();
		} 
		catch (CASException e) 
		{
			throw new ResourceProcessException(e);
		}

		FSIterator it = jcas.getAnnotationIndex(Document.type).iterator();
	
		if (it.hasNext()) 
		{
			Document doc = (Document) it.next();

			//Make sure that your previous annotators have populated this in CAS
			FSList fsTokenList = doc.getTokenList();
			
			qIdList.add(doc.getQueryId());
			relList.add(doc.getRelevanceValue());
			
			// Populate the global word dictionary			
	    populateGlobalDictionary(fsTokenList, doc.getText());
	    
	    // Populate the sentence to Query ID map
	    populateRelevanceToDocumentMap(doc);
		}
	}

	/**
	 * Populate the Global dictionary using the tokens in the document
	 * sentence.
	 * @param fsTokenList List of tokens in the document sentence
	 * @param docText Text contained in the document
	 */
  private void populateGlobalDictionary(FSList fsTokenList, String docText) 
  {
    HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
    ArrayList<Token> tokenList = Utils.fromFSListToCollection(fsTokenList, 
            Token.class);
    
    for (Token token : tokenList)
    {              
      wordFrequencies.put(token.getText(), token.getFrequency());
    }
        
    globalWordDictionary.put(docText, wordFrequencies);
  }
  
  /**
   * Populates the relevanceToDocumentMap by mapping documents of each type
   * to the list of all documents of that type.
   */
  private void populateRelevanceToDocumentMap(Document doc)
  {
    if (relevanceToDocumentMap.containsKey(doc.getRelevanceValue()))
    {
      HashMap<Integer, HashSet<String>> queryIDToText = relevanceToDocumentMap.get(doc.getRelevanceValue());
      if (queryIDToText.containsKey(doc.getQueryId()))
      {
        queryIDToText.get(doc.getQueryId()).add(doc.getText());
      }
      else
      {
        HashSet<String> set = new HashSet<String>();
        set.add(doc.getText());
        queryIDToText.put(doc.getQueryId(), set);
      }
    }
    else
    {
      HashMap<Integer, HashSet<String>> queryIDToText = new HashMap<Integer, HashSet<String>>();
      HashSet<String> set = new HashSet<String>();
     
      set.add(doc.getText());
      queryIDToText.put(doc.getQueryId(), set);
      
      relevanceToDocumentMap.put(doc.getRelevanceValue(), queryIDToText);
    }
  }

	/**
	 * TODO 1. Compute Cosine Similarity and rank the retrieved sentences 2.
	 * Compute the MRR metric
	 */
	@Override
	public void collectionProcessComplete(ProcessTrace trace)
			throws ResourceProcessException, IOException 
	{
		super.collectionProcessComplete(trace);

		// Compute cosine similarity
		HashMap<String, Double> cosineSimilarities = new HashMap<String, Double>();
		HashMap<Integer, HashSet<String>> queryMap = relevanceToDocumentMap.get(QUERY_TYPE);
		
    HashMap<Integer, HashSet<String>> correctAnswerMap = relevanceToDocumentMap.get(CORRECT_TYPE);
    Set<Integer> correctQueryIDs = correctAnswerMap.keySet();
    
    HashMap<Integer, HashSet<String>> incorrectAnswerMap = relevanceToDocumentMap.get(INCORRECT_TYPE);
    Set<Integer> incorrectQueryIDs = incorrectAnswerMap.keySet();

		Set<Integer> queryQueryIDs = queryMap.keySet();
		for (Integer queryQueryID : queryQueryIDs)
		{
		  Iterator<String> iter = queryMap.get(queryQueryID).iterator();
		  String query = iter.next();
		  Map<String, Integer> queryVector = globalWordDictionary.get(query);
		  
		  // Correct Answers
	    computeCosineSimilarityForAnswers(cosineSimilarities, queryQueryID, query, queryVector,
	            correctAnswerMap, correctQueryIDs);
	    
	     // Incorrect Answers
	    computeCosineSimilarityForAnswers(cosineSimilarities, queryQueryID, query, queryVector,
	            incorrectAnswerMap, incorrectQueryIDs);
		}
		
		// Compute the rank of retrieved sentences
    ArrayList<String> rankedAnswers = rankAnswers(cosineSimilarities, correctAnswerMap, 
            incorrectAnswerMap, queryQueryIDs);
    
		
		// Print score, rank, query ID, relevance and sentence
    for (Integer correctQueryID : correctQueryIDs)
    {
      HashSet<String> answers = correctAnswerMap.get(correctQueryID);
      for (String answer : answers)
      {
        System.out.printf("Score: %.8f \t", cosineSimilarities.get(answer));
        System.out.print("rel=" + CORRECT_TYPE + " qid=" + correctQueryID + " " + answer);
        System.out.println();
        System.out.println();
      }
    }
    
		// TODO :: compute the metric:: mean reciprocal rank
		double metric_mrr = compute_mrr();
		System.out.println(" (MRR) Mean Reciprocal Rank ::" + metric_mrr);
	}

  private ArrayList<String> rankAnswers(HashMap<String, Double> cosineSimilarities,
          HashMap<Integer, HashSet<String>> correctAnswerMap,
          HashMap<Integer, HashSet<String>> incorrectAnswerMap, Set<Integer> queryQueryIDs) 
  {
    ArrayList<String> rankedAnswers = new ArrayList<String>();
		for (Integer queryID : queryQueryIDs)
		{
		  HashSet<String> correctAnswers = new HashSet<String>();
		  HashSet<String> incorrectAnswers = new HashSet<String>();
		  HashSet<String> answers = new HashSet<String>();
		  
		  if (correctAnswerMap.containsKey(queryID))
		  {
		    correctAnswers = correctAnswerMap.get(queryID);
		  }
		  
		  if (incorrectAnswerMap.containsKey(queryID))
		  {
		    incorrectAnswers = incorrectAnswerMap.get(queryID);
		  }
		  
		  answers.addAll(correctAnswers);
		  answers.addAll(incorrectAnswers);
		  
		  Iterator<String> unprocessedIter = answers.iterator();
      
      while (unprocessedIter.hasNext())
      {
        String nextUnprocessed = unprocessedIter.next();
        rankedAnswers.add(nextUnprocessed);
        
        for (int i = 0; i < rankedAnswers.size(); i++)
        {
          if (i < (rankedAnswers.size() - 1) 
           && cosineSimilarities.get(nextUnprocessed) > cosineSimilarities.get(rankedAnswers.get(i))
           && cosineSimilarities.get(nextUnprocessed) < cosineSimilarities.get(rankedAnswers.get(i + 1)))
          {
            rankedAnswers.remove(rankedAnswers.size() - 1);
            rankedAnswers.add(i, nextUnprocessed);
          }
        }
        
        unprocessedIter.remove();
      }
		}
		
		return rankedAnswers;
  }

  private void computeCosineSimilarityForAnswers(HashMap<String, Double> cosineSimilarities, 
          Integer queryQueryID, String query, Map<String, Integer> queryVector, HashMap<Integer, 
          HashSet<String>> answerMap, Set<Integer> answerQueryIDs) 
  {
    for (Integer answerQueryID : answerQueryIDs)
    {
      if (answerQueryID == queryQueryID)
      {
        HashSet<String> sentences = answerMap.get(answerQueryID);
        for (String sentence : sentences)
        {
          Map<String, Integer> docVector = globalWordDictionary.get(sentence);
          cosineSimilarities.put(sentence, computeCosineSimilarity(queryVector, docVector));
        }
      }
    }
  }

	/**
	 * Calculates the cosine similarity between the queryVector and 
	 * the document vector.
	 * @return cosine_similarity Calculated cosine similarity value.
	 */
  private double computeCosineSimilarity(Map<String, Integer> queryVector,
			Map<String, Integer> docVector) 
	{		
		ArrayList<Integer> queryFrequencyVector = new ArrayList<Integer>();
    ArrayList<Integer> docFrequencyVector = new ArrayList<Integer>();
		
		Set<String> words = new HashSet<String>();
		words.addAll(queryVector.keySet());
		words.addAll(docVector.keySet());
		
		for (String word : words)
		{
		  if (queryVector.containsKey(word))
		  {
	      queryFrequencyVector.add(queryVector.get(word));
		  }
		  else
		  {
        queryFrequencyVector.add(0);
		  }
		  
		  if (docVector.containsKey(word))
		  {
		    docFrequencyVector.add(docVector.get(word));
		  }
		  else
		  {
		    docFrequencyVector.add(0);
		  }
		}
		
		double dotProduct = 0;
		double queryFrequenciesMagnitude = 0;
		double docFrequenciesMagnitude = 0;
		
		for (int i = 0; i < queryFrequencyVector.size(); i++)
		{
		  dotProduct += queryFrequencyVector.get(i) * docFrequencyVector.get(i);
		  queryFrequenciesMagnitude += queryFrequencyVector.get(i) * 
		          queryFrequencyVector.get(i);
		  docFrequenciesMagnitude += docFrequencyVector.get(i) * 
		          docFrequencyVector.get(i);
		}
		
		queryFrequenciesMagnitude = Math.sqrt(queryFrequenciesMagnitude);
		docFrequenciesMagnitude = Math.sqrt(docFrequenciesMagnitude);
		
		return dotProduct/(queryFrequenciesMagnitude * docFrequenciesMagnitude);
	}

	/**
	 * 
	 * @return mrr
	 */
	private double compute_mrr() 
	{
		double metric_mrr=0.0;

		// TODO :: compute Mean Reciprocal Rank (MRR) of the text collection
		
		return metric_mrr;
	}

}
