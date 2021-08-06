package edu.ucalgary.ensf409;



import java.sql.SQLException;
import java.util.ArrayList;
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
 * Object to hold utility for searching functionality.
 */
public class FurnitureSearch {
    final int REQUIREDQUANTITY;
    final private ArrayList<Furniture> ALLITEMS;
    int cheapestSolutionPrice = Integer.MAX_VALUE;
    private SearchSolution cheapestSolution;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ArrayList<Furniture> items = Furniture.getFurnitureTypeFromString("Chair").getAllOfType("Mesh");
        SearchSolution solution = new FurnitureSearch(items, 1).search();
        System.out.println(Arrays.toString(solution.getSolutionList().toArray()));
    }

    public FurnitureSearch(ArrayList<Furniture> furnitureItems, int requiredQuantity) {
        ALLITEMS = furnitureItems;
        this.REQUIREDQUANTITY = requiredQuantity;
    }

    /**
     * Begin DFS to determine cheapest solution set.
     *
     * @return cheapest solution if found, else, null.
     */
    public SearchSolution search() {
        DFS(new SearchSolution(), 0);
        return cheapestSolution;
    }

    /**
     * Depth-first search to determine cheapest solution.
     *
     * @param currentState of SearchSolution
     * @param index to start search from
     */
    private void DFS(SearchSolution currentState, int index) {
        // if current price > cheapest price
        if (currentState.getSolutionPrice() > cheapestSolutionPrice) {
            return;
        }
        // if current state fulfills quantity
        if (currentState.doesFulfillQuantity(REQUIREDQUANTITY)) {
            cheapestSolution = new SearchSolution(currentState);
            cheapestSolutionPrice = currentState.getSolutionPrice();
        } else {
            // recursion
            for (int i = index; i < ALLITEMS.size(); i++) {
                SearchSolution nextState = new SearchSolution(currentState);
                nextState.add(ALLITEMS.get(i));
                DFS(nextState, i + 1);
            }
        }
    }
}

