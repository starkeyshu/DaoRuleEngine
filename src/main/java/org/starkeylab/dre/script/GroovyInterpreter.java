package org.starkeylab.dre.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;

public class GroovyInterpreter {

	private Binding binding;

	public GroovyInterpreter() {
		binding = new Binding();
	}

	public Object getParameter(String name) {

		return binding.getProperty(name);

	}

	public void addParameter(String paramName, Object paramValue) {

		binding.setProperty(paramName, paramValue);

	}

	public Object runScriptFile(String scriptName) {

		return runScriptFile(new File(scriptName));

	}

	public Object runScriptFile(File scriptFile) {

		GroovyShell shell = new GroovyShell(binding);

		try {

			return shell.evaluate(scriptFile);

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

	}

	public Object runScript(String script) {

		GroovyShell shell = new GroovyShell(binding);

		try {

			return shell.evaluate(script);

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

	}

}
