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
 * Object representation of Filing table in table Furniture.
 */
final public class Filing extends Furniture {
    static final String TABLENAME = "FILING";
    static final private String FURNITURETYPE = "Filing";

    final private boolean RAILS;
    final private boolean DRAWERS;
    final private boolean CABINET;
    final private HashMap<String, Boolean> HASHMAP;

    /**
     * No arg constructor for filing
     */
    public Filing() {
        RAILS = false;
        DRAWERS = false;
        CABINET = false;
        HASHMAP = null;
    }

    /**
     * A constructor for filing which takes arguments
     * @param id,type,rails,drawers,cabinet,price,manufacturerID
     */
    public Filing(String id, String type, boolean rails, boolean drawers, boolean cabinet, int price, String manufacturerID) {
        setID(id);
        setType(type);
        this.RAILS = rails;
        this.DRAWERS = drawers;
        this.CABINET = cabinet;
        setPrice(price);
        setManufacturerID(manufacturerID);

        HASHMAP = new HashMap<>() {{
            put("rails", rails);
            put("drawers", drawers);
            put("cabinet", cabinet);
        }};
    }

    @Override
    /**
     * this method gets the table name
     * @return tableName string
     */
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    /**
     * This method is documented in the furniture class 
     */
    protected Filing newFromResultSet(ResultSet rs) throws SQLException {
        String ID = rs.getString("ID");
        String type = rs.getString("Type");
        boolean rails = rs.getString("Rails").equals("Y");
        boolean drawers = rs.getString("Drawers").equals("Y");
        boolean cabinet = rs.getString("Cabinet").equals("Y");
        int price = rs.getInt("Price");
        String manufacturerID = rs.getString("ManuID");

        return new Filing(ID, type, rails, drawers, cabinet, price, manufacturerID);
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
     * This will get the hashmap
     * @return hashMap
     */
    public HashMap<String, Boolean> getHashMap() {
        return HASHMAP;
    }

    @Override
    /**
     * This will make a string and return it
     * @return a string containing details about the filing
     */
    public String toString() {
        return "Filing{" +
                toStringPrefix() +
                ", rails=" + RAILS +
                ", drawers=" + DRAWERS +
                ", cabinet=" + CABINET +
                toStringPostfix();
    }
}
