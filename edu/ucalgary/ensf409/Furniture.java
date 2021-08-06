package edu.ucalgary.ensf409;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
    @author     Rahat   Islam   <a href="mailto:rahat.islam@ucalgary.ca">rahat.islam@ucalgary.ca</a>
    @author     Mohammed  Kabir   <a href="mailto:mohammed.kabir@ucalgary.ca">mohammed.kabir@ucalgary.ca</a>
    @author     Dhyey   Lalseta <a href="mailto:dhyey.lalseta@ucalgary.ca">dhyey.lalseta@ucalgary.ca</a>
    @author     Aninda  Shome   <a href="mailto:aninda.shome@ucalgary.ca">aninda.shome@ucalgary.ca</a>

     @version   1.7
     @since     1.0
 
 */
/**
 * Base abstract for each table in furniture database.
 */
public abstract class Furniture {
    public static void setDatabaseConnection(DatabaseConnection databaseConnection) {
        Furniture.databaseConnection = databaseConnection;
    }

    static private DatabaseConnection databaseConnection = new DatabaseConnection();
    private String ID;
    private String type;
    private int price;
    private String manufacturerID;

    /**
     * This will get the ID
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * This will set the ID
     * @param ID string
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /** 
     * This will get the type of the furniture
     * @return type of furniture
    */
    public String getType() {
        return type;
    }

    /**
     * This will set the type of the furniture
     * @param type string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This will get the price
     * @return price 
     */
    public int getPrice() {
        return price;
    }

    /**
     * This will set the price
     * @param price which is an integer
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * This will get the manufacturer ID
     * @return manufacturerID string
     */
    public String getManufacturerID() {
        return manufacturerID;
    }

    /**
     * This will set the manufacturer ID
     * @param manufacturerID
     */
    public void setManufacturerID(String manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public abstract String getFurnitureType();

    @Override
    public abstract String toString();

    protected String toStringPrefix() {
        return "ID=" + getID() + ", Type=" + getType();
    }
    
    protected String toStringPostfix() {
        return ", Price=" + getPrice() + ", ManuID=" + getManufacturerID() + "}";
    }

    /**
     * @param rs.
     * @return new object created from ResultSet.
     * @throws SQLException
     */
    protected abstract Furniture newFromResultSet(ResultSet rs) throws SQLException;

    /**
     * @return get table name of selected furniture.
     */
    protected abstract String getTableName();

    public ArrayList<Furniture> getAllOf() throws SQLException {
        ArrayList<Furniture> allFurniture = new ArrayList<>();

        //select all from specific table.
        ResultSet furnitureResults = databaseConnection.selectAllFromTable(getTableName());
        try {
            while (furnitureResults.next()) {
                allFurniture.add(newFromResultSet(furnitureResults));
            }
        } catch (SQLException throwables) {
            furnitureResults.close();
            throw throwables;
        }
        // release resources.
        furnitureResults.close();
        databaseConnection.close();
        return allFurniture;
    }

    /**
     * @param type of furniture.
     * @return all furniture of requested type.
     * @throws SQLException
     */
    public ArrayList<Furniture> getAllOfType(String type) throws SQLException {
        ArrayList<Furniture> allFurnitureOfType = new ArrayList<>();

        // select all from table of type from database.
        ResultSet furnitureResults = databaseConnection.selectAllFromTableOfType(
                getTableName(), type);
        try {
            while (furnitureResults.next()) {
                // add new furniture to list
                allFurnitureOfType.add(newFromResultSet(furnitureResults));
            }
        } catch (SQLException throwables) {
            furnitureResults.close();
            throw throwables;
        }
        // release resources
        furnitureResults.close();
        databaseConnection.close();
        return allFurnitureOfType;
    }

    /**
     * @param furniture class name string.
     * @return new object of requested furniture.
     * @throws ClassNotFoundException
     */
    public static Furniture getFurnitureTypeFromString(String furniture)
            throws ClassNotFoundException {
        if ("Chair".equals(furniture)) {
            return new Chair();
        } else if ("Desk".equals(furniture)) {
            return new Desk();
        } else if ("Filing".equals(furniture)) {
            return new Filing();
        } else if ("Lamp".equals(furniture)) {
            return new Lamp();
        } else {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Delete ID from `getID()` from selected table.
     *
     * @throws SQLException
     */
    public void deleteFromDatabase() throws SQLException {
        // delete from database
        databaseConnection.deleteFromDatabase(getTableName(), getID());
        databaseConnection.close();
    }

    public abstract HashMap<String, Boolean> getHashMap();
}

