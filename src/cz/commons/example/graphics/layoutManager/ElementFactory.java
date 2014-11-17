package cz.commons.example.graphics.layoutManager;

import cz.commons.layoutManager.IFactoryElement;
import cz.commons.utils.GeneratorElementsNumbers;

/**
 * @author Vojtěch Müller
 */
public class ElementFactory implements IFactoryElement<BinaryElement> {

    @Override
    public BinaryElement getElementOnPosition(int x, int y) {
        return new BinaryElement(GeneratorElementsNumbers.getNextId(),x,y,"TODO change");
    }
}
