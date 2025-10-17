package com.example;

import java.math.BigDecimal;
import java.util.UUID;


public class ElectronicsProduct extends Product implements Shippable{
    int warrantyMonths;
    BigDecimal weight;

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);
        if ( warrantyMonths < 0 )
            throw new  IllegalArgumentException("Warranty months cannot be negative.");
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }
    @Override
    public String productDetails() {
        return "Electronics: " + name + ", Warranty: " + warrantyMonths + " months";
    }
    @Override
    public BigDecimal calculateShippingCost(){

        double cost = 79.0;
        double limitWeight = 5.0;

        if (weight.compareTo(BigDecimal.valueOf(limitWeight)) > 0)
            cost = cost + 49.0;
        return BigDecimal.valueOf(cost);
    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}
