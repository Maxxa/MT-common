package cz.commons.layoutManager;

/**
* @author Vojtěch Müller
*/
public class ListTreeInfo {

    final Integer id;
    final Integer depth;
    final Integer indexAtRow;

    public ListTreeInfo(Integer id, Integer depth, Integer indexAtRow) {
        this.id = id;
        this.depth = depth;
        this.indexAtRow = indexAtRow;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getIndexAtRow() {
        return indexAtRow;
    }

    @Override
    public String toString() {
        return String.format("ID[%s], DEPTH[%s]", id, depth);
    }

}
