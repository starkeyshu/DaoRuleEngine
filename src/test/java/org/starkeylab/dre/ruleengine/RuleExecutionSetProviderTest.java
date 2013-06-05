package org.starkeylab.dre.ruleengine;

import java.io.InputStream;

import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.Rule;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import junit.framework.TestCase;

import org.starkeylab.dre.ruleengine.rule.RuleImpl;

public class RuleExecutionSetProviderTest extends TestCase {
	
	RuleAdministrator ruleAdministrator;

	@Override
	protected void setUp() throws Exception {

		// Load the rule service provider of the reference
		// implementation.
		// Loading this class will automatically register this
		// provider with the provider manager.
		Class.forName("org.starkeylab.dre.ruleengine.RuleServiceProviderImpl");

		// Get the rule service provider from the provider manager.
		RuleServiceProvider serviceProvider = RuleServiceProviderManager
				.getRuleServiceProvider("org.starkeylab.dre.ruleengine");

		// get the RuleAdministrator
		ruleAdministrator = serviceProvider.getRuleAdministrator();


	}
	
	public void testPaserRuleSet() throws Exception {

		// get an input stream to a test XML ruleset
		// This rule execution set is part of the TCK.
		InputStream inStream = RuleTermTest.class
				.getResourceAsStream("example6.xml");

		// parse the ruleset from the XML document
		RuleExecutionSet res = ruleAdministrator
				.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(
						inStream, null);
		assertEquals("RuleExecutionSet1", res.getName());
		assertEquals("Rule Execution Set", res.getDescription());
		assertEquals(2, res.getRules().size());
		
		Rule rule1=(Rule) res.getRules().get(0);
		assertEquals("cs001", ((RuleImpl)rule1).getCode());
		assertEquals("vip customer segmentation", ((RuleImpl)rule1).getName());
		assertEquals("vip customer segmentation", ((RuleImpl)rule1).getDescription());
		assertEquals("",((RuleImpl)rule1).getDepends());
		assertEquals(1, ((RuleImpl)rule1).getAssumptions().size());
		assertEquals(1, ((RuleImpl)rule1).getActions().size());
		
		Rule rule2=(Rule) res.getRules().get(1);
		assertEquals("young customer treatment", ((RuleImpl)rule2).getCode());
		assertEquals("young customer treatment", ((RuleImpl)rule2).getName());
		assertEquals("",((RuleImpl)rule2).getDescription());
		assertEquals("cs001", ((RuleImpl)rule2).getDepends());
		assertEquals(1, ((RuleImpl)rule2).getAssumptions().size());
		assertEquals(1, ((RuleImpl)rule2).getActions().size());
		
	}

}
