package fr.univlille.modele;

import fr.univlille.algoknn.distances.Distance;
import fr.univlille.algoknn.distances.DistanceEuclidienne;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ControleurTest {

    private Controleur controleur;

    @BeforeEach
    public void setUp() {
        controleur = new Controleur("res/iris.csv", "variety");
    }

    @Test
    public void testLoadData() {
        controleur.loadData("res/iris.csv", "variety");
        DataSet dataset = controleur.getDataset();
        assertNotNull(dataset);
        assertFalse(dataset.getDataPoints().isEmpty());
    }

    @Test
    public void testInitAttributes() {
        controleur.initAttributes("iris");
        List<Attribute> attributes = controleur.getDataset().getAppAttributes();
        assertNotNull(attributes);
        assertFalse(attributes.isEmpty());
        boolean found = false;
        int i = 0;
        while (i < attributes.size() && !found) {
            if (attributes.get(i).getNom().equals("sepal.length")) {
                found = true;
            }
            i++;
        }

        assertTrue(found);
    }

    @Test
    public void testAjouterPoint() {
        Attribute attr1 = new Attribute("sepal.length", AttributeType.NUM);
        Attribute attr2 = new Attribute("sepal.width", AttributeType.NUM);
        HashMap<Attribute, Object> attributes = new HashMap<>();
        attributes.put(attr1, 5.1);
        attributes.put(attr2, 3.5);

        controleur.ajouterPoint(attributes);

        DataSet dataset = controleur.getDataset();
        assertNotNull(dataset.getPointToClassify());
    }

    @Test
    public void testClassify() throws Exception {
        Controleur controleur = new Controleur("res/iris.csv", "variety");
        DataSet dataset = controleur.getDataset();

        HashMap<Attribute, Object> pointAttributes = new HashMap<>();
        pointAttributes.put(new Attribute("sepal.length", AttributeType.NUM), 5.1);
        pointAttributes.put(new Attribute("sepal.width", AttributeType.NUM), 3.5);
        pointAttributes.put(new Attribute("petal.length", AttributeType.NUM), 1.4);
        pointAttributes.put(new Attribute("petal.width", AttributeType.NUM), 0.2);

        controleur.ajouterPoint(pointAttributes);
        DataPoint pointToClassify = dataset.getPointToClassify();

        assertEquals(Category.getCategory("default"), pointToClassify.getCategory());

        List<Attribute> attributes = new ArrayList<>(pointAttributes.keySet());

        Distance distance = new DistanceEuclidienne();
        Category predictedCategory = controleur.classify(attributes, distance, 3);

        assertNotEquals(Category.getCategory("default"), pointToClassify.getCategory());
        assertEquals(predictedCategory, pointToClassify.getCategory());
    }

}
