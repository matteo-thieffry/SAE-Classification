package fr.univlille.modele;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountUniqueValueTest {

    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        testFile = new File("test.csv");

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Name,Type,Attack\n");
            writer.write("Pikachu,Electric,55\n");
            writer.write("Bulbasaur,Grass,49\n");
            writer.write("Charmander,Fire,52\n");
            writer.write("Pikachu,Electric,55\n");
            writer.write("Bulbasaur,Grass,49\n");
        }
    }

    @Test
    public void testCountUniqueValuesPerColumn() throws IOException {
        Map<String, Integer> result = CountUniqueValue.countUniqueValuesPerColumn(testFile.getAbsolutePath());

        assertEquals(3, result.get("Name"), "Le nombre unique de 'Name' devrait être 3");
        assertEquals(3, result.get("Type"), "Le nombre unique de 'Type' devrait être 3");
        assertEquals(3, result.get("Attack"), "Le nombre unique de 'Attack' devrait être 3");
    }

    @AfterEach
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
