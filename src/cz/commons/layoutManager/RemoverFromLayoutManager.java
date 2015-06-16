package cz.commons.layoutManager;

/**
 * !IMPORTANT
 *
 * USE AT YOUR OWN RISK.
 *
 * For example for remove after removing element at is root and have children... (splay tree)
 *
 * @author Vojtěch Müller
 */
public class RemoverFromLayoutManager {

    private final BinaryTreeLayoutManager manager;
    private final Integer id;

    public RemoverFromLayoutManager(BinaryTreeLayoutManager manager, Integer id){
        this.manager = manager;
        this.id = id;
    }

    public void executeRemove(){
        ElementInfo elementInfo = manager.getElementInfo(id);

        if(elementInfo!=null){
            manager.elementMap.remove(id);
            DepthRowNode rowNode = manager.getDepthManager().getDepth(elementInfo.depth).getNodeElement(elementInfo.getIndexAtRow());
            if(rowNode.getElementId()==id){
                rowNode.setElementId(null);
            }
        }
    }

}
