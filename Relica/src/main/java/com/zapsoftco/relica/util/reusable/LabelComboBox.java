package com.zapsoftco.relica.util.reusable;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class LabelComboBox extends ComboBox<LabelComboBoxData> {

    {
        super.getStyleClass().add("label-combo-box");
        super.setCellFactory(defaultCellFactory());
        super.setButtonCell(defaultCellFactory().call(null));
    }

    public LabelComboBox() {
        super();
    }

    public LabelComboBox(ObservableList<LabelComboBoxData> items) {
        super(items);

    }

    private final Callback<ListView<LabelComboBoxData>, ListCell<LabelComboBoxData>> defaultCellFactory() {
        return new Callback<ListView<LabelComboBoxData>, ListCell<LabelComboBoxData>>() {
            @Override
            public ListCell<LabelComboBoxData> call(ListView<LabelComboBoxData> param) {
                return new ListCell<LabelComboBoxData>() {
                    Label label = new Label();

                    @Override
                    protected void updateItem(LabelComboBoxData item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            label.setText(item.getText());
                            label.setGraphic(createImageView(item.getImage()));
                            label.setContentDisplay(ContentDisplay.LEFT);
                            label.setGraphicTextGap(10);

                            setGraphic(label);
                        }
                    }

                    private ImageView createImageView(Image graphicData) {
                        ImageView view = new ImageView(graphicData);
                        return view;
                    }
                };
            }
        };

    }
}
