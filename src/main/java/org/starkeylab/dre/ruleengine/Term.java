package org.starkeylab.dre.ruleengine;

public class Term {

  /** property name */
  private String name = "";

  /** property value */
  private Object value = "";


  public Term() { }



  /**
   * @param name property name
   * @param value property value
   */
  public Term(String name,Object value) {
    this.name = name;
    this.value = value;
  }


  /**
   * @param name property name
   * value property is set to name
   */
  public Term(String name) {
    this.name = name;
    this.value = name;
  }


  /**
   * @param name property name
   * @param value property value
   */
  public final void setTerm(String name,String value) {
    this.name = name;
    this.value = value;
  }


  /**
   * @param name property name
   * value property is set to name
   */
  public final void setTerm(String name) {
    this.name = name;
    this.value = name;
  }


  /**
   * @return property name
   */
  public final String getName() {
    return name;
  }


  /**
   * @return property value
   */
  public final Object getValue() {
    return value;
  }


  /**
   * Set property name.
   * @param name property name
   */
  public final void setName(String name) {
    this.name = name;
  }


  /**
   * Set property value.
   * @param value property value
   */
  public final void setValue(Object value) {
    this.value = value;
  }


}