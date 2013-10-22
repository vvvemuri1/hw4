
/* First created by JCasGen Tue Oct 22 12:08:05 EDT 2013 */
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
public class Query_Type extends BaseAnnotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Query_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Query_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Query(addr, Query_Type.this);
  			   Query_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Query(addr, Query_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Query.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query");



  /** @generated */
  final Feature casFeat_queryId;
  /** @generated */
  final int     casFeatCode_queryId;
  /** @generated */ 
  public int getQueryId(int addr) {
        if (featOkTst && casFeat_queryId == null)
      jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query");
    return ll_cas.ll_getIntValue(addr, casFeatCode_queryId);
  }
  /** @generated */    
  public void setQueryId(int addr, int v) {
        if (featOkTst && casFeat_queryId == null)
      jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query");
    ll_cas.ll_setIntValue(addr, casFeatCode_queryId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Query_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_queryId = jcas.getRequiredFeatureDE(casType, "queryId", "uima.cas.Integer", featOkTst);
    casFeatCode_queryId  = (null == casFeat_queryId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_queryId).getCode();

  }
}



    