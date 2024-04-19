package merl1n.es;

import merl1n.tool.*;

/**
 * The Stop class stops es execution
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public class Stop extends Action {
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
        project.stop();
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.STOP;
    }
}