package cz.commons.layoutManager;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtěch Müller
 */
public class DepthRow {

    protected final Integer depth;
    protected final List<DepthRowNode> listNodes;

    public DepthRow(Integer depth) {
        this.depth = depth;
        Integer countNodes = BinaryTreeHelper.getCountElements(depth);
        this.listNodes = new ArrayList<>(countNodes);
        initNodes(countNodes);
    }

    protected DepthRowNode getNodeElement(Integer index) {
        if (index >= listNodes.size()) return null;
        return listNodes.get(index);
    }

    public boolean isEmptyRow() {
        for (DepthRowNode row : listNodes) {
            if (row.containsElement()) {
                return false;
            }
        }
        return true;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getMaxSize() {
        return listNodes.size();
    }

    public Integer getCountNodes(Integer idxFrom, Integer idxTo) {
        return 0; //TODO get count elements in the depth...
    }

    /**
     * Method init row for last level.
     */
    private void initNodes(Integer countNodes) {
        for (int i = 0; i < countNodes; i++) {
            listNodes.add(i, new DepthRowNode(new Point2D(0, 0)));
        }
    }

}
