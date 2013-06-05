package org.starkeylab.dre.ruleengine;

import java.io.InputStream;
import java.util.HashMap;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import junit.framework.TestCase;

import org.starkeylab.dre.ruleengine.display.ExampleFrame;

public class RuleWithComponentTest extends TestCase {

	private StatefulRuleSession statefulRuleSession = null;

	public void testExeRule() {
		try {
			// Load the rule service provider of the reference
			// implementation.
			// Loading this class will automatically register this
			// provider with the provider manager.
			Class.forName("org.starkeylab.dre.ruleengine.RuleServiceProviderImpl");

			// Get the rule service provider from the provider manager.
			RuleServiceProvider serviceProvider = RuleServiceProviderManager
					.getRuleServiceProvider("org.starkeylab.dre.ruleengine");

			// get the RuleAdministrator
			RuleAdministrator ruleAdministrator = serviceProvider
					.getRuleAdministrator();
			System.out.println("\nAdministration API\n");
			System.out.println("Acquired RuleAdministrator: "
					+ ruleAdministrator);

			// get an input stream to a test XML ruleset
			// This rule execution set is part of the TCK.
			InputStream inStream = RuleWithComponentTest.class
					.getResourceAsStream("example5.xml");
			System.out.println("Acquired InputStream to example5.xml: "
					+ inStream);

			// parse the ruleset from the XML document
			RuleExecutionSet res1 = ruleAdministrator
					.getLocalRuleExecutionSetProvider(null)
					.createRuleExecutionSet(inStream, null);
			inStream.close();
			System.out.println("Loaded RuleExecutionSet: " + res1);

			// register the RuleExecutionSet
			String uri = res1.getName();
			ruleAdministrator.registerRuleExecutionSet(uri, res1, null);
			System.out.println("Bound RuleExecutionSet to URI: " + uri);

			// Get a RuleRuntime and invoke the rule engine.
			System.out.println("\nRuntime API\n");

			RuleRuntime ruleRuntime = serviceProvider.getRuleRuntime();
			System.out.println("Acquired RuleRuntime: " + ruleRuntime);

			// create a StatefulRuleSession
			statefulRuleSession = (StatefulRuleSession) ruleRuntime
					.createRuleSession(uri, new HashMap(),
							RuleRuntime.STATEFUL_SESSION_TYPE);

			System.out.println("Got Stateful Rule Session: "
					+ statefulRuleSession);

			// create the form
			ExampleFrame exampleFrame = new ExampleFrame(this);
			assertEquals("2000.0", exampleFrame.getCreditLimitControl()
					.getText());
			assertEquals("2000", exampleFrame.getAmountControl().getText());
			assertEquals("paid", exampleFrame.getStatusControl().getStatus());

			exampleFrame.dispose();

		} catch (NoClassDefFoundError e) {
			if (e.getMessage().indexOf("Exception") != -1) {
				System.err
						.println("Error: The Rule Engine Implementation could not be found.");
			} else {
				System.err.println("Error: " + e.getMessage());
			}
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	public final StatefulRuleSession getStatefulRuleSession() {
		return statefulRuleSession;
	}

}
