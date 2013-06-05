package org.starkeylab.dre.ruleengine.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleAdmin {

	private List rules;

	public RuleAdmin(List rules) {
		super();
		this.rules = rules;
		orderRules();
	}

	private void orderRules() {
		if (this.rules != null) {
			Collections.sort(this.rules);
		}
	}
	
	public List getRules() {
		return rules;
	}

	public List<RuleImpl> getRulesByDepends(String depends) {
		List<RuleImpl> result = new ArrayList<RuleImpl>();
		for (int i = 0; i < this.rules.size(); i++) {
			Object ruleObj = this.rules.get(i);
			if (ruleObj == null)
				continue;
			RuleImpl rule = (RuleImpl) ruleObj;
			if (depends == null) {
				if (rule.getDepends() == null
						|| rule.getDepends().trim().length() == 0) {
					result.add(rule);
					continue;
				}
			} else {
				if (rule.getDepends() != null
						&& rule.getDepends().equals(depends)) {
					result.add(rule);
					continue;
				}
			}
		}// end for
		return result;
	}

}
