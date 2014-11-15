package cz.commons.example.graphics.branchNode;

import cz.commons.graphics.BranchNodeElement;

/**
 * @author Vojtěch Müller
 */
public class ExampleBranchNode extends BranchNodeElement {

    private static final int height = 20;

    public ExampleBranchNode(int id, int maxChildren, int keyWidth) {
        super(id, maxChildren, keyWidth,height);
    }

    @Override
    protected void doParentBindings() {

    }
}
