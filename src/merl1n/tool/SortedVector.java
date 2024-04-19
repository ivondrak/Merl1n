package merl1n.tool;

import java.util.*;

/**
 * The SortedVector class sorts items in vector based on compare method
 *
 * @author Ivo Vondrak
 * @version 1.0 07/26/99
 */ 
public abstract class SortedVector extends Vector {
    /**
     * Compares two object
     *
     * @return true if the first object is before the second one
     */
    protected abstract boolean compare(Object first, Object second);
    
    /**
     * Adds new element
     */
    public synchronized void insertElement(Object obj) {
	int index = 0;
	for (Enumeration e=this.elements();e.hasMoreElements();) {
	    Object o = e.nextElement();
	    if (!this.compare(obj, o))
		index++;
	}
	super.insertElementAt(obj, index);    
    }
}