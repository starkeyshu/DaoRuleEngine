package org.starkeylab.dre.ruleengine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.starkeylab.dre.ruleengine.rule.Action;
import org.starkeylab.dre.ruleengine.rule.Assumption;
import org.starkeylab.dre.ruleengine.rule.RuleImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class LocalRuleExecutionSetProviderImpl implements LocalRuleExecutionSetProvider {

  public static final String NAME = "name";
  public static final String DESCRIPTION = "description";


  LocalRuleExecutionSetProviderImpl() { }


  /**
   * Creates a RuleExecutionSet implementation using a supplied input stream and additional vendor-specific properties.
   */
  public RuleExecutionSet createRuleExecutionSet(InputStream inputStream, Map props)
      throws RuleExecutionSetCreateException, IOException {
    Document doc = null;
    try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(new InputSource(inputStream));
    }
    catch(Exception e) {
        throw new RuleExecutionSetCreateException("Parse error", e);
    }
    return createRuleExecutionSet(doc.getDocumentElement(), props);
  }


  /**
   * Creates a RuleExecutionSet implementation using a supplied character stream Reader and additional vendor-specific properties.
   */
  public RuleExecutionSet createRuleExecutionSet(Reader reader, Map props)
      throws RuleExecutionSetCreateException, IOException {
    Document doc = null;
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(new InputSource(reader));
    }
    catch(Exception e) {
      throw new RuleExecutionSetCreateException("Parse error", e);
    }
    return createRuleExecutionSet(doc.getDocumentElement(), props);
  }


  /**
   * Creates a RuleExecutionSet implementation from a vendor specific AST representation and vendor-specific properties.
   */
  public RuleExecutionSet createRuleExecutionSet(Object ast, Map properties)
      throws RuleExecutionSetCreateException {
    if (ast==null || !(ast instanceof Collection))
      throw new RuleExecutionSetCreateException("Invalid type argument: AST argument must be a collection of RuleImpl objects");
    if (properties == null) {
      properties = new HashMap();
    }
    try {
      return createRuleExecutionSetFromRuleList( (Collection) ast, properties);
    }
    catch (Exception ex) {
      throw new RuleExecutionSetCreateException(ex.getMessage());
    }
  }


  /**
   * Create the rule execution set.
   */
  //TODO add schema validation 
  private RuleExecutionSet createRuleExecutionSet(Element docElement, Map properties)
      throws RuleExecutionSetCreateException {
    try {
      NodeList contents = docElement.getElementsByTagName("name");
      if (contents.getLength() == 0) {
        throw new RuleExecutionSetCreateException("Name not specified");
      }
      String name = contents.item(0).getFirstChild().getNodeValue().trim();

      contents = docElement.getElementsByTagName("description");
      if (contents.getLength() == 0) {
        throw new RuleExecutionSetCreateException("Description not specified");
      }
      String description = contents.item(0).getFirstChild().getNodeValue().trim();

      if (properties == null) {
        properties = new HashMap();
      }
      properties.put("name", name);
      properties.put("description", description);

      Element el,node = null;
      ArrayList assumptions = null;
      ArrayList actions = null;
      NodeList nodes = null;

      // read synonyms...
      Hashtable synonymns = new Hashtable();
      nodes = docElement.getElementsByTagName("synonymn");
      for(int j=0;j<nodes.getLength();j++) {
        node = (Element)nodes.item(j);
        synonymns.put(
          node.getAttribute("name"),
          node.getAttribute("class")
        );
      }

      // read rules...
      contents = docElement.getElementsByTagName("rule");
      ArrayList rules = new ArrayList();
      for(int i=0;i<contents.getLength();i++) {
        el = (Element)contents.item(i);
        assumptions = new ArrayList();
        actions = new ArrayList();

        // read assumptions...
        nodes = el.getElementsByTagName("if");
        for(int j=0;j<nodes.getLength();j++) {
          node = (Element)nodes.item(j);

          if (node.getAttributeNode("op")!=null &&
              node.getAttributeNode("op").getValue()!=null &&
              node.getAttributeNode("rightTerm")!=null &&
              node.getAttributeNode("rightTerm").getValue()!=null)
            assumptions.add(new Assumption(
              parseClassName(node.getAttribute("leftTerm"),synonymns),
              node.getAttribute("op"),
              parseClassName(node.getAttribute("rightTerm"),synonymns)
            ));
          else
            assumptions.add(new Assumption(
              parseClassName(node.getAttribute("leftTerm"),synonymns)
            ));
        }

        // read actions...
        ArrayList args = null;
        int k;
        nodes = el.getElementsByTagName("then");
        for(int j=0;j<nodes.getLength();j++) {
          node = (Element)nodes.item(j);
          args = new ArrayList();
          k=1;
          while(node.getAttributeNode("arg"+k)!=null &&
                node.getAttributeNode("arg"+k).getValue()!=null) {
            args.add( parseClassName(node.getAttribute("arg"+k),synonymns) );
            k++;
          }
          actions.add(new Action(
            parseClassName(node.getAttribute("method"),synonymns),args
          ));
        }
        rules.add(new RuleImpl(
            el.getAttribute("code"),	
            el.getAttribute("name"),
            el.getAttribute("description"),
            el.getAttribute("depends"),
            assumptions,
            actions
        ));
      }

      return createRuleExecutionSetFromRuleList(rules, properties);
    }
    catch (Exception ex) {
      throw new RuleExecutionSetCreateException(ex.getMessage());
    }
  }


  /**
   * Analyze className to find out if it contains a synonymn.
   * @param className className + "." + methodName
   * @param synonymns collection of name + classname
   * @return className + "." + methodName, without synonymn
   */
  private String parseClassName(String className,Hashtable synonymns) {
    if (className.indexOf(".")==-1)
      return className;
    String methodName = className.substring(className.lastIndexOf(".")+1);
    className = className.substring(0,className.lastIndexOf("."));
    String realClassName = (String)synonymns.get(className);
    if (realClassName!=null)
      className = realClassName;
    return className+"."+methodName;
  }


  /**
   * Method called from createRuleExecutionSet.
   */
  private RuleExecutionSet createRuleExecutionSetFromRuleList(Collection rules,Map props)
      throws RuleExecutionSetCreateException, IOException {
    try {
      RuleExecutionSetImpl rs;
      String name = "Untitled";
      String description = "Generic rule execution set";
      if (props != null) {
        if (props.get("name") != null) {
          name = (String) props.get("name");
        }
        if (props.get("description") != null) {
          description = (String) props.get("description");
        }
      }
      rs = new RuleExecutionSetImpl(name, description, null);
      rs.getRules().addAll(rules);

      return rs;
    }
    catch (Exception ex) {
      throw new RuleExecutionSetCreateException("Internal error", ex);
    }
  }


}
