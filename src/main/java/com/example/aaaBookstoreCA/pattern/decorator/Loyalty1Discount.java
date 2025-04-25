package com.example.aaaBookstoreCA.pattern.decorator;

public class Loyalty1Discount implements DiscountCalculator {

    private final DiscountCalculator wrapped;

    public Loyalty1Discount(DiscountCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDiscount(double total, int totalBooks, int totalOrders) {
        double base = wrapped.calculateDiscount(total, totalBooks, totalOrders);
        if (totalOrders >= 1) {
            return base * 0.95; // 5% off
        }
        return base;
    }

    @Override
    public String getMessage() {
        return "5% Loyalty Discount (1+ orders)";
    }
}