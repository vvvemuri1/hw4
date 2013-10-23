package edu.cmu.lti.f13.hw4.hw4_vvv.collectionreaders;

import java.util.ArrayList;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document;

/**
 * This class extracts the query ID, relevant value and
 * sentence text from a document.
 * @author Vinay Vyas Vemuri
 */
public class DocumentReader extends JCasAnnotator_ImplBase  {
  
  /**
   * Extracts the queryID, relevance value and sentence text
   * from a document.
   * @param jcas JCas object that provides access to the CAS.
   * @return void
   */
	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		
		// reading sentence from the CAS 
		String sLine = jcas.getDocumentText();

		ArrayList<String> docInfo = parseDataLine(sLine);
		
		//This is to make sure that parsing done properly and 
		//minimal data for rel,qid,text are available to proceed 
		if(docInfo.size()<3)
		{
			System.err.println("Not enough information in the line");
			return;
		}
		
		int rel = Integer.parseInt(docInfo.get(0));
		int qid = Integer.parseInt(docInfo.get(1));
		String txt = docInfo.get(2);
				
		Document doc = new Document(jcas);
		doc.setText(txt);
		doc.setQueryId(qid);
		
		//Setting relevance value
		doc.setRelevanceValue(rel);
		doc.addToIndexes();
		
		//Adding populated FeatureStructure to CAS
		jcas.addFsToIndexes(doc);
	}

	/**
	 * Helper function that parses a single data line from a document and gets an
	 * ArrayList of values that can be used to retrieve the query ID, relevance
	 * value and sentence text.
	 * @param line Line of text in a document from the document collection.
	 * @return ArrayList of values containing the query ID, relevance value and
	 *         sentence text.
	 */
	private static ArrayList<String> parseDataLine(String line) 
	{
		ArrayList<String> docInfo;

		String [] rec  = line.split("[\\t]");
		String    sResQid = (rec[0]).replace("qid=", "");
		String    sResRel = (rec[1]).replace("rel=", "");
		
		StringBuffer sResTxt = new StringBuffer();
		for (int i=2; i<rec.length; i++) 
		{
			sResTxt.append(rec[i]).append(" ");					
		}

		docInfo = new ArrayList<String>();
		docInfo.add(sResRel);
		docInfo.add(sResQid);
		docInfo.add(sResTxt.toString());
		
		return docInfo;
	}

}