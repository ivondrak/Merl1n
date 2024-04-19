package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.*;
import merl1n.es.Module;

/**
 * The ErrorMessage class displays errors caused by parsing
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public abstract class ErrorMessage extends Message {
    /**
     * Reference to error
     */
    protected Throwable error;
    
    /**
     * Reference to module
     */
    protected Module module;
    
    /**
     * Constructor
     */
    public ErrorMessage(Throwable error, Module module) {
        this.error = error;
        this.module = module;
    }
    
    /**
     * Gets associated module
     */
    public Module getModule() {
        return module;
    }

}