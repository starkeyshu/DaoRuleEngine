package org.starkeylab.dre.ruleengine;

import javax.rules.RuleExecutionSetMetadata;

public class RuleExecutionSetMetadataImpl implements RuleExecutionSetMetadata {

  /** rule execution set URI */
  private String uri;

  /** rule execution set name */
  private String name;

  /** rule execution set description */
  private String description;


  /**
   * @param impl rule execution set implementation
   */
  RuleExecutionSetMetadataImpl(RuleExecutionSetImpl impl) {
    name = impl.getName();
    description = impl.getDescription();
    uri = impl.getUri();
  }


  /**
   * @return rule execution set URI
   */
  public final String getUri() {
    return uri;
  }


  /**
   * @return rule execution set name
   */
  public final String getName() {
    return name;
  }


  /**
   * @return rule execution set description
   */
  public final String getDescription() {
    return description;
  }

}
