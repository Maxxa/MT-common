package cz.commons.layoutManager;

import cz.commons.example.graphics.layoutManager.ElementFactory;
import cz.commons.graphics.Element;

/**
 * @author Vojtěch Müller
 * @deprecated
 */
public interface IFactoryElement<T extends Element> {

    public T getElementOnPosition(int x, int y);

}
