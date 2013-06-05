package org.starkeylab.dre.ruleengine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import junit.framework.TestCase;

import org.starkeylab.dre.ruleengine.model.AccountControl;
import org.starkeylab.dre.ruleengine.model.Customer;
import org.starkeylab.dre.ruleengine.util.ResultFinder;
import org.starkeylab.dre.ruleengine.util.StatelessRuleSessionRunner;

public class RuleDependencyTest extends TestCase {

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
			InputStream inStream = RuleDependencyTest.class
					.getResourceAsStream("example6.xml");
			System.out.println("Acquired InputStream to example1.xml: "
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
			
			StatelessRuleSessionRunner sessionRunner=new StatelessRuleSessionRunner(ruleRuntime, uri){

				@Override
				public List executeRules() throws Exception {
					Customer inputCustomer = new Customer();
					inputCustomer.setVIP(true);
					inputCustomer.setAge(21);
					
					AccountControl accountControl=new AccountControl();

					// Create a input list.
					List input = new ArrayList();
					input.add(inputCustomer);
					input.add(accountControl);

					// Execute the rules without a filter.
					return getSession().executeRules(input);
				}
				
			};
			
			List results = sessionRunner.go();
			AccountControl accountControl= (AccountControl)ResultFinder.getResult(results, AccountControl.class.getName());
			assertFalse(accountControl.isTeenagerPackageProvided());
			
			sessionRunner=new StatelessRuleSessionRunner(ruleRuntime, uri){

				@Override
				public List executeRules() throws Exception {
					Customer inputCustomer = new Customer();
					inputCustomer.setVIP(true);
					inputCustomer.setAge(15);
					
					AccountControl accountControl=new AccountControl();

					// Create a input list.
					List input = new ArrayList();
					input.add(inputCustomer);
					input.add(accountControl);

					// Execute the rules without a filter.
					return getSession().executeRules(input);
				}
				
			};
			
			results = sessionRunner.go();
			accountControl= (AccountControl)ResultFinder.getResult(results, AccountControl.class.getName());
			assertTrue(accountControl.isTeenagerPackageProvided());

			sessionRunner=new StatelessRuleSessionRunner(ruleRuntime, uri){

				@Override
				public List executeRules() throws Exception {
					Customer inputCustomer = new Customer();
					inputCustomer.setVIP(false);
					inputCustomer.setAge(15);
					
					AccountControl accountControl=new AccountControl();

					// Create a input list.
					List input = new ArrayList();
					input.add(inputCustomer);
					input.add(accountControl);

					// Execute the rules without a filter.
					return getSession().executeRules(input);
				}
				
			};
			
			results = sessionRunner.go();
			accountControl= (AccountControl)ResultFinder.getResult(results, AccountControl.class.getName());
			assertFalse(accountControl.isTeenagerPackageProvided());
			

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

}
