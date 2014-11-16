package cz.commons.graphics;

/**
 * Node element width only <b> two children </b>.
 *
 * @author Vojtěch Müller
 */
public abstract class BinaryNodeElement extends BranchNodeElement{

    private static final int MAX_CHILDREN = 2;

    private static final int CHILD_LEFT = 0;
    private static final int CHILD_RIGHT = 1;

    public BinaryNodeElement(int id,int keyWidth,int height) {
        super(id, MAX_CHILDREN, keyWidth, height);
    }

    public ConnectibleElement getLeftChildConnector(){
        return getChildConnector(CHILD_LEFT);
    }

    public ConnectibleElement getRightChildConnector(){
        return getChildConnector(CHILD_RIGHT);
    }

}
