package edu.cmu.lti.f13.hw4.hw4_vvv.annotators;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_vvv.utils.Utils;

/**
 * This class extracts the token text and the token frequency values for each
 * token in the sentence.
 * @author Vinay Vyas Vemuri
 */
public class DocumentVectorAnnotator extends JCasAnnotator_ImplBase {

  /**
   * Extracts the token text and token frequency values for each token
   * in a document.
   * @param jcas JCas object that provides access to the CAS.
   * @return void
   */
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException 
	{
		FSIterator<Annotation> iter = jcas.getAnnotationIndex().iterator();
		if (iter.isValid()) 
		{
			iter.moveToNext();
			Document doc = (Document) iter.get();
			createTermFreqVector(jcas, doc);
			createBigramsList(jcas, doc);
		}
	}
	
	/**
	 * Constructs a list of bigrams from the document text.
	 * @param jcas JCas object that provides access to the CAS.
	 * @param doc Document from which we wish to get a list of
	 *            bigrams.
	 * @return void
	 */
	private void createBigramsList(JCas jcas, Document doc)
	{
    Collection<Bigram> bigramList = new LinkedList<Bigram>();
    
    String strippedInput = doc.getText().replaceAll(",", "");
    strippedInput = strippedInput.replaceAll("'", "");
    strippedInput = strippedInput.replaceAll(";", "");
    strippedInput = strippedInput.replaceAll("\\.", "");
    
    StringTokenizer st = new StringTokenizer(strippedInput);
    
    String prevToken = null;
    while (st.hasMoreTokens())
    {
      String token = st.nextToken();
      
      if (prevToken != null)
      {
        Bigram bigram = new Bigram(jcas);
        bigram.setFirstToken(prevToken.toUpperCase());
        bigram.setSecondToken(token.toUpperCase());
        bigramList.add(bigram);
      }      
      
      prevToken = token;
    }
           	   
    FSList bigrams = Utils.fromCollectionToFSList(jcas, bigramList);
    doc.setBigramList(bigrams);
	}
	
	/**
	 * Constructs a list of Tokens and populates them with the token text,
	 * frequency of the token in the sentence and the query ID.
	 * @param jcas JCas object that provides access to the CAS.
	 * @param doc Document whose token texts and token frequencies we wish
	 *            to determine.
	 * @return void
	 */
	private void createTermFreqVector(JCas jcas, Document doc) 
	{
		String docText = doc.getText();
		StringTokenizer st = new StringTokenizer(docText);
		Collection<Token> tokenList = new LinkedList<Token>();
		
		HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
		
		while (st.hasMoreTokens())
		{
		  String tokenText = st.nextToken();
		  tokenText = tokenText.toUpperCase();
		  
		  Token token = new Token(jcas);
		  token.setText(tokenText);
		  
		  if (wordFrequency.containsKey(tokenText))
		  {
		    wordFrequency.put(token.getText(), 
		            wordFrequency.get(token.getText()) + 1);
		  }
		  else
		  {
		    wordFrequency.put(token.getText(), 1);
		    tokenList.add(token);
		  }
		}
		
		for (Token token : tokenList)
		{
		  token.setFrequency(wordFrequency.get(token.getText()));
		  token.setQueryId(doc.getQueryId());
		}
		
		FSList tokens = Utils.fromCollectionToFSList(jcas, tokenList);
		doc.setTokenList(tokens);
	}
}
