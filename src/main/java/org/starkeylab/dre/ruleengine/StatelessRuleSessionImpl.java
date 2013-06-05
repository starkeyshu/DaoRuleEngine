package org.starkeylab.dre.ruleengine;

import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import javax.rules.*;

public class StatelessRuleSessionImpl  implements StatelessRuleSession {

  /** stateful session */
  private StatefulRuleSessionImpl session;

  /** rules */
  private RuleExecutionSetImpl ruleSet;


  public StatelessRuleSessionImpl(RuleExecutionSetImpl res, Map properties) {
    session = new StatefulRuleSessionImpl(res, properties);
    this.ruleSet = res;
  }


  /**
   * Executes the rules in the bound rule execution set using the supplied list of objects.
   */
  public final List executeRules(List objects) throws InvalidRuleSessionException {
    return executeRules(objects, ruleSet.resolveObjectFilter());
  }


  /**
   * Executes the rules in the bound rule execution set using the supplied list of objects.
   */
  public final List executeRules(List objects, ObjectFilter filter)
    throws InvalidRuleSessionException {
    session.reset();
    session.addObjects(objects);
    session.executeRules();
    return session.getObjects(filter);
  }


  public final RuleExecutionSetMetadata getRuleExecutionSetMetadata() throws InvalidRuleSessionException {
    return session.getRuleExecutionSetMetadata();
  }


  public final void release() throws InvalidRuleSessionException {
    session.release();
    session = null;
  }


  public final int getType() throws InvalidRuleSessionException {
    session.validateRuleSession();
    return RuleRuntime.STATELESS_SESSION_TYPE;
  }


  /**
   * @return method not included into JSR specifications: used to access to working memory
   */
  public final Hashtable getWorkingMemory() {
    return session.getWorkingMemory();
  }


}