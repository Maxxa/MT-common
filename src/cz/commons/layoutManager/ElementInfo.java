package cz.commons.layoutManager;

import cz.commons.graphics.Element;

public class ElementInfo{

    protected Element element;

    Integer depth;
    Integer indexAtRow;
    Integer idParent;

    ElementInfo(Element element, Integer depth, Integer indexAtRow, Integer idParent) {
        this.element = element;
        this.depth = depth;
        this.indexAtRow = indexAtRow;
        this.idParent = idParent;
    }

    ElementInfo(ElementInfo copyElementInfo){
        this(copyElementInfo.element,copyElementInfo.depth,copyElementInfo.indexAtRow,copyElementInfo.idParent);
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

    @Override
    public String toString() {
        return String.format("Ele [ %s ], Depth[ %s ],index at row [ %s ], id parent [ %s ]",element.getElementId(),depth,indexAtRow,idParent);
    }
}
