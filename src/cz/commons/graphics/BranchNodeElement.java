package cz.commons.graphics;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Zakladni trida pro prvky s vice potomky.
 * @author Martin Šára
 */
public abstract class BranchNodeElement extends Element implements ConnectibleElement, MultiConnectibleElement {

    /**
     * Sirka jednoho pole.
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

    public BranchNodeElement(int id, int maxChilds, int keyWidth, int height) {
        this.id = id;
        this.childConnector = new ConnectibleElement[maxChilds];
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
