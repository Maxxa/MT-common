package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutSettings {

    protected final Integer paddingTop;
    protected final Integer horizontalSpace;
    protected final Integer verticalSpace;
    protected final Integer widthNode;
    protected final Integer heightNode;

    public TreeLayoutSettings(Integer horizontalSpace, Integer verticalSpace, Integer widthNode, Integer heightNode) {
        this(15,horizontalSpace,verticalSpace, widthNode,heightNode);
    }

	/***
	 * 
	 * @param paddingTop
	 *            Padding from top of the canvas
	 * @param horizontalSpace
	 *            Horizontal Spacing between the elements
	 * @param verticalSpace
	 *            Vertical spacing between the elements
	 * @param widthNode
	 *            Width of node element
	 * @param heightNode
	 *            Height of node element
	 */
    public TreeLayoutSettings(Integer paddingTop, Integer horizontalSpace, Integer verticalSpace, Integer widthNode, Integer heightNode) {
        this.paddingTop = paddingTop;
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

    public Integer getPaddingTop() {
        return paddingTop;
    }
}
