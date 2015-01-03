package cz.commons.layoutManager;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutManager implements ITreeLayoutManager {

    protected Map<Integer,ElementInfo> elementMap = new HashMap<>();

    protected Integer currentMaxDepth = 0;

    protected final TreeLayoutSettings settings;

    protected final CanvasRangeInfo rangeInfo;

    protected final DepthManager depthManager;

    public TreeLayoutManager(final TreeLayoutSettings settings,Pane canvas) {
        this.settings = settings;
        rangeInfo = new CanvasRangeInfo((int) canvas.getWidth()/2);
        depthManager = new DepthManager(new IDefaultTreeInfo() {
            @Override
            public TreeLayoutSettings getLayoutSetting() {
                return settings;
            }

            @Override
            public CanvasRangeInfo getCanvasInfo() {
                return rangeInfo;
            }
        });
    }

    public ElementInfo getElementInfo(Integer elementId){
        return elementMap.get(elementId);
    }

    public ElementInfo addElement(IFactoryElement factory, Integer depth,Integer idParent){
           //TODO
        return null;
    }


    @Override
    public void clear() {
        currentMaxDepth=0;
        elementMap.clear();
        depthManager.clear();

    }
}
