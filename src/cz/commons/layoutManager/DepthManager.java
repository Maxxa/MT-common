package cz.commons.layoutManager;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO remove debuging prints
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
        double width= info.getCanvasInfo().getMaxX()-info.getCanvasInfo().getMinX();
        Integer size =BinaryTreeHelper.getCountElements(depthRows.size()+1);
        double d =width/size;
//        System.out.println(String.format("%d -- width: %d -- max depth: %d -- offsetX size: %.3f",
//                                    depthRows.size()+1,
//                                    info.getCanvasInfo().getMaxX()-info.getCanvasInfo().getMinX(),
//                                    BinaryTreeHelper.getCountElements(depthRows.size()+1),
//                                    d
//                            ));
        for (int i = 1; i < depthRows.size(); i++) {
            depthRows.get(i).recalculateNodesPositions(depthRows.size() + 1);
        }
        depthRows.add(depthRows.size(),row);
    }

    public void removeLastDepth() {
        depthRows.remove(depthRows.size());
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

    public Integer getDepth() {
        return depthRows.size();
    }

    public void setEventConsumer(Object consumer){
        eventBus.register(consumer);
    }

//    public static void main(String[] args) {
//        final TreeLayoutSettings settings=  new TreeLayoutSettings(10,10,20,20);
//        final CanvasRangeInfo ci = new CanvasRangeInfo(500);
//        DepthManager man = new DepthManager(new IDefaultTreeInfo() {
//            @Override
//            public TreeLayoutSettings getLayoutSetting() {
//                return settings;
//            }
//
//            @Override
//            public CanvasRangeInfo getCanvasInfo() {
//                return ci;
//            }
//        });
//        System.out.println("_____________________________________________");
//        man.addDepth();
//        System.out.println("_____________________________________________");
//        man.addDepth();
//        System.out.println("_____________________________________________");
//        man.addDepth();
//        System.out.println("_____________________________________________");
//        man.addDepth();
//        System.out.println("_____________________________________________");
//        man.addDepth();
//    }
}
