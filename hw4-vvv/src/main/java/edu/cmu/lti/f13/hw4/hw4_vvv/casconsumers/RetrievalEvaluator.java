package edu.cmu.lti.f13.hw4.hw4_vvv.casconsumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_vvv.utils.Utils;

public class RetrievalEvaluator extends CasConsumer_ImplBase 
{
  private static final Integer INCORRECT_TYPE = 0;
  private static final Integer CORRECT_TYPE = 1;
  private static final Integer QUERY_TYPE = 99;
  
	/** query id number **/
  private ArrayList<Integer> qIdList;

	/** query and text relevant values **/
	private ArrayList<Integer> relList;
	
	/** 
	 * Global word dictionary that keeps track of the words and 
	 * word frequencies in each sentence.
	 */
	private HashMap<String, HashMap<String, Integer>> globalWordDictionary;

	/**
	 * Hash Map of sentence to map of relevance values to Documents.
	 */
	private HashMap<Integer, HashMap<Integer, HashSet<String>>> relevanceToDocumentMap;
	
	/**
	 * Hash Map of sentence to rank
	 */
  private HashMap<String, Integer> sentenceToRankMap;
  	
  /**
   * Hash Map of sentence to collection of Bigrams
   */
  private HashMap<String, Collection<String>> textToBigramMap;
  
  /**
   * Hash Map of collection of bigrams to sentence
   */
  private HashMap<Collection<String>, String> bigramtoTextMap;
  
  /**
   * Hash Map of sentence to relevance value
   */
  private HashMap<String, Integer> sentenceToRelevanceMap;
  
  /**
   * Hash Map of sentence to queryID
   */
  private HashMap<String, Collection<Integer>> sentenceToQueryIDMap;

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
		sentenceToRankMap = new HashMap<String, Integer>();
		textToBigramMap = new HashMap<String, Collection<String>>();
		sentenceToRelevanceMap = new HashMap<String, Integer> ();
		sentenceToQueryIDMap = new HashMap<String, Collection<Integer>> ();
		bigramtoTextMap = new HashMap<Collection<String>, String>();
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
			
			// Add list of Bigrams corresponding to sentence to sentenceToBigramMap
			Collection<Bigram> bigrams = Utils.fromFSListToCollection(doc.getBigramList(), Bigram.class);
			
			// Add to textToBigramMap and sentenceToQueryIDMap
			HashSet<String> bigramStringCollection = new HashSet<String>();
			
			for (Bigram bigram : bigrams)
			{
		    String bigramStrings = "";
			  bigramStrings = bigram.getFirstToken();
			  bigramStrings += (bigram.getSecondToken());
		    bigramStringCollection.add(bigramStrings);
			}
			
			textToBigramMap.put(doc.getText(), bigramStringCollection);
			bigramtoTextMap.put(bigramStringCollection, doc.getText());
			
			if (sentenceToQueryIDMap.containsKey(doc.getText()))
			{
			  sentenceToQueryIDMap.get(doc.getText()).add(doc.getQueryId());
			}
			else
			{
			  HashSet<Integer> queryIDs = new HashSet<Integer>();
			  queryIDs.add(doc.getQueryId());
			  sentenceToQueryIDMap.put(doc.getText(), queryIDs);
			}
			
			// Add Sentence and relevance value to sentenceToRelevanceMap
			sentenceToRelevanceMap.put(doc.getText(), doc.getRelevanceValue());
			
			// Populate the global word dictionary			
	    populateGlobalDictionary(fsTokenList, doc.getText());
	    
