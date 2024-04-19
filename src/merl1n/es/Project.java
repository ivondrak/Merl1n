package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The Project class defines a project
 *
 * @author Ivo Vondrak
 * @version 1.1 10/06/99
 */ 
public class Project implements Serializable {
    
    /**
     * Vector of modules
     */
    protected Vector modules;
    
    /**
     * Variables
     */
    protected Hashtable variables;
    
    /**
     * Input device
     */
    transient protected InputDevice input;
    
    /**
     * Output device
     */
    transient protected OutputDevice output;
    
    /**
     * State
     */
    protected int state;
    
    /**
     * Constructor
     */
    public Project() {
        modules = new Vector();
        variables = new Hashtable();
	state = Public.IDLE;
    }
    
    /**
     * Sets modules
     */
    public void setModules(Vector modules) {
        this.modules = modules;
        variables.clear();
    }
    
    /**
     * Gets modules
     */
    public Vector getModules() {
        return modules;
    }
        
    /**
     * Gets variables
     */
    public Hashtable getVariables() {
        return variables;
    }
    
    /**
     * Gets project state
     */
    public int getState() {
        return state;
    }
    
    /**
     * Sets state
     */
    public void setState(int state) {
        this.state = state;
    }
    
    /**
     * Inference engine
     */
    public void evaluate(InputDevice input, OutputDevice output) {
        this.input = input;
        this.output = output;
        this.ready();
        this.setState(Public.PENDING);
        Module start = (Module) modules.firstElement();
        start.evaluateRules(this);
        output.display("<CENTER><B>End of Evaluation</B><HR></CENTER>");
        this.setState(Public.READY);
    }
    
    /**
     * Makes rules and variables ready for fire
     */
    public void ready() {
        for (Enumeration ms = modules.elements(); ms.hasMoreElements();) {
            Module module = (Module) ms.nextElement();
            module.ready();
        }
        for (Enumeration vars = variables.keys(); vars.hasMoreElements();) {
            Variable var = (Variable) variables.get((String) vars.nextElement());
            if (!var.isExternal())
                var.reset();
        }
    }
    
    /**
     * Evaluates rules from a given module
     */
    public void evaluateModule(Module module) {
        boolean read = false;
        for (Enumeration vars=module.getVariables().elements(); vars.hasMoreElements();) {
            Variable var = (Variable) vars.nextElement();
            if (var.isExternal()) {
                read = true;
                break;
            }
        }
        if (read) 
            input.read(module);
        module.evaluateRules(this);
    }
    
    /**
     * Prints message on ouput
     */
    public void print(String text) {
        output.display(text);
    }
    
    /**
     * Stops execution
     */
    public void stop() {
        state = Public.STOPPED;
    }
}