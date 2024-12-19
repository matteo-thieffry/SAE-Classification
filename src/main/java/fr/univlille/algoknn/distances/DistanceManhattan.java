
package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.List;

public class DistanceManhattan implements Distance {

    /**
     * Calcule la distance de Manhattan entre deux points de données en fonction d'une liste d'attributs.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La distance de Manhattan entre les deux points.
     */
    @Override
    public double distance(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        double sum = 0.0;
        for (Attribute attribute : attributes) {
            sum += Math.abs(point.getAttributes().get(attribute) - otherPoint.getAttributes().get(attribute));
        }
        return sum;
    }

    @Override
    public String toString() {
        return "DistanceManhattan";
    }
}
