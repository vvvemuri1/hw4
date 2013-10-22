
/* First created by JCasGen Tue Oct 22 12:06:23 EDT 2013 */
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
public class Relevance_Type extends BaseAnnotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Relevance_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Relevance_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Relevance(addr, Relevance_Type.this);
  			   Relevance_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Relevance(addr, Relevance_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Relevance.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance");
 
  /** @generated */
  final Feature casFeat_relevanceValue;
  /** @generated */
  final int     casFeatCode_relevanceValue;
  /** @generated */ 
  public int getRelevanceValue(int addr) {
        if (featOkTst && casFeat_relevanceValue == null)
      jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance");
    return ll_cas.ll_getIntValue(addr, casFeatCode_relevanceValue);
  }
  /** @generated */    
  public void setRelevanceValue(int addr, int v) {
        if (featOkTst && casFeat_relevanceValue == null)
      jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance");
    ll_cas.ll_setIntValue(addr, casFeatCode_relevanceValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Relevance_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_relevanceValue = jcas.getRequiredFeatureDE(casType, "relevanceValue", "uima.cas.Integer", featOkTst);
    casFeatCode_relevanceValue  = (null == casFeat_relevanceValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_relevanceValue).getCode();

  }
}



    