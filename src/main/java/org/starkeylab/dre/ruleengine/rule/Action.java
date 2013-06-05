package org.starkeylab.dre.ruleengine.rule;

import java.util.List;
import java.io.Serializable;

public class Action implements Serializable {

  /** method to execute */
  private String method;

  /** argument values of the method to execute */
  private List values;

  /**
   * @param method one argument method to execute
   * @param values argument values of the method to execute
   */
  public Action(String method,List values) {
    this.method = method;
    this.values = values;
  }


  /**
   * @return left term of the assumption
   */
  public final String getMethod() {
    return method;
  }


  /**
   * @return argument value of the method to execute
   */
  public final List getValues() {
    return values;
  }


}
