
package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.List;

public interface Distance {
    double distance(DataPoint point, DataPoint otherPoint, List<Attribute> attributes);
}
