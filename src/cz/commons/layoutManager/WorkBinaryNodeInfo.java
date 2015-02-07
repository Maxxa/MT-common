package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public class WorkBinaryNodeInfo {

    protected ElementInfo currentElementInfo;

    protected ElementInfo parent;
    protected ElementInfo leftChild;
    protected ElementInfo rightChild;

    protected WorkBinaryNodeInfo(ElementInfo currentElementInfo) {
        this.currentElementInfo = currentElementInfo;
    }

    public boolean isEmpty(){
        return currentElementInfo==null;
    }

    public boolean hasParent(){
        return parent!=null;

    }
    public boolean hasLeft(){
        return leftChild!=null;

    }
    public boolean hasRight(){
        return rightChild!=null;

    }

    public ElementInfo getCurrentElement() {
        return currentElementInfo;
    }

    public ElementInfo getParent() {
        return parent;
    }

    public ElementInfo getLeftChild() {
        return leftChild;
    }

    public ElementInfo getRightChild() {
        return rightChild;
    }

    @Override
    public String toString() {
        if(isEmpty()){
            return "IS EMPTY !!!";
        }else{
            return String.format("IDX [%d] NODE [%d] (parent [%d]) -- left {%d}, right {%d} ",
                    currentElementInfo.indexAtRow, currentElementInfo.getElement().getElementId(),
                    currentElementInfo.getIdParent(),
                    hasLeft()?leftChild.getElement().getElementId():null,
                    hasRight()?rightChild.getElement().getElementId():null
            );
        }
    }
}
