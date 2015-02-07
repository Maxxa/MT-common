package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class WorkBinaryNodeInfoBuilder {

    public static WorkBinaryNodeInfo getWorkInfo(Integer elementId, ITreeLayoutManager manager){
        WorkBinaryNodeInfo result = new WorkBinaryNodeInfo(manager.getElementInfo(elementId));
        if(!result.isEmpty()){
            result.parent= getElementInfo(result.currentElementInfo.getIdParent(), manager);
            int childDepth = result.currentElementInfo.depth+1;
            if(childDepth<manager.getDepthManager().getMaxDepth()){
                int idxLeftChild = BinaryTreeHelper.getLeftChildIndex(result.currentElementInfo.indexAtRow);
                DepthRow childRow = manager.getDepthManager().getDepth(childDepth);
                result.leftChild = getElementInfo(childRow.getNodeElement(idxLeftChild).getElementId(),manager);
                result.rightChild = getElementInfo(childRow.getNodeElement(idxLeftChild + 1).getElementId(), manager);
            }
        }
        return result;
    }


    private static ElementInfo getElementInfo(Integer elementId, ITreeLayoutManager manager) {
        return elementId!=null?manager.getElementInfo(elementId):null;
    }

}
