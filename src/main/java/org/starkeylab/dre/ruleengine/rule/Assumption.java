package org.starkeylab.dre.ruleengine.rule;

import java.io.Serializable;

public class Assumption implements Serializable {

  /** used as operator */
  public static final String EQUALS_TO = "=";

  /** used as operator */
  public static final String LESS_THAN = "<";

  /** used as operator */
  public static final String LESS_OR_EQUALS_TO = "<=";

  /** used as operator */
  public static final String GREATER_THAN = ">";

  /** used as operator */
  public static final String GREATER_OR_EQUALS_TO = ">=";

  /** used as operator */
  public static final String NOT_EQUALS_TO = "<>";

  /** used as operator */
  public static final String EXISTS = "exists";

  /** used as operator */
  public static final String CONTAINS = "contains";

  /** used as operator */
  public static final String NOT_CONTAINS = "notcontains";

  /** used as operator */
  public static final String CONTAINSATLEASTONE = "containsatleastone";

  /** used as operator */
  public static final String NOT_CONTAINSANYONE = "notcontainsanyone";

  /** left term of the assumption */
  private String leftTerm;

  /** operator of the assumption; possible values: =,<,>,<=,>=,<> */
  private String operator;

  /** right term of the assumption */
  private String rightTerm;


  /**
   * @param uniqueTerm unique term of the assumption
   * operator is set to "exists"
   * rightTerm is set to ""
   */
  public Assumption(String uniqueTerm) {
    this.leftTerm = uniqueTerm;
    this.operator = "exists";
    this.rightTerm = "";
  }


  /**
   * @param leftTerm left term of the assumption
   * @param operator operator of the assumption; possible values: =,<,>,<=,>=,<>
   * @param rightTerm right term of the assumption
   */
  public Assumption(String leftTerm,String operator,String rightTerm) {
    this.leftTerm = leftTerm;
    this.operator = operator;
    this.rightTerm = rightTerm;
  }


  /**
   * @return left term of the assumption
   */
  public final String getLeftTerm() {
    return leftTerm;
  }


  /**
   * @return operator of the assumption; possible values: =,<,>,<=,>=,<>
   */
  public final String getOperator() {
    return operator;
  }


  /**
   * @return right term of the assumption
   */
  public final String getRightTerm() {
    return rightTerm;
  }

}