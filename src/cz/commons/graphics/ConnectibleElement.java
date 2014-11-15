package cz.commons.graphics;

import javafx.beans.property.DoubleProperty;

/**
 * Interface defining connection points for line
 *
 * @author Martin Šára
 */
public interface ConnectibleElement {
    
    /**
     * Get X coordinate property for connectible element
     *
     * @return
     */
    DoubleProperty connectXProperty();
    
    /**
     * Get Y coordinate property for connectible element
     *
     * @return
     */
    DoubleProperty connectYProperty();
    
}
