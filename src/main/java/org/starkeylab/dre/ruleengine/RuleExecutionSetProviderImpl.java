package org.starkeylab.dre.ruleengine;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.Map;
import javax.rules.admin.*;
import org.w3c.dom.Element;

public class RuleExecutionSetProviderImpl implements RuleExecutionSetProvider {


  public RuleExecutionSetProviderImpl() {}


  /**
   * Creates a RuleExecutionSet implementation from an XML Document and additional vendor-specific properties.
   */
  public RuleExecutionSet createRuleExecutionSet(Element docElement, Map properties)
      throws RuleExecutionSetCreateException, RemoteException {
    return (new LocalRuleExecutionSetProviderImpl()).createRuleExecutionSet(docElement, properties);
  }


  /**
   * Creates a RuleExecutionSet implementation from a vendor specific AST representation and vendor-specific properties.
   */
  public RuleExecutionSet createRuleExecutionSet(Serializable ast, Map properties)
      throws RuleExecutionSetCreateException, RemoteException {
    throw new RuleExecutionSetCreateException("Operation not supported");
  }


  /**
   * Creates a RuleExecutionSet implementation from a URI.
   */
  public RuleExecutionSet createRuleExecutionSet(String uri, Map properties)
      throws RuleExecutionSetCreateException, IOException, RemoteException {
    URLConnection urlc = (new URL(uri)).openConnection();
    java.io.InputStream is = urlc.getInputStream();
    return (new LocalRuleExecutionSetProviderImpl()).createRuleExecutionSet(is, properties);
  }

}