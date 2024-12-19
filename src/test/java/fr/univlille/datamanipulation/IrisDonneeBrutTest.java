package fr.univlille.datamanipulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IrisDonneeBrutTest {

    private IrisDonneeBrut iris;

    @BeforeEach
    public void setUp() {
        iris = new IrisDonneeBrut();
        iris.sepalLength = 5.1;
        iris.sepalWidth = 3.5;
        iris.petalLength = 1.4;
        iris.petalWidth = 0.2;
        iris.variety = "Setosa";
    }

    @Test
    public void testToString() {
        String expectedString = "Sepal length : 5.1, Sepal width : 3.5, Petal length : 1.4, Petal width : 0.2, Variety : Setosa";
        assertEquals(expectedString, iris.toString());
    }

    @Test
    public void testGetValues() {
        List<IrisDonneeBrut> irisList = new ArrayList<>();
        irisList.add(iris);

        Object[][] expectedValues = {
                {5.1, 3.5, 1.4, 0.2, "Setosa"}
        };

        Object[][] actualValues = IrisDonneeBrut.getValues(irisList);
        assertArrayEquals(expectedValues, actualValues);
    }
}
