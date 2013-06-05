package org.starkeylab.dre.ruleengine;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RuleTestSet {

	public static Test suite() {
		TestSuite suite = new TestSuite(RuleTestSet.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(RuleTermTest.class);
		suite.addTestSuite(RuleJavaObjTest.class);
		suite.addTestSuite(RuleAdminTest.class);
		suite.addTestSuite(RuleWithComponentTest.class);
		suite.addTestSuite(RuleExecutionSetProviderTest.class);
		suite.addTestSuite(RuleDependencyTest.class);
		//$JUnit-END$
		return suite;
	}

}
