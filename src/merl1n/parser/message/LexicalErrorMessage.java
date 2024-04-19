package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.*;
import merl1n.es.Module;

/**
 * The LexicalErrorMessage class displays errors caused by parsing
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public class LexicalErrorMessage extends ErrorMessage {
    /**
     * Constructor
     */
    public LexicalErrorMessage(TokenMgrError error, Module module) {
        super(error, module);
    }
    
    /**
     * Gets lexical error line
     */
    public int getLine() {
        return ((TokenMgrError) error).line;
    }
    
    /**
     * Converts to string
     */
    public String toString() {
        return "Error in module <"+module.getName()+">: "+((TokenMgrError) error).getMessage();
    }

}