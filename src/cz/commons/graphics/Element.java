package cz.commons.graphics;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;

/**
 * Base class for all graphic element.
 * @author Martin Šára
 */
public abstract class Element extends Parent {

    protected int id;
    
    /**
     * List lines leading to other nodes
     * Integer - ID node at the other end
     */
    protected Map<Integer, LineElement> connectors = new HashMap<>();

    /**
     * Add connecter line.
     * @param other two node
     * @param line connected line
     */
    public void addConnector(Element other, LineElement line) {
        connectors.put(other.getElementId(), line);
        if (other.connectors.containsKey(this.getElementId()) == false) {
            other.addConnector(this, line);
        }
    }
    
    public LineElement removeConnector(Element other) {
        LineElement line = connectors.remove(other.id);
        if (other.connectors.containsKey(this.getElementId())) {
            other.removeConnector(this);
        }
        return line;
    }

    public boolean isConnectedWith(Element other) {
        return connectors.containsKey(other.id);
    }
    
    public int getElementId() {
        return id;
    }
    
}
