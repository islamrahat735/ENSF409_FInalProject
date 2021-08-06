package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Object representation of Chair record in table Furniture.
 */
final public class Chair extends Furniture {
    static final String TABLENAME = "CHAIR";
    static final String FURNITURETYPE = "Chair";

    final private boolean LEGS;
    final private boolean ARMS;
    final private boolean SEAT;
    final private boolean CUSHION;
    final private HashMap<String, Boolean> HASHMAP;

    /**
     * A constructor that takes in args
     */
    public Chair(String ID,
                 String type,
                 boolean legs,
                 boolean arms,
                 boolean seat,
                 boolean cushion,
                 int price,
                 String manufacturerID) {
        setID(ID);
        setType(type);
        this.LEGS = legs;
        this.ARMS = arms;
        this.SEAT = seat;
        this.CUSHION = cushion;
        setPrice(price);
        setManufacturerID(manufacturerID);

        HASHMAP = new HashMap<>() {{
            put("legs", legs);
            put("arms", arms);
            put("seat", seat);
            put("cushion", cushion);
        }};
    }

    /**
     * A no args constructor for chair
     */
    public Chair() {
        LEGS = false;
        ARMS = false;
        SEAT = false;
        CUSHION = false;
        HASHMAP = null;
    }

    @Override
    /**
     * This method will get the table name
     * @return tableName string
     */
    public String getTableName() {
        return TABLENAME;
    }


    @Override
    /**
     * This method is documented in the furniture class 
     */
    public Chair newFromResultSet(ResultSet rs) throws SQLException {
        String ID = rs.getString("ID");
        String type = rs.getString("Type");
        boolean legs = rs.getString("Legs").equals("Y");
        boolean arms = rs.getString("Arms").equals("Y");
        boolean seat = rs.getString("Seat").equals("Y");
        boolean cushion = rs.getString("Cushion").equals("Y");
        int price = rs.getInt("Price");
        String manufacturerID = rs.getString("ManuID");

        return new Chair(ID, type, legs, arms, seat, cushion, price, manufacturerID);
    }


    @Override
    /**
     * This will get the hashmap
     * @return hashmap
     */
    public HashMap<String, Boolean> getHashMap() {
        return HASHMAP;
    }

    @Override
    /**
     * This will get the furniture type
     * @return furnitureType
     */
    public String getFurnitureType() {
        return FURNITURETYPE;
    }

    @Override
    /**
     * This will form a string
     * @return a string containing details about the chair
     */
    public String toString() {
        return "Chair{" +
                toStringPrefix() +
                ", legs=" + LEGS +
                ", arms=" + ARMS +
                ", seat=" + SEAT +
                ", cushion=" + CUSHION +
                toStringPostfix();
    }
}
