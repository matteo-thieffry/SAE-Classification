package fr.univlille.modele;

public class AlreadyAffectedCategoryException extends Exception {
    public AlreadyAffectedCategoryException() {
        super("La catégorie de ce point est déjà affectée.") ;
    }
}
