package fr.univlille.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

public class DataPointTest {

    private HashMap<Attribute, Object> attributes;
    private Attribute attr1;
    private Attribute attr2;

    @BeforeEach
    public void setUp() {
        // Initialisation des attributs
        attr1 = new Attribute("Longueur", AttributeType.NUM);
        attr2 = new Attribute("Largeur", AttributeType.NUM);
        attributes = new HashMap<>();
        attributes.put(attr1, 180);
        attributes.put(attr2, 75);
    }

    @Test
    public void testSetCategory() {
        DataPoint dp1 = new DataPoint(attributes);
        Category newCategory1 = new Category("Iris");
        dp1.setCategory(newCategory1);
        assertEquals("Iris", dp1.getStrCategory());
        DataPoint dp2 = new DataPoint("Pokémon", attributes);
        Category newCategory2 = new Category("Alligator");
        dp2.setCategory(newCategory2);
        assertEquals("Alligator", dp2.getStrCategory());
    }

    @Test
    public void testGetValues() {
        DataPoint dp = new DataPoint("Iris", attributes);
        List<Attribute> attrList = List.of(attr1, attr2);
        String[] tab = {"180.0", "75.0", "Iris"};
        assertArrayEquals(tab, dp.getValues(attrList));
    }
}