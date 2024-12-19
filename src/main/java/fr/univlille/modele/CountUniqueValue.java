package fr.univlille.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CountUniqueValue {
    public static Map<String, Integer> countUniqueValuesPerColumn(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String headerLine = reader.readLine();

        if (headerLine == null) {
            reader.close();
            throw new IOException("Le fichier CSV est vide.");
        }

        String[] headers = headerLine.split(",");
        int columnCount = headers.length;

        List<Set<String>> uniqueValuesSets = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            uniqueValuesSets.add(new HashSet<>());
        }

        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",", -1);
            for (int i = 0; i < columnCount; i++) {
                if (i < values.length) {
                    uniqueValuesSets.get(i).add(values[i].trim());
                }
            }
        }
        reader.close();

        Map<String, Integer> uniqueValuesMap = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
            uniqueValuesMap.put(headers[i], uniqueValuesSets.get(i).size());
        }

        return uniqueValuesMap;
    }
}
