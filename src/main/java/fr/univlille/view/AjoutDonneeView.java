package fr.univlille.view;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.AttributeType;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AjoutDonneeView {
    private final Stage newWindow;
    private final View vue;
    private final VBox listAttr;
    private final ArrayList<TextField> values;
    private final HBox buttonZone;
    private static final Double[] DIMS = new Double[]{600.0, 450.0};

    public AjoutDonneeView(View vue) {
        this.vue = vue;
        this.newWindow = new Stage();
        this.newWindow.initModality(Modality.APPLICATION_MODAL);
        this.newWindow.initOwner(this.vue);
        this.newWindow.setTitle("Ajout d'un point");

        BorderPane layout = new BorderPane();
        layout.setMinSize(DIMS[0], DIMS[1]);
        this.listAttr = new VBox();
        this.values = new ArrayList<>();
        this.listAttr.setMinHeight(0.85 * layout.getMinHeight());
        this.addAttrZones();

        this.buttonZone = new HBox();
        this.generateButton();

        VBox content = new VBox();
        content.getChildren().addAll(this.listAttr,this.buttonZone);

        layout.setCenter(content);

        Scene scene = new Scene(layout, DIMS[0], DIMS[1]);
        newWindow.setScene(scene);
        this.activeValidation();
        newWindow.showAndWait();
    }

    public void addAttrZones() {
        int cptAttr = 0;
        for (Attribute attr : this.vue.modele.getDataset().getAppAttributes()) {
            if (!attr.getNom().equals(this.vue.modele.getSelectedCategory())){

            HBox attrZone = new HBox();
            attrZone.setPrefHeight(listAttr.getMinHeight() / 4);

            Label label = new Label(attr.getNom() + " : ");
            HBox.setHgrow(label, Priority.ALWAYS);
            label.setMinWidth(0.30 * DIMS[1]);

            TextField result = getFieldForAttribute(attr);
            result.textProperty().addListener((observable, oldValue, newValue) -> this.activeValidation());
            values.add(result);

            attrZone.getChildren().addAll(label, result);
            attrZone.setAlignment(Pos.CENTER_LEFT);
            attrZone.setMinHeight(0.1 * listAttr.getHeight());
            attrZone.setStyle(this.vue.getFont());
            attrZone.setPadding(new Insets(0, 0, 0, 15));

            if (cptAttr++ % 2 != 0) attrZone.setStyle(attrZone.getStyle() + "-fx-background-color: #D0D0D0;");
            this.listAttr.getChildren().add(attrZone);
            }
        }
    }

    private TextField getFieldForAttribute(Attribute attr) {
        TextField textField = new TextField();
        AttributeType type = attr.getType();

        switch (type) {
            case NUM:
                textField.setPromptText("Entrez un nombre");
                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("^[0-9]*([.,][0-9]*)?$")) {
                        return change;
                    }
                    return null;
                }));
                break;

            case BOOL:
                textField.setPromptText("true ou false");
                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText().toLowerCase();
                    if ("true".startsWith(newText) || "false".startsWith(newText)) {
                        return change;
                    }
                    return null;
                }));
                break;

            case STR:
            default:
                textField.setPromptText("Entrez une valeur");
                break;
        }

        return textField;
    }

    private void generateButton() {
        Button cancelButton = new Button("Annuler");
        Button validateButton = new Button("Valider");

        cancelButton.addEventHandler(ActionEvent.ACTION, e -> this.newWindow.close());
        validateButton.addEventHandler(ActionEvent.ACTION, e -> this.addPoint());

        ChangerAxesView.styliseButton(cancelButton, validateButton, this.vue, this.buttonZone);
    }

    private void activeValidation() {
        try {
            for (int i = 0; i < this.values.size(); i++) {
                TextField tf = this.values.get(i);
                Attribute attr = this.vue.modele.getAppAttributes().get(i);

                if (attr.getType() == AttributeType.NUM) {
                    Double.parseDouble(tf.getText());
                }
            }
            this.buttonZone.getChildren().get(1).setDisable(false);
        } catch (NumberFormatException nfe) {
            this.buttonZone.getChildren().get(1).setDisable(true);
        }
    }

    public void addPoint() {
        HashMap<Attribute, Object> attributes = new HashMap<>();
        List<Attribute> appAttributes = this.vue.modele.getDataset().getAppAttributes();

        for (int idxAttr = 0; idxAttr < this.values.size(); idxAttr++) {
            String rawValue = this.values.get(idxAttr).getText();
            AttributeType type = appAttributes.get(idxAttr).getType();
            Object value;

            if (type == AttributeType.NUM) {
                value = Double.parseDouble(rawValue);
            } else if (type == AttributeType.BOOL) {
                value = Boolean.parseBoolean(rawValue.toLowerCase());
            } else {
                value = rawValue;
            }

            attributes.put(appAttributes.get(idxAttr), value);
        }

        this.vue.modele.ajouterPoint(attributes);
        this.newWindow.close();
        this.vue.getAddPointButton().setDisable(true);
        this.vue.getClassifyButton().setDisable(false);
    }
}
