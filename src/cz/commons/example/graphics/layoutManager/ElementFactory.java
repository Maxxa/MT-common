package cz.commons.example.graphics.layoutManager;

import cz.commons.graphics.Element;
import cz.commons.layoutManager.IFactoryElement;

/**
 * @author Vojtěch Müller
 */
public class ElementFactory implements IFactoryElement<BinaryElement> {

    @Override
    public BinaryElement getElementOnPosition(int x, int y) {
        return new BinaryElement(GeneratorElementsNumbers.getNextId(),x,y,"TODO change");
    }
}
