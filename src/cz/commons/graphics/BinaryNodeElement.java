package cz.commons.graphics;

/**
 * Node element with only <b> two children </b>.
 *
 * @author Vojtěch Müller
 */
public abstract class BinaryNodeElement extends BranchNodeElement{

    private static final int MAX_CHILDREN = 2;

    public BinaryNodeElement(int id,int keyWidth,int height) {
        super(id, MAX_CHILDREN, keyWidth, height);
    }

}
