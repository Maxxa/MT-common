package cz.commons.layoutManager.helpers;

import java.util.Iterator;

/**
 * @author Vojtěch Müller
 */
public class TreeStructure implements ITreeStructure {

    private boolean isLeftChild;

    private Integer id;
    private Integer idParent;

    private ITreeStructure left;
    private ITreeStructure right;

    public TreeStructure(Integer id, boolean isLeftChild) {
        this.isLeftChild = isLeftChild;
        this.id = id;
    }

    @Override
    public Integer getIdParent() {
        return idParent;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public ITreeStructure getLeftChild() {
        return left;
    }

    @Override
    public ITreeStructure getRightChild() {
        return right;
    }

    @Override
    public boolean isRoot() {
        return idParent == null;
    }

    @Override
    public boolean isLeftChild() {
        return isLeftChild;
    }

    @Override
    public boolean hasLeftChild() {
        return left != null;
    }

    @Override
    public boolean hasRightChild() {
        return right != null;
    }

    @Override
    public void setLeftChild(ITreeStructure left) {
        this.left = left;
    }

    @Override
    public void setRightChild(ITreeStructure right) {
        this.right = right;
    }

    @Override
    public void setIsLeftChild(boolean isLeftChild) {
        this.isLeftChild = isLeftChild;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @Override
    public String toString() {
        return String.format("id: %s , parent: %s , isLeft: %s",id,idParent,isLeftChild);
    }

    @Override
    public Iterator<ITreeStructure> iterator() {
        return new TreeStructureIterator(this);
    }
}
