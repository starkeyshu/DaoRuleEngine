package org.starkeylab.dre.ruleengine;

import java.rmi.RemoteException;
import java.util.*;
import javax.rules.admin.*;

public class RuleAdministratorImpl implements RuleAdministrator {

  /** rule execution sets */
  private static Map ruleExecutionSets = Collections.synchronizedMap(new HashMap());


  public RuleAdministratorImpl() { }


  /**
   * Creates a RuleExecutionSetProvider instance.
   * @param properties not supported
   * @return RuleExecutionSetProvider instance
   */
  public final RuleExecutionSetProvider getRuleExecutionSetProvider(Map properties) throws RemoteException {
      return new RuleExecutionSetProviderImpl();
  }


  /**
   * Creates a LocalRuleExecutionSetProvider instance.
   * @param properties not supported
   * @return LocalRuleExecutionSetProvider instance.
   */
  public final LocalRuleExecutionSetProvider getLocalRuleExecutionSetProvider(Map properties)
      throws RemoteException, UnsupportedOperationException {
    return new LocalRuleExecutionSetProviderImpl();
  }


  /**
   * Register a rule execution set.
   * @param bindUri rule execution set name
   * @param set rule execution set
   * @param properties not supported
   */
  public final void registerRuleExecutionSet(String bindUri, RuleExecutionSet set, Map properties)
      throws RuleExecutionSetRegisterException, RemoteException {
    if(!(set instanceof RuleExecutionSetImpl)) {
      throw new RuleExecutionSetRegisterException("Wrong driver");
    }
    else {
        ((RuleExecutionSetImpl)set).setUri(bindUri);
        ruleExecutionSets.put(bindUri, set);
        return;
    }
  }


  /**
   * De-register a rule execution set.
   * @param bindUri rule execution set name
   * @param properties not supported
   */
  public final void deregisterRuleExecutionSet(String bindUri, Map properties)
      throws RuleExecutionSetDeregistrationException, RemoteException {
    RuleExecutionSetImpl set = (RuleExecutionSetImpl)ruleExecutionSets.remove(bindUri);
    if(set != null)
      set.setUri(null);
  }


  /**
   * @return rule execution set names
   */
  static List getRegistrations() {
    return new ArrayList(ruleExecutionSets.keySet());
  }


  /**
   * @param uri rule execution set name
   * @return rule execution set
   */
  static RuleExecutionSetImpl lookup(String uri) {
    return (RuleExecutionSetImpl)ruleExecutionSets.get(uri);
  }


}
