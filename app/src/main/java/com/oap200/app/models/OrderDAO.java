/**
 * Data Access Object for handling operations related to orders in the database.
 * @author Patrik.
 */
public class OrderDAO {

    /**
     * Fetches all orders from the database and returns them as a list of string arrays.
     *
     * @return List of string arrays representing orders.
     */
    public List<String[]> fetchOrders() {
        List<String[]> orders = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM orders");

            while (myRs.next()) {
                String[] order = new String[] {
                        myRs.getString("orderNumber"),
                        myRs.getString("orderDate"),
                        myRs.getString("requiredDate"),
                        myRs.getString("shippedDate"),
                        myRs.getString("status"),
                        myRs.getString("comments"),
                        myRs.getString("customerNumber"),
                };
                orders.add(order);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    /**
     * Searches for specific orders in the database based on the provided order number.
     *
     * @param orderNumber The order number to search for.
     * @return List of string arrays representing search results.
     */
    public List<String[]> searchOrders(String orderNumber) {
        List<String[]> searchResult = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

            PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM orders WHERE orderNumber LIKE ? ");
            statement.setString(1, "%" + orderNumber + "%");
            ResultSet myRs = statement.executeQuery();

            while (myRs.next()) {
                String[] order = new String[] {
                        myRs.getString("orderNumber"),
                        myRs.getString("orderDate"),
                        myRs.getString("requiredDate"),
                        myRs.getString("shippedDate"),
                        myRs.getString("status"),
                        myRs.getString("comments"),
                        myRs.getString("customerNumber"),
                };
                searchResult.add(order);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResult;
    }

    /**
     * Adds a new order to the database.
     *
     * @param orderNumber    The order number.
     * @param orderDate      The order date.
     * @param requiredDate   The required date.
     * @param shippedDate    The shipped date.
     * @param status         The order status.
     * @param comments       The order comments.
     * @param customerNumber The customer number.
     * @return True if the order is added successfully, false otherwise.
     */
    public boolean addOrders(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, int customerNumber) {
        Connection myConnection = null;

        try {
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();

            // Fetch the highest orderNumber from the orders table
            PreparedStatement getMaxOrderNumber = myConnection.prepareStatement("SELECT MAX(orderNumber) AS OrderNumber FROM orders");
            ResultSet resultSet = getMaxOrderNumber.executeQuery();
            int maxOrderNumber = 0;
            if (resultSet.next()) {
                maxOrderNumber = resultSet.getInt("maxOrderNumber");
            }
            // Increment the maxOrderNumber to get the new orderNumber
            int newOrderNumber = maxOrderNumber + 1;

            java.sql.Date orderDateSQL = java.sql.Date.valueOf(orderDate);
            java.sql.Date requiredDateSQL = java.sql.Date.valueOf(requiredDate);
            java.sql.Date shippedDateSQL = java.sql.Date.valueOf(shippedDate);

            PreparedStatement statement = myConnection.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setInt(1, newOrderNumber);
            statement.setDate(2, orderDateSQL);
            statement.setDate(3, requiredDateSQL);
            statement.setDate(4, shippedDateSQL);
            statement.setString(5, status);
            statement.setString(6, comments);
            statement.setInt(7, customerNumber);

            // Validate input data
            if (orderDate == null || orderDate.trim().isEmpty() || requiredDate == null || requiredDate.trim().isEmpty() || shippedDate == null || shippedDate.trim().isEmpty() || status == null || status.trim().isEmpty() || comments == null || comments.trim().isEmpty()) {
                System.out.println("Order details are required.");
                return false;
            }

            if (customerNumber <= 0) {
                System.out.println("CustomerNumber is required.");
                return false;
            }

            myConnection.setAutoCommit(false);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                myConnection.commit();
                System.out.println("Order has been added successfully!");
            } else {
                myConnection.rollback();
                System.out.println("Order has not been added!");
            }

            System.out.println("Rows affected: " + rowsAffected);

            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
     * Validates the date format based on the provided date string and format.
     *
     * @param dateStr    The date string to validate.
     * @param dateFormat The expected date format.
     * @return True if the date is valid, false otherwise.
     */
    public boolean isValidDate(String dateStr, String dateFormat) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
/**
 * Deletes orders and related order details from the database based on the provided order number.
 *
 * This method first deletes associated rows in the 'orderdetails' table and then deletes the order itself.
 *
 * @param orderNumber The order number to be deleted.
 * @return True if deletion is successful, false otherwise.
 */
public boolean deleteOrders(String orderNumber) {
    try {
        // Establish a connection to the database
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Delete related rows in 'orderdetails' table
        String deleteOrderDetailsQuery = "DELETE FROM orderdetails WHERE orderNumber = ?";
        PreparedStatement deleteOrderDetailsStatement = myConnection.prepareStatement(deleteOrderDetailsQuery);
        deleteOrderDetailsStatement.setString(1, orderNumber);
        int rowsAffectedOrderDetails = deleteOrderDetailsStatement.executeUpdate();

        // Delete the order from the 'orders' table
        String deleteOrderQuery = "DELETE FROM orders WHERE orderNumber = ?";
        PreparedStatement deleteOrderStatement = myConnection.prepareStatement(deleteOrderQuery);
        deleteOrderStatement.setString(1, orderNumber);
        int rowsAffectedOrders = deleteOrderStatement.executeUpdate();

        return rowsAffectedOrders > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Updates orders in the database based on the provided parameters.
 *
 * This method allows for selective updates of order attributes, validating and converting data as needed.
 *
 * @param orderNumberToUpdate The order number to update.
 * @param neworderDate        The new order date (format: "yyyy-MM-dd").
 * @param newrequiredDate     The new required date (format: "yyyy-MM-dd").
 * @param newshippedDate      The new shipped date (format: "yyyy-MM-dd").
 * @param newstatus           The new order status.
 * @param newcomments         The new order comments.
 * @return True if the update is successful, false otherwise.
 */
public boolean updateOrders(int orderNumberToUpdate, String neworderDate, String newrequiredDate,
                            String newshippedDate, String newstatus, String newcomments) {
    Connection myConnection = null;

    try {
        // Establish a connection to the database
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        // Lists to store update columns and values
        List<String> updateColumns = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();

        // Validate and convert orderDate
        if (neworderDate != null && !neworderDate.trim().isEmpty()) {
            updateColumns.add("orderDate = ?");
            updateValues.add(java.sql.Date.valueOf(neworderDate));
        }

        // Validate and convert requiredDate
        if (newrequiredDate != null && !newrequiredDate.trim().isEmpty()) {
            updateColumns.add("requiredDate = ?");
            updateValues.add(java.sql.Date.valueOf(newrequiredDate));
        }

        // Validate and convert shippedDate
        if (newshippedDate != null && !newshippedDate.trim().isEmpty()) {
            updateColumns.add("shippedDate = ?");
            updateValues.add(java.sql.Date.valueOf(newshippedDate));
        }

        // Validate and convert status
        if (newstatus != null && !newstatus.trim().isEmpty()) {
            updateColumns.add("status = ?");
            updateValues.add(newstatus);
        }

        // Validate and convert comments
        if (newcomments != null && !newcomments.trim().isEmpty()) {
            updateColumns.add("comments = ?");
            updateValues.add(newcomments);
        }

        // Build the dynamic update SQL statement
        StringBuilder updateSql = new StringBuilder("UPDATE orders SET ");
        updateSql.append(String.join(", ", updateColumns));
        updateSql.append(" WHERE orderNumber = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = myConnection.prepareStatement(updateSql.toString());

        // Set parameter values
        for (int i = 0; i < updateValues.size(); i++) {
            preparedStatement.setObject(i + 1, updateValues.get(i));
        }
        preparedStatement.setInt(updateValues.size() + 1, orderNumberToUpdate);

        // Execute the update
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}
