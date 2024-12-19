
package fr.univlille.validation;

import fr.univlille.algoknn.distances.*;
import fr.univlille.datamanipulation.DataWriter;

import java.io.*;
import java.util.*;

/**
 * Fait le tour de toutes les possibilités de fichier, tous les K et toutes les distances
 */

public class Valideur {
    private String filename;
    private String tempPathDir;
    private String strCategory;
    private int idxCategory;
    private ArrayList<ResultValues> resultValuesList;
    private Set<Distance> distancesInst;
    final static int K_MAX = 15;

    public static void main(String[] args) throws IOException {
        Valideur valideurIris = new Valideur("./res/iris.csv", "variety", 4);
        //Valideur valideurPokemon = new Valideur("./res/pokemon_train.csv", "type1", 9);
    }

    public Valideur(String filename, String strCategory, int idxCategory) throws IOException {
        this.filename = filename;
        this.tempPathDir = this.getOutputDirPath();
        this.strCategory = strCategory;
        this.idxCategory = idxCategory;
        this.initDistancesInst();
        this.initResultValuesList();
        SeparerFichier.splitCsv(filename, tempPathDir);
        this.allTesting(filename);
        this.removeTempFiles(new File(this.getResultsFilepath()));
        this.writeData();
        this.removeTempDir();
    }

    private String getResultsFilepath() {
        String[] filenameParts = filename.split("/");
        return "results/" + filenameParts[filenameParts.length - 1].split("\\.")[0] + "_" + strCategory + "MeilleurK.csv";
    }

    private void writeData() {
        String[] line;

        DataWriter writer = new DataWriter(this.getResultsFilepath());
        for (ResultValues resultValues : resultValuesList) {
            line = new String[]{
                "" + resultValues.getK(),
                resultValues.getDistanceUsed().toString(),
                "" + resultValues.getPourcentage()
            };
            writer.append(line);
        }
    }

    private void initDistancesInst() {
        this.distancesInst = new HashSet<>();
        this.distancesInst.add(new DistanceEuclidienne());
        this.distancesInst.add(new DistanceManhattan());
        this.distancesInst.add(new DistanceEuclidienneNormalisee());
        this.distancesInst.add(new DistanceManhattanNormalisee());
    }

    private void initResultValuesList() {
        this.resultValuesList = new ArrayList<>();
        for (int k = 1; k < K_MAX; k++) {
            for (Distance distance : this.distancesInst) {
                resultValuesList.add(new ResultValues(distance, k));
            }
        }
    }

    private ResultValues getResultValues(int k, Distance distance) {
        for (ResultValues resultValues : this.resultValuesList) {
            if (resultValues.getK() == k && resultValues.getDistanceUsed() == distance) return resultValues;
        }
        return new ResultValues(distance, k);
    }

    private String getOutputDirPath() {
        String[] filenameParts = filename.split("/");
        return filenameParts[filenameParts.length - 2] + "/temp/";
    }

    private boolean removeTempDir() {
        File tempDir = new File(tempPathDir);
        removeTempFiles(tempDir);
        return tempDir.delete();
    }

    private boolean removeTempFiles(File tempDir) {
        if (tempDir.exists() && tempDir.isDirectory()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) removeTempFiles(file);
                    boolean deleted = file.delete();
                    if (!deleted) return false;
                }
            }
        } else {
            return tempDir.delete();
        }
        return true;
    }
    private void allTesting(String filename) {
        List<String> filenames = SeparerFichier.getTempFilenames(tempPathDir);
        for (String newValuesFilename: filenames) {
            ArrayList<String> knownValuesFilenames = new ArrayList<>(filenames);
            knownValuesFilenames.remove(newValuesFilename);
            String dataValuesFileName = assemblerFichier(filename, knownValuesFilenames);
            for (int k = 1; k < K_MAX; k++) {
                int idxDist = 0;
                for (Distance distance : distancesInst) {
                    CalculerResultat result = new CalculerResultat(dataValuesFileName, newValuesFilename, this.strCategory, this.idxCategory, distance, k);
                    this.getResultValues(k, distance).addBooleanValues(result.checkValues());
                    idxDist++;
                }
            }
        }
    }

    private String assemblerFichier(String originFilename, ArrayList<String> filenames) {
        String[] originFilenameParts = originFilename.split("/");
        String outputName = "res/temp/" + originFilenameParts[originFilenameParts.length - 1];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            assembleCSVFiles(writer, filenames);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier de sortie: " + outputName);
        }
        return outputName;
    }

    private void assembleCSVFiles(BufferedWriter writer, ArrayList<String> filenames) throws IOException {
        boolean headerWritten = false;

        for (String filename : filenames) {
            processCSVFile(writer, filename, headerWritten);

            if (!headerWritten) {
                headerWritten = true; // L'en-tête est maintenant écrit
            }
        }
    }

    private void processCSVFile(BufferedWriter writer, String filename, boolean headerWritten) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;

                    if (!headerWritten) {
                        writer.write(line);
                        writer.newLine();
                    }
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + filename);
            e.printStackTrace();
        }
    }
}
