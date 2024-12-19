
package fr.univlille.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

/**
 * Classe DataSet qui représente un ensemble de données comprenant des attributs, des catégories et des points de données.
 *
 * La classe permet de gérer les attributs et les catégories applicables, d'ajouter des points de données et de suivre
 * un point de données spécifique à classer. Elle offre des méthodes pour manipuler et afficher ces éléments.
 */
public class DataSet {
    private List<Attribute> appAttributes;
    private List<Category> appCategories;
    private List<DataPoint> points;
    private DataPoint pointToClassify;
    private List<DataPoint> addedPoints;
    private boolean initialisation;

    /**
     * Constructeur de la classe DataSet.
     *
     * Initialise les listes d'attributs, de catégories, et de points de données.
     * Ajoute également une catégorie par défaut ("default").
     */
    public DataSet(){
        this.appAttributes = new ArrayList<>();
        this.appCategories = new ArrayList<>();
        this.appCategories.add(new Category("default"));
        this.points = new ArrayList<>();
        this.pointToClassify = null;
        this.addedPoints = new ArrayList<>();
        this.initialisation = true;
    }

    /**
     * Retourne le point de données actuel à classer.
     *
     * @return Le point de données à classer.
     */
    public DataPoint getPointToClassify() {
        return pointToClassify;
    }

    /**
     * Définit le point de données à classer.
     *
     * Si le point peut être ajouté au jeu de données sans violer les restrictions, il est affecté
     * comme le point à classer.
     *
     * @param pointToClassify Le point de données à classer.
     * @throws UniquePointToClassifyException si un autre point avec la catégorie "default" est déjà présent.
     */
    public void setPointToClassify(DataPoint pointToClassify) throws UniquePointToClassifyException {
        if (this.ajouterPoint(pointToClassify)) this.pointToClassify = pointToClassify;
    }

    /**
     * Ajoute un point de données au jeu de données.
     *
     * @param point Le point de données à ajouter.
     * @return true si le point a été ajouté avec succès, false sinon.
     * @throws UniquePointToClassifyException si un autre point avec la catégorie "default" est déjà présent.
     */
    public boolean ajouterPoint(DataPoint point) throws UniquePointToClassifyException {
        if (this.getNbPointsCategory("default") > 0) throw new UniquePointToClassifyException();
        if (!initialisation) {
            points.add(point);
            addedPoints.add(point);
            return true;
        } else {
            return points.add(point);
        }
    }

    /**
     * Crée et ajoute un point de données au jeu de données à partir d'une catégorie et d'un dictionnaire d'attributs.
     *
     * @param categoryStr La chaîne de caractères représentant la catégorie.
     * @param attributes  Le dictionnaire des attributs du point de données.
     * @return true si le point a été ajouté avec succès, false sinon.
     * @throws UniquePointToClassifyException si un autre point avec la catégorie "default" est déjà présent.
     */
    public boolean ajouterPoint(String categoryStr, HashMap<Attribute, Object> attributes) throws UniquePointToClassifyException {
        DataPoint tmp = new DataPoint(categoryStr, attributes);
        return this.ajouterPoint(tmp);
    }

    /**
     * Retourne la liste des attributs applicables du jeu de données.
     *
     * @return La liste des attributs.
     */
    public List<Attribute> getAppAttributes() {
        return appAttributes;
    }

    /**
     * Retourne la liste des catégories applicables du jeu de données.
     *
     * @return La liste des catégories.
     */
    public List<Category> getAppCategories() {
        return appCategories;
    }

    /**
     * Retourne la liste des points de données présents dans le jeu de données.
     *
     * @return La liste des points de données.
     */
    public List<DataPoint> getPoints() {
        return points;
    }

    /**
     * Alias pour {@link #getPoints()}.
     *
     * @return La liste des points de données.
     */
    public List<DataPoint> getDataPoints() {
        return points;
    }

    /**
     * Ajoute un attribut au jeu de données.
     *
     * @param attrStr Le nom de l'attribut.
     * @param type    Le type de l'attribut.
     * @return true si l'attribut a été ajouté avec succès, false s'il était déjà présent.
     */
    public boolean addAttributes(String attrStr, AttributeType type) {
        Attribute attr = new Attribute(attrStr, type);
        if (this.appAttributes.contains(attr)) return false;
        return this.appAttributes.add(attr);
    }

    /**
     * Affiche tous les points de données du jeu de données.
     */
    public void displayPoints() {
        for (DataPoint tmp: this.points) {
            out.println(tmp);
        }
    }

    /**
     * Retourne le nombre de points de données appartenant à une catégorie donnée.
     *
     * @param catStr La chaîne de caractères représentant la catégorie.
     * @return Le nombre de points de données dans la catégorie spécifiée.
     */
    public int getNbPointsCategory(String catStr) {
        int cpt = 0;
        for (DataPoint tmp: points) {
            if (tmp.getStrCategory().equals(catStr)) cpt++;
        }
        return cpt;
    }

    public void initialized() {
        this.initialisation = false;
    }

    public List<DataPoint> getAddedPoints() {
        return addedPoints;
    }
}
