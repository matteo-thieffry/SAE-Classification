package fr.univlille.utils;

import java.util.Collection;
import java.util.HashSet;

public abstract class Observable {
    protected Collection<Observer> attached = new HashSet<>();
    protected Collection<Observer> toDetach = new HashSet<>();

    public void attach(Observer obs) {
        attached.add(obs);
    }

    public void detach(Observer obs) {
        this.toDetach.add(obs);
    }

    protected void notifyObservers() {
        this.updateList();
        for (Observer o : attached) {
            o.update(this);
        }
    }

    protected void notifyObservers(Object data) {
        this.updateList();
        for (Observer o : attached) {
            o.update(this, data);
        }
    }

    private void updateList() {
        this.attached.removeAll(toDetach);
        this.toDetach.clear();
    }

}
