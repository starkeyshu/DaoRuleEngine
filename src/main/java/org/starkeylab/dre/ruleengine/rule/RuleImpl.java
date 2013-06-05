package org.starkeylab.dre.ruleengine.rule;

import java.util.*;

import javax.rules.admin.Rule;
import java.io.Serializable;

public class RuleImpl implements Rule, Serializable, Comparable<RuleImpl> {

	/** rule code */
	private String code;

	/** rule name */
	private String name;

	/** rule depends */
	private String depends;

	/** assumptions list */
	private ArrayList assumptions;

	/** actions list */
	private ArrayList actions;

	/** rule description */
	private String description;

	/** user defined or vendor defined properties */
	private Hashtable props = new Hashtable();

	/**
	 * @param name
	 *            rule name
	 * @param description
	 *            rule description
	 * @param assumptions
	 *            assumptions list
	 * @param actions
	 *            actions list
	 */
	public RuleImpl(String name, String description, ArrayList assumptions,
			ArrayList actions) {
		this.code = name;
		this.name = name;
		this.description = description;
		this.assumptions = assumptions;
		this.actions = actions;
	}

	/**
	 * @param code
	 * @param name
	 * @param description
	 * @param depends
	 * @param assumptions
	 * @param actions
	 */
	public RuleImpl(String code, String name, String description,
			String depends, ArrayList assumptions, ArrayList actions) {
		super();
		this.code = code;
		if (code == null || code.trim().length() == 0)
			this.code = name;
		this.name = name;
		this.description = description;
		this.depends = depends;
		this.assumptions = assumptions;
		this.actions = actions;
	}

	/**
	 * @return rule code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return rule name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return assumptions list
	 */
	public final ArrayList getAssumptions() {
		return assumptions;
	}

	/**
	 * @return rule depends
	 */
	public String getDepends() {
		return depends;
	}

	/**
	 * @return actions list
	 */
	public final ArrayList getActions() {
		return actions;
	}

	/**
	 * @return rule description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param propName
	 *            a user defined or vendor defined property name
	 * @return a user defined or vendor defined property
	 */
	public final Object getProperty(Object propName) {
		return props.get(propName);
	}

	/**
	 * Set a user defined or vendor defined property.
	 * 
	 * @param propName
	 *            a user defined or vendor defined property name
	 * @param propValue
	 *            a user defined or vendor defined property value
	 */
	public final void setProperty(Object propName, Object propValue) {
		props.put(propName, propValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleImpl other = (RuleImpl) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RuleImpl [code=" + code + ", name=" + name + ", description="
				+ description + ", props=" + props + "]";
	}

	public int compareTo(RuleImpl o) {
		if(this.code==null||o==null||o.code==null)
			return 0;
		return this.code.compareTo(o.code);
	}

}