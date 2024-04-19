package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The Rule class defines a project rule
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class Rule implements Serializable {
    /**
     * Name of a rule
     */
    protected String name;
    
    /**
     * Priority value
     */
    protected int priority;
    
    /**
     * Condition part
     */
    protected Condition condition;
    
    /**
     * Associated acctions
     */
    protected Vector actions;
    
    /**
     * "Used" flag
     */
    transient protected boolean used;
    
    /**
     * Constructor
     */
    public Rule() {
        name = "Noname";
        priority = 0;
        condition = new Condition();
        actions = new Vector();
        used = false;
    }
    
    /**
     * Sets name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    /**
     * Gets priority
     */
    public int getPriority() {
        return priority;
    }
    
    
    /**
     * Sets condition part
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    /**
     * Gets condition part
     */
    public Condition getCondition() {
        return condition;
    }
    
    /**
     * Sets actions
     */
    public void setActions(Vector actions) {
        this.actions = actions;
    }
    
    /**
     * Gets actions
     */
    public Vector getActions() {
        return actions;
    }
    
    /**
     * Sets used flag
     */
    public void setUsed(boolean used) {
        this.used = used;
    }
    
    /**
     * Gets used flag
     */
    public boolean getUsed() {
        return used;
    }
    
    /**
     * Fires a rule
     *
     * @return true if rule changes es state
     */
    public boolean fire() {
        if (used) return false;
        if (!condition.evaluate()) return false;
        used = true;
        boolean changed = false;
        for (Enumeration as = actions.elements(); as.hasMoreElements();) {
            Action action = (Action) as.nextElement();
            action.execute();
            if (action.getType() == Public.ASSIGN)
                changed = true;
            if (action.getType() == Public.STOP)
                // Cancel evaluation of action part because of "stop" action
                break;
            if (action.getType() == Public.EXEC) {
                Exec exec = (Exec) action;
                if (exec.getProject().getState() == Public.STOPPED)
                    // Cancel evaluation of action part because of "cancel"
                    // or "stop" action in module evaluation
                    break;
            }
        }
        return changed;
    }
    
}