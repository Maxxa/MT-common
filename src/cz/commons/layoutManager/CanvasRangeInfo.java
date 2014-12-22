package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class CanvasRangeInfo {

    private Integer minX;
    private Integer maxX;

    protected final Integer centerX;

    public CanvasRangeInfo(Integer centerX) {
        this.centerX = centerX;
    }

    protected void recalculateRange(Integer depthWidth) {
        Integer half = depthWidth/2;
        minX = centerX - half;
        maxX = centerX + half;
    }

    public Integer getMinX() {
        return minX;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public Integer getCenterX() {
        return centerX;
    }
}
