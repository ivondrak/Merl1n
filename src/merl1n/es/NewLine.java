package merl1n.es;

import merl1n.tool.*;

/**
 * The NewLine class generates newline on output
 *
 * @author Ivo Vondrak
 * @version 2.0 09/21/2001
 */ 
public class NewLine extends Action {
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Gets project
     */
    public Project getProject() {
        return project;
    }
    
    /**
     * Sets project
     */
    public void setProject(Project project) {
        this.project = project;
    }  
        
    /**
     * Executes action part
     */
    public void execute() {
        project.print("<BR>");
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.NL;
    }
}