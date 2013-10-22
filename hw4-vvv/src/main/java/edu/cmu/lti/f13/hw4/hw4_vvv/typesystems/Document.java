

/* First created by JCasGen Fri Oct 11 01:58:03 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_vvv.typesystems;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.cas.FSList;

/** 
 * Updated by JCasGen Tue Oct 22 12:08:50 EDT 2013
 * XML source: /Users/vvvemuri1/git/hw4/hw4-vvv/src/main/resources/descriptors/typesystems/VectorSpaceTypes.xml
 * @generated */
public class Document extends BaseAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Document.class);
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
  protected Document() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Document(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Document(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Document(JCas jcas, int begin, int end) {
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
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Document_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    jcasType.ll_cas.ll_setStringValue(addr, ((Document_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: tokenList

  /** getter for tokenList - gets 
   * @generated */
  public FSList getTokenList() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets  
   * @generated */
  public void setTokenList(FSList v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: relevance

  /** getter for relevance - gets 
   * @generated */
  public Relevance getRelevance() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relevance == null)
      jcasType.jcas.throwFeatMissing("relevance", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return (Relevance)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_relevance)));}
    
  /** setter for relevance - sets  
   * @generated */
  public void setRelevance(Relevance v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_relevance == null)
      jcasType.jcas.throwFeatMissing("relevance", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_relevance, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: query

  /** getter for query - gets 
   * @generated */
  public Query getQuery() {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_query == null)
      jcasType.jcas.throwFeatMissing("query", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    return (Query)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Document_Type)jcasType).casFeatCode_query)));}
    
  /** setter for query - sets  
   * @generated */
  public void setQuery(Query v) {
    if (Document_Type.featOkTst && ((Document_Type)jcasType).casFeat_query == null)
      jcasType.jcas.throwFeatMissing("query", "edu.cmu.lti.f13.hw4.hw4_vvv.typesystems.Document");
    jcasType.ll_cas.ll_setRefValue(addr, ((Document_Type)jcasType).casFeatCode_query, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    