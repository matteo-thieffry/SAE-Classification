
package fr.univlille.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe Category représente une catégorie de données et fournit des méthodes statiques
 * pour gérer une liste globale de toutes les catégories disponibles.
 * Chaque catégorie est identifiée par un nom unique.
 */
public class Category {
    private static List<Category> categories = new ArrayList<>();
    private String name;

    /**
     * Constructeur de la classe Category. Il crée une nouvelle catégorie avec le nom spécifié
     * et l'ajoute à la liste statique des catégories.
     *
     * @param name Le nom de la nouvelle catégorie.
     */
    public Category(String name){
        this.name = name;
        Category.categories.add(this);
    }

    public String getName() {
        return name;
    }

    /**
     * Recherche et retourne une catégorie par son nom dans la liste des catégories.
     * Si la catégorie n'est pas trouvée, retourne null.
     *
     * @param e Le nom de la catégorie à rechercher.
     * @return La catégorie correspondant au nom spécifié, ou null si elle n'est pas trouvée.
     */
    public static Category getCategory(String e) {
        int idx = 0;
        String a = "";
        while(idx < categories.size() && !a.equals(e)) {
           a = categories.get(idx).getName();
           if(a.equals(e)){
               return categories.get(idx);
           }
           idx++;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Crée et ajoute une nouvelle catégorie avec le nom spécifié à la liste des catégories.
     *
     * @param c Le nom de la catégorie à ajouter.
     * @return La nouvelle catégorie créée.
     */
    public static Category addCategory (String c){
        Category cate = new Category(c);
        categories.add(cate);
        return cate;
    }

    public static List<Category> getCategories(){
        return categories;
    }
}
