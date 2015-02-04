package cz.commons.animation;

/**
 * @author Vojtěch Müller
 */
public enum MovingType {
    BACK(-1),
    FORWARD(1);

    private final int rate;

    MovingType(int rate){
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
