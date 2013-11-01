

/* First created by JCasGen Wed Oct 30 12:43:31 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_vvv.typesystems;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** A bigram type containing two consecutive tokens
 * Updated by JCasGen Fri Nov 01 14:56:08 EDT 2013
 * XML source: /Users/vvvemuri1/git/hw4/hw4-vvv/src/main/resources/descriptors/typesystems/VectorSpaceTypes.xml
 * @generated */
public class Bigram extends BaseAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Bigram.class);
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
  protected Bigram() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Bigram(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Bigram(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Bigram(JCas jcas, int begin, int end) {
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
  //* Feature: firstToken

  /** getter for firstToken - gets First token of the bigram
   * @generated */
  public String getFirstToken() {
    if (Bigram_Type.featOkTst && ((Bigram_Type)jcasType).casFeat_firstToken == null)
      jcasType.jcas.throwFeatMissing("firstToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Bigram_Type)jcasType).casFeatCode_firstToken);}
    
  /** setter for firstToken - sets First token of the bigram 
   * @generated */
  public void setFirstToken(String v) {
    if (Bigram_Type.featOkTst && ((Bigram_Type)jcasType).casFeat_firstToken == null)
      jcasType.jcas.throwFeatMissing("firstToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    jcasType.ll_cas.ll_setStringValue(addr, ((Bigram_Type)jcasType).casFeatCode_firstToken, v);}    
   
    
  //*--------------*
  //* Feature: secondToken

  /** getter for secondToken - gets The second token of the bigram
   * @generated */
  public String getSecondToken() {
    if (Bigram_Type.featOkTst && ((Bigram_Type)jcasType).casFeat_secondToken == null)
      jcasType.jcas.throwFeatMissing("secondToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Bigram_Type)jcasType).casFeatCode_secondToken);}
    
  /** setter for secondToken - sets The second token of the bigram 
   * @generated */
  public void setSecondToken(String v) {
    if (Bigram_Type.featOkTst && ((Bigram_Type)jcasType).casFeat_secondToken == null)
      jcasType.jcas.throwFeatMissing("secondToken", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Bigram");
    jcasType.ll_cas.ll_setStringValue(addr, ((Bigram_Type)jcasType).casFeatCode_secondToken, v);}    
  }

    