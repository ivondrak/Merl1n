package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.Module;

/**
 * The SyntaxErrorMessage class displays errors caused by parsing
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public class SyntaxErrorMessage extends ErrorMessage {
    /**
     * Constructor
     */
    public SyntaxErrorMessage(ParseException error, Module module) {
        super(error, module);
    }
    
    /**
     * Gets lexical error line
     */
    public int getLine() {
        return ((ParseException) error).currentToken.endLine;
    }
        
    /**
     * Converts to string
     */
    public String toString() {
        return "Error in module <"+module.getName()+">: "+((ParseException) error).getMessage();
    }

}