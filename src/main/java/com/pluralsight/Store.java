
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\n------------------------------");
            System.out.println("  Welcome to the Online Store!");
            System.out.println("-------------------------------");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.println("---------------------------------");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice! Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] parts = line.split("\|");

                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                Product product = new Product(id,name,price);
                inventory.add(product);

                line = bufferedReader.readLine();

            }
            bufferedReader.close();
        } catch (IOException error) {
            System.out.println("Error reading inventory file: " + error.getMessage());
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        System.out.println("\n---- All Products ----");
        System.out.println();

        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.println();
        System.out.println("Enter product ID to add to cart (or X to go back): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("X")) {
            return;
        }

        Product foundProduct = findProductById(input, inventory);

        if (foundProduct != null) {
            cart.add(foundProduct);
            System.out.println(foundProduct.getName() + " has been added to your cart.");
        } else {
            System.out.println("Product not found. Please check the ID and try again.");
        }

    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            System.out.println();
            System.out.println("Press Enter to go back the home screen...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n----- Your Cart -----");
        System.out.println();

        double totalAmount = 0.0;

        for (Product product : cart) {
            System.out.println(product);
            totalAmount = totalAmount + product.getPrice();
        }

        System.out.println();
        System.out.println("Total: $" + String.format("%.2f" , totalAmount));
        System.out.println();
        System.out.println("Checked out (C) or Go back (X): ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("C")) {
            checkOut(cart, totalAmount, scanner);
        }

    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        System.out.println();
        System.out.println("---- Checkout ----");
        System.out.println("Total amount due: $" + String.format("%.2f", totalAmount));
        System.out.println();
        System.out.println("Enter payment amount: $");

        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid payment amount. Please enter a number.");
            scanner.nextLine();
            return;

        }

        double payment = scanner.nextDouble();
        scanner.nextLine();

        if (payment < totalAmount) {
            System.out.println("Payment is not enough. You still owe $" +
                    String.format("%.2f", totalAmount - payment));
            System.out.println("Please try checkout again. ");
            return;
        }

        double change = payment - totalAmount;

        System.out.println();
        System.out.println("--------------------------");
        System.out.println("       SALES RECEIPT      ");
        System.out.println("--------------------------");
        System.out.println();

        for (Product Product : cart) {
            System.out.println(product.getName() + " $" + String.format("%.2f", product.getprice()));
        }

        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println("Total:   $" + String.format("%.2f", totalAmount));
        System.out.println("Paid:    $" + String.format("%.2f", payment));
        System.out.println("Change:  $" + String.format("%.2f", change));
        System.out.println("===================================");
        System.out.println("    Thank you for you purchase!    ");
        System.out.println("===================================");


        saveReceiptToFile(cart, totalAmount, payment, change);

        cart.clear();

        System.out.println();
        System.out.print("Press Enter to return to the home screen...");
        scanner.nextLine();

    }

    public static void saveReceiptToFile(ArrayList<Product> cart,
                                         double totalAmount,
                                         double payment,
                                         double change) {
        try {
            java.io.File receiptFolder = new java.io.File("Receipts");
            if (!receiptFolder.exists()) {
                receiptFolder.mkdir();
            }
        }

    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}

