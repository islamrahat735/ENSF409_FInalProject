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
 * Object representation of Desk table in table Furniture.
 */
final public class Desk extends Furniture {
    static final String TABLENAME = "DESK";
    static final private String FURNITURETYPE = "Desk";


    private final boolean LEGS;
    private final boolean TOP;
    private final boolean DRAWER;
    final private HashMap<String, Boolean> HASHMAP;

    /**
     * This is a no arg constructor which 
     * sets legs, top, drawer to false. hashMap to null
     */
    public Desk() {
        LEGS = false;
        TOP = false;
        DRAWER = false;
        HASHMAP = null;
    }

    /**
     * This is another constructor which takes in args
     * @param ID,type,legs,top,drawer,price,manufacturerID
     */
    public Desk(String ID,
                String type,
                boolean legs,
                boolean top,
                boolean drawer,
                int price,
                String manufacturerID) {
        setID(ID);
        setType(type);
        this.LEGS = legs;
        this.TOP = top;
        this.DRAWER = drawer;
        setPrice(price);
        setManufacturerID(manufacturerID);

        HASHMAP = new HashMap<>() {{
            put("legs", legs);
            put("top", top);
            put("drawer", drawer);
        }};
    }

    @Override
    /**
     * This method gets the table name
     * @return a string containing the table name
     */
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    /**
     * this method is documented in the furniture class
     */
    protected Furniture newFromResultSet(ResultSet rs) throws SQLException {
        String ID = rs.getString("ID");
        String type = rs.getString("Type");
        boolean legs = rs.getString("Legs").equals("Y");
        boolean arms = rs.getString("Top").equals("Y");
        boolean seat = rs.getString("Drawer").equals("Y");
        int price = rs.getInt("Price");
        String manufacturerID = rs.getString("ManuID");

        return new Desk(ID, type, legs, arms, seat, price, manufacturerID);
    }


    @Override
    /**
     * This will get the furniture type
     * @return furnitureType string
     */
    public String getFurnitureType() {
        return FURNITURETYPE;
    }

    @Override
    /**
     * This will get the hashmap
     * @return hashMap
     */
    public HashMap<String, Boolean> getHashMap() {
        return HASHMAP;
    }

    @Override
    /**
     * This will form a string
     * @return a string containing details about the desk
     */
    public String toString() {
        return "Desk{" +
                toStringPrefix() +
                ", legs=" + LEGS +
                ", top=" + TOP +
                ", drawer=" + DRAWER +
                toStringPostfix();
    }
}
