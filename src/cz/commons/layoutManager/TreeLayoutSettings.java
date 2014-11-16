package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutSettings {

    protected final Integer horizontalSpace;
    protected final Integer verticalSpace;
    protected final Integer width;
    protected final Integer height;

    public TreeLayoutSettings(Integer horizontalSpace, Integer verticalSpace, Integer width, Integer height) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.width = width;
        this.height = height;
    }

    public Integer getHorizontalSpace() {
        return horizontalSpace;
    }

    public Integer getVerticalSpace() {
        return verticalSpace;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
