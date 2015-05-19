package cz.commons.layoutManager.helpers;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Iterator to width
 *
 * @author Vojtěch Müller
 */
public class TreeStructureIterator implements Iterator<ITreeStructure> {

    private LinkedList<ITreeStructure> fifo = new LinkedList<>();

    private ITreeStructure temp = null;

    public TreeStructureIterator(ITreeStructure root) {
        if (root == null) return;
        fifo.add(root);
    }

    @Override
    public boolean hasNext() {
        return !fifo.isEmpty();
    }

    @Override
    public ITreeStructure next() {
        if (fifo.isEmpty()) {
            return null;
        }
        temp = fifo.removeFirst();
        if (temp.hasLeftChild()) {
            fifo.add(temp.getLeftChild());
        }
        if (temp.hasRightChild()) {
            fifo.add(temp.getRightChild());
        }
        return temp;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
