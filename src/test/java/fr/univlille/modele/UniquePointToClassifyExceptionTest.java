package fr.univlille.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class UniquePointToClassifyExceptionTest {

    private DataSet dataset;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un DataSet avant chaque test
        dataset = new DataSet();
    }

    @Test
    public void testUniquePointToClassifyException() {
        // Ajoute un premier point pour classification
        HashMap<Attribute, Object> attributes = new HashMap<>();
        attributes.put(new Attribute("Sepal length", AttributeType.NUM), 5.1);
        attributes.put(new Attribute("Sepal width", AttributeType.NUM), 3.5);
        attributes.put(new Attribute("Petal length", AttributeType.NUM), 1.4);
        attributes.put(new Attribute("Petal width", AttributeType.NUM), 0.2);

        // Ajout du premier point, sans exception
        assertDoesNotThrow(() -> dataset.ajouterPoint("default", attributes));

        // Tentative d'ajout d'un deuxième point à classifier, devrait lever l'exception
        assertThrows(UniquePointToClassifyException.class, () -> {
            dataset.ajouterPoint("Versicolor", attributes);
        });
    }
}