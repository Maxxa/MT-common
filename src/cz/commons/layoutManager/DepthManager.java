package cz.commons.layoutManager;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtěch Müller
 */
public class DepthManager {

    protected final EventBus eventBus = new EventBus();

    List<DepthRow> depthRows = new ArrayList<>();

    public void addDepth() {
        DepthRow row = new DepthRow(depthRows.size()+1);
        depthRows.add(depthRows.size(),row);
    }

    public void removeLastDepth() {
        depthRows.remove(depthRows.size()-1);
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

}
