package edu.ucalgary.ensf409;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Object representation of Lamp table in table Furniture.
 */

 /**
    @author     Rahat   Islam   <a href="mailto:rahat.islam@ucalgary.ca">rahat.islam@ucalgary.ca</a>
    @author     Mohammed  Kabir   <a href="mailto:mohammed.kabir@ucalgary.ca">mohammed.kabir@ucalgary.ca</a>
    @author     Dhyey   Lalseta <a href="mailto:dhyey.lalseta@ucalgary.ca">dhyey.lalseta@ucalgary.ca</a>
    @author     Aninda  Shome   <a href="mailto:aninda.shome@ucalgary.ca">aninda.shome@ucalgary.ca</a>

     @version   1.7
     @since     1.0
 
 */
final public class Lamp extends Furniture {
    static final String TABLENAME = "LAMP";
    static final private String FURNITURETYPE = "Lamp";

    final private boolean BULB;
    final private boolean BASE;
    final private HashMap<String, Boolean> HASHMAP;

    /**
     * This is the constructor for lamp
     * @param id,type,base,bulb,price,manufacturerID
     */
    public Lamp(String id, String type, boolean base, boolean bulb, int price, String manufacturerID) {
        setID(id);
        setType(type);
        this.BASE = base;
        this.BULB = bulb;
        setPrice(price);
        setManufacturerID(manufacturerID);

        HASHMAP = new HashMap<>() {{
            put("base", base);
            put("bulb", bulb);
        }};
    }

    /**
     * This is a no arg constructor that sets bulb, base and hasmap to null 
     */
    public Lamp() {
        BULB = false;
        BASE = false;
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
     * Abstract method documented in the furniture class
     */
    protected Lamp newFromResultSet(ResultSet rs) throws SQLException {
        String ID = rs.getString("ID");
        String type = rs.getString("Type");
        boolean base = rs.getString("Base").equals("Y");
        boolean bulb = rs.getString("Bulb").equals("Y");
        int price = rs.getInt("Price");
        String manufacturerID = rs.getString("ManuID");

        return new Lamp(ID, type, base, bulb, price, manufacturerID);
    }


    @Override
    /**
     * This function will get the furniture type
     * @return furnitureType string
     */
    public String getFurnitureType() {
        return FURNITURETYPE;
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
     * This method will form a string. Defined in abstract class as an abstract method
     * @return a string
     */
    public String toString() {
        return "Lamp{" +
                toStringPrefix() +
                ", hasBase=" + BASE +
                ", hasBulb=" + BULB +
                toStringPostfix();
    }
}
