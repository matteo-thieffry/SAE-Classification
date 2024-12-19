package fr.univlille.modele;

public enum AttributeType {
    NUM,
    STR,
    BOOL;

    public boolean isNumeric(){
        return this.ordinal()==0;
    }

    public boolean isBoolean(){
        return this.ordinal()==2;
    }
}
