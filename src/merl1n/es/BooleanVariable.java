package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The BooleanVariable class 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public class BooleanVariable extends Variable {
    
    /**
     * Constructor
     */
    public BooleanVariable() {
        this.initialize(new Boolean(false));
    }
            
    /**
     * Gets type
     */
    public int getType() {
        return Public.BOOLEAN;
    }
}