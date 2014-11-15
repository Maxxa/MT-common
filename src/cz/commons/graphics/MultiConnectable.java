package cz.commons.graphics;

/**
 * Rozhrani pro graficke elementy definujici vice pripojnych bodu pro pomotky.
 * @author Martin Šára
 */
public interface MultiConnectable {
    
    /**
     * Vrati pripojny bod pro i-teho potomka.
     * @param i
     * @return 
     */
    ConnectibleElement getChildConnector(int i);
    
}
