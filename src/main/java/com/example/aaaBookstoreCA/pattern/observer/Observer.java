package com.example.aaaBookstoreCA.pattern.observer;

// Observer interface — all observers must implement this
public interface Observer {
    void update(); // Will be called when Book average rating changes
}