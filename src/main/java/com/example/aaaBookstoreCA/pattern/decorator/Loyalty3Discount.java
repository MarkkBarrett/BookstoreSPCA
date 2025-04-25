package com.example.aaaBookstoreCA.pattern.decorator;

public class Loyalty3Discount implements DiscountCalculator {

    private final DiscountCalculator wrapped;

    public Loyalty3Discount(DiscountCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDiscount(double total, int totalBooks, int totalOrders) {
        double base = wrapped.calculateDiscount(total, totalBooks, totalOrders);
        if (totalOrders >= 3) {
            return base * 0.90; // 10% off
        }
        return base;
    }

    @Override
    public String getMessage() {
        return "10% Loyalty Discount (3+ orders)";
    }
}