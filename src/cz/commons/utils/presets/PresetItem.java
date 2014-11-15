package cz.commons.utils.presets;

/**
 * Rozhrani pro implementaci jednotlivych sad prednastavenych dat.
 * @author Martin Šára
 * @param <V> typ polozky (napr. vkladane do struktury)
 */
public interface PresetItem<V> {
    
    V[] getItems();    
    
    @Override
    String toString();
    
}
