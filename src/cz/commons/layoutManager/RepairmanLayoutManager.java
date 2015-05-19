package cz.commons.layoutManager;

import cz.commons.layoutManager.helpers.ITreeStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * This component reconstruct layout manager for changes in the structure.
 *
 * @author Vojtěch Müller
 */
public class RepairmanLayoutManager {

    private final BinaryTreeLayoutManager manager;

    private final ITreeStructure newChangeStructure;

    public RepairmanLayoutManager(BinaryTreeLayoutManager manager, ITreeStructure newChangeStructure) {
        this.manager = manager;
        this.newChangeStructure = newChangeStructure;
    }

    /**
     * This method starting reconstruction
     */
    public List<MoveElementEvent> reconstruction() {
        List<MoveElementEvent> result = new LinkedList<>();
        for (ITreeStructure structure : newChangeStructure) {
            ElementInfo currentNodeInfo = manager.getElementInfo(structure.getId());
            ElementInfo parentInfo = manager.getElementInfo(structure.getIdParent());
            int parentDepth = parentInfo != null? parentInfo.getDepth():0;
            int parentIndexAtRow = parentInfo!=null? parentInfo.getIndexAtRow():0;

            currentNodeInfo.idParent = structure.getIdParent();

            //old position information...
            DepthRowNode oldNodePosition = getDepthRowNode(currentNodeInfo);
            oldNodePosition.setElementId(null);

            // change new depth and index at row
            currentNodeInfo.depth = parentDepth + 1;
            currentNodeInfo.indexAtRow = structure.isLeftChild() ?
                    BinaryTreeHelper.getLeftChildIndex(parentIndexAtRow) : BinaryTreeHelper.getRightChildIndex(parentIndexAtRow);

            //set new positions information
            DepthRowNode newNodePosition = getDepthRowNode(currentNodeInfo);
            newNodePosition.setElementId(structure.getId());

            if(!oldNodePosition.getPoint().equals(newNodePosition.getPoint())){
                result.add(new MoveElementEvent(structure.getId(),oldNodePosition.getPoint(),newNodePosition.getPoint()));
            }

        }
        controlLastDepth();
        return result;
    }

    private void controlLastDepth() {
        int depth = manager.getDepthManager().getMaxDepth() - 1;
        DepthRow row = manager.getDepthManager().getDepth(depth);
        for (int i = 0; i < BinaryTreeHelper.getCountElements(depth); i++) {
            DepthRowNode node = row.getNodeElement(i);
            if (node.getElementId() != null) {
                return;
            }
        }
        manager.getDepthManager().removeLastDepth();
    }

    private DepthRowNode getDepthRowNode(ElementInfo info) {
        if (info.getDepth() > manager.getDepthManager().getMaxDepth()) {
            manager.getDepthManager().addDepth();
        }
        return manager.getDepthManager().getDepth(info.depth).getNodeElement(info.indexAtRow);
    }


}
