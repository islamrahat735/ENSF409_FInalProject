package edu.ucalgary.ensf409;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

 /**
    @author     Rahat   Islam   <a href="mailto:rahat.islam@ucalgary.ca">rahat.islam@ucalgary.ca</a>
    @author     Mohammed  Kabir   <a href="mailto:mohammed.kabir@ucalgary.ca">mohammed.kabir@ucalgary.ca</a>
    @author     Dhyey   Lalseta <a href="mailto:dhyey.lalseta@ucalgary.ca">dhyey.lalseta@ucalgary.ca</a>
    @author     Aninda  Shome   <a href="mailto:aninda.shome@ucalgary.ca">aninda.shome@ucalgary.ca</a>

     @version   1.7
     @since     1.0
 
 */
/**
 * Utility class to memoize price of solution set alongside counter of attributes.
 */
public class SearchSolution {
    final private ArrayList<Furniture> SOLUTIONLIST;
    final private HashMap<String, Integer> ATTRIBUTECOUNT;
    private int solutionPrice;

    SearchSolution() {
        SOLUTIONLIST = new ArrayList<>();
        ATTRIBUTECOUNT = new HashMap<>();
        solutionPrice = 0;
    }

    SearchSolution(SearchSolution searchSolution) {
        SOLUTIONLIST = new ArrayList<>(searchSolution.SOLUTIONLIST);
        solutionPrice = searchSolution.solutionPrice;
        ATTRIBUTECOUNT = new HashMap<>(searchSolution.ATTRIBUTECOUNT);
    }

    /**
     * @param newItem to add to existing SearchSolution.
     */
    void add(Furniture newItem) {
        // update attributes and price
        updateAttributeCounter(newItem.getHashMap());
        solutionPrice += newItem.getPrice();
        // add to internal list
        SOLUTIONLIST.add(newItem);
    }

    /**
     * @param incomingHashMap to update internal attribute counter.
     */
    void updateAttributeCounter(HashMap<String, Boolean> incomingHashMap) {
        if (ATTRIBUTECOUNT.isEmpty()) {
            // update internal counter with 1 if attribute exists, else 0
            incomingHashMap.forEach((attribute, hasAttribute) ->
                    ATTRIBUTECOUNT.put(attribute, hasAttribute ? 1 : 0)
            );
        } else {
            // update internal counter
            incomingHashMap.forEach((attribute, hasAttribute) -> {
                if (hasAttribute) {
                    int count = ATTRIBUTECOUNT.get(attribute);
                    ATTRIBUTECOUNT.put(attribute, ++count);
                }
            });
        }
    }

    /**
     * @param requiredQuantity of furniture.
     * @return boolean on whether or not all attributes exist in SearchSolution.
     */
    boolean doesFulfillQuantity(int requiredQuantity) {
        // return true if not empty and attribute count is all above required quantity.
        return !ATTRIBUTECOUNT.isEmpty() && ATTRIBUTECOUNT
                .values().stream().allMatch(count -> count >= requiredQuantity);
    }

    /**
     * @return copy of internal solution list.
     */
    public ArrayList<Furniture> getSolutionList() {
        return new ArrayList<>(SOLUTIONLIST);
    }

    /**
     * @return price of solution.
     */
    public int getSolutionPrice() {
        return solutionPrice;
    }

    @Override
    public String toString() {
        return "SearchSolution{" +
                "solutionQueue=" + SOLUTIONLIST +
                ", solutionPrice=" + solutionPrice +
                '}';
    }

    /**
     * Approve SearchSolution, will iterate through and delete approved items.
     *
     * @throws SQLException
     */
    public void approve() throws SQLException {
        // iterate and delete.
        for (Furniture approved: SOLUTIONLIST) {
            approved.deleteFromDatabase();
        }
    }

    /**
     * Returns just the item ids of the solution.
     * Function is used when populating the orderedItems list in class Transaction
     * @return A String array of the item IDs in ascending order from SOLUTIONLIST
     */
    public String[] createItemIDlist() {

        String[] iDs = new String[SOLUTIONLIST.size()];
        for(int i = 0; i < SOLUTIONLIST.size(); i++){
            iDs[i] = SOLUTIONLIST.get(i).getID();
        }
        Arrays.sort(iDs);
        return iDs;
    }
}
