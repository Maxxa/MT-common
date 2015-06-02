package cz.commons.layoutManager;

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
        //TODO recalculate tree structure...

    }

    @Override
    public void removeElement() {
        //TODO recalculate tree structure...
    }
}
