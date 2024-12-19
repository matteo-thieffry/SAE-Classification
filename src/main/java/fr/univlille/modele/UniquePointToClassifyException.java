
package fr.univlille.modele;

public class UniquePointToClassifyException extends Exception {

    public UniquePointToClassifyException() {
        super("Erreur : Un point attends déjà sa classification.");
    }

}
