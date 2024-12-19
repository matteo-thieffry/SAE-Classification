package fr.univlille.algoknn.distances;

import fr.univlille.algoknn.distances.DistanceEuclidienneNormalisee;
import fr.univlille.modele.Attribute;
import fr.univlille.modele.AttributeType;
import fr.univlille.modele.DataPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceEuclidienneNormaliseTest {

    private static List<Attribute> attributes;
    private static DistanceEuclidienneNormalisee distanceEuclidienneNormalisee;

    @BeforeAll
    static void setUp() {
        attributes = new ArrayList<>();
        Attribute attack = new Attribute("attack", AttributeType.NUM);
        Attribute defense = new Attribute("defense", AttributeType.NUM);
        Attribute speed = new Attribute("speed", AttributeType.NUM);

        attributes.add(attack);
        attributes.add(defense);
        attributes.add(speed);

        distanceEuclidienneNormalisee = new DistanceEuclidienneNormalisee();

        List<DataPoint> dataPoints = new ArrayList<>();
        DataPoint.initExtremums(attributes, dataPoints);
    }

    @Test
    void testDistance() {

        HashMap<Attribute, Object> attributesPoint1 = new HashMap<>();
        attributesPoint1.put(new Attribute("attack", AttributeType.NUM), 50);
        attributesPoint1.put(new Attribute("defense", AttributeType.NUM), 60);
        attributesPoint1.put(new Attribute("speed", AttributeType.NUM), 70);

        HashMap<Attribute, Object> attributesPoint2 = new HashMap<>();
        attributesPoint2.put(new Attribute("attack", AttributeType.NUM), 55);
        attributesPoint2.put(new Attribute("defense", AttributeType.NUM), 65);
        attributesPoint2.put(new Attribute("speed", AttributeType.NUM), 75);

        // Création des DataPoints
        DataPoint point1 = new DataPoint("category1", attributesPoint1);
        DataPoint point2 = new DataPoint("category2", attributesPoint2);

        // Calcul de la distance
        double distance = distanceEuclidienneNormalisee.distance(point1, point2, attributes);

        // Calcul de la distance attendue
        double expectedDistance = Math.sqrt(
                Math.pow((50.0 - 55.0) / (100.0 - 0.0), 2) +
                        Math.pow((60.0 - 65.0) / (100.0 - 0.0), 2) +
                        Math.pow((70.0 - 75.0) / (100.0 - 0.0), 2)
        );

        expectedDistance = Math.round(expectedDistance);

        assertEquals(expectedDistance, distance, 0.0001);
    }
}
