package merl1n.es;

import java.io.*;
import java.util.*;

import merl1n.tool.*;

/**
 * The TextVariable class 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public class TextVariable extends Variable {
    
    /**
     * Constructor
     */
    public TextVariable() {
        this.initialize("");
    }
            
    /**
     * Gets type
     */
    public int getType() {
        return Public.TEXT;
    }
}