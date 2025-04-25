package com.example.aaaBookstoreCA.pattern.decorator;

// No discount by default
public class BaseDiscount implements DiscountCalculator {

    @Override
    public double calculateDiscount(double total, int totalBooks, int totalOrders) {
        return total;
    }

    @Override
    public String getMessage() {
        return "No discount applied.";
    }
}
