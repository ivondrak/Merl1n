package merl1n.es;

import java.io.*;

/**
 * The InputDevice interface provides protocol for reading values for a given module
 *
 * @author Ivo Vondrak
 * @version 1.1 10/06/99
 */ 
public interface InputDevice {
    /**
     * Read values for a module
     */
    void read(Module module);
}