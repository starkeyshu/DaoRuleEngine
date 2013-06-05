package org.starkeylab.dre.ruleengine;

import javax.rules.*;
import javax.rules.admin.RuleAdministrator;

public class RuleServiceProviderImpl extends RuleServiceProvider {

    public RuleServiceProviderImpl() { }


    public RuleRuntime getRuleRuntime() throws ConfigurationException {
      try {
        return (RuleRuntime) createInstance("org.starkeylab.dre.ruleengine.RuleRuntimeImpl");
      }
      catch (Exception ex) {
        throw new ConfigurationException("Can't create RuleRuntime", ex);
      }
    }


    public RuleAdministrator getRuleAdministrator() throws ConfigurationException {
      try {
        return (RuleAdministrator) createInstance(
            "org.starkeylab.dre.ruleengine.RuleAdministratorImpl");
      }
      catch (Exception ex) {
        throw new ConfigurationException("Can't create RuleAdministrator", ex);
      }
    }


    static {
      try {
          RuleServiceProviderManager.registerRuleServiceProvider("org.starkeylab.dre.ruleengine", RuleServiceProviderImpl.class);
      }
      catch(ConfigurationException ce) {
          ce.printStackTrace();
      }
    }
}
