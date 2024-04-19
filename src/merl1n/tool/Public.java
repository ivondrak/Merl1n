package merl1n.tool;

import java.util.*;

/**
 * The Public class contains refrences to objects publicly available
 *
 * @author Ivo Vondrak
 * @version 1.0 09/21/99
 */ 
public class Public {

    /**
     * Keys for icons
     */
    public static final String M1SHELL =	"m1shell";
    public static final String M1SHELL2X =	"m1shell2x";
    public static final String M1WEB =	        "m1web";
    public static final String M1WEB2X =	"m1web2x";
    public static final String NEW =		"new";
    public static final String OPEN =		"open";
    public static final String SAVE =		"save";
    public static final String CUT =		"cut";
    public static final String COPY =		"copy";
    public static final String PASTE =		"paste";
    public static final String INSERT =         "insert";
    public static final String MOVEUP =         "moveup";
    public static final String MOVEDOWN =       "movedown";
    public static final String BUILD =          "build";
    public static final String EXECUTE =        "execute";
    public static final String TRUE =           "true";
    public static final String FALSE =          "false";
    public static final String BACK =           "back";
    public static final String FORWARD =        "forward";
    public static final String HOME =           "home";
    public static final String NULL =           "null";
    
    /**
     * Dictionary for icons
     */
    public static Hashtable ICONS = new Hashtable();
    
    /**
     * Variable type constants
     */
    final static public int BOOLEAN = 1;
    final static public int INTEGER = 2;
    final static public int REAL = 3;
    final static public int TEXT = 4;
    
    /**
     * Project state constants
     */
    final static public int IDLE = 1;
    final static public int READY = 2;
    final static public int PENDING = 3;
    final static public int STOPPED = 4;
    
    /**
     * Action type constants
     */
    final static public int ASSIGN = 1;
    final static public int SEND = 2;
    final static public int PRINT = 2;
    final static public int EXEC = 3;
    final static public int STOP = 4;
    final static public int NL = 5;
    final static public int IMAGE = 6;
    final static public int DOCUMENT = 7;
    
    
    /**
     * Evaluation trigger
     */
    public static boolean EVAL = false;    
}