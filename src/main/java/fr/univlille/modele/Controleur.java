
package fr.univlille.modele;

import fr.univlille.algoknn.CategorieFinder;
import fr.univlille.algoknn.distances.Distance;
import fr.univlille.algoknn.distances.DistanceEuclidienne;
import fr.univlille.algoknn.MethodeKnn;
import fr.univlille.datamanipulation.DataLoader;
import fr.univlille.datamanipulation.FormatDonneebrut;
import fr.univlille.datamanipulation.IrisDonneeBrut;
import fr.univlille.datamanipulation.PokemonDonneeBrut;
import fr.univlille.utils.Observable;

import java.util.*;

import static fr.univlille.datamanipulation.DataLoader.determineFileType;

/**
 * La classe Controleur est responsable du chargement des données, de l'initialisation
 * des attributs et de la gestion des points de données dans un DataSet.
 * Cette classe est observable et notifie les observateurs lorsqu'il y a des changements
 * dans le DataSet, comme l'ajout d'un nouveau point ou la modification des données.
 */
public class Controleur extends Observable {

    /**
     * Le DataSet utilisé pour stocker et gérer les données de l'application.
     */
    private DataSet dataset;
    private String selectedCategory;
    private List<Attribute> appAttributes;
    /**
     * Constructeur de la classe Controleur. Il initialise le DataSet,
     * charge les données à partir de la source de données,
     * et ajoute les catégories disponibles au DataSet.
     */
    public Controleur(String filename, String selectedCategory){
        this.dataset = new DataSet();
        this.selectedCategory = selectedCategory;
        loadData(filename, selectedCategory);
        this.dataset.getAppCategories().addAll(Category.getCategories());
        this.dataset.initialized();
        DataPoint.initExtremums(this.getDataset().getAppAttributes(), this.getDataset().getDataPoints());

        appAttributes = this.getDataset().getAppAttributes();
        int attrToRemove = -1;
        for (int i=0; i<appAttributes.size();i++){
            if(appAttributes.get(i).getNom().equals(this.getSelectedCategory())){
                attrToRemove = i;
            }
        }
        appAttributes.remove(attrToRemove);
    }


    public DataSet getDataset() {
        return dataset;
    }

    /**
     * Initialise les attributs du jeu de données.
     *
     * Les attributs sont ajoutés avec leurs noms et sont de type numérique. Cette méthode
     * configure quatre attributs : longueur et largeur des sépales, ainsi que longueur et
     * largeur des pétales.
     */



    public void initAttributes(String fileType) {
        ArrayList<String> attrStrList = new ArrayList<>();

        switch (fileType) {
            case "iris":
                for(int i=0; i<IrisDonneeBrut.columnList.size(); i++){
                    String courant = IrisDonneeBrut.columnList.get(i);
                    attrStrList.add(courant);
                    this.dataset.addAttributes(courant,IrisDonneeBrut.attributeTypeList.get(i));
                }
                break;

            case "pokemon":
                for(int i=0; i<PokemonDonneeBrut.columnList.size(); i++){
                    String courant = PokemonDonneeBrut.columnList.get(i);
                    attrStrList.add(courant);
                    this.dataset.addAttributes(courant,PokemonDonneeBrut.attributeTypeList.get(i));
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported file type");
        }

    }

    /**
     * Les valeurs sont en String par défaut, peut-être modifiable par la suite avec Object
     * /**
     * Les valeurs sont en String par défaut, peut-être modifiable par la suite avec Object
     * Charge les données du jeu de données à partir d'une source externe.
     * Cette méthode initialise d'abord les attributs, puis charge les données brutes
     * de type {@link IrisDonneeBrut}. Ensuite, elle convertit les données en une table
     * de valeurs et les ajoute au jeu de données.
     */
    public void loadData(String filename, String selectedCategory) {
        String fileType = determineFileType(filename);
        this.initAttributes(fileType);
        Object[][] strValues;
        int categoryIndex = 0;

        switch (fileType) {
            case "iris":
                categoryIndex = IrisDonneeBrut.columnList.indexOf(selectedCategory);
                strValues = IrisDonneeBrut.getValues((DataLoader.charger(filename)));
                break;
            case "pokemon":
                categoryIndex = PokemonDonneeBrut.columnList.indexOf(selectedCategory);
                strValues = PokemonDonneeBrut.getValues(DataLoader.charger(filename));
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type");
        }

        List<Attribute> attrList = this.dataset.getAppAttributes();
        for (Object[] strPointValues: strValues) {
            HashMap<Attribute, Object> attributes = new HashMap<>();
            for (int idxAttribute = 0; idxAttribute < attrList.size(); idxAttribute++) {
                Object value = strPointValues[idxAttribute];

                if (value instanceof Boolean) {
                    value = value.toString();
                }

                attributes.put(attrList.get(idxAttribute), value);
            }


            try {
                this.dataset.ajouterPoint(strPointValues[categoryIndex].toString(), attributes);
            } catch (UniquePointToClassifyException unpt) {
                System.out.println(unpt.getMessage());
            }
        }
    }

    /**
     * Ajoute un nouveau point de données au DataSet avec les attributs spécifiés.
     * Notifie les observateurs après l'ajout du point.
     *
     * @param attributes Un dictionnaire contenant les attributs du point à ajouter.
     */
    public void ajouterPoint(HashMap<Attribute, Object> attributes) {
        try {
            DataPoint point = new DataPoint(attributes);
            this.dataset.setPointToClassify(point);
            this.notifyObservers();
        } catch (UniquePointToClassifyException unpt) {
            System.out.println(unpt.getMessage());
        }
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public List<Attribute> getAppAttributes() {
        return appAttributes;
    }


    public Category classify(List<Attribute> attributes, Distance distance, int k) throws AlreadyAffectedCategoryException {
        Category current = this.getDataset().getPointToClassify().getCategory();
        if(!current.equals(Category.getCategory("default"))) throw new AlreadyAffectedCategoryException();

        MethodeKnn methodeKnn = new MethodeKnn(this.getDataset().getDataPoints());
        DataPoint[] nearestPoints = methodeKnn.getKnn(6, this.getDataset().getPointToClassify(), distance, attributes);

        CategorieFinder catFinder = new CategorieFinder(nearestPoints);
        Category cat = catFinder.find();

        this.getDataset().getPointToClassify().setCategory(cat);
        this.notifyObservers();
        return cat;
    }
}
