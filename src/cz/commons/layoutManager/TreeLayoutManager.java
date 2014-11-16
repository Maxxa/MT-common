package cz.commons.layoutManager;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutManager {

    protected Map<Integer,ElementInfo> elementMap = new HashMap<>();

    protected Integer currentMaxDepth = 0;

    protected final TreeLayoutSettings settings;

    protected final RangeInfo rangeInfo;

    public TreeLayoutManager(TreeLayoutSettings settings,Pane canvas) {
        this.settings = settings;
        rangeInfo = new RangeInfo();
        rangeInfo.centerX = (int) canvas.getWidth()/2;
    }

    public ElementInfo getElementInfo(Integer elementId){
        return elementMap.get(elementId);
    }

    public ElementInfo addElement(IFactoryElement factory, Integer depth,Integer idParent){
        if(depth> currentMaxDepth){
            recalculateRange(depth);
        }
        ElementInfo info = new ElementInfo();
        if(idParent==null){ //center position
            info.element= factory.getElementOnPosition(rangeInfo.centerX-settings.getWidth()/2,15);
        }else{
            //find and calculate to next depth positions...
        }

        info.depth = depth;
        info.idParent = idParent;
        elementMap.put(info.element.getElementId(),info);
        return info;
    }

    protected void recalculateRange(Integer depth) {
        currentMaxDepth = depth;
        Integer half = BinaryTreeHelper.getMaxDepthWidth(depth,settings)/2;
        rangeInfo.minX = rangeInfo.centerX - half;
        rangeInfo.minX = rangeInfo.centerX+half;
    }


}
