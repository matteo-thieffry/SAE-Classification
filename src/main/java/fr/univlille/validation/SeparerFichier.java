
package fr.univlille.validation;

import java.io.*;
import java.util.*;

public class SeparerFichier {

    /**
     * Divise un fichier CSV en 5 fichiers de sortie.
     *
     * @param inputFilePath  Chemin du fichier CSV d'entrée
     * @param outputDirPath  Répertoire de sortie pour les fichiers générés
     * @throws IOException   En cas d'erreur lors de la lecture/écriture
     */
    public static void splitCsv(String inputFilePath, String outputDirPath) throws IOException {
        validateInputFile(inputFilePath);
        createOutputDirectory(outputDirPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String header = readHeader(reader);
            List<BufferedWriter> writers = createOutputFiles(outputDirPath, header);

            distributeLines(reader, writers);

            closeWriters(writers);
        }
    }

    /**
     * Valide l'existence du fichier d'entrée.
     */
    private static void validateInputFile(String inputFilePath) throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Le fichier CSV d'entrée est introuvable : " + inputFilePath);
        }
    }

    /**
     * Crée le répertoire de sortie s'il n'existe pas.
     */
    private static void createOutputDirectory(String outputDirPath) throws IOException {
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Impossible de créer le répertoire de sortie : " + outputDirPath);
            }
        }
    }

    /**
     * Lit l'en-tête du fichier CSV.
     */
    private static String readHeader(BufferedReader reader) throws IOException {
        String header = reader.readLine();
        if (header == null) {
            throw new IOException("Le fichier CSV est vide.");
        }
        return header;
    }

    /**
     * Crée les fichiers de sortie et y écrit l'en-tête.
     */
    private static List<BufferedWriter> createOutputFiles(String outputDirPath, String header) throws IOException {
        List<BufferedWriter> writers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            File outputFile = new File(outputDirPath, "part" + (i + 1) + ".csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(header);
            writer.newLine();
            writers.add(writer);
        }
        return writers;
    }

    public static List<String> getTempFilenames(String outputDirPath) {
        File outputDir = new File(outputDirPath);
        ArrayList<String> filenames = new ArrayList<>();
        if (outputDir.exists() && outputDir.isDirectory()) {
            File[] files = outputDir.listFiles();
            if (files != null) {
                for (File file : files) filenames.add(outputDirPath + '/' + file.getName());
            }
        }
        return filenames;
    }

    /**
     * Répartit les lignes du fichier CSV entre les fichiers de sortie.
     */
    private static void distributeLines(BufferedReader reader, List<BufferedWriter> writers) throws IOException {
        String line;
        int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            int fileIndex = lineCount % 5;
            writers.get(fileIndex).write(line);
            writers.get(fileIndex).newLine();
            lineCount++;
        }
    }

    /**
     * Ferme tous les BufferedWriter.
     */
    private static void closeWriters(List<BufferedWriter> writers) throws IOException {
        for (BufferedWriter writer : writers) {
            writer.close();
        }
    }
}
