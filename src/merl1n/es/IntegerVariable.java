package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The IntegerVariable class 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public class IntegerVariable extends Variable {
    
    /**
     * Constructor
     */
    public IntegerVariable() {
        this.initialize(new Integer(0));
    }
    
    /**
     * Gets type
     */
    public int getType() {
        return Public.INTEGER;
    }
}