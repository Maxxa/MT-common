package cz.commons.layoutManager;

import com.google.common.eventbus.EventBus;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtěch Müller
 */
public class DepthRow {

    private final IDefaultTreeInfo info;
    private final Integer depth;
    private final List<DepthRowNode> listNodes;
    private final EventBus eventBus;

    public DepthRow(EventBus eventBus, IDefaultTreeInfo info, Integer depth) {
        this.eventBus =eventBus;
        this.info = info;
        this.depth = depth;
        Integer countNodes = BinaryTreeHelper.getCountElements(depth);
        this.listNodes = new ArrayList<>(countNodes);
        initPoints(countNodes);
    }

    public void recalculateNodesPositions(Integer maxDepth){
        final Integer maxDepthSize = BinaryTreeHelper.getCountElements(maxDepth);
        final double minimalOffset= ((double)(info.getCanvasInfo().getMaxX()-info.getCanvasInfo().getMinX())/maxDepthSize);
        final Integer offsetMultiple = maxDepthSize/listNodes.size();

        Double lastOffset = Double.valueOf(info.getCanvasInfo().getMinX())
                                - info.getLayoutSetting().getWidthNode()/2;
        for (int i = 0; i < listNodes.size(); i++) {
            if(i==0){
                lastOffset += ((double)(offsetMultiple/2))*minimalOffset;
            }else{
                lastOffset += offsetMultiple*minimalOffset;
            }
            DepthRowNode node = listNodes.get(i);
            Point2D point = new Point2D(lastOffset,node.getPoint().getY());
            movePoint(node, point);
        }
    }

    public void recalculateLastDepth() {
        setMaxWith();
        double offsetX = info.getCanvasInfo().getMinX();
        for (int i = 0; i < listNodes.size(); i++) {
            DepthRowNode node = listNodes.get(i);
            Point2D point = new Point2D(offsetX,node.getPoint().getY());
            movePoint(node, point);
            offsetX = point.getX()+info.getLayoutSetting().getWidthNode()+info.getLayoutSetting().getVerticalSpace();
        }
    }

    public DepthRowNode getNodeElement(Integer index){
        if(index >= listNodes.size()) return null;
        return listNodes.get(index);
    }

    private void setMaxWith() {
        info.getCanvasInfo().recalculateRange(BinaryTreeHelper.getDepthWidth(depth, info.getLayoutSetting()));
    }

    /**
     * Method init row for last level.
     */
    private void initPoints(Integer countNodes) {
        setMaxWith();
        Integer positionY = BinaryTreeHelper.getDepthYPosition(depth,info.getLayoutSetting());
        double offsetX = info.getCanvasInfo().getMinX();
        for (int i = 0; i < countNodes; i++) {
            Point2D point = new Point2D(offsetX,positionY);
            listNodes.add(i,new DepthRowNode(point));
            offsetX = point.getX()+info.getLayoutSetting().getWidthNode()+info.getLayoutSetting().getVerticalSpace();
        }
    }

    private void movePoint(DepthRowNode node, Point2D newPoint){
        if(node.getElementId()!=null) {
            eventBus.post(new MoveElementEvent(node.getElementId(), node.getPoint(), newPoint));
        }
        node.setPoint(newPoint);
    }
}
