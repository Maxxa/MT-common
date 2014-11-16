package cz.commons.graphics;

/**
 * TODO for multiple K connectible
 * @author Vojtěch Müller
 */
public abstract class KNodeElement extends BranchNodeElement {


    /**
     * Representing an Element who can be member of branch and can have multiple
     * children attached
     *
     * @param id          - unique id of node
     * @param maxChildren - maximum children <i> (number of keys + 1) </i>
     * @param keyWidth    - width of single key field
     * @param height
     */
    public KNodeElement(int id, int maxChildren, int keyWidth, int height) {
        super(id, maxChildren, keyWidth, height);
    }

    @Override
    protected void doParentBindings() {

    }
}
