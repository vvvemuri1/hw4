

/* First created by JCasGen Tue Oct 22 12:08:05 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_vvv.typesystems;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Oct 22 12:08:50 EDT 2013
 * XML source: /Users/vvvemuri1/git/hw4/hw4-vvv/src/main/resources/descriptors/typesystems/VectorSpaceTypes.xml
 * @generated */
public class Query extends BaseAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Query.class);
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
  protected Query() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Query(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Query(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Query(JCas jcas, int begin, int end) {
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
  //* Feature: queryId

  /** getter for queryId - gets 
   * @generated */
  public int getQueryId() {
    if (Query_Type.featOkTst && ((Query_Type)jcasType).casFeat_queryId == null)
      jcasType.jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Query_Type)jcasType).casFeatCode_queryId);}
    
  /** setter for queryId - sets  
   * @generated */
  public void setQueryId(int v) {
    if (Query_Type.featOkTst && ((Query_Type)jcasType).casFeat_queryId == null)
      jcasType.jcas.throwFeatMissing("queryId", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Query");
    jcasType.ll_cas.ll_setIntValue(addr, ((Query_Type)jcasType).casFeatCode_queryId, v);}    
  }

    