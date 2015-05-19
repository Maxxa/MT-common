package cz.commons.layoutManager.helpers;

/**
 * @author Vojtěch Müller
 */
public interface ITreeStructure {

    Integer getIdParent();

    Integer getId();

    ITreeStructure getLeftChild();

    ITreeStructure getRightChild();

    boolean isRoot();

    boolean isLeftChild();

    boolean hasLeftChild();

    boolean hasRightChild();

    void setLeftChild(ITreeStructure left);
    void setRightChild(ITreeStructure right);

    void setIsLeftChild(boolean isLeftChild);

    void setId(Integer id);
    void setIdParent(Integer idParent);

}
