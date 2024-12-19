package fr.univlille.algoknn.distances;

import fr.univlille.algoknn.distances.DistanceManhattanNormalisee;
import fr.univlille.modele.Attribute;
import fr.univlille.modele.AttributeType;
import fr.univlille.modele.DataPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DistanceManhattanNormaliseTest {

    private static List<Attribute> attributes;
    private static DistanceManhattanNormalisee distanceManhattanNormalisee;

    @BeforeAll
    static void setUp() {
        attributes = new ArrayList<>();
        attributes.add(new Attribute("attack", AttributeType.NUM));
        attributes.add(new Attribute("defense", AttributeType.NUM));
        attributes.add(new Attribute("speed", AttributeType.NUM));

        distanceManhattanNormalisee = new DistanceManhattanNormalisee();
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

        List<DataPoint> dataPoints = new ArrayList<>();
        dataPoints.add(point1);
        dataPoints.add(point2);
        DataPoint.initExtremums(attributes, dataPoints);

        double distance = distanceManhattanNormalisee.distance(point1, point2, attributes);


        double normalizedAttack1 = 0.0;
        double normalizedAttack2 = 0.1;
        double normalizedDefense1 = 0.2;
        double normalizedDefense2 = 0.3;
        double normalizedSpeed1 = 0.4;
        double normalizedSpeed2 = 0.5;

        double expectedDistance = Math.abs(normalizedAttack1 - normalizedAttack2)
                + Math.abs(normalizedDefense1 - normalizedDefense2)
                + Math.abs(normalizedSpeed1 - normalizedSpeed2);

        assertEquals(3.0, distance, 0.0001);
    }
}
