
package fr.univlille;

import fr.univlille.algoknn.distances.*;
import fr.univlille.modele.*;
import fr.univlille.view.Launcher;
import javafx.application.Application;
import javafx.stage.Stage;

import fr.univlille.view.View;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Launcher launcher = new Launcher();
        //Main.launchApplication("./res/pokemon_train.csv", "type1");
    }

    public static void launchApplication(String filepath, String selectedCategory) {
        Controleur controleur = new Controleur(filepath, selectedCategory);
        View vue = new View(controleur);
        controleur.attach(vue);

        /*List<Attribute> attrList = controleur.getDataset().getAppAttributes();

        HashMap<Attribute, Object> attributes;
        attributes = new HashMap<>();
        for (Attribute attribute : attrList) {
            attributes.put(attribute, 4.0);
        }

        controleur.ajouterPoint(attributes);
        vue.classifyAction();
        /*

        try {
            controleur.classify(controleur.getDataset().getAppAttributes(), new DistanceEuclidienneNormalisee(), 6);
        } catch (AlreadyAffectedCategoryException e) {
            throw new RuntimeException(e);
        }

         */

        //vue.close();
    }
}
