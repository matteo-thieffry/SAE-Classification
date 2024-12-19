package fr.univlille.algoknn.distances;

import fr.univlille.algoknn.distances.DistanceManhattan;
import fr.univlille.modele.Attribute;
import fr.univlille.modele.AttributeType;
import fr.univlille.modele.DataPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DistanceManhattanTest {

    private static List<Attribute> attributes;
    private static DistanceManhattan distanceManhattan;

    @BeforeAll
    static void setUp() {
        // Initialisation des attributs pour les tests
        attributes = new ArrayList<>();
        attributes.add(new Attribute("attack", AttributeType.NUM));
        attributes.add(new Attribute("defense", AttributeType.NUM));
        attributes.add(new Attribute("speed", AttributeType.NUM));

        // Initialisation de la distance de Manhattan
        distanceManhattan = new DistanceManhattan();
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


        DataPoint point1 = new DataPoint("category1", attributesPoint1);
        DataPoint point2 = new DataPoint("category2", attributesPoint2);


        double distance = distanceManhattan.distance(point1, point2, attributes);


        double expectedDistance = Math.abs(50 - 55) + Math.abs(60 - 65) + Math.abs(70 - 75);


        assertEquals(expectedDistance, distance, 0.0001);
    }
}
