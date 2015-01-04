package cz.commons.layoutManager;

import cz.commons.graphics.Element;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vojtěch Müller
 */
public class BinaryTreeLayoutManager implements ITreeLayoutManager {

    protected Map<Integer,ElementInfo> elementMap = new HashMap<>();

    protected final TreeLayoutSettings settings;

    protected final Pane canvas;

    protected final CanvasRangeInfo rangeInfo;

    protected final DepthManager depthManager;

    public BinaryTreeLayoutManager(final TreeLayoutSettings settings, Pane canvas) {
        this.settings = settings;
        this.canvas = canvas;
        rangeInfo = new CanvasRangeInfo((int) canvas.getWidth()/2);
        depthManager = new DepthManager(new IDefaultTreeInfo() {
            @Override
            public TreeLayoutSettings getLayoutSetting() {
                return settings;
            }

            @Override
            public CanvasRangeInfo getCanvasInfo() {
                return rangeInfo;
            }
        });
    }

    public ElementInfo getElementInfo(Integer elementId){
        return elementMap.get(elementId);
    }

    public ElementInfo addElement(Element element, Integer idParent,boolean isLeftChild){
        Integer depth;
        Integer indexAtRow;

        ElementInfo parentInfo = elementMap.get(idParent);

        if(parentInfo!=null){
            depth = parentInfo.depth+1;
            if(isLeftChild){
                indexAtRow = BinaryTreeHelper.getLeftChildrenIndex(parentInfo.indexAtRow);
            }else{
                indexAtRow = BinaryTreeHelper.getRightChildrenIndex(parentInfo.indexAtRow);
            }
        }else{
            depth = 1;
            indexAtRow = 0;
        }

        if(depth>depthManager.getMaxDepth()){
            depthManager.addDepth();
        }

        ElementInfo info = new ElementInfo(element,depth,indexAtRow,idParent);
        depthManager.getDepth(depth).getNodeElement(indexAtRow).setElementId(element.getElementId());
        elementMap.put(element.getElementId(),info);
        return info;
    }

    public void removeElement(Integer elementId){
        //Odstranit pouze jen listy
    }

    public void swapElement(Integer first,Integer second){

    }


    @Override
    public Pane getCanvas() {
        return canvas;
    }

    @Override
    public DepthManager getDepthManager() {
        return depthManager;
    }

    @Override
    public void clear() {
        canvas.getChildren().clear();
        elementMap.clear();
        depthManager.clear();
    }

}
