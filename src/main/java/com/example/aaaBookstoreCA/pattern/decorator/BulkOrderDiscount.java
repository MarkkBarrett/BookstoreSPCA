package com.example.aaaBookstoreCA.pattern.decorator;

public class BulkOrderDiscount implements DiscountCalculator {

    private final DiscountCalculator wrapped;

    public BulkOrderDiscount(DiscountCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateDiscount(double total, int totalBooks, int totalOrders) {
        double base = wrapped.calculateDiscount(total, totalBooks, totalOrders);
        if (totalBooks >= 10) {
            return base * 0.80; // 20% off
        }
        return base;
    }

    @Override
    public String getMessage() {
        return "20% Bulk order Discount (10+ books)";
    }
}