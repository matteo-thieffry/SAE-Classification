package fr.univlille.datamanipulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataLoaderTest {

    @BeforeEach
    public void setUp() throws IOException {
        // Avant chaque test, assure-toi que le fichier iris.csv existe et contient des données valides.
        String csvContent = """
                sepal.length,sepal.width,petal.length,petal.width,variety
                5.1,3.5,1.4,0.2,Setosa
                4.9,3.0,1.4,0.2,Setosa
                """;
        Files.write(Paths.get("./res/iris.csv"), csvContent.getBytes());
    }

    @Test
    public void testCharger() {
        // Test du chargement des données avec un fichier CSV valide
        List<IrisDonneeBrut> dataList = (List<IrisDonneeBrut>) DataLoader.charger("res/iris.csv");
        assertNotNull(dataList, "La liste de données ne doit pas être nulle");
        assertEquals(2, dataList.size(), "La liste de données doit contenir 2 éléments");

        // Vérification des valeurs du premier élément
        IrisDonneeBrut iris1 = dataList.get(0);
        assertEquals(5.1, iris1.getSepalLength(), 0.01);
        assertEquals(3.5, iris1.getSepalWidth(), 0.01);
        assertEquals(1.4, iris1.getPetalLength(), 0.01);
        assertEquals(0.2, iris1.getPetalWidth(), 0.01);
        assertEquals("Setosa", iris1.getVariety());

        // Vérification des valeurs du second élément
        IrisDonneeBrut iris2 = dataList.get(1);
        assertEquals(4.9, iris2.getSepalLength(), 0.01);
        assertEquals(3.0, iris2.getSepalWidth(), 0.01);
        assertEquals(1.4, iris2.getPetalLength(), 0.01);
        assertEquals(0.2, iris2.getPetalWidth(), 0.01);
        assertEquals("Setosa", iris2.getVariety());
    }
}
