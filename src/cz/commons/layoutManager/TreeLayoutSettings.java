package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutSettings {

    protected final Integer horizontalSpace;
    protected final Integer verticalSpace;
    protected final Integer widthNode;
    protected final Integer heightNode;

    public TreeLayoutSettings(Integer horizontalSpace, Integer verticalSpace, Integer widthNode, Integer heightNode) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.widthNode = widthNode;
        this.heightNode = heightNode;
    }

    public Integer getHorizontalSpace() {
        return horizontalSpace;
    }

    public Integer getVerticalSpace() {
        return verticalSpace;
    }

    public Integer getWidthNode() {
        return widthNode;
    }

    public Integer getHeightNode() {
        return heightNode;
    }

}
