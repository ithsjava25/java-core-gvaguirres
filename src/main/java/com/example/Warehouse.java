package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> warehouseProduct;
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final Set<UUID> changedProducts = new HashSet<>();

    private Warehouse(String name) {
        this.warehouseProduct = new ArrayList<>();
    }

    public static Warehouse getInstance(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty");
        return instances.computeIfAbsent(name, Warehouse::new);
    }
    public static Warehouse getInstance() {
        return Warehouse.getInstance("DefaultWarehouse");
    }

    public List<Product> getProducts() {
        return List.copyOf(warehouseProduct);
    }
    public void addProduct(Product product) {
        if ( product == null )
            throw new IllegalArgumentException("Product cannot be null.");

        UUID productId = product.uuid();
        boolean idExists = warehouseProduct.stream()
                .anyMatch(p -> p.uuid().equals(productId));
        if (idExists) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        warehouseProduct.add(product);

    }
    public Optional<Product> getProductById(UUID id) {
        return warehouseProduct.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();
    }
    public void updateProductPrice(UUID id, BigDecimal price) {

        Optional <Product> optionalProduct = getProductById(id);
        if ( optionalProduct.isEmpty() || id == null )
            throw new  NoSuchElementException("Product not found with id: " + id);

        optionalProduct.get().setPrice(price);
        changedProducts.add(id);
    }

    public List<Perishable> expiredProducts(){
        return warehouseProduct.stream()
                .filter(product -> product instanceof Perishable)
                .map(product -> (Perishable) product)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }
    public List<Shippable> shippableProducts(){
        return warehouseProduct.stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .collect(Collectors.toList());
    }
    public void remove(UUID id) {
        warehouseProduct.removeIf(product -> product.uuid() != null && product.uuid().equals(id));
    }

    public boolean isEmpty() {
        return warehouseProduct.isEmpty();
    }

    public void clearProducts() {
        warehouseProduct.clear();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return warehouseProduct.stream()
                .collect(Collectors.groupingBy(Product::category));
    }
    public Set<UUID> getChangedProducts() {
        return Set.copyOf(changedProducts);
    }

}
