

/* First created by JCasGen Tue Oct 22 12:06:23 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_vvv.typesystems;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Oct 22 12:08:50 EDT 2013
 * XML source: /Users/vvvemuri1/git/hw4/hw4-vvv/src/main/resources/descriptors/typesystems/VectorSpaceTypes.xml
 * @generated */
public class Relevance extends BaseAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Relevance.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Relevance() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Relevance(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Relevance(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Relevance(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: relevanceValue

  /** getter for relevanceValue - gets 
   * @generated */
  public int getRelevanceValue() {
    if (Relevance_Type.featOkTst && ((Relevance_Type)jcasType).casFeat_relevanceValue == null)
      jcasType.jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Relevance_Type)jcasType).casFeatCode_relevanceValue);}
    
  /** setter for relevanceValue - sets  
   * @generated */
  public void setRelevanceValue(int v) {
    if (Relevance_Type.featOkTst && ((Relevance_Type)jcasType).casFeat_relevanceValue == null)
      jcasType.jcas.throwFeatMissing("relevanceValue", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Relevance");
    jcasType.ll_cas.ll_setIntValue(addr, ((Relevance_Type)jcasType).casFeatCode_relevanceValue, v);}    
  }

    