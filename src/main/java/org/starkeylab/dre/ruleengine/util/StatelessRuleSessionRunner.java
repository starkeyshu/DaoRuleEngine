package org.starkeylab.dre.ruleengine.util;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.rules.RuleExecutionException;
import javax.rules.RuleRuntime;
import javax.rules.StatelessRuleSession;

public abstract class StatelessRuleSessionRunner {

	private RuleRuntime ruleRuntime;

	private String ruleSetUri;

	protected StatelessRuleSession statelessRuleSession = null;

	public StatelessRuleSessionRunner(RuleRuntime ruleRuntime, String ruleSetUri) {
		super();
		this.ruleRuntime = ruleRuntime;
		this.ruleSetUri = ruleSetUri;
	}

	public List go() throws Exception {

		try {

			statelessRuleSession = (StatelessRuleSession) ruleRuntime
					.createRuleSession(ruleSetUri, new HashMap(),
							RuleRuntime.STATELESS_SESSION_TYPE);
			System.out.println("Got Stateless Rule Session: "
					+ statelessRuleSession);

			return executeRules();

		} catch (Exception e) {
			throw e;
		} finally {
			if (statelessRuleSession != null) {
				statelessRuleSession.release();
				System.out.println("Released Stateless Rule Session.");
			}
		}

	}

	protected StatelessRuleSession getSession() {
		return statelessRuleSession;
	}

	public abstract List executeRules()throws Exception;

}
