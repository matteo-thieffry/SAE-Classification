
package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.List;

public class DistanceEuclidienneNormalisee implements Distance {

    /**
     * Calcule la distance euclidienne normalisée entre deux points de données
     * en fonction d'une liste d'attributs.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La distance euclidienne normalisée entre les deux points.
     */

    @Override
    public double distance(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        return Math.sqrt(sumNormalizedAttributes(point, otherPoint, attributes));
    }

    /**
     * Calcule la somme des carrés des différences entre les attributs normalisés des deux points.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attributes    La liste des attributs à considérer pour le calcul.
     * @return              La somme des carrés des différences entre les attributs normalisés.
     */

    private double sumNormalizedAttributes(DataPoint point, DataPoint otherPoint, List<Attribute> attributes) {
        final int POW = 2;
        double sum = 0;
        for (Attribute attribute : attributes) {
            Double[] normalizedPointsValue = getNormalizedPointsValues(point, otherPoint, attribute);
            sum += Math.pow(normalizedPointsValue[0] - normalizedPointsValue[1], POW);
        }
        return sum;
    }


    /**
     * Calcule les valeurs normalisées des attributs pour deux points de données donnés.
     * La normalisation utilise la formule :
     * {@code normalizedValue = (value - min) / (max - min)}.
     *
     * @param point         Le premier point de données.
     * @param otherPoint    Le second point de données.
     * @param attribute     L'attribut à normaliser.
     * @return              Un tableau contenant les valeurs normalisées pour les deux points
     *                      [valeur_normalisée_point1, valeur_normalisée_point2].
     */
    static Double[] getNormalizedPointsValues(DataPoint point, DataPoint otherPoint, Attribute attribute) {
        double pointValue = point.getAttributes().get(attribute);
        double otherPointValue = otherPoint.getAttributes().get(attribute);
        Double[] extremumsAttr = DataPoint.getExtremums(attribute);
        double min = extremumsAttr[0];
        double max = extremumsAttr[1];

        double normalizedPointValue = (pointValue - min) / (max - min);
        double normalizedOtherPointValue = (otherPointValue - min) / (max - min);
        return new Double[]{normalizedPointValue, normalizedOtherPointValue};
    }

    @Override
    public String toString() {
        return "DistanceEuclidienneNoramlisée";
    }
}
