package org.starkeylab.dre.ruleengine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.starkeylab.dre.ruleengine.StatefulRuleSessionImpl;
import org.starkeylab.dre.ruleengine.Term;

import junit.framework.TestCase;

public class RuleTermTest extends TestCase {

	RuleAdministrator ruleAdministrator;

	RuleRuntime ruleRuntime;

	StatefulRuleSession statefulRuleSession;

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

		// get the RuleRuntime
		ruleRuntime = serviceProvider.getRuleRuntime();

	}

	public void testUpdateTerm() throws Exception {

		// get an input stream to a test XML ruleset
		// This rule execution set is part of the TCK.
		InputStream inStream = RuleTermTest.class
				.getResourceAsStream("example3.xml");
		System.out.println("Acquired InputStream to example3.xml: " + inStream);

		// parse the ruleset from the XML document
		RuleExecutionSet res1 = ruleAdministrator
				.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(
						inStream, null);
		inStream.close();
		System.out.println("Loaded RuleExecutionSet: " + res1);

		// register the RuleExecutionSet
		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null);
		System.out.println("Bound RuleExecutionSet to URI: " + uri);

		// create a StatefulRuleSession
		statefulRuleSession = (StatefulRuleSession) ruleRuntime
				.createRuleSession(uri, new HashMap(),
						RuleRuntime.STATEFUL_SESSION_TYPE);

		System.out.println("Got Stateful Rule Session: " + statefulRuleSession);

		// Add some Terms...
		ArrayList input = new ArrayList();
		input.add(new Term("Socrate is human"));

		// add an Object to the statefulRuleSession
		statefulRuleSession.addObjects(input);
		System.out.println("Called addObject on Stateful Rule Session: "
				+ statefulRuleSession);

		statefulRuleSession.executeRules();
		System.out.println("Called executeRules");

		// extract the Objects from the statefulRuleSession
		List results = statefulRuleSession.getObjects();
		assertEquals(2, results.size());
		assertEquals("Socrate is mortal", results.get(0).toString());
		assertEquals("Socrate is human", results.get(1).toString());

		System.out.println("Result of calling getObjects: " + results.size()
				+ " results.");

		// Loop over the results.
		Iterator itr = results.iterator();
		while (itr.hasNext()) {
			Object obj = itr.next();
			System.out.println("Term Found: " + obj.toString());
		}

	}

	public void testUpdateTermWithVar()  throws Exception {

		// get an input stream to a test XML ruleset
		// This rule execution set is part of the TCK.
		InputStream inStream = RuleTermTest.class
				.getResourceAsStream("example4.xml");
		System.out.println("Acquired InputStream to example4.xml: " + inStream);

		// parse the ruleset from the XML document
		RuleExecutionSet res1 = ruleAdministrator
				.getLocalRuleExecutionSetProvider(null).createRuleExecutionSet(
						inStream, null);
		inStream.close();
		System.out.println("Loaded RuleExecutionSet: " + res1);

		// register the RuleExecutionSet
		String uri = res1.getName();
		ruleAdministrator.registerRuleExecutionSet(uri, res1, null);
		System.out.println("Bound RuleExecutionSet to URI: " + uri);

		statefulRuleSession = (StatefulRuleSession) ruleRuntime
				.createRuleSession(uri, new HashMap(),
						RuleRuntime.STATEFUL_SESSION_TYPE);

		System.out.println("Got Stateful Rule Session: " + statefulRuleSession);

		// Add some terms...
		ArrayList input = new ArrayList();
		input.add(new Term("Socrate", "is human"));

		// add an Object to the statefulRuleSession
		statefulRuleSession.addObjects(input);
		System.out.println("Called addObject on Stateful Rule Session: "
				+ statefulRuleSession);

		statefulRuleSession.executeRules();
		System.out.println("Called executeRules");

		// extract the Objects from the statefulRuleSession
		List results = statefulRuleSession.getObjects();

		System.out.println("Result of calling getObjects: " + results.size()
				+ " results.");

		// Loop over the results.

		Hashtable wm = ((StatefulRuleSessionImpl) statefulRuleSession)
				.getWorkingMemory();
		Enumeration en = wm.keys();
		while (en.hasMoreElements()) {
			Object obj = en.nextElement();
			System.out.println("Term Found: " + obj + " " + wm.get(obj));
			assertEquals("Socrate", obj);
			assertEquals("is mortal", wm.get(obj));
		}
		
		assertEquals(1, results.size());
		
	}

	@Override
	protected void tearDown() throws Exception {
		// release the statefulRuleSession
		statefulRuleSession.release();
	}

}
