package org.starkeylab.dre.ruleengine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.rules.Handle;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import junit.framework.TestCase;

import org.starkeylab.dre.ruleengine.model.AccountControl;
import org.starkeylab.dre.ruleengine.model.Customer;
import org.starkeylab.dre.ruleengine.model.Invoice;

public class RuleJavaObjTest extends TestCase {

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
			InputStream inStream = RuleJavaObjTest.class
					.getResourceAsStream("example1.xml");
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

			// create a StatelessRuleSession
			StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime
					.createRuleSession(uri, new HashMap(),
							RuleRuntime.STATELESS_SESSION_TYPE);

			System.out.println("Got Stateless Rule Session: "
					+ statelessRuleSession);

			// call executeRules with some input objects

			// Create a Customer as specified by the TCK documentation.
			Customer inputCustomer = new Customer();
			inputCustomer.setCreditLimit(5000);

			// Create an Invoice as specified by the TCK documentation.
			Invoice inputInvoice = new Invoice();
			inputInvoice.setAmount(2000);

			// Create a input list.
			List input = new ArrayList();
			input.add(inputCustomer);
			input.add(inputInvoice);

			// Print the input.
			System.out.println("Calling rule session with the following data");
			System.out.println("Customer credit limit input: "
					+ inputCustomer.getCreditLimit());
			System.out.println("Invoice:\n" + " amount: "
					+ inputInvoice.getAmount() + " status: "
					+ inputInvoice.getStatus());

			assertEquals(5000.0, inputCustomer.getCreditLimit());
			assertEquals(2000.0, inputInvoice.getAmount());
			assertEquals("unpaid", inputInvoice.getStatus());

			// Execute the rules without a filter.
			List results = statelessRuleSession.executeRules(input);

			System.out
					.println("Called executeRules on Stateless Rule Session: "
							+ statelessRuleSession);

			System.out.println("Result of calling executeRules: "
					+ results.size() + " results.");

			assertEquals(2, results.size());

			// Loop over the results.
			Iterator itr = results.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				if (obj instanceof Customer)
					System.out.println("Customer credit limit result: "
							+ ((Customer) obj).getCreditLimit());
				if (obj instanceof Invoice)
					System.out.println("Invoice:\n" + " amount: "
							+ ((Invoice) obj).getAmount() + " status: "
							+ ((Invoice) obj).getStatus());
			}

			assertEquals(3000.0, inputCustomer.getCreditLimit());
			assertEquals(2000.0, inputInvoice.getAmount());
			assertEquals("paid", inputInvoice.getStatus());

			// Release the session.
			statelessRuleSession.release();
			System.out.println("Released Stateless Rule Session.");
			System.out.println();

			// create a StatefulRuleSession
			StatefulRuleSession statefulRuleSession = (StatefulRuleSession) ruleRuntime
					.createRuleSession(uri, new HashMap(),
							RuleRuntime.STATEFUL_SESSION_TYPE);

			System.out.println("Got Stateful Rule Session: "
					+ statefulRuleSession);
			// Add another Invoice.
			Invoice inputInvoice2 = new Invoice();
			inputInvoice2.setAmount(1750);
			input.add(inputInvoice2);
			System.out.println("Calling rule session with the following data");
			System.out.println("Customer credit limit input: "
					+ inputCustomer.getCreditLimit());
			System.out.println("Invoice:\n" + " amount: "
					+ inputInvoice.getAmount() + " status: "
					+ inputInvoice.getStatus());
			System.out.println("Invoice:\n" + " amount: "
					+ inputInvoice2.getAmount() + " status: "
					+ inputInvoice2.getStatus());

			assertEquals(3000.0, inputCustomer.getCreditLimit());
			assertEquals(2000.0, inputInvoice.getAmount());
			assertEquals("paid", inputInvoice.getStatus());
			assertEquals(1750.0, inputInvoice2.getAmount());
			assertEquals("unpaid", inputInvoice2.getStatus());

			// add an Object to the statefulRuleSession
			statefulRuleSession.addObjects(input);
			System.out.println("Called addObject on Stateful Rule Session: "
					+ statefulRuleSession);

			statefulRuleSession.executeRules();
			System.out.println("Called executeRules");

			// extract the Objects from the statefulRuleSession
			results = statefulRuleSession.getObjects();

			System.out.println("Result of calling getObjects: "
					+ results.size() + " results.");

			assertEquals(2, results.size());

			// Loop over the results.
			itr = results.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				if (obj instanceof Customer)
					System.out.println("Customer credit limit result: "
							+ ((Customer) obj).getCreditLimit());
				if (obj instanceof Invoice)
					System.out.println("Invoice:\n" + " amount: "
							+ ((Invoice) obj).getAmount() + " status: "
							+ ((Invoice) obj).getStatus());
			}

			assertEquals(1250.0, inputCustomer.getCreditLimit());
			assertEquals(2000.0, inputInvoice.getAmount());
			assertEquals("paid", inputInvoice.getStatus());
			assertEquals(1750.0, inputInvoice2.getAmount());
			assertEquals("paid", inputInvoice2.getStatus());

			// release the statefulRuleSession
			statefulRuleSession.release();
			System.out.println("Released Stateful Rule Session.");
			System.out.println();

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

	public void testExeMoreRules() {

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
			InputStream inStream = RuleJavaObjTest.class
					.getResourceAsStream("example2.xml");
			System.out.println("Acquired InputStream to example2.xml: "
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
			StatefulRuleSession statefulRuleSession = (StatefulRuleSession) ruleRuntime
					.createRuleSession(uri, new HashMap(),
							RuleRuntime.STATEFUL_SESSION_TYPE);

			System.out.println("Got Stateful Rule Session: "
					+ statefulRuleSession);

			AccountControl controller = new AccountControl();

			// Add this form as rule engine input object
			Handle objHandle=statefulRuleSession.addObject(controller);
			assertEquals(4000.0, controller.getCreditLimit());
			assertEquals(true, controller.isEnableInvoice());
			assertEquals(2000.0, controller.getInvoiceAmount());
			assertEquals("unpaid", controller.getInvoiceStatus());

			System.out.println("Called addObject on Stateful Rule Session: "
					+ statefulRuleSession);

			statefulRuleSession.executeRules();
			System.out.println("Called executeRules");

			// extract the Objects from the statefulRuleSession
			java.util.List results = statefulRuleSession.getObjects();
			System.out.println("Result of calling getObjects: "
					+ results.size() + " results.");
			
			assertEquals(1, results.size());
			assertEquals(2000.0, controller.getCreditLimit());
			assertEquals(true, controller.isEnableInvoice());
			assertEquals(2000.0, controller.getInvoiceAmount());
			assertEquals("paid", controller.getInvoiceStatus());
			
			controller.setCreditLimit(-3000);
			//statefulRuleSession.updateObject(objHandle,controller );
			statefulRuleSession.executeRules();
			results = statefulRuleSession.getObjects();
			assertNotNull(results.get(0));
			assertFalse(((AccountControl)results.get(0)).isEnableInvoice());

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
