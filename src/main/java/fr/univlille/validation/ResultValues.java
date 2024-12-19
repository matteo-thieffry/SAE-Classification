
package fr.univlille.validation;

import fr.univlille.algoknn.distances.Distance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultValues {
    private Distance distanceUsed;
    private int k;
    private ArrayList<Boolean> classificationResults;

    public ResultValues(Distance distanceUsed, int k, ArrayList<Boolean> classificationResults) {
        this.distanceUsed = distanceUsed;
        this.k = k;
        this.classificationResults = classificationResults;
    }

    public ResultValues(Distance distanceUsed, int k) {
        this(distanceUsed, k, new ArrayList<>());
    }

    public Distance getDistanceUsed() {
        return distanceUsed;
    }

    public int getK() {
        return k;
    }

    public ArrayList<Boolean> getClassificationResults() {
        return classificationResults;
    }

    public void addBooleanValues(List<Boolean> values) {
        classificationResults.addAll(values);
    }

    public String toString() {
        return this.distanceUsed.toString() + " | " + this.k + " | " + Arrays.toString(this.classificationResults.toArray());
    }

    public double getPourcentage() {
        int cptTrue = 0;
        for (Boolean value : classificationResults) {
            if (value) cptTrue++;
        }
        return (double) cptTrue / (double) classificationResults.size() * 100;
    }

}
