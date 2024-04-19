package merl1n.es;

import java.io.*;
import java.util.*;

/**
 * The Variable class defines a project variable
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public abstract class Variable implements Serializable {
    /**
     * Label for external variable
     */
    protected String label;
    
    /**
     * Value
     */
    protected Object value;
    
    /**
     * Initial value
     */
    protected Object initial;
    
    /**
     * Definition range
     */
    protected Vector range;
    
        
    /**
     * Sets label
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    /**
     * Gets label
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Initialize variable with a given value
     */
    public void initialize(Object value) {
        if (this.isValid(value)) {
            this.initial = value;
            this.value = value;
        }
    }
    
    /**
     * Sets value
     */
    public void setValue(Object value) {
        if (this.isValid(value))
            this.value = value;
    }
    
    /**
     * Gets value
     */
    public Object getValue() {
        return value;
    }
    
    /**
     * Sets range
     */
    public void setRange(Vector range) {
        this.range = range;
        if (range != null)
            this.value = range.firstElement();
    }
    
    /**
     * Gets range
     */
    public Vector getRange() {
        return range;
    }
    
    /**
     * Gets type
     */
    public abstract int getType();
    
    /**
     * Tests if the variable is external (visible to user)
     */
    public boolean isExternal() {
        if (label == null)
            return false;
        else
            return true;
    }
    
    /**
     * Tests if the variable is enumerative
     */
    public boolean isEnumerative() {
        if (range == null)
            return false;
        else
            return true;
    }
    
    /**
     * Tests if a new value is valid in a definition range
     */
    public boolean isValid(Object value) {
        if (!this.isEnumerative())
            // All values are allowed
            return true;
        for (Enumeration values = this.getRange().elements(); values.hasMoreElements();) {
            Object v = values.nextElement();
            if (v.equals(value))
                return true;
        }
        return false;
    }
    
    /**
     * Resets value to initial
     */
    public void reset() {
        value = initial;
    }
    
    /**
     * Change to string
     */
    public String toString() {
        String result = "";
        if (label != null)
            result += "label:"+label+" ";
        if (value != null)
            result += "value:"+value.toString()+" ";
        if (range != null)
            result += "range:"+range.toString();
        return result;
    }
}