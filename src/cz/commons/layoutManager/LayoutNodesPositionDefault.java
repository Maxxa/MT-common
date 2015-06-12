package cz.commons.layoutManager;

import javafx.geometry.Point2D;

/**
 * @author Vojtěch Müller
 */
public class LayoutNodesPositionDefault implements ILayoutChange {

    private final DepthManager depthManager;
    private final IDefaultTreeInfo info;
    private boolean isEnableGenerateEvent = true;

    public LayoutNodesPositionDefault(DepthManager depthManager, IDefaultTreeInfo info) {
        this.depthManager = depthManager;
        this.info = info;
    }

    @Override
    public void addRow() {
        Integer newDepth = depthManager.getMaxDepth();
        initPoints(depthManager.getDepth(newDepth-1));
        for (int i = 1; i < newDepth-1; i++) {
            DepthRow row = depthManager.getDepth(i);
            recalculateNodesPositions(row, newDepth);
        }
    }

    @Override
    public void addElement() {
        // this algorithm ignore adding elements ...
    }

    @Override
    public void removeElement() {
        if (depthManager.getMaxDepth() > 1) {
            DepthRow lastRow = depthManager.getDepth(depthManager.getMaxDepth() - 1);
            if(!lastRow.isEmptyRow()){
                return;
            }

            depthManager.removeLastDepth();

            boolean first = true;
            for (int i = depthManager.getMaxDepth() - 1; i > 0; i--) {
                DepthRow depth = depthManager.getDepth(i);
                if (first) {
                    first = false;
                    recalculateLastDepth(depth);
                } else {
                    recalculateNodesPositions(depth,depthManager.getMaxDepth());
                }
            }
        }
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Refresh for default nodes position counter is not enabled.");
    }

    @Override
    public void disableGenerateEvent() {
        this.isEnableGenerateEvent = false;
    }

    @Override
    public void enableGenerateEvent() {
        this.isEnableGenerateEvent = true;
    }

    /**
     * Method init row for last level.
     */
    private void initPoints(DepthRow row) {
        setMaxWith(row.depth);
        Integer positionY = BinaryTreeHelper.getDepthYPosition(row.depth, info.getLayoutSetting());
        double offsetX = info.getCanvasInfo().getMinX();
        for (int i = 0; i < row.getMaxSize(); i++) {
            Point2D point = new Point2D(offsetX,positionY);
            row.getNodeElement(i).setPoint(point);
            offsetX = point.getX()+info.getLayoutSetting().getWidthNode()+info.getLayoutSetting().getVerticalSpace();
        }
    }

    private void movePoint(DepthRowNode node, Point2D newPoint) {
        if (node.getElementId() != null && isEnableGenerateEvent) {
            depthManager.eventBus.post(new MoveElementEvent(node.getElementId(), node.getPoint(), newPoint));
        }
        node.setPoint(newPoint);
    }

    private void recalculateNodesPositions(DepthRow row, Integer maxDepth){
        final Integer maxDepthSize = BinaryTreeHelper.getCountElements(maxDepth);
        final double minimalOffset= ((double)(info.getCanvasInfo().getMaxX()-info.getCanvasInfo().getMinX())/maxDepthSize);
        final Integer offsetMultiple = maxDepthSize/row.getMaxSize();

        Double lastOffset = Double.valueOf(info.getCanvasInfo().getMinX())
                - info.getLayoutSetting().getWidthNode()/2;
        for (int i = 0; i < row.getMaxSize(); i++) {
            if(i==0){
                lastOffset += ((double)(offsetMultiple/2))*minimalOffset;
            }else{
                lastOffset += offsetMultiple*minimalOffset;
            }
            DepthRowNode node = row.getNodeElement(i);
            Point2D point = new Point2D(lastOffset,node.getPoint().getY());
            movePoint(node, point);
        }
    }

    private void recalculateLastDepth(DepthRow row) {
        setMaxWith(row.depth);
        double offsetX = info.getCanvasInfo().getMinX();
        for (int i = 0; i < row.getMaxSize(); i++) {
            DepthRowNode node = row.getNodeElement(i);
            Point2D point = new Point2D(offsetX, node.getPoint().getY());
            movePoint(node, point);
            offsetX = point.getX() + info.getLayoutSetting().getWidthNode() + info.getLayoutSetting().getVerticalSpace();
        }
    }

    private void setMaxWith(Integer depth) {
       info.getCanvasInfo().recalculateRange(
               BinaryTreeHelper.getDepthWidth(depth, info.getLayoutSetting())
       );
    }
}
