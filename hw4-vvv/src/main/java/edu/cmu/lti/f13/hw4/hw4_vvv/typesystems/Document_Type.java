
/* First created by JCasGen Fri Oct 11 01:58:03 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_vvv.typesystems;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Tue Oct 22 12:08:50 EDT 2013
 * @generated */
public class Document_Type extends BaseAnnotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Document_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Document_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Document(addr, Document_Type.this);
  			   Document_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Document(addr, Document_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Document.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
 
  /** @generated */
  final Feature casFeat_text;
  /** @generated */
  final int     casFeatCode_text;
  /** @generated */ 
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
  /** @generated */    
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tokenList;
  /** @generated */
  final int     casFeatCode_tokenList;
  /** @generated */ 
  public int getTokenList(int addr) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokenList);
  }
  /** @generated */    
  public void setTokenList(int addr, int v) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokenList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_relevance;
  /** @generated */
  final int     casFeatCode_relevance;
  /** @generated */ 
  public int getRelevance(int addr) {
        if (featOkTst && casFeat_relevance == null)
      jcas.throwFeatMissing("relevance", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_relevance);
  }
  /** @generated */    
  public void setRelevance(int addr, int v) {
        if (featOkTst && casFeat_relevance == null)
      jcas.throwFeatMissing("relevance", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_relevance, v);}
    
  
 
  /** @generated */
  final Feature casFeat_query;
  /** @generated */
  final int     casFeatCode_query;
  /** @generated */ 
  public int getQuery(int addr) {
        if (featOkTst && casFeat_query == null)
      jcas.throwFeatMissing("query", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_query);
  }
  /** @generated */    
  public void setQuery(int addr, int v) {
        if (featOkTst && casFeat_query == null)
      jcas.throwFeatMissing("query", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_query, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Document_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_tokenList = jcas.getRequiredFeatureDE(casType, "tokenList", "uima.cas.FSList", featOkTst);
    casFeatCode_tokenList  = (null == casFeat_tokenList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenList).getCode();

 
    casFeat_relevance = jcas.getRequiredFeatureDE(casType, "relevance", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance", featOkTst);
    casFeatCode_relevance  = (null == casFeat_relevance) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relevance).getCode();

 
    casFeat_query = jcas.getRequiredFeatureDE(casType, "query", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query", featOkTst);
    casFeatCode_query  = (null == casFeat_query) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_query).getCode();

  }
}



    