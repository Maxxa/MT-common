package cz.commons.utils.presets;

import java.util.ArrayList;

/**
 * Rozhrani pro implementaci prednastavenych vstupnich dat.
 * @author Martin Šára
 * @param <V> typ dat (vkladanych do struktury)
 * @param <P> typ jednotlivych polozek sady
 */
public interface Preset<V, P extends PresetItem<V>> {
    
    ArrayList<P> getAll();
    
}
