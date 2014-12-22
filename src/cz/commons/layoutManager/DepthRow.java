package cz.commons.layoutManager;

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

    public DepthRow(IDefaultTreeInfo info,Integer depth) {
        this.info = info;
        this.depth = depth;
        Integer countNodes = BinaryTreeHelper.getCountElements(depth);
        this.listNodes = new ArrayList<>(countNodes);
        initPoints(countNodes);
    }

    /**
     * Method init row for last level.
     *
     * @param countNodes
     * */
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

    private void setMaxWith() {
        info.getCanvasInfo().recalculateRange(BinaryTreeHelper.getDepthWidth(depth, info.getLayoutSetting()));
    }

    public void recalculateNodesPositions(Integer maxDepth){
        final Integer maxDepthSize = BinaryTreeHelper.getCountElements(maxDepth);
        final double minimalOffset= ((double)(info.getCanvasInfo().getMaxX()-info.getCanvasInfo().getMinX())/maxDepthSize);
        final Integer offsetMultiple = maxDepthSize/listNodes.size();
        System.out.println(String.format(
                    "| Depth: %d | Size: %s | Offset: %d | First offest: %d |",
                    depth,
                    listNodes.size(),
                    offsetMultiple,
                    offsetMultiple/2));

        Double lastOffset = Double.valueOf(info.getCanvasInfo().getMinX())
                                - info.getLayoutSetting().getWidthNode()/2;
        System.out.print("\t");
        for (int i = 0; i < listNodes.size(); i++) {
            if(i==0){
                lastOffset += ((double)(offsetMultiple/2))*minimalOffset;
            }else{
                lastOffset += offsetMultiple*minimalOffset;
            }
            System.out.print(String.format(" [ %.3f ] ", lastOffset));
            DepthRowNode node = listNodes.get(i);
            Point2D point = new Point2D(lastOffset,node.getPoint().getY());
            movePoint(node.getPoint(),point);
            node.setPoint(point);
        }
        System.out.println("");
    }

    public void recalculateLastDepth() {
        double offsetX = info.getCanvasInfo().getMinX();
        for (int i = 0; i < listNodes.size(); i++) {
            DepthRowNode node = listNodes.get(i);
            Point2D point = new Point2D(offsetX,node.getPoint().getY());
            movePoint(node.getPoint(),point);
            node.setPoint(point);
            offsetX = point.getX()+info.getLayoutSetting().getWidthNode()+info.getLayoutSetting().getVerticalSpace();
        }
    }

    private void movePoint(Point2D oldPoint, Point2D newPoint){
        //TODO send event
    }
}
