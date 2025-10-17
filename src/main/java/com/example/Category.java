package com.example;

import java.util.HashMap;
import java.util.Map;

public class Category{
    private final String name;
    private static final Map<String, Category> cache = new HashMap<>();

    private Category(String name){
        this.name = name;
  }
    public static Category of(String rawName) {
        if (rawName == null)
            throw new IllegalArgumentException("Category name can't be null");
        if (rawName.isBlank())
            throw new IllegalArgumentException("Category name can't be blank");

        String formatted = formatName(rawName);

        return cache.computeIfAbsent(formatted, Category::new);
    }

    private static String formatName(String name){
            name = name.trim().toLowerCase();
            return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Category category)) return false;
        return name.equalsIgnoreCase(category.name);
    }
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
