package cz.commons.layoutManager;

import javafx.geometry.Point2D;

import java.util.LinkedList;

/**
 * @author Vojtěch Müller
 */
public class LayoutNodesPositionInsertedNodes implements ILayoutChange {

    private final DepthManager depthManager;
    private final IDefaultTreeInfo settings;
    private boolean isEnableGenerateEvent = true;

    public LayoutNodesPositionInsertedNodes(DepthManager depthManager, IDefaultTreeInfo settings) {
        this.depthManager = depthManager;
        this.settings = settings;
    }

    @Override
    public void addRow() {
        // this algorithm ignore adding rows ...
    }

    @Override
    public void addElement() {
        recalculatePosition();
    }

    @Override
    public void removeElement() {
        recalculatePosition();
    }

    @Override
    public void refresh() {
        recalculatePosition();
    }

    @Override
    public void disableGenerateEvent() {
        this.isEnableGenerateEvent = false;
    }

    @Override
    public void enableGenerateEvent() {
        this.isEnableGenerateEvent = true;
    }

    private void recalculatePosition() {
        TreeToList treeToList = new TreeToList(depthManager);
        Integer indexRoot = treeToList.getIndexRoot();
        LinkedList<ListTreeInfo> listTree = treeToList.getListTree();
        TreeLayoutSettings layoutSetting = this.settings.getLayoutSetting();
        final double colSize = layoutSetting.getVerticalSpace() + layoutSetting.getWidthNode();
        final double space = layoutSetting.getVerticalSpace() / 2;

        for (int i = 0; i < listTree.size(); i++) {
            ListTreeInfo info = listTree.get(i);
            DepthRowNode node = depthManager.getDepth(info.getDepth()).getNodeElement(info.getIndexAtRow());
            double positionY = BinaryTreeHelper.getDepthYPosition(info.getDepth() + 1, settings.getLayoutSetting());
            double positionX;
            if (i < indexRoot) {
                positionX = settings.getCanvasInfo().getCenterX() - colSize / 2
                        - (indexRoot - i) * colSize;
                positionX -= space;
            } else if (i > indexRoot) {
                positionX = settings.getCanvasInfo().getCenterX() + colSize / 2
                        + (i - indexRoot - 1) * colSize;
                positionX += space;
            } else {
                positionX = settings.getCanvasInfo().getCenterX() - colSize / 2;
                positionX += space;
            }
            controlNode(node, positionX, positionY
            );
        }
        clearEmptyNodes();
    }

    private void clearEmptyNodes() {
        for (int i = 0; i < depthManager.getMaxDepth(); i++) {
            DepthRow row = depthManager.getDepth(i);
            for (int j = 0; j < row.getMaxSize(); j++) {
                DepthRowNode nodeElement = row.getNodeElement(j);
                if (!nodeElement.containsElement()) {
                    nodeElement.setPoint(new Point2D(0, 0));
                }
            }
        }
    }

    private void controlNode(DepthRowNode node, double positionX, double positionY) {
        Point2D oldPoint = node.getPoint();
        Point2D newPoint = new Point2D(positionX, positionY);
        node.setPoint(new Point2D(positionX, positionY));
        if (isEnableGenerateEvent) {
            if (Double.compare(oldPoint.getX(), newPoint.getX()) != 0 && oldPoint.getX() != 0) {
                depthManager.eventBus.post(new MoveElementEvent(node.getElementId(), oldPoint, newPoint));
            }
        }
    }

}
