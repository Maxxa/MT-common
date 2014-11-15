package cz.commons.graphics;

/**
 * Interface defining more connection points for children
 * 
 * @author Martin Šára
 */
public interface MultiConnectibleElement {
    
    /**
	 * Gets connector for child on i-position. Sorted from left
	 * 
	 * @param i
	 *            - the position of children
	 * @return - ConnectibleElement to connect
	 */
    ConnectibleElement getChildConnector(int i);
    
}
