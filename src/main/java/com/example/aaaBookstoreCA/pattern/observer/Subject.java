package com.example.aaaBookstoreCA.pattern.observer;

import java.util.ArrayList;
import java.util.List;

// Subject class from lecture notes
public class Subject {

    private List<Observer> observers = new ArrayList<>(); // List of observers
    private int state; // The state to be tracked (e.g., average rating)

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers(); // Notify observers when state changes
    }

    public void attach(Observer observer) {
        observers.add(observer); // Add new observer
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(); // Update all observers
        }
    }
}