package merl1n.es;

import java.io.*;

/**
 * The OutputDevice interface provides protocol for displaying results
 *
 * @author Ivo Vondrak
 * @version 1.0 09/29/99
 */ 
public interface OutputDevice {
    /**
     * Displayes message
     */
    void display(String message);
}