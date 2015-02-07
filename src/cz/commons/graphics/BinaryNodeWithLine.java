package cz.commons.graphics;

/**
 * @author Vojtěch Müller
 */
public abstract class BinaryNodeWithLine extends BinaryNodeElement {

    private LineElement[] lineToChild = new LineElement[2];

    public BinaryNodeWithLine(int id, int keyWidth, int height) {
        super(id, keyWidth, height);
        lineToChild[0] = new LineElement(this.getLeftChildConnector(),this);
        lineToChild[1] = new LineElement(this.getRightChildConnector(),this);
        lineToChild[0].setVisible(false);
        lineToChild[1].setVisible(false);
    }

    public LineElement getChildLine(NodePosition position){
        return lineToChild[position.equals(NodePosition.LEFT)?0:1];
    }

}