	    // Populate the sentence to Query ID map
	    populateRelevanceToDocumentMap(doc);
		}
	}

	/**
	 * Populate the Global dictionary using the tokens in the document
	 * sentence.
	 * @param fsTokenList List of tokens in the document sentence.
	 * @param docText Text contained in the document.
	 * @return void
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
   * @param doc Document object for which we wish to construct a relevance to
   * document map.
   * @return void
   */
  private void populateRelevanceToDocumentMap(Document doc)
  {
    if (relevanceToDocumentMap.containsKey(doc.getRelevanceValue()))
    {
      HashMap<Integer, HashSet<String>> queryIDToText = 
              relevanceToDocumentMap.get(doc.getRelevanceValue());
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
      HashMap<Integer, HashSet<String>> queryIDToText = 
              new HashMap<Integer, HashSet<String>>();
      HashSet<String> set = new HashSet<String>();
     
      set.add(doc.getText());
      queryIDToText.put(doc.getQueryId(), set);
      
      relevanceToDocumentMap.put(doc.getRelevanceValue(), queryIDToText);
    }
  }

  /**
   * Calculates the cosine similarity for each sentence and ranks the retrieved
   * sentences. Uses the ranked sentences to calculate the MRR value.
   * @param trace Object that provides access to information about all the events
   * that have occurred.
   * @return void
   */
	@Override
	public void collectionProcessComplete(ProcessTrace trace)
			throws ResourceProcessException, IOException 
	{
		super.collectionProcessComplete(trace);

		// Compute cosine similarity
		computeCosineSimilarity();
		
		// Compute Dice Coefficient
		computeDiceCoefficient();
	}

	/**
	 * Helper function that computes the dice coefficient and outputs the MRR value.
	 */
  private void computeDiceCoefficient() 
  {
    System.out.println("Using Dice Coefficient:");
    
    Set<String> documentTexts = textToBigramMap.keySet();
    HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> queryIdToQuestionBigramToCorrectAnswerMap
        = new HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>>();
    HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> queryIdToQuestionBigramToWrongAnswerMap
        = new HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>>();
    
    populateQuestionBigramToAnswerMaps(documentTexts, queryIdToQuestionBigramToCorrectAnswerMap,
            queryIdToQuestionBigramToWrongAnswerMap);

    HashMap<Integer, HashMap<String, Double>> queryIdToCorrectAnswerToScoreMap
        = new HashMap<Integer, HashMap<String, Double>>();
    HashMap<Integer, HashMap<String, Double>> queryIdToWrongAnswerToScoreMap
        = new HashMap<Integer, HashMap<String, Double>>();

    computeDiceScore(queryIdToQuestionBigramToCorrectAnswerMap,
                     queryIdToQuestionBigramToWrongAnswerMap, 
                     queryIdToCorrectAnswerToScoreMap,
                     queryIdToWrongAnswerToScoreMap);
            
    HashMap<Integer, HashMap<String, Integer>> queryIdToCorrectAnswerToRankMap
        = new HashMap<Integer, HashMap<String, Integer>>();
    
    rankAnswersUsingDiceScore(queryIdToQuestionBigramToCorrectAnswerMap,
            queryIdToCorrectAnswerToScoreMap, queryIdToWrongAnswerToScoreMap,
            queryIdToCorrectAnswerToRankMap);
        
    Collection<Integer> queryIDs = queryIdToCorrectAnswerToRankMap.keySet();
    
    System.out.println(queryIdToCorrectAnswerToScoreMap);
    System.out.println(queryIdToWrongAnswerToScoreMap);
    
    for (Integer queryID : queryIDs)
    {
      Collection<String> correctAnswers = queryIdToCorrectAnswerToScoreMap.get(queryID).keySet();
      for (String answer : correctAnswers)
      {
        System.out.printf("Score: %.8f \t rank=%d ", queryIdToCorrectAnswerToScoreMap.get(queryID).get(answer), 
                (queryIdToCorrectAnswerToRankMap.get(queryID).get(answer)));
        System.out.print("rel=" + CORRECT_TYPE + " qid=" + queryID + " " + answer);
        System.out.println();
      }
    }
  }

  /**
   * Computes the Dice rank for all the answers.
   * @param queryIdToQuestionBigramToCorrectAnswerMap Map of Query ID to Question Bigram to Correct Answer
   * @param queryIdToCorrectAnswerToScoreMap Map of Query ID to Correct Answer to Score
   * @param queryIdToWrongAnswerToScoreMap Map of Query ID to Wrong Answer to Score
   * @param queryIdToCorrectAnswerToRankMap Map of Query ID to Correct Answer to Score
   */
  private void rankAnswersUsingDiceScore(
          HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> queryIdToQuestionBigramToCorrectAnswerMap,
          HashMap<Integer, HashMap<String, Double>> queryIdToCorrectAnswerToScoreMap,
          HashMap<Integer, HashMap<String, Double>> queryIdToWrongAnswerToScoreMap,
          HashMap<Integer, HashMap<String, Integer>> queryIdToCorrectAnswerToRankMap) {
    Set<Integer> queryIDs = queryIdToQuestionBigramToCorrectAnswerMap.keySet();
    
    for (Integer queryID : queryIDs)
    {
      final HashMap<String, Double> correctAnswerToScoreMap = queryIdToCorrectAnswerToScoreMap.get(queryID);
      final HashMap<String, Double> wrongAnswerToScoreMap = queryIdToWrongAnswerToScoreMap.get(queryID);
      
      Set<String> wrongAnswers = wrongAnswerToScoreMap.keySet();
      Set<String> correctAnswers = correctAnswerToScoreMap.keySet();
      
      Set<String> answers = new HashSet<String>();
      answers.addAll(wrongAnswers);
      answers.addAll(correctAnswers);
      
      List<String> answerList = new LinkedList<String>(answers);
      
      Collections.sort(answerList, new Comparator<String>()
                                   {
                                      @Override
                                      public int compare(String answer1, String answer2) 
                                      {
                                        double score1, score2;
                                        if (correctAnswerToScoreMap.containsKey(answer1))
                                        {
                                          score1 = correctAnswerToScoreMap.get(answer1);
                                        }
                                        else
                                        {
                                          score1 = wrongAnswerToScoreMap.get(answer1);
                                        }
                                        
                                        if (correctAnswerToScoreMap.containsKey(answer2))
                                        {
                                          score2 = correctAnswerToScoreMap.get(answer2);
                                        }
                                        else
                                        {
                                          score2 = wrongAnswerToScoreMap.get(answer2);
                                        }

                                        return (int) ((score2 - score1) * 100);
                                      }               
                                   }
      );
      
      int rank = 1;
      HashMap<String, Integer> correctAnswerToRankMap = new HashMap<String, Integer>();
      for (String answer : answerList)
      {
        correctAnswerToRankMap.put(answer, rank++);
      }
      
      for (String answer : answerList)
      {
        if (!(correctAnswers.contains(answer)))
        {
          correctAnswerToRankMap.remove(answer);
        }
      }
      
      queryIdToCorrectAnswerToRankMap.put(queryID, correctAnswerToRankMap);
    }
  }

  /**
   * Computes the Dice coefficient for all the answers.
   * @param queryIdToQuestionBigramToCorrectAnswerMap Map of Query ID to Question Bigram to Correct Answer
   * @param queryIdToQuestionBigramToWrongAnswerMap Map of Query ID to Question Bigram to Wrong Answer
   * @param queryIdToCorrectAnswerToScoreMap Map of Query ID to Correct Answer to Score 
   * @param queryIdToWrongAnswerToScoreMap Map of Query ID to Wrong Answer to Score
   */
  private void computeDiceScore(
      HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> queryIdToQuestionBigramToCorrectAnswerMap,
      HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> queryIdToQuestionBigramToWrongAnswerMap,
      HashMap<Integer, HashMap<String, Double>> queryIdToCorrectAnswerToScoreMap,
      HashMap<Integer, HashMap<String, Double>> queryIdToWrongAnswerToScoreMap) 
  {
    Set<Integer> queryIDs = queryIdToQuestionBigramToCorrectAnswerMap.keySet();
    for (Integer queryID : queryIDs)
    {
      HashMap<Collection<String>, Collection<Collection<String>>> questionBigramsToCorrectAnswerBigrams = 
              queryIdToQuestionBigramToCorrectAnswerMap.get(queryID);
      Collection<Collection<String>> questionBigramCollections = questionBigramsToCorrectAnswerBigrams.keySet();
      
      for (Collection<String> questionBigrams : questionBigramCollections)
      {
        Collection<Collection<String>> correctAnswerBigramCollections = 
                questionBigramsToCorrectAnswerBigrams.get(questionBigrams);
        for (Collection<String> correctAnswerBigrams : correctAnswerBigramCollections)
        {
          HashSet<String> qBigrams = new HashSet<String>(questionBigrams);
          HashSet<String> aBigrams = new HashSet<String>(correctAnswerBigrams);

          aBigrams.retainAll(qBigrams);
          double score = 2 * ((double)(aBigrams.size())/(questionBigrams.size() + correctAnswerBigrams.size()));
          String answer = bigramtoTextMap.get(correctAnswerBigrams);
                    
          HashMap<String, Double> correctAnswerToScoreMap;
          
          if (queryIdToCorrectAnswerToScoreMap.containsKey(queryID))
          {
            correctAnswerToScoreMap = queryIdToCorrectAnswerToScoreMap.get(queryID);
          }
          else
          {
            correctAnswerToScoreMap = new HashMap<String, Double>();
          }          
          
          correctAnswerToScoreMap.put(answer, score);
          queryIdToCorrectAnswerToScoreMap.put(queryID, correctAnswerToScoreMap);
        }
      }
    }
        
    queryIDs = queryIdToQuestionBigramToWrongAnswerMap.keySet();
    for (Integer queryID : queryIDs)
    {
      HashMap<Collection<String>, Collection<Collection<String>>> questionBigramsToWrongAnswerBigrams = 
              queryIdToQuestionBigramToWrongAnswerMap.get(queryID);
      Collection<Collection<String>> questionBigramCollections = 
              questionBigramsToWrongAnswerBigrams.keySet();

      for (Collection<String> questionBigrams : questionBigramCollections)
      {
        Collection<Collection<String>> wrongAnswerBigramCollections = 
                questionBigramsToWrongAnswerBigrams.get(questionBigrams);
        for (Collection<String> wrongAnswerBigrams : wrongAnswerBigramCollections)
        {
          HashSet<String> qBigrams = new HashSet<String>(questionBigrams);
          HashSet<String> aBigrams = new HashSet<String>(wrongAnswerBigrams);
          aBigrams.retainAll(qBigrams);
          
          double score = 2 * ((double)(aBigrams.size())/(questionBigrams.size() + 
                  wrongAnswerBigrams.size()));
          String answer = bigramtoTextMap.get(wrongAnswerBigrams);
          
          HashMap<String, Double> wrongAnswerToScoreMap;
          
          if (queryIdToWrongAnswerToScoreMap.containsKey(queryID))
          {
            wrongAnswerToScoreMap = queryIdToWrongAnswerToScoreMap.get(queryID);
          }
          else
          {
            wrongAnswerToScoreMap = new HashMap<String, Double>();
          }          
          
          wrongAnswerToScoreMap.put(answer, score);
          queryIdToWrongAnswerToScoreMap.put(queryID, wrongAnswerToScoreMap);
        }
      }
    }
  }

  /**
   * Populates the QuestionBigramToCorrectAnswer and QuestionBigramToWrongAnswer maps.
   * @param documentTexts Set of all documents
   * @param queryIdToQuestionBigramToCorrectAnswerMap Map of query ID to Question Bigram to Correct
   *          Answer.  
   * @param queryIdToQuestionBigramToWrongAnswerMap Map of query ID to Question Bigram to Wrong
   *          Answer.
   */
  private void populateQuestionBigramToAnswerMaps
  (Set<String> documentTexts,
  HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> 
    queryIdToQuestionBigramToCorrectAnswerMap,
  HashMap<Integer, HashMap<Collection<String>, Collection<Collection<String>>>> 
    queryIdToQuestionBigramToWrongAnswerMap) 
  {
    for (String doc: documentTexts)
    {      
      Collection<String> bigrams = textToBigramMap.get(doc);
      if (sentenceToRelevanceMap.get(doc) == QUERY_TYPE)
      {
        HashMap<Collection<String>, Collection<Collection<String>>> queryBigramsToCorrectAnswerBigramsMap = 
                new HashMap<Collection<String>, Collection<Collection<String>>>();
        HashMap<Collection<String>, Collection<Collection<String>>> queryBigramsToWrongAnswerBigramsMap = 
                new HashMap<Collection<String>, Collection<Collection<String>>>();
                
        queryBigramsToCorrectAnswerBigramsMap.put(bigrams, new HashSet<Collection<String>>());
        queryBigramsToWrongAnswerBigramsMap.put(bigrams, new HashSet<Collection<String>>());
        
        Collection<Integer> queryIDs = sentenceToQueryIDMap.get(doc);
        for (Integer queryID : queryIDs)
        {
          queryIdToQuestionBigramToCorrectAnswerMap.put(queryID, 
                  queryBigramsToCorrectAnswerBigramsMap);
          queryIdToQuestionBigramToWrongAnswerMap.put(queryID, 
                  queryBigramsToWrongAnswerBigramsMap);
        }
      }
    }
    
    for (String doc: documentTexts)
    {      
      Collection<String> bigrams = textToBigramMap.get(doc);
      Collection<Integer> queryIDs = sentenceToQueryIDMap.get(doc);
      
      for (Integer queryID : queryIDs)
      {
        if (sentenceToRelevanceMap.get(doc) == INCORRECT_TYPE)
        {
          HashMap<Collection<String>, Collection<Collection<String>>> queryBigramsToAnswerBigramsMap = 
                  queryIdToQuestionBigramToWrongAnswerMap.get(queryID);
          Collection<Collection<String>> queryBigramList = 
                  queryBigramsToAnswerBigramsMap.keySet();
          for (Collection<String> queryBigrams : queryBigramList)
          {
            if (queryIdToQuestionBigramToWrongAnswerMap.get(queryID).
                    containsKey(queryBigrams))
            {
              queryIdToQuestionBigramToWrongAnswerMap.get(queryID)
                .get(queryBigrams).add(bigrams);
            }
            else
            {
              HashSet<Collection<String>> bigramCollection = new HashSet<Collection<String>>();
              bigramCollection.add(bigrams);
              
              queryIdToQuestionBigramToWrongAnswerMap.get(queryID)
                .put(queryBigrams, bigramCollection);
            }
          }        
        }
        else if (sentenceToRelevanceMap.get(doc) == CORRECT_TYPE)
        {
          HashMap<Collection<String>, Collection<Collection<String>>> queryBigramsToAnswerBigramsMap = 
                  queryIdToQuestionBigramToCorrectAnswerMap.get(queryID);
          Collection<Collection<String>> queryBigramList = queryBigramsToAnswerBigramsMap.keySet();
          for (Collection<String> queryBigrams : queryBigramList)
          {
            if (queryIdToQuestionBigramToCorrectAnswerMap.get(queryID).
                    containsKey(queryBigrams))
            {
              queryIdToQuestionBigramToCorrectAnswerMap.get(queryID)
                .get(queryBigrams).add(bigrams);
            }
            else
            {
              HashSet<Collection<String>> bigramCollection = new HashSet<Collection<String>>();
              bigramCollection.add(bigrams);
              
              queryIdToQuestionBigramToCorrectAnswerMap.get(queryID)
                .put(queryBigrams, bigramCollection);
            }       
          }        
        }
      }
    }
  }
	
	/**
	 * Helper function that computes the cosine similarity and outputs the MRR value.
	 */
  private void computeCosineSimilarity() 
  {
    System.out.println("Using Cosine Similarity:");
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
    rankAnswers(cosineSimilarities, correctAnswerMap, incorrectAnswerMap, queryQueryIDs);
    
		// Print score, rank, query ID, relevance and sentence
    for (Integer correctQueryID : correctQueryIDs)
    {
      HashSet<String> answers = correctAnswerMap.get(correctQueryID);
      for (String answer : answers)
      {
        System.out.printf("Score: %.8f \t rank=%d ", cosineSimilarities.get(answer), 
                (sentenceToRankMap.get(answer)));
        System.out.print("rel=" + CORRECT_TYPE + " qid=" + correctQueryID + " " + answer);
        System.out.println();
      }
    }
    
		// Compute the mean reciprocal rank
		double metric_mrr = compute_mrr(queryQueryIDs.size());
		System.out.println(" (MRR) Mean Reciprocal Rank ::" + metric_mrr);
		System.out.println();
  }

	/**
	 * Helper function that ranks all the answers using their cosine scores and populates
	 * the sentenceToRankMap using the calculated ranks.
	 * @param cosineSimilarities Mapping from sentence to computed cosine similarity.
	 * @param correctAnswerMap Mapping from queryID to a set of correct answers.
	 * @param incorrectAnswerMap Mapping from queryID to a set of incorrect answers.
	 * @param queryQueryIDs List of query IDs across all the documents.
	 * @return void
	 */
  private void rankAnswers(HashMap<String, Double> cosineSimilarities,
          HashMap<Integer, HashSet<String>> correctAnswerMap,
          HashMap<Integer, HashSet<String>> incorrectAnswerMap, Set<Integer> queryQueryIDs) 
  {    
		for (Integer queryID : queryQueryIDs)
		{
	    ArrayList<String> rankedAnswers = new ArrayList<String>();

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
        
        boolean added = false;
        for (int i = 0; i < rankedAnswers.size(); i++)
        {
          if (cosineSimilarities.get(nextUnprocessed) > 
                cosineSimilarities.get(rankedAnswers.get(i)))
          {
            rankedAnswers.add(i, nextUnprocessed);
            added = true;
            break;
          }
        }
                
        if (!added)
        {
          rankedAnswers.add(nextUnprocessed);
        }
        
        unprocessedIter.remove();
      }
            
      for (int i = 0; i < rankedAnswers.size(); i++)
      {
        if (correctAnswers.contains(rankedAnswers.get(i)))
        {
          sentenceToRankMap.put(rankedAnswers.get(i), (i + 1));
        }
      }
		}
  }

  /**
   * Helper function that computes the cosine similarity for all the answers.
   * @param cosineSimilarities Map which is loaded with the cosine similarity values
   * @param queryQueryID Query ID of the query for whose answers we are computing cosine
   *        similarities. 
   * @param query Text contained in query
   * @param queryVector Mapping from token to token frequency
   * @param answerMap Mapping from answer query ID to a list of sentences.
   * @param answerQueryIDs List of all answer query IDs
   * @return void
   */
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
	 * Calculates the mean reciprocal rank for all sentences.
	 * @param numQueries Total number of queries.
	 * @return mrr Calculated Mean Reciprocal Rank value.
	 */
	private double compute_mrr(int numQueries) 
	{
		double metric_mrr = 0.0;
		double numerator = 0;
		Set<String> sentences = sentenceToRankMap.keySet();
		
		
		for (String sentence : sentences)
		{
		  numerator += (1f/(sentenceToRankMap.get(sentence)));
		}
		
		metric_mrr = numerator/numQueries;
		
		return metric_mrr;
	}

}
