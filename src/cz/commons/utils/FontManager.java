package cz.commons.utils;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.text.Font;

/**
 * Manazer fontu zajistujici jejich metriku.
 * Pokud je dostupny objekt {@link FontMetrics}, pouzije jej.
 * Pokud neni pristup k {@link FontMetrics}, pokusi se ziskat metriku fontu 
 * z predem ulozenych dat (prepokladano vyuziti monospaced fontu).
 * {@link FontMetrics} nelze pouzit v nepodepsane aplikaci pri spusteni 
 * pres JNLP (tedy i ve webovem prohlizeci) - nejsou prava.
 * @author Martin Šára
 */
public class FontManager {

    /**
     * Zakazat pouzivani {@link FontMetrics}.
     */
    private static final boolean DO_NOT_USE_FONT_METRICS = true;
    /**
     * Indikuje dostupnost {@link FontMetrics}.
     */
    private boolean fontMetricsAccess;
    /**
     * Ulozeni informaci o fontech.
     */
    private Map<String,FontData> fontData;
    /**
     * Ulozenich {@link FontMetrics} ke konkretnim fontum.
     */
    private Map<Font,FontMetrics> fontMetrics;
  

    
    public FontManager() {
        checkAccess();
        
        if (fontMetricsAccess) {
            fontMetrics = new HashMap<>();
        } else {
            fontData = new HashMap<>();
            addFontData();
        }
    }
    
    
    
    /**
     * Testuje dostupnost {@link FontMetrics}.
     */
    private void checkAccess() {
        try {
            Toolkit.getToolkit().getFontLoader().getFontMetrics(Font.getDefault());
            fontMetricsAccess = true;
        } catch (Exception e) {
            fontMetricsAccess = false;
        }
        if (DO_NOT_USE_FONT_METRICS) fontMetricsAccess = false;
    }
    
    /**
     * Manualni pridani dat o fontoch.
     */
    private void addFontData() {
        /*
        TeXGyreCursor-Regular_12.0
        i width: 7.2
        m width: 7.2
        font size: 12.0
        line height: 14.7
        X height: 5.0039997
        
        TeXGyreCursor-Bold_14.0
        i width: 8.4
        m width: 8.4
        font size: 14.0
        line height: 18.886
        X height: 6.118
        */
        fontData.put("TeXGyreCursor-Regular_12.0", new FontData(7.2f, 14.7f));
        fontData.put("TeXGyreCursor-Bold_14.0", new FontData(8.4f, 18.886f));
    }
    
    /**
     * Vypise informace o fontu.
     * Vyuziva {@link FontMetrics}.
     * @param font 
     */
    public void printFontData(Font font) {
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
        System.out.println(getFontName(font));
        System.out.println("i width: " + fm.computeStringWidth("i"));
        System.out.println("m width: " + fm.computeStringWidth("m"));
        System.out.println("font size: " + font.getSize());
        System.out.println("line height: " + fm.getLineHeight());
        System.out.println("X height: " + fm.getXheight());
    }
    
    /**
     * Vypocita sirku textu vypsaneho danym fontem.
     * @param str
     * @param font
     * @return 
     */
    public float computeStringWidth(String str, Font font) {
        if (fontMetricsAccess) {
            return getFontMetrics(font).computeStringWidth(str);
        } else {
            String fontName = getFontName(font);
            checkFont(fontName);
            return fontData.get(fontName).letterWidth * str.length();
        }
    }
    
    /**
     * Vrati vysku radku daneho fontu.
     * @param font
     * @return 
     */
    public float getLineHeight(Font font) {
        if (fontMetricsAccess) {
            return getFontMetrics(font).getLineHeight();
        } else {
            String fontName = getFontName(font);
            checkFont(fontName);
            return fontData.get(fontName).lineHeight;
        }
    }
    
    /**
     * Vrati polovicni rozdil mezi vyskou radku a vyskou pismene X.
     * @param font
     * @return 
     */
    public float getLineTranslate(Font font) {
        if (fontMetricsAccess) {
            FontMetrics fm = getFontMetrics(font);
            return (fm.getLineHeight() + fm.getXheight()) / 2;
        } else {
            String fontName = getFontName(font);
            checkFont(fontName);
            return fontData.get(fontName).lineTranslate;
        }
    }
    
    private FontMetrics getFontMetrics(Font font) {
        if (fontMetrics.containsKey(font)) {
            return fontMetrics.get(font);
        } else {
            FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
            fontMetrics.put(font, fm);
            return fm;
        }
    }
    
    private void checkFont(String fontName) {
        if (!fontData.containsKey(fontName)) {
            throw new UnsupportedOperationException("Metrika fontu neni zadana.");
        }
    }
    
    private String getFontName(Font font) {
        String fontName = font.getName() + "_" + font.getSize();
        return fontName;
    }
    
    
    
    /**
     * Reprezentace nekterych rozmeru fontu.
     */
    private class FontData {
         
        private float letterWidth;
        private float lineHeight;
        private float lineTranslate;

        public FontData(float letterWidth, float lineHeight) {
            this.letterWidth = letterWidth;
            this.lineHeight = lineHeight;
            this.lineTranslate = (lineHeight-letterWidth)/2;
        }
        
    }
    
}
