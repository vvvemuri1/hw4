
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
 * Updated by JCasGen Fri Nov 01 14:56:08 EDT 2013
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
  final Feature casFeat_relevanceValue;
  /** @generated */
  final int     casFeatCode_relevanceValue;
  /** @generated */ 
  public int getRelevanceValue(int addr) {
        if (featOkTst && casFeat_relevanceValue == null)
      jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getIntValue(addr, casFeatCode_relevanceValue);
  }
  /** @generated */    
  public void setRelevanceValue(int addr, int v) {
        if (featOkTst && casFeat_relevanceValue == null)
      jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setIntValue(addr, casFeatCode_relevanceValue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_queryId;
  /** @generated */
  final int     casFeatCode_queryId;
  /** @generated */ 
  public int getQueryId(int addr) {
        if (featOkTst && casFeat_queryId == null)
      jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getIntValue(addr, casFeatCode_queryId);
  }
  /** @generated */    
  public void setQueryId(int addr, int v) {
        if (featOkTst && casFeat_queryId == null)
      jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setIntValue(addr, casFeatCode_queryId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_bigramList;
  /** @generated */
  final int     casFeatCode_bigramList;
  /** @generated */ 
  public int getBigramList(int addr) {
        if (featOkTst && casFeat_bigramList == null)
      jcas.throwFeatMissing("bigramList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return ll_cas.ll_getRefValue(addr, casFeatCode_bigramList);
  }
  /** @generated */    
  public void setBigramList(int addr, int v) {
        if (featOkTst && casFeat_bigramList == null)
      jcas.throwFeatMissing("bigramList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    ll_cas.ll_setRefValue(addr, casFeatCode_bigramList, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Document_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_tokenList = jcas.getRequiredFeatureDE(casType, "tokenList", "uima.cas.FSList", featOkTst);
    casFeatCode_tokenList  = (null == casFeat_tokenList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenList).getCode();

 
    casFeat_relevanceValue = jcas.getRequiredFeatureDE(casType, "relevanceValue", "uima.cas.Integer", featOkTst);
    casFeatCode_relevanceValue  = (null == casFeat_relevanceValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relevanceValue).getCode();

 
    casFeat_queryId = jcas.getRequiredFeatureDE(casType, "queryId", "uima.cas.Integer", featOkTst);
    casFeatCode_queryId  = (null == casFeat_queryId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryId).getCode();

 
    casFeat_bigramList = jcas.getRequiredFeatureDE(casType, "bigramList", "uima.cas.FSList", featOkTst);
    casFeatCode_bigramList  = (null == casFeat_bigramList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_bigramList).getCode();

  }
}



    