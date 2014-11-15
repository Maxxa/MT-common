package cz.commons.graphics;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;

/**
 * Rodicovska trida pro vsechny graficke prvky.
 * @author Martin Šára
 */
public abstract class Element extends Parent {

    /**
     * ID elementu
     */
    protected int id;
    
    /**
     * Seznam car vedoucich do ostatnich uzlu.
     * Integer - ID uzlu na druhem konci.
     * LineElement - cara.
     */
    protected Map<Integer, LineElement> connectors = new HashMap<>();

    /**
     * Prida spojivaci caru.
     * @param other druhy prvek
     * @param line spojovaci cara
     */
    public void addConnector(Element other, LineElement line) {
        connectors.put(other.getElementId(), line);
        if (other.connectors.containsKey(this.getElementId()) == false) {
            other.addConnector(this, line);
        }
    }
    
    /**
     * Odebere spojovaci caru.
     * @param other druhy prvek
     * @return 
     */
    public LineElement removeConnector(Element other) {
        LineElement line = connectors.remove(other.id);
        if (other.connectors.containsKey(this.getElementId())) {
            other.removeConnector(this);
        }
        return line;
    }
    
    /**
     * Testuje zda jsou prvky spojeny carou.
     * @param other
     * @return 
     */
    public boolean isConnectedWith(Element other) {
        return connectors.containsKey(other.id);
    }
    
    /**
     * Vrati ID prvku.
     * @return 
     */
    public int getElementId() {
        return id;
    }
    
}
