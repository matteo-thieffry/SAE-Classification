package fr.univlille.view;

import fr.univlille.Main;
import fr.univlille.datamanipulation.IrisDonneeBrut;
import fr.univlille.datamanipulation.PokemonDonneeBrut;
import fr.univlille.modele.Category;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import fr.univlille.modele.AttributeType;

import static fr.univlille.datamanipulation.DataLoader.determineFileType;
import static fr.univlille.modele.CountUniqueValue.countUniqueValuesPerColumn;

public class Launcher extends Stage {

    private String selectedFilePath = "";
    private String selectedColumn = "";

    public Launcher() {
        VBox elements = new VBox();
        elements.setAlignment(Pos.CENTER);
        elements.setSpacing(40);
        Scene scene = new Scene(elements, 1080, 720);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,700&display=swap");

        String font = "-fx-font-family : Inter;";

        Label title = new Label("TurboClass");
        title.setStyle(font + "-fx-font-size: 40;");

        Button launchButton = new Button("Lancer");
        launchButton.setStyle("-fx-background-color: #138824; -fx-text-fill: white; -fx-font-size: 18; -fx-background-radius: 5;" + font);
        launchButton.setDisable(true);


        ComboBox<String> columnComboBox = new ComboBox<>();
        columnComboBox.setDisable(true);
        columnComboBox.setStyle("-fx-font-size: 18; -fx-background-color: #f0f0f0;" + font);


        Button fileButton = new Button("Ouvrir le fichier de données");
        fileButton.setStyle("-fx-background-color: #138824;" + "-fx-text-fill: white;" + font + "-fx-background-radius: 5;" + "-fx-font-size: 18;");
        fileButton.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(this);
            List<String> columnList = new ArrayList<>();
            List<AttributeType> attributeTypeList = new ArrayList<>();
            if (file != null) {
                selectedFilePath = file.getPath();

                String fileType = determineFileType(selectedFilePath);
                Map<String,Integer> countPerValue = new HashMap<>();

                switch (fileType) {
                    case "iris":
                        columnList = IrisDonneeBrut.columnList;
                        try {
                            countPerValue = countUniqueValuesPerColumn(selectedFilePath);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        attributeTypeList = IrisDonneeBrut.attributeTypeList;
                        break;

                    case "pokemon":
                        columnList = PokemonDonneeBrut.columnList;
                        try {
                            countPerValue = countUniqueValuesPerColumn(selectedFilePath);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        attributeTypeList = PokemonDonneeBrut.attributeTypeList;
                        break;
                    default:
                        break;
                }

                List<AttributeType> finalAttributeTypeList = attributeTypeList;
                List<String> finalColumnList = columnList;
                List<String> filteredColumns = columnList.stream()
                        .filter(column -> finalAttributeTypeList.get(finalColumnList.indexOf(column)) == AttributeType.STR || finalAttributeTypeList.get(finalColumnList.indexOf(column)) == AttributeType.BOOL)
                        .collect(Collectors.toList());

                for(int i = 0; i<filteredColumns.size();i++){
                    if(countPerValue.get(filteredColumns.get(i)) != null){
                        if(countPerValue.get(filteredColumns.get(i)) > 30){
                            filteredColumns.remove(i);
                        }
                    }
                }

                columnComboBox.getItems().clear();
                columnComboBox.getItems().addAll(filteredColumns);
                columnComboBox.setDisable(false);
            }
        });

        columnComboBox.setOnAction(e -> {
            selectedColumn = columnComboBox.getValue();
            launchButton.setDisable(false);
        });

        launchButton.setOnAction(e -> {
            if (!selectedFilePath.isEmpty() && !selectedColumn.isEmpty()) {
                Main.launchApplication(selectedFilePath, selectedColumn);
                this.close();
            }
        });


        elements.getChildren().addAll(title, fileButton, columnComboBox, launchButton);
        this.setScene(scene);
        this.setTitle("TurboClass");
        this.show();
    }

}

