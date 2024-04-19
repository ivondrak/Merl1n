package merl1n.es;

import merl1n.tool.*;

/**
 * The Link class prints hyperlink on es output
 *
 * @author Ivo Vondrak
 * @version 2.0 09/20/2001
 */ 
public abstract class Link extends Action {
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Label Expression
     */
    protected TextNode label;
    
    /**
     * Location Expression
     */
    protected TextNode location;
    
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
     * Gets label
     */
    public TextNode getLabel() {
        return label;
    }
    
    /**
     * Sets label
     */
    public void setLabel(TextNode label) {
        this.label = label;
    }
    
    /**
     * Gets location
     */
    public TextNode getLocation() {
        return location;
    }
    
    /**
     * Sets location
     */
    public void setLocation(TextNode location) {
        this.location = location;
    }    
}
