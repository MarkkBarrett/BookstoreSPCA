package com.example.aaaBookstoreCA.pattern.decorator;

public interface DiscountCalculator {
    double calculateDiscount(double total, int totalBooks, int totalOrders);
    String getMessage(); // message for discount quantity
}