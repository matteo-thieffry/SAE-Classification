
package fr.univlille.view;

import fr.univlille.algoknn.distances.*;
import fr.univlille.modele.AlreadyAffectedCategoryException;
import fr.univlille.modele.Attribute;
import fr.univlille.modele.Controleur;
import fr.univlille.modele.UniquePointToClassifyException;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassificationView {
    private final Stage newWindow;
    private final View vue;
    private final Controleur controleur;
    private HBox buttonZone;
    private static final Double[] DIMS = new Double[]{600.0, 800.0};
    private Spinner<Integer> kSpinner;
    private ComboBox<String> distanceComboBox;
    private HashMap<String, Distance> distInstances;
    private HashMap<Attribute, CheckBox> attributeCheckBoxes;

    public ClassificationView(View vue, Controleur controleur) {
        this.vue = vue;
        this.controleur = controleur;
        this.initInstances();
        this.newWindow = new Stage();
        this.newWindow.initModality(Modality.APPLICATION_MODAL);
        this.newWindow.initOwner(this.vue);
        this.newWindow.setTitle("Classification - Choix des paramètres");
        this.newWindow.setResizable(false);

        VBox layout = new VBox();
        layout.setMinSize(DIMS[0], DIMS[1]);
        layout.setStyle(this.vue.getFont());

        layout.getChildren().addAll(getKChoiceZone(), getDistanceZone(), getAttributesZone(), getButtonZone());

        Scene scene = new Scene(layout, DIMS[0], DIMS[1]);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }

    private void initInstances() {
        this.distInstances = new HashMap<>();
        this.distInstances.put("Distance euclidienne", new DistanceEuclidienne());
        this.distInstances.put("Distance manhattan", new DistanceManhattan());
        this.distInstances.put("Distance euclidienne normalisée", new DistanceEuclidienneNormalisee());
        this.distInstances.put("Distance manhattan normalisée", new DistanceManhattanNormalisee());
    }

    private HBox getZone() {
        HBox result = new HBox();
        result.setPrefHeight(DIMS[1] / 12);
        result.setAlignment(Pos.CENTER_LEFT);
        result.setPadding(new Insets(0, 0, 0, 15));
        return result;
    }

    private HBox getKChoiceZone() {
        HBox result = this.getZone();

        Label label = new Label("Nombre de voisins : ");
        label.setMinWidth(0.3 * DIMS[0]);
        HBox.setHgrow(label, Priority.ALWAYS);

        this.kSpinner = new Spinner<>();
        this.kSpinner.setMinWidth(0.5 * DIMS[0]);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controleur.getDataset().getDataPoints().size(), 5);
        this.kSpinner.setValueFactory(valueFactory);
        this.kSpinner.setEditable(true);

        result.getChildren().addAll(label, kSpinner);
        return result;
    }

    private HBox getDistanceZone() {
        HBox result = this.getZone();
        result.setStyle("-fx-background-color: #D0D0D0;");
        Label label = new Label("Distance utilisée : ");
        label.setMinWidth(0.3 * DIMS[0]);
        HBox.setHgrow(label, Priority.ALWAYS);

        ArrayList<String> distancesStr = new ArrayList<>(this.distInstances.keySet());

        this.distanceComboBox = new ComboBox<>();
        this.distanceComboBox.setMinWidth(0.5 * DIMS[0]);
        this.distanceComboBox.setStyle("-fx-background-color: none; -fx-border-color: black; -fx-border-radius: 5");
        this.distanceComboBox.getItems().addAll(distancesStr);

        result.getChildren().addAll(label, distanceComboBox);
        return result;
    }

    private HBox getButtonZone() {
        this.buttonZone = new HBox();
        this.buttonZone.setMinHeight(DIMS[1] / 12);
        this.generateButton();
        return buttonZone;
    }

    private VBox getAttributesZone() {
        initAttributesCheckBoxes();
        VBox attributesZone = new VBox();
        attributesZone.setMinHeight(9.0 / 12 * DIMS[1]);
        attributesZone.setAlignment(Pos.TOP_CENTER);

        GridPane attributesSelectionZone = new GridPane();
        attributesSelectionZone.setAlignment(Pos.CENTER);
        attributesSelectionZone.setStyle(   "-fx-border-color: black transparent;" +
                                            "-fx-border-width: 2 0;" +
                                            "-fx-border-style: solid;"
        );

        List<Attribute> appAttributes = controleur.getDataset().getAppAttributes();
        int nbCases = appAttributes.size();
        double minHeightCase = (8.0 / 12 * DIMS[1]) / (Math.ceil(nbCases / 2.0));
        minHeightCase = Math.min(minHeightCase, 75);

        List<String> backgroundColors = generateAttrZonesBackColor();

        for (int i = 0; i < appAttributes.size(); i++) {
            Attribute attr = appAttributes.get(i);
            HBox attrZone = createAttributeZone(attr, backgroundColors.get(i), minHeightCase);
            int col = i % 2;
            int row = i / 2;
            attributesSelectionZone.add(attrZone, col, row);
        }

        Label titleSection = new Label("Attributs utilisés pour la classification : ");
        titleSection.setMinHeight(DIMS[1] / 12);

        attributesZone.getChildren().addAll(titleSection, attributesSelectionZone);
        return attributesZone;
    }

    private HBox createAttributeZone(Attribute attr, String backgroundColor, double minHeight) {
        HBox attrZone = new HBox();
        attrZone.setMinWidth(0.5 * DIMS[0]);
        attrZone.setAlignment(Pos.CENTER);
        attrZone.setMinHeight(minHeight);
        attrZone.setStyle("-fx-background-color: " + backgroundColor + ";");

        Label label = new Label(attr.getNom());
        label.setAlignment(Pos.CENTER);
        HBox.setMargin(label, new Insets(0, 15, 0, 0));

        attrZone.getChildren().addAll(label, attributeCheckBoxes.get(attr));
        return attrZone;
    }

    private void initAttributesCheckBoxes() {
        this.attributeCheckBoxes = new HashMap<>();
        for (Attribute attr: controleur.getDataset().getAppAttributes()) {
            CheckBox attrCheckBox = new CheckBox();
            attrCheckBox.setSelected(true);
            this.attributeCheckBoxes.put(attr, attrCheckBox);
        }
    }

    private ArrayList<String> generateAttrZonesBackColor() {
        ArrayList<String> colors = new ArrayList<>();
        for (int idx = 0; idx <= controleur.getDataset().getAppAttributes().size() / 4; idx++) {
            colors.add("none");
            colors.add("#D0D0D0");
            colors.add("#D0D0D0");
            colors.add("none");
        }
        return colors;
    }

    private void generateButton() {
        Button cancelButton = new Button("Annuler");
        Button validateButton = new Button("Valider");

        cancelButton.addEventHandler(ActionEvent.ACTION, e -> this.newWindow.close());
        validateButton.addEventHandler(ActionEvent.ACTION, e -> {
            try {
                this.validateAction();
                this.newWindow.close();
            } catch (AlreadyAffectedCategoryException ex) {
                throw new RuntimeException(ex);
            } catch (UniquePointToClassifyException ex) {
                throw new RuntimeException(ex);
            }
        });

        BooleanBinding allSelected = this.distanceComboBox.valueProperty().isNotNull();
        validateButton.disableProperty().bind(allSelected.not());

        ChangerAxesView.styliseButton(cancelButton, validateButton, vue, buttonZone);
    }

    public void validateAction() throws AlreadyAffectedCategoryException, UniquePointToClassifyException {
        controleur.classify(this.getAttributesSelected(), distInstances.get(distanceComboBox.getValue()), this.kSpinner.getValue());
    }

    private List<Attribute> getAttributesSelected() {
        List<Attribute> result = new ArrayList<>();
        for (Attribute attr: attributeCheckBoxes.keySet()) {
            if (attributeCheckBoxes.get(attr).isSelected()) result.add(attr);
        }
        return result;
    }
}

