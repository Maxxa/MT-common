package cz.commons.example.graphics.layoutManager;

/**
 * @author Vojtěch Müller
 */
public final class GeneratorElementsNumbers {

    private static Integer currentCount = 0;

    public static synchronized Integer getNextId(){
        currentCount++;
        return currentCount;
    }


}
