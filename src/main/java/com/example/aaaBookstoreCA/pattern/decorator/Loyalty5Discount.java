package com.example.aaaBookstoreCA.pattern.decorator;

public class Loyalty5Discount implements DiscountCalculator {

    private final DiscountCalculator wrapped;

    public Loyalty5Discount(DiscountCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDiscount(double total, int totalBooks, int totalOrders) {
        double base = wrapped.calculateDiscount(total, totalBooks, totalOrders);
        if (totalOrders >= 5) {
            return base * 0.85; // 15% off
        }
        return base;
    }

    @Override
    public String getMessage() {
        return "15% Loyalty Discount (5+ orders)";
    }
}