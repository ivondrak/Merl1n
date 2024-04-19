package merl1n.app;

import merl1n.es.*;

/**
 * The ProjectType interface defines the access to project 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/20/99
 */ 
public interface ProjectType  {

    /**
     * Sets a project
     */
    void setProject(Project project);
    
    /**
     * Gets the project
     */
    Project getProject();
    
    /**
     * Sets flag of a project change
     *
     * @param flag flag of project change
     */
    void setProjectChanged(boolean flag);
    
    /**
     * Gets flag of a project change
     */
    boolean getProjectChanged();
    
    /**
     * Sets home directory of a project
     *
     * @param home home of project
     */
    void setProjectHome(String home);
    
    /**
     * Gets home directory of a project
     */
    String getProjectHome();
}