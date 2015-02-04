package cz.commons.layoutManager;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtěch Müller
 */
public class DepthManager {

    private final IDefaultTreeInfo info;

    private final EventBus eventBus = new EventBus();

    List<DepthRow> depthRows = new ArrayList<>();

    public DepthManager(IDefaultTreeInfo info){
        this.info =info;
    }

    public void addDepth() {
        DepthRow row = new DepthRow(eventBus,info, depthRows.size()+1);
        for (int i = 1; i < depthRows.size(); i++) {
            depthRows.get(i).recalculateNodesPositions(depthRows.size() + 1);
        }
        depthRows.add(depthRows.size(),row);
    }

    public void removeLastDepth() {
        depthRows.remove(depthRows.size()-1);
        boolean first = true;
        for (int i = depthRows.size()-1; i > 0; i--){
            if(first){
                first = false;
                depthRows.get(i).recalculateLastDepth();
            }else{
                depthRows.get(i).recalculateNodesPositions(depthRows.size());
            }
        }
    }

    public DepthRow getDepth(Integer depth) {
        return depth < depthRows.size()? depthRows.get(depth):null;
    }

    public Integer getMaxDepth() {
        return depthRows.size();
    }

    public void addEventConsumer(Object consumer){
        eventBus.register(consumer);
    }

    public void clear() {
        this.depthRows.clear();
    }

    public void controlRecalculationLastDepth() {
        if(depthRows.size()>1){
            DepthRow lastRow = this.depthRows.get(depthRows.size()-1);
            for (int i = 0; i < BinaryTreeHelper.getCountElements(getMaxDepth()); i++) {
               if(lastRow.getNodeElement(i).getElementId()!=null){
                   return;
               }
            }
            this.removeLastDepth();
        }
    }
}
