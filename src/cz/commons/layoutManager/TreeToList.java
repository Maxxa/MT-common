package cz.commons.layoutManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Vojtěch Müller
 */
public class TreeToList {

    private final DepthManager depthManager;

    private Map<Integer, Integer> indexes = new HashMap<>();

    private LinkedList<ListTreeInfo> listTree = new LinkedList<>();

    private Integer indexRoot = 0;

    public TreeToList(DepthManager depthManager) {
        this.depthManager = depthManager;
        build(0, 0, null, true);
    }

    private void build(int depth, int indexAtRow, Integer idParent, boolean isLeft) {
        DepthRow depthRow = depthManager.getDepth(depth);
        if (depthRow != null) {
            DepthRowNode node = depthRow.getNodeElement(indexAtRow);
            if (node != null && node.containsElement()) {
                ListTreeInfo info = new ListTreeInfo(node.getElementId(), depth, indexAtRow);
                int index = 0;
                if (indexes.containsKey(idParent)) {
                    int indexParent = indexes.get(idParent);
                    index = indexParent;
                    if (isLeft) {
                        changeIndexes(indexParent);
                    } else {
                        index++;
                        changeIndexes(indexParent + 1);
                    }

                    if (index <= indexRoot) {
                        indexRoot++;
                    }
                }
                indexes.put(node.getElementId(), index);


                this.listTree.add(index, info);
                int leftChildIndex = BinaryTreeHelper.getLeftChildIndex(indexAtRow);
                build(depth + 1, leftChildIndex, node.getElementId(), true);
                build(depth + 1, leftChildIndex + 1, node.getElementId(), false);
            }
        }
    }

    public LinkedList<ListTreeInfo> getListTree() {
        return listTree;
    }

//    public Map<Integer, Integer> getIndexes() {
//        return indexes;
//    }

    public Integer getIndexRoot() {
        return indexRoot;
    }

    private void changeIndexes(int fromIndex) {
        for (int i = fromIndex; i < listTree.size(); i++) {
            ListTreeInfo info = listTree.get(i);
            int oldId = indexes.get(info.id);
            indexes.put(info.id, oldId + 1);
        }
    }

}
