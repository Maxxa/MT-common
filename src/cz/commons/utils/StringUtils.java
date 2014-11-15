package cz.commons.utils;

/**
 * Pomocne metody pro praci s retezci.
 * @author Martin Šára
 */
public class StringUtils {
    
    /**
     * Prevede retezec z radku na retezec ve sloupci.
     * Kazdy znak vlozi na novy radek.
     * @param text
     * @return 
     */
    public static String getVerticalText(String text) {
        StringBuilder sb = new StringBuilder();
        int i = 0;        
        text = text.toUpperCase();
        
        for (char ch : text.toCharArray()) {
            sb.append(ch);
            if (++i < text.length()) sb.append("\n");
        }
        
        return sb.toString();
    }
    
}
