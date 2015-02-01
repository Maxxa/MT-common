package cz.commons.layoutManager;

import cz.commons.graphics.Element;
import javafx.geometry.Point2D;
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


    @Override
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
                indexAtRow = BinaryTreeHelper.getLeftChildIndex(parentInfo.indexAtRow);
            }else{
                indexAtRow = BinaryTreeHelper.getRightChildIndex(parentInfo.indexAtRow);
            }
        }else{
            depth = 0;
            indexAtRow = 0;
        }

        if(depth>(depthManager.getMaxDepth()-1) || depth==0){
            depthManager.addDepth();
        }

        ElementInfo info = new ElementInfo(element,depth,indexAtRow,idParent);
        depthManager.getDepth(depth).getNodeElement(indexAtRow).setElementId(element.getElementId());
        elementMap.put(element.getElementId(),info);
        canvas.getChildren().addAll(element);
        return info;
    }

    @Override
    public boolean removeElement(Integer elementId) {
        return removeElement(elementId,true);
    }

    @Override
    public boolean removeElement(Integer elementId, boolean removeFromCanvas){
        ElementInfo elementInfo = elementMap.get(elementId);
        if(elementInfo!=null){
            if((elementInfo.depth+1)<depthManager.getMaxDepth()){
                // My be exist child i must control.
                Integer leftChild = BinaryTreeHelper.getLeftChildIndex(elementInfo.indexAtRow);
                DepthRow depthRow = depthManager.getDepth(elementInfo.depth);
                DepthRowNode left = depthRow.getNodeElement(leftChild);
                DepthRowNode right = depthRow.getNodeElement(leftChild+1);
                if(left.getElementId()!=null || right.getElementId()!=null){
                    return false;
                }
            }
            elementMap.remove(elementId);
            depthManager.getDepth(elementInfo.depth).getNodeElement(elementInfo.indexAtRow).setElementId(null);
            if(removeFromCanvas){
                canvas.getChildren().remove(elementInfo.element);
            }
            return true;
        }
        return false;
    }

    public void swapElement(Integer firstId,Integer secondId){
        ElementInfo firstInfo = elementMap.get(firstId);
        ElementInfo secondInfo = elementMap.get(secondId);
        if(firstInfo!=null && secondInfo!= null){
            //swap ids at Depth Manager.
            depthManager.getDepth(firstInfo.depth).getNodeElement(firstInfo.indexAtRow).setElementId(secondId);
            depthManager.getDepth(secondInfo.depth).getNodeElement(secondInfo.indexAtRow).setElementId(firstId);

            ElementInfo temp = new ElementInfo(null, firstInfo.getDepth(),firstInfo.getIndexAtRow(),firstInfo.getIdParent());

            setChildrenNewParent(firstInfo, secondId);
            setChildrenNewParent(secondInfo, firstId);

            //swap elements info
            firstInfo.depth = secondInfo.getDepth();
            firstInfo.indexAtRow = secondInfo.getIndexAtRow();

            secondInfo.depth = temp.getDepth();
            secondInfo.indexAtRow = temp.getIndexAtRow();

            if(firstInfo.idParent!=secondId){
                firstInfo.idParent = secondInfo.getIdParent();
            }

            if(temp.getIdParent()!=firstId){
                secondInfo.idParent = temp.getIdParent();
            }

        }
    }

    private void setChildrenNewParent(ElementInfo firstInfo, Integer newParent) {
        if((firstInfo.depth+1)<depthManager.getMaxDepth()){
            Integer idxLeft = BinaryTreeHelper.getLeftChildIndex(firstInfo.indexAtRow);
            Integer leftChildId = depthManager.getDepth(firstInfo.depth+1).getNodeElement(idxLeft).getElementId();
            Integer rightChildId = depthManager.getDepth(firstInfo.depth+1).getNodeElement(idxLeft+1).getElementId();
            if(leftChildId!=null){
                getElementInfo(leftChildId).idParent=newParent;
            }
            if(rightChildId!=null){
                getElementInfo(rightChildId).idParent=newParent;
            }
        }
    }


    @Override
    public Point2D getNodePosition(Integer elementId) {
        ElementInfo elementInfo = elementMap.get(elementId);
        Point2D result = null;
        if(elementInfo!=null){
            result = depthManager.getDepth(elementInfo.getDepth()).getNodeElement(elementInfo.indexAtRow).getPoint();
        }
        return result;
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

    /**
     * This method set elements default position.
     * */
    @Override
    public void rebuildElements() {
        for (Map.Entry<Integer,ElementInfo> entry:elementMap.entrySet()){
            ElementInfo currentInfo = entry.getValue();
            Point2D point = getNodePosition(entry.getKey());
            currentInfo.element.setTranslateX(point.getX());
            currentInfo.element.setTranslateY(point.getY());
        }
    }

}
