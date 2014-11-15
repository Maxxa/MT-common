package cz.commons.utils.dialogs;

import cz.commons.resources.CommonResources;
import cz.commons.utils.presets.Preset;
import cz.commons.utils.presets.PresetItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * Implementace dialogu pro vyber prednastavenych dat.
 * @author Martin Šára
 * @param <V> typ prednastavenych dat (int, double, String...)
 * @param <P> konkretni typ polozek dane sady
 */
public class PresetsDialog<V, P extends PresetItem<V>> extends Dialog.BaseDialogImpl {
    
    /**
     * Sirka dialogu.
     */
    public static final int WIDTH = 500;
    /**
     * Vyska dialogu.
     */
    public static final int HEIGHT = 300;

     /**
     * Sada prednastavenych dat.
     */
    private final Preset<V,P> preset;
    /**
     * Vybrana prednastavena sada dat.
     */
    private P selectedPreset;
    /**
     * Volba animace.
     */
    private boolean animate;

    private final BorderPane layout = new BorderPane();
    
    private final StackPane iconPane = new StackPane();
    private final ImageView image = new ImageView(CommonResources.getResource("icons/confirm48.png").toString());
    
    private ComboBox<P> comboBox;
    private final CheckBox checkBox = new CheckBox("animovat");
                
    private final Label presetsLabel = new Label("Sady:");    
    private final Label itemsLabel;
    private final Label itemsValuesLabel = new Label();

    private final Color BACKGROUND_COLOR =  Color.LIGHTYELLOW;

    /**
     * Kunstrukce dialogu se zvolenymi prednastavenymi daty.
     * @param title titulek dialogu
     * @param preset prednastavena data
     * @param label nadpis prvku sady
     */
    public PresetsDialog(String title, Preset<V,P> preset, String label) {
        super(StageStyle.UTILITY);
        this.itemsLabel = new Label(label);
        init(title);
        setWidth(WIDTH);
        setHeight(HEIGHT);
                
        this.preset = preset;
        doLayout();
        comboBox.requestFocus();
    }
    
    public PresetsDialog(String title, Preset<V,P> preset) {
        this(title, preset, "Vkládané prvky:");
    }

    private void doLayout() {        
        iconPane.getChildren().add(image);
        iconPane.setPrefWidth(48 + 40);
        iconPane.setMinWidth(48 + 40);
        layout.setLeft(iconPane);
        
        comboBox = new ComboBox<>(FXCollections.observableList(preset.getAll()));
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onComboboxChanged();
            }
        });
        if (preset.getAll().size() > 0) comboBox.setValue(preset.getAll().get(0));
        
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animate = checkBox.isSelected();
            }
        });
        
        itemsLabel.setStyle("-fx-padding: 10px 0 0 0;");
        itemsValuesLabel.setStyle("-fx-padding: 0 0 10px 0;");
        itemsValuesLabel.setWrapText(true);
        
        centerVBox.getChildren().addAll(presetsLabel, comboBox, itemsLabel, itemsValuesLabel, checkBox);
        BorderPane.setMargin(centerVBox, new Insets(20, 20, 10, 0));
        layout.setCenter(centerVBox);

        HBox buttonsHBox = getButtonsHBox();
        buttonsHBox.getChildren().addAll(getOkButton(), getCancelButton());
        layout.setBottom(buttonsHBox);

        Scene scene = new Scene(layout, BACKGROUND_COLOR);
        setScene(scene);
        addShortcuts(scene);
    }
    
    private void onComboboxChanged() {
        selectedPreset = comboBox.getValue();        
        V[] items = selectedPreset.getItems();
        int itemsCount = items.length;
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < itemsCount; i++) {
            sb.append(items[i].toString());
            if (i < itemsCount-1) sb.append(", ");
        }
        
        itemsValuesLabel.setText(sb.toString());
    }
    
    /**
     * Vrati zvolenou sadu dat.
     * @return 
     */
    public V[] getSelectedPresetItems() {
        return selectedPreset.getItems();
    }
    
    public P getSelectedPreset() {
        return selectedPreset;
    }
    
    /**
     * Vrati, zda je vybrana moznost animace.
     * @return 
     */
    public boolean runAnimation() {
        return animate;
    }
}
