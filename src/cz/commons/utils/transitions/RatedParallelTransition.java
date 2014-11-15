package cz.commons.utils.transitions;

import javafx.animation.ParallelTransition;

/**
 * Builder paralelni animace s prednastavenou rychlosti.
 * @author Martin Šára
 */
public class RatedParallelTransition {
    
    /**
     * Vrati paralelni animaci s prednastavenou rychlosti.
     * @param rate rychlost
     * @return 
     */
    public static ParallelTransition build(double rate) {
        ParallelTransition pt = new ParallelTransition();
        pt.setRate(rate);
        return pt;
    }
    
}
