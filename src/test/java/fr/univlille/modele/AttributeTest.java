package fr.univlille.modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttributeTest {

    @Test
    void isNumeric() {
        Attribute a = new Attribute("test",AttributeType.NUM);
        Assertions.assertTrue(a.isNumeric());
        Attribute b = new Attribute("test",AttributeType.BOOL);
        Assertions.assertFalse(b.isNumeric());
    }

    @Test
    void isBoolean() {
        Attribute a = new Attribute("test",AttributeType.NUM);
        Assertions.assertFalse(a.isBoolean());
        Attribute b = new Attribute("test",AttributeType.BOOL);
        Assertions.assertTrue(b.isBoolean());
    }
}