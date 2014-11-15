package cz.commons.utils;

import java.util.Random;

/**
 * Pomocna trida pro generovani cisel.
 * @author Martin Šára
 */
public class Generator {

    /**
     * Vrati nahodne cislo v danem rozmezi.
     * @param min dolni hranice
     * @param max horni hranice
     * @return nahodne cislo v urcenem rozmezi
     */
    public static int generate(int min, int max) {
        Random r = new Random();
        return min + (int) Math.round((max - min) * r.nextDouble());
    }
    
    /**
     * Vrati nahodny retezec obsahujici znaky a-z pozadovane delky.
     * @param length
     * @return 
     */
    public static String generateRandomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        
        for (int i = 0; i < length; i++) {
            char c = chars[r.nextInt(chars.length)];
            sb.append(c);
        }
        
        return sb.toString();
    }
    
    /**
     * Vrati nahodne generovane slovo obsahujici znaky dle jejich pravdepodobnosti vyskytu v ceskem jazyce.
     * @param length
     * @param diacritics
     * @return 
     */
    public static String generateCzechWord(int length, boolean diacritics) { 
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            String character = getCzechChar(diacritics);
            sb.append(character);
        }
        
        return sb.toString();
    }
    
    /**
     * Vrati znak dle pravdepodobnosti jeho vyskytu v ceskem textu.
     * Zdroj: http://www.mensa.cz/volny-cas/hlavolamy/detska-sifrovaci-liga/frekvencni-tabulka-pismen
     * @param diacritics pokud true, vraci i pismena s diakritikou
     * @return 
     */
    private static String getCzechChar(boolean diacritics) {
        double val = new Random().nextDouble()*100;
        if (val <= 8.6664) return "o";
        else if (val <= 16.3616) return "e";
        else if (val <= 22.8969) return "n";
        else if (val <= 29.1162) return "a";
        else if (val <= 34.843) return "t";
        else if (val <= 39.5046) return "v";
        else if (val <= 44.0206) return "s";
        else if (val <= 48.3734) return "i";
        else if (val <= 52.2158) return "l";
        else if (val <= 55.9525) return "k";
        else if (val <= 59.6495) return "r";
        else if (val <= 63.2514) return "d";
        else if (val <= 66.6641) return "p";
        else if (val <= 69.934) return diacritics ? "í" : "i";
        else if (val <= 73.1607) return "m";
        else if (val <= 76.305) return "u";
        else if (val <= 78.5405) return diacritics ? "á" : "a";
        else if (val <= 80.7392) return "z";
        else if (val <= 82.8586) return "j";
        else if (val <= 84.7679) return "y";
        else if (val <= 86.4132) return diacritics ? "ě" : "e";
        else if (val <= 88.0199) return "c";
        else if (val <= 89.5781) return "b";
        else if (val <= 90.9127) return diacritics ? "é" : "e";
        else if (val <= 92.1839) return "h";
        else if (val <= 93.4005) return diacritics ? "ř" : "r";
        else if (val <= 94.5714) return "ch";
        else if (val <= 95.6435) return diacritics ? "ý" : "y";
        else if (val <= 96.6387) return diacritics ? "ž" : "z";
        else if (val <= 97.5877) return diacritics ? "č" : "c";
        else if (val <= 98.3929) return diacritics ? "š" : "s";
        else if (val <= 99.0877) return diacritics ? "ů" : "u";
        else if (val <= 99.3609) return "f";
        else if (val <= 99.6338) return "g";
        else if (val <= 99.7369) return diacritics ? "ú" : "u";
        else if (val <= 99.8183) return diacritics ? "ň" : "n";
        else if (val <= 99.8938) return "x";
        else if (val <= 99.9364) return diacritics ? "ť" : "t";
        else if (val <= 99.9677) return diacritics ? "ó" : "o";
        else if (val <= 99.9899) return diacritics ? "ď" : "d";
        else if (val <= 99.9987) return "w";
        else return "q";
    }
    
}
