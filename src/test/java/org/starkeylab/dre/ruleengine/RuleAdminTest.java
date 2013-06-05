package org.starkeylab.dre.ruleengine;

import java.util.ArrayList;
import java.util.List;

import javax.rules.admin.Rule;

import junit.framework.TestCase;

import org.starkeylab.dre.ruleengine.rule.RuleAdmin;
import org.starkeylab.dre.ruleengine.rule.RuleImpl;

public class RuleAdminTest extends TestCase{
	
	private RuleAdmin ruleAdmin;
	
	@Override
	protected void setUp() throws Exception {
		
		List rules=new ArrayList();
		Rule rule1=new RuleImpl("cs001","Customer segment rule 1","Customer segment rule 1","",null,null);
		Rule rule11=new RuleImpl("ct001","Customer treatment rule 1","Customer treatment rule 1","cs001",null,null);
		Rule rule12=new RuleImpl("ct002","Customer treatment rule 2","Customer treatment rule 2","cs001",null,null);
		Rule rule2=new RuleImpl("cs002","Customer segment rule 2","Customer segment rule 2",null,null,null);
		rules.add(rule1);
		rules.add(rule11);
		rules.add(rule12);
		rules.add(rule2);
		
		ruleAdmin=new RuleAdmin(rules);
	}
	
	public void testOrderedRules() {
		
		List rules=ruleAdmin.getRules();
		assertEquals(4, rules.size());
		assertEquals("cs001", ((RuleImpl)rules.get(0)).getCode());
		assertEquals("", ((RuleImpl)rules.get(0)).getDepends());
		assertEquals("cs002", ((RuleImpl)rules.get(1)).getCode());
		assertEquals(null, ((RuleImpl)rules.get(1)).getDepends());
		assertEquals("ct001", ((RuleImpl)rules.get(2)).getCode());
		assertEquals("cs001", ((RuleImpl)rules.get(2)).getDepends());
		assertEquals("ct002", ((RuleImpl)rules.get(3)).getCode());
		assertEquals("cs001", ((RuleImpl)rules.get(3)).getDepends());
	}
	
	public void testGetRulesByDepends() {
		
		List rules=ruleAdmin.getRulesByDepends(null);
		assertEquals(2, rules.size());
		assertEquals("cs001", ((RuleImpl)rules.get(0)).getCode());
		assertEquals("cs002", ((RuleImpl)rules.get(1)).getCode());
		
		rules=ruleAdmin.getRulesByDepends("cs001");
		assertEquals(2, rules.size());
		assertEquals("ct001", ((RuleImpl)rules.get(0)).getCode());
		assertEquals("ct002", ((RuleImpl)rules.get(1)).getCode());
		
		rules=ruleAdmin.getRulesByDepends("cs002");
		assertEquals(0, rules.size());

	}

}
