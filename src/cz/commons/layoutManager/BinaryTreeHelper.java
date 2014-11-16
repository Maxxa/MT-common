package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public final class BinaryTreeHelper {

    public static Integer getMaxElements(Integer depth){
        if(depth ==0) return 0;
        return new Double(Math.pow(2,depth)).intValue();
    }


    public static Integer getMaxDepthWidth(Integer maxDepth,TreeLayoutSettings settings){
        if(maxDepth==0)return 0;
        Integer maxElements = getMaxElements(maxDepth);
        return  maxElements*settings.getWidth()+(maxElements-1)*settings.getVerticalSpace();
    };


}
