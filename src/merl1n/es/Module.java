package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The Module class represents a piece of a knowledge base
 *
 * @author Ivo Vondrak
 * @version 1.0 09/22/99
 */ 
public class Module implements Serializable {

    /**
     * Name of a module
     */
    protected String name;
    
    /**
     * Vector of local variables
     */
    protected Vector variables;
    
    /**
     * Vector of rules
     */
    protected SortedVector rules;
    
    /**
     * Source text representation of a knowledge base
     */
    protected String source;
    
    /**
     * Constructor
     */
    public Module() {
	name = "Noname";
        variables = new Vector();
        rules = new SortedVector() {
	    public boolean compare(Object first, Object second) {
		return ((Rule) first).getPriority() > ((Rule) second).getPriority();
	    }
	};
	source = "/* Knowledge base module definition */";
    }
    
    /**
     * Sets name of a object
     */
    public void setName(String name) {
	this.name = name;
    }
    
    /**
     * Gets name of a module
     */
    public String getName() {
	return name;
    }
    
    /**
     * Gets variables
     */
    public Vector getVariables() {
        return variables;
    }
    
    /**
     * Gets rules
     */
    public SortedVector getRules() {
        return rules;
    }    
    
    /**
     * Sets source text
     */
    public void setSource(String source) {
	this.source = source;
    }
    
    /**
     * Gets the the knowledge base source text
     *
     * @return source text
     */
    public String getSource() {
	return source;
    }
    
    /**
     * Evaluates module rules
     *
     * @param project owning project
     */
    public void evaluateRules(Project project) {
        while(project.getState() == Public.PENDING) {
            boolean changed = false;
            for (Enumeration rs=rules.elements(); rs.hasMoreElements();) {
                Rule rule = (Rule) rs.nextElement();
                // Test if rule fire changed state
                changed = rule.fire();
                if (changed || project.getState() == Public.STOPPED) 
                    break;
            }
            if (!changed || project.getState() == Public.STOPPED) 
                break;
        }
    }
    
    /**
     * Makes rules ready for fire
     */
    public void ready() {
        for (Enumeration rs = rules.elements(); rs.hasMoreElements();) {
            Rule rule = (Rule) rs.nextElement();
            rule.setUsed(false);
        }
    }
}