package fr.univlille.modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttributeTypeTest {

    @Test
    public void testAttributeType(){
        AttributeType num = AttributeType.NUM;
        AttributeType str = AttributeType.STR;
        AttributeType bool = AttributeType.BOOL;

        Assertions.assertTrue(num.isNumeric());
        Assertions.assertFalse(str.isNumeric());
        Assertions.assertFalse(bool.isNumeric());

        Assertions.assertFalse(num.isBoolean());
        Assertions.assertFalse(str.isBoolean());
        Assertions.assertTrue(bool.isBoolean());

    }
}
