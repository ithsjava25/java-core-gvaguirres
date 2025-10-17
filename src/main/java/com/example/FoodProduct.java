package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class FoodProduct extends Product implements Shippable, Perishable {
    protected final LocalDate expirationDate;
    private final BigDecimal weight;

    public FoodProduct(UUID id, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight ) {
        super(id, name, category, price);
        if ( price.compareTo(BigDecimal.ZERO) <= 0 )
            throw new IllegalArgumentException("Price cannot be negative.");
        if ( weight.compareTo(BigDecimal.ZERO) <= 0 )
            throw new IllegalArgumentException("Weight cannot be negative.");
        this.expirationDate = expirationDate;
        this.weight = weight;
    }
    @Override
    public String productDetails() {
        return "Food: " + name + ", Expires: " + expirationDate;
    }
    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal cost;
        cost = weight.multiply(BigDecimal.valueOf(50.0));
        return cost;
    }

    @Override
    public LocalDate expirationDate() {
        return null;
    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }

    @Override
    public boolean isExpired(){
        return expirationDate.isBefore(LocalDate.now());
    }

}
