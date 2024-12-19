
package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.List;

public class DistanceEuclidienne implements Distance {

    /**
     * Calcule la distance euclidienne entre deux points de données en fonction d'une liste d'attributs.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La distance euclidienne entre les deux points.
     */

    @Override
    public double distance(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        return Math.sqrt(sumAttributes(point, otherPoint, attributes));
    }

    /**
     * Calcule la somme des carrés des différences entre les attributs des deux points.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La somme des carrés des différences entre les attributs.
     */
    private double sumAttributes(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        final int POW = 2;
        double sum = 0;
        for (Attribute attribute : attributes) {
            sum += Math.pow((double)point.getAttributes().get(attribute) - (double)otherPoint.getAttributes().get(attribute), POW);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "DistanceEuclidienne";
    }
}
