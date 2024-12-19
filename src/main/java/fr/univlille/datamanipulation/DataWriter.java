
package fr.univlille.datamanipulation;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    private String filename;

    public DataWriter(String filename) {
        this.filename = filename;
    }

    public void append(String[] newLine) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename, true))) {
            writer.writeNext(newLine);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajour du point au fichier");
        }
    }
}
