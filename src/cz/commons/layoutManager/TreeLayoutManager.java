package cz.commons.layoutManager;

import cz.commons.graphics.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vojtěch Müller
 */
public class TreeLayoutManager {

    protected Map<Integer,Element> elementMap = new HashMap<>();

    protected Integer depth = 0;

    protected final TreeLayoutSettings settings;

    public TreeLayoutManager(TreeLayoutSettings settings) {
        this.settings = settings;
    }





}
