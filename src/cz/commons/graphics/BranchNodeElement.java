package cz.commons.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Basic class for elements with <b>multiple</b> children.
 * 
 * @author Martin Šára
 */
public abstract class BranchNodeElement extends Element implements ConnectibleElement, MultiConnectibleElement {

    /**
	 * Width of single field. The node can consist multiple fields (depending on
	 * how many keys/maxChildren are present)
	 */
    private final int fieldWidth;
    /**
     * Vyska.
     */
    private final int height;
    /**
     * Pripojna souradnice X.
     */
    protected final DoubleProperty centerX = new SimpleDoubleProperty();
    /**
     * Pripojna souradnice Y.
     */
    protected final DoubleProperty centerY = new SimpleDoubleProperty();
    /**
     * Pripojene body pro potomky.
     */
    private final ConnectibleElement[] childConnector;

	/***
	 * Representing an Element who can be member of branch and can have multiple
	 * chidlren attached
	 * 
	 * 
	 * @param id
	 *            - unique id of node
	 * @param maxChildren
	 *            - maximum children <i> (number of keys + 1) </i>
	 * @param keyWidth
	 *            - width of single key field
	 * @param height
	 *            - height of element
	 */
	public BranchNodeElement(int id, int maxChildren, int keyWidth, int height) {
        this.id = id;
		this.childConnector = new ConnectibleElement[maxChildren];
        this.fieldWidth = keyWidth;
        this.height = height;

        doChildBindings();
    }

    /**
     * Inicializace pripojneho bodu pro pripojeni rodice.
     */
    protected abstract void doParentBindings();

    /**
     * Inicializace pripojnych bodu potomku.
     */
    private void doChildBindings() {
        for (int i = 0; i < childConnector.length; i++) {
            final int j = i;
            childConnector[i] = new ConnectibleElement() {
                @Override
                public DoubleProperty connectXProperty() {
                    DoubleProperty connectX = new SimpleDoubleProperty();
                    connectX.bind(Bindings.add(j * fieldWidth, translateXProperty()));
                    return connectX;
                }

                @Override
                public DoubleProperty connectYProperty() {
                    DoubleProperty connectY = new SimpleDoubleProperty();
                    connectY.bind(Bindings.add(height, translateYProperty()));
                    return connectY;
                }
            };
        }
    }

    
    @Override
    public ConnectibleElement getChildConnector(int i) {
        return childConnector[i];
    }
    
    @Override
    public DoubleProperty connectXProperty() {
        return centerX;
    }

    @Override
    public DoubleProperty connectYProperty() {
        return centerY;
    }

}
