
package fr.univlille.view;

import fr.univlille.modele.Attribute;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ChangerAxesView {
    private final Stage newWindow;
    private final View vue;
    private final VBox axises;
    private final ArrayList<ComboBox<String>> values;
    private final HBox buttonZone;
    private static final Double[] DIMS = new Double[]{600.0, 400.0};

    public ChangerAxesView(View vue) {
        this.vue = vue;
        this.newWindow = new Stage();
        this.newWindow.initModality(Modality.APPLICATION_MODAL);
        this.newWindow.initOwner(this.vue);
        this.newWindow.setTitle("Changer les axes");
        this.newWindow.setResizable(false);

        BorderPane layout = new BorderPane();
        layout.setMinSize(DIMS[0], DIMS[1]);
        layout.setStyle(this.vue.getFont());

        this.values = new ArrayList<>();
        this.axises = new VBox();
        this.axises.setMinHeight(0.85 * layout.getMinHeight());
        this.generateAxisesZone();

        this.buttonZone = new HBox();
        this.buttonZone.setMinHeight(layout.getMinHeight() - this.axises.getMinHeight());
        this.generateButton();

        layout.setTop(axises);
        layout.setBottom(buttonZone);

        Scene scene = new Scene(layout, DIMS[0], DIMS[1]);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }

    public void generateAxisesZone() {
        this.generateAxises("abscisses", "none");
        this.generateAxises("ordonnées", "#D0D0D0");

        values.get(0).valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                if (oldValue != null) values.get(1).getItems().add(oldValue);
                values.get(1).getItems().remove(newValue);
                if (newValue.equals(values.get(1).getValue())) values.get(1).setValue(null);
            }
        });
    }

    public void generateAxises(String titleAxises, String hexaColorCode) {
        ArrayList<String> attributes = this.getAttributesString();
        HBox elements = new HBox();
        elements.setPrefHeight(this.axises.getMinHeight() / 6);
        elements.setAlignment(Pos.CENTER_LEFT);
        elements.setPadding(new Insets(0, 0, 0, 15));
        elements.setStyle("-fx-background-color: " + hexaColorCode + ";");

        Label label = new Label("Axe des " + titleAxises + " : ");
        label.setMinWidth(0.3 * DIMS[0]);
        HBox.setHgrow(label, Priority.ALWAYS);

        ComboBox<String> axis = new ComboBox<>();
        axis.setMinWidth(0.3 * DIMS[0]);
        axis.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 5");
        axis.getItems().addAll(attributes);

        elements.getChildren().addAll(label, axis);

        this.values.add(axis);
        this.axises.getChildren().add(elements);
    }

    public ArrayList<String> getAttributesString() {
        ArrayList<String> result = new ArrayList<>();
        for (Attribute attr: this.vue.modele.getDataset().getAppAttributes()) {
            result.add(attr.getNom());
        }
        return result;
    }

    private void generateButton() {
        Button cancelButton = new Button("Annuler");
        Button validateButton = new Button("Valider");

        cancelButton.addEventHandler(ActionEvent.ACTION, e -> this.newWindow.close());
        validateButton.addEventHandler(ActionEvent.ACTION, e -> this.changeAxes());

        /* Permet d'activer le bouton que lorsque les deux comboBox ont une valeur */
        BooleanBinding allSelected = values.get(0).valueProperty().isNotNull().and(values.get(1).valueProperty().isNotNull());
        validateButton.disableProperty().bind(allSelected.not());

        styliseButton(cancelButton, validateButton, this.vue, this.buttonZone);
    }

    private void changeAxes() {
        Attribute xAttr = this.getAttribute(this.values.get(0).getValue());
        Attribute yAttr = this.getAttribute(this.values.get(1).getValue());
        this.vue.changeAttributes(xAttr, yAttr);
        this.newWindow.close();
    }

    private Attribute getAttribute(String textValue) {
        for (Attribute attr: this.vue.modele.getDataset().getAppAttributes()) {
            if (attr.getNom().equals(textValue)) return attr;
        }
        return null;
    }

    static void styliseButton(Button cancelButton, Button validateButton, View vue, HBox buttonZone) {
        String buttonStyle = "-fx-text-fill: white;" + vue.getFont() + "-fx-background-radius: 5";
        cancelButton.setStyle("-fx-background-color: #FF0000;" + buttonStyle);
        validateButton.setStyle("-fx-background-color: #138824;" + buttonStyle);

        buttonZone.getChildren().addAll(cancelButton, validateButton);
        buttonZone.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(validateButton, new Insets(15));
    }
}
