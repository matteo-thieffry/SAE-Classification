package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.List;

public class DistanceManhattanNormalisee implements Distance {

    /**
     * Calcule la distance de Manhattan normalisée entre deux points de données
     * en fonction d'une liste d'attributs.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La distance de Manhattan normalisée entre les deux points.
     */

    @Override
    public double distance(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        double sum = 0.0;
        for (Attribute attribute : attributes) {
            Double[] normalizedPointsValue = DistanceEuclidienneNormalisee.getNormalizedPointsValues(point, otherPoint, attribute);
            sum += Math.abs(normalizedPointsValue[0] - normalizedPointsValue[1]);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "DistanceManhattanNormalisée";
    }
}
