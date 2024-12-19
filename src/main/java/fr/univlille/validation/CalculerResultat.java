
package fr.univlille.validation;

import fr.univlille.algoknn.distances.Distance;
import fr.univlille.modele.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CalculerResultat {
    private String baseFilename;
    private String toAddFilename;
    private String strCategory;
    private int idxCategory;
    private Distance distance;
    private int k;
    private Controleur controleur;

    public CalculerResultat(String baseFilename, String toAddFilename, String strCategory, int idxCategory, Distance distance, int k) {
        this.baseFilename = baseFilename;
        this.toAddFilename = toAddFilename;
        this.strCategory = strCategory;
        this.idxCategory = idxCategory;
        this.distance = distance;
        this.k = k;
        this.controleur = new Controleur(baseFilename, strCategory);
    }

    private List<String[]> getAllDataToAdd() {
        List<String[]> contenu = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.toAddFilename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] colonnes = line.split(",");
                contenu.add(colonnes);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return contenu;
    }

    private void displayDataToAdd(List<String[]> contenu) {
        for (String[] colonne : contenu) {
            System.out.println(Arrays.toString(colonne));
        }
    }

    public List<Category> getCategories() {
        List<String[]> rawData = getAllDataToAdd();
        List<Attribute> appAttributes = this.controleur.getDataset().getAppAttributes();
        List<Category> categoriesFounded = new ArrayList<>();
        for (String[] colonne : rawData) {
            ajouterPoint(colonne, appAttributes);
            try {
                categoriesFounded.add(this.controleur.classify(appAttributes, this.distance, this.k));
            } catch (AlreadyAffectedCategoryException e) {
                categoriesFounded.add(null);
            }
        }
        return categoriesFounded;
    }

    private void ajouterPoint(String[] attrValues, List<Attribute> appAttributes) {
        HashMap<Attribute, Object> pointAttributes = new HashMap<>();
        HashMap<Attribute, Integer> attrIndexes = this.getAttributesIndex();
        int idxAttribute = 0;
        for (Attribute attribute : attrIndexes.keySet()) {
            pointAttributes.put(attribute, attrValues[attrIndexes.get(attribute)]);
        }
        this.controleur.ajouterPoint(pointAttributes);
    }

    private HashMap<Attribute, Integer> getAttributesIndex() {
        HashMap<Attribute, Integer> indexes = new HashMap<>();
        int idxAttribute = 0;
        for (Attribute attribute : this.controleur.getDataset().getAppAttributes()) {
            if (!attribute.getNom().equals(this.strCategory)) {
                indexes.put(attribute, idxAttribute);
            }
            idxAttribute++;
        }
        return indexes;
    }

    public List<Boolean> checkValues() {
        List<String[]> addedData = this.getAllDataToAdd();
        List<Category> categories = this.getCategories();
        List<Boolean> resultats = new ArrayList<>();
        int idxPoint = 0;
        for (Category category : categories) {
            resultats.add(category.getName().equals(addedData.get(idxPoint++)[idxCategory].split("\"")[1]));
        }
        return resultats;
    }

}
