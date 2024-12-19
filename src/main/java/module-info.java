module view {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;

    opens fr.univlille.view to javafx.fxml;
    exports fr.univlille;
    exports fr.univlille.view;
    exports fr.univlille.datamanipulation;
    exports fr.univlille.modele;
    exports fr.univlille.utils;
    exports fr.univlille.algoknn;
    exports fr.univlille.algoknn.distances;
}