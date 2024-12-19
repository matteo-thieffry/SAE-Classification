package fr.univlille.algoknn.distances;

import fr.univlille.modele.Attribute;
import fr.univlille.modele.AttributeType;
import fr.univlille.modele.DataPoint;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceEuclidienneTest {

    @Test
    public void testDistanceZero() {
        Attribute attr1 = new Attribute("speed", AttributeType.NUM);
        Attribute attr2 = new Attribute("attack", AttributeType.NUM);

        HashMap<Attribute, Object> attributes = new HashMap<>();
        attributes.put(attr1, 0.0);
        attributes.put(attr2, 0.0);

        DataPoint point1 = new DataPoint(attributes);
        DataPoint point2 = new DataPoint(attributes);

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(point1, point2, List.of(attr1, attr2));
        assertEquals(0.0, result, "La distance entre deux points identiques devrait être 0");
    }

    @Test
    public void testDistancePositive() {
        Attribute attr1 = new Attribute("speed", AttributeType.NUM);
        Attribute attr2 = new Attribute("attack", AttributeType.NUM);

        HashMap<Attribute, Object> attributes1 = new HashMap<>();
        attributes1.put(attr1, 0.0);
        attributes1.put(attr2, 0.0);

        HashMap<Attribute, Object> attributes2 = new HashMap<>();
        attributes2.put(attr1, 3.0);
        attributes2.put(attr2, 4.0);

        DataPoint point1 = new DataPoint(attributes1);
        DataPoint point2 = new DataPoint(attributes2);

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(point1, point2, List.of(attr1, attr2));
        assertEquals(5.0, result, "La distance entre (0,0) et (3,4) devrait être 5");
    }

    @Test
    public void testDistanceMultipleAttributes() {
        Attribute attr1 = new Attribute("speed", AttributeType.NUM);
        Attribute attr2 = new Attribute("attack", AttributeType.NUM);
        Attribute attr3 = new Attribute("defense", AttributeType.NUM);

        HashMap<Attribute, Object> attributes1 = new HashMap<>();
        attributes1.put(attr1, 1.0);
        attributes1.put(attr2, 2.0);
        attributes1.put(attr3, 3.0);

        HashMap<Attribute, Object> attributes2 = new HashMap<>();
        attributes2.put(attr1, 4.0);
        attributes2.put(attr2, 6.0);
        attributes2.put(attr3, 8.0);

        DataPoint point1 = new DataPoint(attributes1);
        DataPoint point2 = new DataPoint(attributes2);

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(point1, point2, List.of(attr1, attr2, attr3));
        assertEquals(7.071, result, 0.001, "La distance devrait être correcte pour les données 3D");
    }

    @Test
    public void testDistanceWithEmptyAttributes() {
        DataPoint point1 = new DataPoint(new HashMap<>());
        DataPoint point2 = new DataPoint(new HashMap<>());

        DistanceEuclidienne distance = new DistanceEuclidienne();
        double result = distance.distance(point1, point2, List.of());
        assertEquals(0.0, result, "La distance entre deux points sans attributs devrait être 0");
    }
}
