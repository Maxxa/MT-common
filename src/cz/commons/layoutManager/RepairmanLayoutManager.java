package cz.commons.layoutManager;

import cz.commons.layoutManager.helpers.ITreeStructure;
import javafx.geometry.Point2D;

import java.util.*;

/**
 * This component reconstruct layout manager for changes in the structure.
 *
 * @author Vojtěch Müller
 */
public class RepairmanLayoutManager {

    private final BinaryTreeLayoutManager manager;

    private final ITreeStructure newChangeStructure;

    private final Set<Integer> ignoresMovingIds = new HashSet<>();

    public RepairmanLayoutManager(BinaryTreeLayoutManager manager, ITreeStructure newChangeStructure) {
        this.manager = manager;
        this.newChangeStructure = newChangeStructure;
    }

    public void addIgnoreId(Integer id){
        ignoresMovingIds.add(id);
    }

    /**
     * This method starting reconstruction
     */
    public List<MoveElementEvent> reconstruction() {
        Map<Integer, Point2D> oldPositions= buildOldPositions();

        List<MoveElementEvent> result = new LinkedList<>();
        for (ITreeStructure structure : newChangeStructure) {
            ElementInfo currentNodeInfo = manager.getElementInfo(structure.getId());

            ElementInfo parentInfo = manager.getElementInfo(structure.getIdParent());
            int parentDepth = parentInfo != null ? parentInfo.getDepth() : 0;
            int parentIndexAtRow = parentInfo != null ? parentInfo.getIndexAtRow() : 0;

            // change new depth and index at row
            currentNodeInfo.idParent = structure.getIdParent();
            currentNodeInfo.depth = structure.isRoot() ? 0 : parentDepth + 1;
            currentNodeInfo.indexAtRow = structure.isLeftChild() ?
                    BinaryTreeHelper.getLeftChildIndex(parentIndexAtRow) : BinaryTreeHelper.getRightChildIndex(parentIndexAtRow);

            //set new positions information
            DepthRowNode newNodePosition = getDepthRowNode(currentNodeInfo);
            newNodePosition.setElementId(structure.getId());

            if(!ignoresMovingIds.contains(structure.getId())){
                result.add(new MoveElementEvent(structure.getId(), oldPositions.get(structure.getId()), newNodePosition.getPoint()));
            }
        }
        controlLastDepth();
        return result;
    }

    private Map<Integer, Point2D> buildOldPositions() {
        Map<Integer, Point2D> old = new HashMap<>();
        for (ITreeStructure structure : newChangeStructure) {
            ElementInfo info = manager.getElementInfo(structure.getId());
            DepthRowNode node = manager.getDepthManager().getDepth(info.depth).getNodeElement(info.indexAtRow);
            node.setElementId(null);
            old.put(structure.getId(),node.getPoint());
        }
        return old;
    }

    private void controlLastDepth() {
        int depth = manager.getDepthManager().getMaxDepth() - 1;
        System.out.println("controla posledni vrstvy " + depth);
        DepthRow row = manager.getDepthManager().getDepth(depth);
        for (int i = 0; i < BinaryTreeHelper.getCountElements(depth + 1); i++) {
            DepthRowNode node = row.getNodeElement(i);
            if (node.getElementId() != null) {
                return;
            }
        }
        System.err.println("ODEBRIAM VRSTVU");
        manager.getDepthManager().removeLastDepth();
    }

    private DepthRowNode getDepthRowNode(ElementInfo info) {
        if (info.getDepth() >= manager.getDepthManager().getMaxDepth()) {
            System.err.println("PRIDAVAM VRSTVU");
            manager.getDepthManager().addDepth();
        }
        DepthRow row = manager.getDepthManager().getDepth(info.depth);
        if (row == null) {
            String msg = "depht " + info.depth + " not exist max " + manager.getDepthManager().getMaxDepth();
            throw new IllegalArgumentException(msg);
        }
        DepthRowNode node = row.getNodeElement(info.indexAtRow);
        if (node == null) {
            String msg = info.toString();
            throw new IllegalArgumentException(msg);
        }
        return node;
    }

}
