package org.starkeylab.dre.ruleengine;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.rules.*;

public class RuleRuntimeImpl  implements RuleRuntime {

    public RuleRuntimeImpl() { }


    public RuleSession createRuleSession(String uri, Map properties, int ruleSessionType)
        throws RuleSessionTypeUnsupportedException, RuleSessionCreateException, RuleExecutionSetNotFoundException, RemoteException {
      RuleExecutionSetImpl res = RuleAdministratorImpl.lookup(uri);
      if(res == null)
          throw new RuleExecutionSetNotFoundException(uri);
      switch(ruleSessionType) {
        case RuleRuntime.STATELESS_SESSION_TYPE:
          return new StatelessRuleSessionImpl(res, properties);
        case RuleRuntime.STATEFUL_SESSION_TYPE:
          return new StatefulRuleSessionImpl(res, properties);
      }
      String message = String.valueOf(ruleSessionType);
      throw new RuleSessionTypeUnsupportedException(message);
    }


    public List getRegistrations()
        throws RemoteException
    {
        return RuleAdministratorImpl.getRegistrations();
    }

}