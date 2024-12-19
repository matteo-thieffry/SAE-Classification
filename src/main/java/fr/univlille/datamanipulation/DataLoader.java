
package fr.univlille.datamanipulation;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataLoader {
    public static String determineFileType(String filename) {
        if (filename.contains("iris")) {
            return "iris";
        } else if (filename.contains("pokemon")) {
            return "pokemon";
        }
        return "unknown";
    }

    public static List<? extends FormatDonneebrut> charger(String filename) {
        try {
            String fileType = determineFileType(filename);
            switch (fileType) {
                case "iris":
                    return new CsvToBeanBuilder<IrisDonneeBrut>(Files.newBufferedReader(Paths.get(filename)))
                            .withSeparator(',')
                            .withType(IrisDonneeBrut.class)
                            .build().parse();

                case "pokemon":
                    return new CsvToBeanBuilder<PokemonDonneeBrut>(Files.newBufferedReader(Paths.get(filename)))
                            .withSeparator(',')
                            .withType(PokemonDonneeBrut.class)
                            .build().parse();
            }

        } catch (IOException ioe) {
            System.err.println("Erreur lors de la lecture du fichier de données");
            System.exit(1);
        }
        return null;
    }


}
