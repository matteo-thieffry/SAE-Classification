package fr.univlille.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * La classe DataPoint représente un point de données avec une catégorie associée
 * et un ensemble d'attributs. Chaque DataPoint appartient à une catégorie et possède
 * un ensemble d'attributs avec leurs valeurs respectives.
 */
public class DataPoint {
    private Category category;
    private HashMap<Attribute, Double> attributes;
    private static HashMap<Attribute, ArrayList<String>> cateString = new HashMap<>();
    private static HashMap<Attribute, Double[]> extremums;

    /**
     * Constructeur qui initialise un DataPoint avec une catégorie et un dictionnaire
     * d'attributs.
     *
     * @param categoryStr Le nom de la catégorie associée à ce DataPoint.
     * @param attributes  Le dictionnaire des attributs et de leurs valeurs.
     */
    public DataPoint(String categoryStr, HashMap<Attribute, Object> attributes) {
        HashMap<Attribute, Double> convertedAttributes = new HashMap<>();
        for (Attribute attr : attributes.keySet()) {;
            Double convertedValue = convertToDouble(attr, attributes.get(attr));
            convertedAttributes.put(attr, convertedValue);
        }

        Category category = Category.getCategory(categoryStr);
        if (category == null) category = Category.addCategory(categoryStr);
        this.category = category;
        this.attributes = convertedAttributes;
    }

    /**
     * Constructeur qui initialise un DataPoint avec une catégorie par défaut et un
     * dictionnaire d'attributs.
     *
     * @param attributes Le dictionnaire des attributs et de leurs valeurs.
     */
    public DataPoint(HashMap<Attribute, Object> attributes) {
        this("default", attributes);
    }

    /**
     * Retourne un String de ce DataPoint, avec la catégorie
     * et les attributs.
     *
     * @return Un String représentant le DataPoint.
     */
    @Override
    public String toString() {
        return "Categorie : " + this.category + " | Attributs : " + this.attributes;
    }

    /**
     * Définit la catégorie de ce DataPoint.
     *
     * @param category La nouvelle catégorie du DataPoint.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Retourne la catégorie associée à ce DataPoint.
     *
     * @return La catégorie de ce DataPoint.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Retourne le dictionnaire des attributs de ce DataPoint.
     *
     * @return Le dictionnaire contenant les attributs et leurs valeurs.
     */
    public HashMap<Attribute, Double> getAttributes() {
        return attributes;
    }

    /**
     * Retourne la catégorie de ce DataPoint en String.
     *
     * @return La catégorie du DataPoint en String.
     */
    public String getStrCategory() {
        return this.category.toString();
    }

    /**
     * Retourne un tableau de String représentant les valeurs des
     * attributs spécifiés, suivi de la catégorie.
     *
     * @param attrList La liste des attributs à récupérer pour ce DataPoint.
     * @return Un tableau de String représentant les valeurs des attributs et la
     * catégorie.
     */
    public String[] getValues(List<Attribute> attrList) {
        String[] result = new String[this.attributes.size() + 1];
        int cpt = 0;
        for (Attribute attr : attrList) {
            result[cpt++] = this.attributes.get(attr).toString();
        }
        result[this.attributes.size()] = this.getStrCategory();
        return result;
    }

    public static void displayPoints(DataPoint[] points) {
        for (DataPoint point : points) {
            System.out.println(point);
        }
    }

    /**
     * Converts an Object value to a Double.
     *
     * @param value The value to convert.
     * @return The converted Double value.
     */
    private Double convertToDouble(Attribute attr, Object value) {
        if (value instanceof String) {
            if(cateString.containsKey(attr)){
                if(cateString.get(attr).contains(value)){
                    return (double) cateString.get(attr).indexOf(value);
                }
                else {
                    cateString.get(attr).add(value.toString());
                    return (double) cateString.get(attr).indexOf(value);
                }
            }
            else{
                cateString.put(attr, new ArrayList<String>());
                cateString.get(attr).add(value.toString());
                return (double) cateString.get(attr).indexOf(value);
            }


        } else if (value instanceof Boolean) {
            return (boolean) value ? 1.0 : 0.0;
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            return 0.0;
        }
    }

    public static void initExtremums(List<Attribute> attributes, List<DataPoint> dataPoints) {
        extremums = new HashMap<>();
        for (Attribute attr : attributes) {
            extremums.put(attr, new Double[]{getMin(attr, dataPoints), getMax(attr, dataPoints)});
        }
    }

    public static double getMin(Attribute attr, List<DataPoint> dataPoints) {
        double min = Double.MAX_VALUE;
        for (DataPoint point : dataPoints) {
            if (point.getAttributes().get(attr) < min) min = point.getAttributes().get(attr);
        }
        return min;
    }

    public static double getMax(Attribute attr, List<DataPoint> dataPoints) {
        double max = Double.MIN_VALUE;
        for (DataPoint point : dataPoints) {
            if (point.getAttributes().get(attr) > max) max = point.getAttributes().get(attr);
        }
        return max;
    }

    public static Double[] getExtremums(Attribute attr) {
        return extremums.get(attr);
    }

    public static void displayExtremums() {
        for (Attribute attr : extremums.keySet()) {
            System.out.println(attr + " : " + Arrays.toString(extremums.get(attr)));
        }
    }
}
