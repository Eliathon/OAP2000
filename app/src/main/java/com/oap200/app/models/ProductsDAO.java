/**
 * The ProductsDAO class is responsible for interacting with the database to perform
 * operations related to products, such as fetching, searching, deleting, and updating.
 * @author Sebastian
 */
package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.math.BigDecimal;
import java.sql.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProductsDAO class provides methods to interact with the database for product-related operations.
 */
public class ProductsDAO {
    private static String generatedProductCode;

    /**
     * Fetches all products from the database.
     *
     * @return A list of arrays containing product information.
     */
    public List<String[]> fetchProducts() {
        System.out.println("View button clicked!");
        List<String[]> products = new ArrayList<>();

        try (Connection myConnection = new DbConnect().getConnection();
             Statement myStmt = myConnection.createStatement();
             ResultSet myRs = myStmt.executeQuery("SELECT * FROM products")) {

            // Process the retrieved data and populate the 'products' list
            while (myRs.next()) {
                String[] product = new String[]{
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    /**
     * Searches for products by name in the database.
     *
     * @param productName The name of the product to search for.
     * @return A list of arrays containing search results.
     */
    public List<String[]> searchProducts(String productName) {
        List<String[]> searchResults = new ArrayList<>();

        try (Connection myConnection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = myConnection
                     .prepareStatement("SELECT * FROM products WHERE productName LIKE ?")) {

            preparedStatement.setString(1, "%" + productName + "%");
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
            while (myRs.next()) {
                String[] product = new String[]{
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                searchResults.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Searches for products by code in the database.
     *
     * @param productCode The code of the product to search for.
     * @return A list of arrays containing search results.
     */
    public List<String[]> searchProductsByCode(String productCode) {
        List<String[]> searchResults = new ArrayList<>();

        try (Connection myConnection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = myConnection
                     .prepareStatement("SELECT * FROM products WHERE productCode LIKE ?")) {

            preparedStatement.setString(1, productCode + "%");
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
            while (myRs.next()) {
                String[] product = new String[]{
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                searchResults.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }

    /**
     * Deletes a product from the database.
     *
     * @param productCode The code of the product to delete.
     * @return True if deletion was successful; otherwise, false.
     */
    public boolean deleteProduct(String productCode) {
        try (Connection myConnection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = myConnection
                     .prepareStatement("DELETE FROM products WHERE productCode = ?")) {

            preparedStatement.setString(1, productCode);

            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if deletion was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves distinct product lines from the database.
     *
     * @return A list of distinct product lines.
     */
    public static List<String> getProductLines() {
        List<String> productLines = new ArrayList<>();

        try (Connection myConnection = new DbConnect().getConnection();
             Statement myStmt = myConnection.createStatement();
             ResultSet myRs = myStmt.executeQuery("SELECT DISTINCT productLine FROM productlines")) {

            // Process the retrieved data and populate the 'productLines' list
            while (myRs.next()) {
                String productLine = myRs.getString("productLine");
                productLines.add(productLine);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return productLines;
    }

    /**
     * Generates a random product code based on the specified length.
     *
     * @param codeLength The length of the generated product code.
     * @return The generated product code.
     */
    private String generateProductCode(int codeLength) {
        String allowedCharacters = "0123456789";
        StringBuilder productCodeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < codeLength; i++) {
            char randomChar = allowedCharacters.charAt(random.nextInt(allowedCharacters.length()));
            productCodeBuilder.append(randomChar);
        }
        generatedProductCode = productCodeBuilder.toString();

        return generatedProductCode;
    }

    /**
     * Gets the last generated product code.
     *
     * @return The generated product code.
     */
    public String getGeneratedProductCode() {
        return generatedProductCode;
    }

    /**
     * Checks if a product code already exists in the database.
     *
     * @param productCode The product code to check.
     * @return True if the product code exists; otherwise, false.
     */
    private boolean doesProductCodeExist(String productCode) {
        try (Connection myConnection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = myConnection.prepareStatement(
                     "SELECT COUNT(*) FROM products WHERE productCode = ?")) {

            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new product to the database.
     *
     * @param productName        The name of the new product.
     * @param productLine        The product line of the new product.
     * @param productScale       The product scale of the new product.
     * @param productVendor      The product vendor of the new product.
     * @param productDescription The product description of the new product.
     * @param quantityInStock    The quantity in stock of the new product.
     * @param buyPriceText       The buy price of the new product.
     * @param MSRPText           The MSRP of the new product.
     * @return True if the addition was successful; otherwise, false.
     */
    public boolean addProduct(String productName, String productLine, String productScale,
                              String productVendor, String productDescription, int quantityInStockText,
                              BigDecimal buyPriceText, BigDecimal MSRPText) {
        Connection myConnection = null;

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();

            String productCode = generateProductCode(8);

            while (doesProductCodeExist(productCode)) {
                productCode = generateProductCode(8);
            }

            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, productCode);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, productLine);
            preparedStatement.setString(4, productScale);
            preparedStatement.setString(5, productVendor);
            preparedStatement.setString(6, productDescription);
            preparedStatement.setInt(7, quantityInStockText);
            preparedStatement.setBigDecimal(8, buyPriceText);
            preparedStatement.setBigDecimal(9, MSRPText);

            myConnection.setAutoCommit(false);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                myConnection.commit();
                System.out.println("Product added successfully");
            } else {
                myConnection.rollback();
                System.out.println("Failed to add product");
            }

            System.out.println("Rows affected: " + rowsAffected);

            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            ex.printStackTrace();
            return false;

        } finally {
            try {
                if (myConnection != null) {
                    myConnection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates a product in the database.
     *
     * @param productCode       The code of the product to update.
     * @param newQuantityInStock The new quantity in stock.
     * @param newBuyPrice        The new buy price.
     * @param newMSRP            The new MSRP.
     * @return True if the update was successful; otherwise, false.
     */
    public boolean updateProduct(String productCode, Integer newQuantityInStock, BigDecimal newBuyPrice,
                                 BigDecimal newMSRP) {
        try (Connection myConnection = new DbConnect().getConnection();
             PreparedStatement preparedStatement = myConnection.prepareStatement(
                     "UPDATE products SET quantityInStock = IFNULL(?, quantityInStock), " +
                             "buyPrice = IFNULL(?, buyPrice), MSRP = IFNULL(?, MSRP) WHERE productCode = ?")) {

            if (newQuantityInStock != null) {
                preparedStatement.setInt(1, newQuantityInStock);
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }

            if (newBuyPrice != null) {
                preparedStatement.setBigDecimal(2, newBuyPrice);
            } else {
                preparedStatement.setNull(2, Types.DECIMAL);
            }

            if (newMSRP != null) {
                preparedStatement.setBigDecimal(3, newMSRP);
            } else {
                preparedStatement.setNull(3, Types.DECIMAL);
            }

            preparedStatement.setString(4, productCode);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a list of product names with low stock.
     *
     * @return A list of product names with low stock.
     */
    public List<String> getLowStockProducts() {
        List<String> lowStockItems = new ArrayList<>();
        String sql = "SELECT productName FROM products WHERE quantityInStock < 200";

        try (Connection conn = new DbConnect().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lowStockItems.add(rs.getString("productName"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lowStockItems;
    }
}
