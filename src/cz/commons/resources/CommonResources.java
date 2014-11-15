package cz.commons.resources;

import java.net.URL;

/**
 * Sprava spolecnych zdroju.
 * @author Martin Šára
 */
public class CommonResources {

    /**
     * Vrati URL objektu dle relativni cesty vztazene k teto tride.
     * @param relativePath relativni cesta
     * @return URL
     */
    public static URL getResource(String relativePath) {
        return CommonResources.class.getResource(relativePath);
    }
    
}
