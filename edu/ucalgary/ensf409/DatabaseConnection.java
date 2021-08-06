package edu.ucalgary.ensf409;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Wrapper for internal database connection.
 */
public final class DatabaseConnection {
    
    public Connection connection;
    public static boolean testing = false; // determines which database is to be used depending on whether you need to unit test or run the main program
    private String c = "dfsaf";
   

    /**
     * Refresh private Connection.
     * Depening of whether testing is true of false, a different database is used
     * @throws SQLException
     */
    public void getDatabaseConnection() throws SQLException {
        // get connection
        if(!testing){
           connection = DriverManager.getConnection("jdbc:mysql://localhost/INVENTORY",
                "scm", "ensf409");
        }
        else{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/UNITTESTS",
                "scm", "ensf409");
        }
    }

    /**
     * @param tableName to select from.
     * @return ResultSet of all records.
     * @throws SQLException
     */
    public ResultSet selectAllFromTable(String tableName) throws SQLException {
        getDatabaseConnection();
        // select all from table ordered by ascending price
        String query = String.format("SELECT * FROM %1$s ORDER BY Price ASC",
                tableName);
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException throwables) {
            connection.close();
            throw throwables;
        }
    }

    /**
     * @param tableName to select from.
     * @param type of furniture to select.
     * @return ResultSet of all matching records.
     * @throws SQLException
     */
    public ResultSet selectAllFromTableOfType(String tableName, String type) throws SQLException {
        getDatabaseConnection();

        // select all from table of type by ascending price.
        String query = String.format(
                "SELECT * FROM %1$s WHERE Type='%2$s' ORDER BY Price ASC",
                tableName, type);
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException throwables) {
            connection.close();
            throw throwables;
        }
    }

    /**
     * @param tableName to delete from.
     * @param ID to delete.
     * @throws SQLException
     */
    public void deleteFromDatabase(String tableName, String ID) throws SQLException {
        getDatabaseConnection();
        // delete from database where ID equals.
        String query = String.format(
                "DELETE FROM %1$s WHERE ID='%2$s' LIMIT 1",
                tableName, ID);
        try {
            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException throwables) {
            connection.close();
            throw throwables;
        }
    }

    /**
     * Close connection.
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        connection.close();
    }
    /**
     * This function reads through a chosen table in the inventory database
     * and returns a string of all the IDs of the remaining items that have the chosen type
     * Functions is mainly used in unit testing
     * @param tableName table name in database
     * @param type type of the chosen furniture
     * @return String of IDs
     * @throws SQLException if there is a problem accessing and using the database
     */
    public String selectAllFromTableOfType_OrderByID(String tableName, String type) throws SQLException {
        getDatabaseConnection(); // start connnection
        StringBuffer temp = new StringBuffer("");
        String query = String.format(
                "SELECT * FROM %1$s WHERE Type='%2$s'",
                tableName, type);
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                temp.append(rs.getString("ID")+ " ");
            }
            connection.close(); // end connection
            return temp.toString().trim(); // return string of IDs
        } catch (SQLException throwables) {
            connection.close();
            throw throwables;
        }
    }

    /**
     * This method will empty the table of its data
     * Used in unit testing
     * @param tableName
     */
    public void emptyTable(String tableName) throws SQLException{
        try{
            getDatabaseConnection();
            String query = "DELETE FROM "+ tableName;
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            close();
        }catch(SQLException e){
            throw e;
        }
    }

    /**
     * This method will insert the desired values into the table
     * using the queries
     * @param tableName,data
     */
    public void insertValuesInTable (String tableName, String data) throws SQLException {
        tableName = tableName.toLowerCase();
        String query = "";
        if(tableName.equals("chair")){
            query = "INSERT INTO CHAIR (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID)";
        }
        else if(tableName.equals("desk")){
            query = "INSERT INTO DESK (ID, Type, Legs, Top, Drawer, Price, ManuID)";
        }
        else if(tableName.equals("lamp")){
            query = "INSERT INTO LAMP (ID, Type, Base, Bulb, Price, ManuID)";
        }
        else if(tableName.equals("filing")){
            query = "INSERT INTO FILING (ID, Type, Rails, Drawers, Cabinet, Price, ManuID)";
        }
        else{
            throw new SQLException("Invalid tableName");
        }
        
        try{
            getDatabaseConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query+ "VALUES " + data);
            close();
        }
        catch(SQLException throwables){
            System.out.println("Errorrorr");
            throw throwables;
        }
    }
}
