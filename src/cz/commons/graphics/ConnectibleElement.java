package cz.commons.graphics;

import javafx.beans.property.DoubleProperty;

/**
 * Rozhrani pro graficke elementy definujici pripojne body pro cary.
 * @author Martin Šára
 */
public interface ConnectibleElement {
    
    /**
     * Pripojna souradnice X.
     * @return 
     */
    DoubleProperty connectXProperty();
    
    /**
     * Pripojna souradnice Y.
     * @return 
     */
    DoubleProperty connectYProperty();
    
}
