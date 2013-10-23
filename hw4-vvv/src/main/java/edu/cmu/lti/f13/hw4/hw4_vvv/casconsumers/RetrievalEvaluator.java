package edu.cmu.lti.f13.hw4.hw4_vvv.casconsumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	 * Initializes the list of query IDS, the list of relevance values and the
	 * Global word dictionary.
	 * @return void
	 */
	public void initialize() throws ResourceInitializationException 
	{
		qIdList = new ArrayList<Integer>();
		relList = new ArrayList<Integer>();
		globalWordDictionary = new HashMap<String, HashMap<String, Integer>>();
	}

	/**
	 * Constructs the global word dictionary that maintains the list
	 * of all words and their frequencies in each sentence.
	 * @param aCas Cas Object
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
	    populateGlobalDictionary(fsTokenList);
		}
	}

	/**
	 * Populate the Global dictionary using the tokens in the document
	 * sentence.
	 * @param fsTokenList List of tokens in the document sentence
	 */
  private void populateGlobalDictionary(FSList fsTokenList) 
  {
    String sentence = "";
    HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
    ArrayList<Token> tokenList = Utils.fromFSListToCollection(fsTokenList, 
            Token.class);
    
    for (Token token : tokenList)
    {              
      wordFrequencies.put(token.getText(), token.getFrequency());
      sentence += " " + token.getText();
    }
    
    if (sentence.length() != 0)
    {
      sentence = sentence.substring(1);
    }
    
    globalWordDictionary.put(sentence, wordFrequencies);
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

		// TODO :: compute the cosine similarity measure

		
		// TODO :: compute the rank of retrieved sentences
		
		
		// TODO :: compute the metric:: mean reciprocal rank
		double metric_mrr = compute_mrr();
		System.out.println(" (MRR) Mean Reciprocal Rank ::" + metric_mrr);
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
