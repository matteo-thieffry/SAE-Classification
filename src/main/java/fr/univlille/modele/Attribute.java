
package fr.univlille.modele;

import java.util.Objects;

/**
 * La classe Attribute représente un attribut dans un ensemble de données.
 * Un attribut est défini par un nom et un type (numérique, booléen, etc.).
 *
 * Cette classe fournit des méthodes pour accéder aux propriétés de l'attribut et
 * des méthodes utilitaires comme `equals`, `hashCode`, et `toString`.
 */
public class Attribute {
    private String nom;
    private AttributeType type;

    /**
     * Constructeur de la classe Attribute. Il initialise l'attribut avec un nom et un type spécifiés.
     *
     * @param nom Le nom de l'attribut.
     * @param attrType Le type de l'attribut, représenté par une instance d'AttributeType.
     */
    public Attribute(String nom, AttributeType attrType) {
        this.nom = nom;
        this.type = attrType;
    }

    public String getNom() {
        return nom;
    }

    public AttributeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(nom, attribute.nom) && type == attribute.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, type);
    }

    @Override
    public String toString() {
        return this.nom + "(" + this.type + ")";
    }

    public boolean isNumeric(){
        return type.isNumeric();
    }

    public boolean isBoolean(){
        return type.isBoolean();
    }
}
