package org.starkeylab.dre.script;

import groovy.lang.GString;

import org.starkeylab.dre.BaseJUnitCase;
import org.starkeylab.dre.script.GroovyInterpreter;

public class GroovyInterpreterTest extends BaseJUnitCase{
	
	private GroovyInterpreter groovyInterpreter;
	
	@Override
	protected void setUp() throws Exception {
		groovyInterpreter = new GroovyInterpreter();
		super.setUp();
	}
	
	public void testRunScript() {
		groovyInterpreter.addParameter("name", "world");
		String result=((GString)groovyInterpreter.runScript("return \"Hello $name!\"")).toString();
        assertEquals(result, "Hello world!");
	}
	
	public void testRunScriptFile() throws Exception {
		String result=(String)groovyInterpreter.runScriptFile(getFileResource("test1.groovy"));
        assertEquals(result, "HSV Maloo");
	}
	
	public void testRunScriptFileWithPojo() throws Exception {
		String result=(String)groovyInterpreter.runScriptFile(getFileResource("test2.groovy"));
		assertEquals(result, "HSV Maloo");
	}

}
