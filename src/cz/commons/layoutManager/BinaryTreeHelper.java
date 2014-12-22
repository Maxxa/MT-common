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
     * Method return maximal <b>width</b> for depth, with all elements.
     * */
    public static Integer getDepthWidth(Integer maxDepth, TreeLayoutSettings settings){
        if(maxDepth==0)return 0;
        Integer maxElements = getCountElements(maxDepth);
        return  maxElements*settings.getWidth()+(maxElements-1)*settings.getVerticalSpace();
    }

}
