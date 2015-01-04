package cz.commons.layoutManager;

import cz.commons.graphics.Element;

public class ElementInfo{

    private Element element;

    Integer depth;
    Integer indexAtRow;
    Integer idParent;

    ElementInfo(Element element, Integer depth, Integer indexAtRow, Integer idParent) {
        this.element = element;
        this.depth = depth;
        this.indexAtRow = indexAtRow;
        this.idParent = idParent;
    }

    public Element getElement() {
        return element;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getIndexAtRow() {
        return indexAtRow;
    }

    public Integer getIdParent() {
        return idParent;
    }
}
