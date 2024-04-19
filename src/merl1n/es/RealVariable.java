package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The TealVariable class 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public class RealVariable extends Variable {
    
    /**
     * Constructor
     */
    public RealVariable() {
        this.initialize(new Float(0.0));
    }
    
    /**
     * Gets type
     */
    public int getType() {
        return Public.REAL;
    }
}