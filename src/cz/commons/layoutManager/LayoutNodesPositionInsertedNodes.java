package cz.commons.layoutManager;

import javafx.geometry.Point2D;

import java.util.LinkedList;

/**
 * @author Vojtěch Müller
 */
public class LayoutNodesPositionInsertedNodes implements ILayoutChange {

    private final DepthManager depthManager;
    private final IDefaultTreeInfo settings;

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

    private void recalculatePosition() {
        TreeToList treeToList = new TreeToList(depthManager);
        Integer indexRoot = treeToList.getIndexRoot();
        LinkedList<ListTreeInfo> listTree = treeToList.getListTree();
        TreeLayoutSettings layoutSetting = this.settings.getLayoutSetting();
        System.out.println("____________ start "+indexRoot);
        final double colSize = layoutSetting.getVerticalSpace() + layoutSetting.getWidthNode();
        final double space = layoutSetting.getVerticalSpace()/2;


        for (int i = 0; i < listTree.size(); i++) {
            ListTreeInfo info = listTree.get(i);
            DepthRowNode node = depthManager.getDepth(info.getDepth()).getNodeElement(info.getIndexAtRow());
            double positionY = BinaryTreeHelper.getDepthYPosition(info.getDepth()+1, settings.getLayoutSetting());
            double positionX;
            if (i < indexRoot) {
                positionX = settings.getCanvasInfo().getCenterX() - colSize / 2
                            - (indexRoot - i) * colSize;
                System.out.println(positionX);
                positionX-=space;
            } else if (i > indexRoot) {
                positionX = settings.getCanvasInfo().getCenterX() + colSize / 2
                        + (i - indexRoot-1) * colSize;
                positionX+=space;
            } else {
                positionX = settings.getCanvasInfo().getCenterX() - colSize / 2;
                positionX+=space;
            }
            controlNode(node,positionX, positionY
            );
        }
        System.out.println("____________ end");

    }

    private void controlNode(DepthRowNode node, double positionX, double positionY) {
        node.setPoint(new Point2D(positionX,positionY));
    }


}
