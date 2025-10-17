package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Perishable {
    LocalDate expirationDate();
    default boolean isExpired() {
        return expirationDate().isBefore(LocalDate.now());
    }

}
interface Shippable {
    BigDecimal calculateShippingCost();
    double weight();
}
