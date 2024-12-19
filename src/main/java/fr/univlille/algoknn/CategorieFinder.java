
package fr.univlille.algoknn;

import fr.univlille.modele.Category;
import fr.univlille.modele.DataPoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CategorieFinder {
    DataPoint[] listPoints;
    Map<Category, Integer> occurencesCategorie;

    public CategorieFinder(DataPoint[] listPoints) {
        this.listPoints = listPoints;
        this.initOccurencesCategorie();
    }

    private void initOccurencesCategorie() {
        occurencesCategorie = new HashMap<>();
        for (DataPoint point : listPoints) {
            occurencesCategorie.put(point.getCategory(), 0);
        }
    }

    public Category find() {
        this.countOccurences();
        return Collections.max(occurencesCategorie.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void countOccurences() {
        for (DataPoint point : listPoints) {
            Category pointCategorie = point.getCategory();
            occurencesCategorie.put(pointCategorie, occurencesCategorie.get(pointCategorie) + 1);
        }
    }
}
