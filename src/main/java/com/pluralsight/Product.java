package com.pluralsight;

public class Product {
    private String id;
    private String name;
    private double price;

    public Product(String name, String id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return id + " - " + name + " - $" + String.format("%.2f", price);
    }
}
