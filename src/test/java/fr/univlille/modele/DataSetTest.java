package fr.univlille.modele;

import org.junit.jupiter.api.Test;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class DataSetTest {

    @Test
    void TestSetPointToClassify() throws UniquePointToClassifyException {
        DataSet ds = new DataSet();
        HashMap<Attribute, Object> dictionary = new HashMap<>();
        dictionary.put(new Attribute("A", AttributeType.NUM), 1);
        DataPoint dp = new DataPoint("Iris", dictionary);
        ds.setPointToClassify(dp);
        assertEquals(ds.getPointToClassify(), dp);
    }

    @Test
    void TestAjouterPoint() throws UniquePointToClassifyException {
        DataSet ds = new DataSet();
        HashMap<Attribute, Object> dictionary = new HashMap<>();
        dictionary.put(new Attribute("A", AttributeType.NUM), 1);
        DataPoint dp1 = new DataPoint("Iris", dictionary);
        DataPoint dp2 = new DataPoint("Iris", dictionary);
        assertTrue(ds.ajouterPoint(dp1));
        assertTrue(ds.ajouterPoint(dp1));
        assertTrue(ds.ajouterPoint(dp2));
    }

    @Test
    void TestAddAttributes() {
        DataSet ds = new DataSet();
        ds.addAttributes("Longueur", AttributeType.NUM);
        assertTrue(ds.addAttributes("Hauteur", AttributeType.NUM));
        assertFalse(ds.addAttributes("Longueur", AttributeType.NUM));
    }

    @Test
    void TestGetNbPointsCategory() throws UniquePointToClassifyException {
        DataSet ds = new DataSet();
        HashMap<Attribute, Object> dictionary = new HashMap<>();
        dictionary.put(new Attribute("Longueur", AttributeType.NUM), 1);
        dictionary.put(new Attribute("Hauteur", AttributeType.NUM), 1);
        dictionary.put(new Attribute("Largeur", AttributeType.NUM), 1);

        ds.ajouterPoint(new DataPoint("Table", dictionary));
        ds.ajouterPoint(new DataPoint("Crocodile", dictionary));
        ds.ajouterPoint(new DataPoint("Crocodile", dictionary));
        ds.ajouterPoint(new DataPoint("Bateau", dictionary));
        ds.ajouterPoint(new DataPoint("Bateau", dictionary));
        ds.ajouterPoint(new DataPoint("Bateau", dictionary));

        assertEquals(1, ds.getNbPointsCategory("Table"));
        assertNotEquals(0, ds.getNbPointsCategory("Table"));
        assertEquals(2, ds.getNbPointsCategory("Crocodile"));
        assertEquals(3, ds.getNbPointsCategory("Bateau"));
    }
}