package cz.commons.layoutManager;

/**
 * @author Vojtěch Müller
 */
public interface ILayoutChange {

    void addRow();

    void addElement();

    void removeElement();

    void refresh();

    void disableGenerateEvent();

    void enableGenerateEvent();

}
