package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public final class BinaryTreeHelper {

    /**
     *  Method return count elements for depth. Root element is in 1 depth.
     * */
    public static Integer getCountElements(Integer depth){
        if(depth == 0) return 0;
        return new Double(Math.pow(2,depth-1)).intValue();
    }

    /**
     * Method return maximal row <b>with</b> for depth, with all elements.
     * */
    public static Integer getDepthWidth(Integer maxDepth, TreeLayoutSettings settings){
        if(maxDepth==0) return 0;
        Integer maxElements = getCountElements(maxDepth);
        return  maxElements*settings.getWidthNode()+(maxElements-1)*settings.getVerticalSpace();
    }

    /**
     * @return Integer - Y position for depth
     * */
    public static Integer getDepthYPosition(Integer depth, TreeLayoutSettings settings){
        if(depth==0) return 0;
        return settings.getPaddingTop()+(depth-1)*settings.getHeightNode()+(depth-1)*settings.getHorizontalSpace();
    }

}
