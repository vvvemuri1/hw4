
/* First created by JCasGen Wed Oct 30 12:43:31 EDT 2013 */
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

/** A bigram type containing two consecutive tokens
 * Updated by JCasGen Wed Oct 30 14:17:08 EDT 2013
 * @generated */
public class Bigram_Type extends BaseAnnotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Bigram_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Bigram_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Bigram(addr, Bigram_Type.this);
  			   Bigram_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Bigram(addr, Bigram_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Bigram.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
 
  /** @generated */
  final Feature casFeat_firstToken;
  /** @generated */
  final int     casFeatCode_firstToken;
  /** @generated */ 
  public int getFirstToken(int addr) {
        if (featOkTst && casFeat_firstToken == null)
      jcas.throwFeatMissing("firstToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_firstToken);
  }
  /** @generated */    
  public void setFirstToken(int addr, int v) {
        if (featOkTst && casFeat_firstToken == null)
      jcas.throwFeatMissing("firstToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    ll_cas.ll_setRefValue(addr, casFeatCode_firstToken, v);}
    
  
 
  /** @generated */
  final Feature casFeat_secondToken;
  /** @generated */
  final int     casFeatCode_secondToken;
  /** @generated */ 
  public int getSecondToken(int addr) {
        if (featOkTst && casFeat_secondToken == null)
      jcas.throwFeatMissing("secondToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_secondToken);
  }
  /** @generated */    
  public void setSecondToken(int addr, int v) {
        if (featOkTst && casFeat_secondToken == null)
      jcas.throwFeatMissing("secondToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    ll_cas.ll_setRefValue(addr, casFeatCode_secondToken, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Bigram_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_firstToken = jcas.getRequiredFeatureDE(casType, "firstToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Token", featOkTst);
    casFeatCode_firstToken  = (null == casFeat_firstToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_firstToken).getCode();

 
    casFeat_secondToken = jcas.getRequiredFeatureDE(casType, "secondToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Token", featOkTst);
    casFeatCode_secondToken  = (null == casFeat_secondToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_secondToken).getCode();

  }
}



    