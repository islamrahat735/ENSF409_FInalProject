package edu.ucalgary.ensf409;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
/**
    @author     Rahat   Islam   <a href="mailto:rahat.islam@ucalgary.ca">rahat.islam@ucalgary.ca</a>
    @author     Mohammed  Kabir   <a href="mailto:mohammed.kabir@ucalgary.ca">mohammed.kabir@ucalgary.ca</a>
    @author     Dhyey   Lalseta <a href="mailto:dhyey.lalseta@ucalgary.ca">dhyey.lalseta@ucalgary.ca</a>
    @author     Aninda  Shome   <a href="mailto:aninda.shome@ucalgary.ca">aninda.shome@ucalgary.ca</a>

     @version   1.7
     @since     1.0
 
 */
 

/**
 * Class modelling transaction between client and backend searching service.
 */
public class Transaction {
    private String furnitureRequested;
    private String furnitureTypeRequested;
    private int requestedQuantity;
    private boolean canBeProcessed = false;
    private SearchSolution approvedFurniture;
    private String[] orderedItems = null;
    private int totalPrice;
    private final String ERRORMESSAGE = "Your order could not be fulfilled based on current inventory. Suggested manufacturers are: ";

    /**
     * 
     * @param furnitureRequested     This argument takes in the furniture to be
     *                               requested (Chair, Desk Filing or Lamp)
     * @param furnitureTypeRequested This argument takes in the type of furniture
     * @param requestedQuantity      This argument takes in the quantity of
     *                               furniture to be made
     */
    // Constructor for class Transaction
    public Transaction(String furnitureRequested, String furnitureTypeRequested, int requestedQuantity) {
        this.furnitureRequested = furnitureRequested;
        this.furnitureTypeRequested = furnitureTypeRequested;
        this.requestedQuantity = requestedQuantity;
    }
    // Default constructor
    public Transaction(){ }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
    {
        DatabaseConnection.testing = false;
        Transaction demo = new Transaction();
        demo.askForOrder();
        demo.checkIfValidEntry();  
        demo.process();
        String[] output = demo.printOrder();
        System.out.println(output[0]);
    }

    /**
     * Process transaction.
     * Acesses database and attempts to biuld the cheapest combination of the desired item
     * if the attempt is successful, canBeProcessed will be true, and orderedItems, totalPrice will
     * be changed according to the found solution(the ordered items will be in ascending order).
     * Further, the items that were chosen will be taken off the database
     * If the attempt to biuld the item fails, canBeProcessed will be set to false
     */
    public void process() {
        ArrayList<Furniture> items;
        try {
            items = Furniture
                    .getFurnitureTypeFromString(furnitureRequested)
                    .getAllOfType(furnitureTypeRequested);// retrieves all the furniture items from the database
        } catch (SQLException e) {
          
            return;
        } catch (ClassNotFoundException e) {
        
            return;
        }

        SearchSolution solution = new FurnitureSearch(items, requestedQuantity).search();//searches for the cheapest combination and returns it to solution if found
        if (solution != null) {
            canBeProcessed = true;
            try {
                solution.approve();// found items are taken off the database
                approvedFurniture = solution;
                totalPrice = approvedFurniture.getSolutionPrice(); // price of the solution
                orderedItems = approvedFurniture.createItemIDlist();// items that make the solution
            } catch (SQLException e) {
               
            }

        } else {
       
        }
    }

    /**
     * @return internal list of solution.
     */
    public List<Furniture> getApprovedFurniture() {
        return approvedFurniture.getSolutionList();
    }

    /**
    *
    * @return a boolean on whether the order can be processed or not
    */
    public boolean getCanBeProcessed() {
        return canBeProcessed;
    }
    /**
     * set canBeProcessed (used for unit testing)
     * @param truth
     */
    public void setCanBeProcessed(boolean truth) {
        this.canBeProcessed = truth;
    }

    /**
    *
    * @return an integer of the total price of the components needed to make the
    * furniture
    */
    public int getTotalPrice() {
        return totalPrice;
    }
    /**
     * set total price (used for unit testing)
     * @param price 
     */
    public void setTotalPrice(int price) {
        this.totalPrice = price;
    }

    /**
    *
    * @return a String array of the items in an ordered fashion
    */
    public String[] getOrderedItem(){
        return orderedItems;
    }
    /**
     * set orderedItems array (used for unit testing)
     * @param items ordered Items for the solution
     */
    public void setOrderedItems(String[] items){
        this.orderedItems = items;
    }

       /**
     * Function askForOrder prompts the user for input from command line. They will
     * be asked to enter input 3 times: first for a furniture piece, second of the
     * furnity type and third for the quantity of the furniture that they want to
     * make. If user enters an invalid input, the program will show an error message
     * and prompt the user for a valid input. This will keep happening until a valid
     * input is provided.
     * 
     * User input is stored in the three variables respectively: furnitureRequested,
     * furnitureTypeRequested and requestedQuantity. These fields will then be used
     * by other method(s) and classes as needed
     */
    public void askForOrder()
    {   
        // Dialog boxes before the program starts
        JOptionPane.showMessageDialog(null, "<--ENSF 409 Final Project-->\n \nBy: Rahat Islam, Mohammed Kabir, Dhyey Lalseta and Aninda Shome.\n\n Please press Ok to continue");
        JOptionPane.showMessageDialog(null, "Welcome to the SCM program. \n \n This application has been designed to calculate the cheapest combination of available inventory items to fill your furniture order. \n \n Please follow the instructions in the terminal for your desired order. \n \n If your order cannot be fulfilled, a list of suggested manufacturers will be provided for you. \n \n Please press the Ok button below to run the program.");
        // Prompt user for input
        System.out.println("Please enter the furniture piece:");
        System.out.println("Here are the options: Chair, Lamp, Desk, Filing.");
        // Take in user input
        Scanner piece = new Scanner(System.in);
        String pieceArg = piece.nextLine();
             // Loop to see if the user input is valid, if it is, then update the respective
            // field, if it is not,
            // then continue to loop and prompt the user for a valid input
        while(true)
        {
            if(!pieceArg.toLowerCase().equals("chair") && !pieceArg.toLowerCase().equals("lamp") && !pieceArg.toLowerCase().equals("filing") && !pieceArg.toLowerCase().equals("desk"))
            {
                System.out.println("Please enter a valid furniture piece.");
                Scanner tempPiece = new Scanner(System.in);
                pieceArg = tempPiece.nextLine();
            }
            else 
            {
                if(pieceArg.toLowerCase().equals("chair"))
                {
                    this.furnitureRequested = "Chair";
                }
                if(pieceArg.toLowerCase().equals("lamp"))
                {
                    this.furnitureRequested = "Lamp";
                }
                if(pieceArg.toLowerCase().equals("filing"))
                {
                    this.furnitureRequested= "Filing";
                }
                if(pieceArg.toLowerCase().equals("desk"))
                {
                    this.furnitureRequested = "Desk";
                }
                break;
            }
            
        }

        if(furnitureRequested.toLowerCase().equals("chair"))
        {
            System.out.println("Please enter the type of furniture:");
            System.out.println("Here are the options: Task, Mesh, Kneeling, Executive, Ergonomic.");
            Scanner type = new Scanner(System.in);
            String typeArg = type.nextLine();
            while(true)
            {
                if(!typeArg.toLowerCase().equals("task") && !typeArg.toLowerCase().equals("mesh") && !typeArg.toLowerCase().equals("kneeling") && !typeArg.toLowerCase().equals("executive") && !typeArg.toLowerCase().equals("ergonomic"))
                {
                    System.out.println("Please enter a valid type.");
                    Scanner tempType = new Scanner(System.in);
                    typeArg = tempType.nextLine();
                }
                else
                {
                    if(typeArg.toLowerCase().equals("task"))
                    {
                        this.furnitureTypeRequested = "Task";
                    }
                    if(typeArg.toLowerCase().equals("mesh"))
                    {
                        this.furnitureTypeRequested = "Mesh";
                    }
                    if(typeArg.toLowerCase().equals("kneeling"))
                    {
                        this.furnitureTypeRequested = "Kneeling";
                    }
                    if(typeArg.toLowerCase().equals("executive"))
                    {
                        this.furnitureTypeRequested = "Executive";
                    }
                    if(typeArg.toLowerCase().equals("ergonomic"))
                    {
                        this.furnitureTypeRequested = "Ergonomic";
                    }
                    break;
                }
            }
        }
        if(furnitureRequested.toLowerCase().equals("lamp"))
        {
            System.out.println("Please enter the type of furniture:");
            System.out.println("Here are the options: Desk, Swing Arm, Study");
            Scanner type = new Scanner(System.in);
            String typeArg = type.nextLine();
            while(true)
            {
                if(!typeArg.toLowerCase().equals("desk") && !typeArg.toLowerCase().equals("swing arm") && !typeArg.toLowerCase().equals("study"))
                {
                    System.out.println("Please enter a valid type.");
                    Scanner tempType = new Scanner(System.in);
                    typeArg = tempType.nextLine();
                }
                else
                {
                    if(typeArg.toLowerCase().equals("desk"))
                    {
                        this.furnitureTypeRequested = "Desk";
                    }
                    if(typeArg.toLowerCase().equals("swing arm"))  //COME BACK TO THIS
                    {
                        this.furnitureTypeRequested = "Swing Arm";
                    }
                    if(typeArg.toLowerCase().equals("study"))
                    {
                        this.furnitureTypeRequested = "Study";
                    }
                    break;
                }
            }
        }
        if(furnitureRequested.toLowerCase().equals("desk"))
        {
            System.out.println("Please enter the type of furniture:");
            System.out.println("Here are the options: Traditional, Adjustable, Standing");
            Scanner type = new Scanner(System.in);
            String typeArg = type.nextLine();
            while(true)
            {
                if(!typeArg.toLowerCase().equals("traditional") && !typeArg.toLowerCase().equals("adjustable") && !typeArg.toLowerCase().equals("standing"))
                {
                    System.out.println("Please enter a valid type.");
                    Scanner tempType = new Scanner(System.in);
                    typeArg = tempType.nextLine();
                }
                else
                {
                    if(typeArg.toLowerCase().equals("traditional"))
                    {
                        this.furnitureTypeRequested = "Traditional";
                    }
                    if(typeArg.toLowerCase().equals("adjustable"))
                    {
                        this.furnitureTypeRequested = "Adjustable";
                    }
                    if(typeArg.toLowerCase().equals("standing"))
                    {
                        this.furnitureTypeRequested = "Standing";
                    }
                    break;
                }
            }
        }
        if(furnitureRequested.toLowerCase().equals("filing"))
        {
            System.out.println("Please enter the type of furniture:");
            System.out.println("Here are the options: Small, Medium, Large");
            Scanner type = new Scanner(System.in);
            String typeArg = type.nextLine();
            while(true)
            {
                if(!typeArg.toLowerCase().equals("small") && !typeArg.toLowerCase().equals("medium") && !typeArg.toLowerCase().equals("large"))
                {
                    System.out.println("Please enter a valid type.");
                    Scanner tempType = new Scanner(System.in);
                    typeArg = tempType.nextLine();
                }
                else
                {
                    if(typeArg.toLowerCase().equals("small"))
                    {
                        this.furnitureTypeRequested = "Small";
                    }
                    if(typeArg.toLowerCase().equals("medium"))
                    {
                        this.furnitureTypeRequested = "Medium";
                    }
                    if(typeArg.toLowerCase().equals("large"))
                    {
                        this.furnitureTypeRequested = "Large";
                    }
                    break;
                }
            }
        }

        Scanner sc = new Scanner(System.in);
        int number;

        do {
            System.out.println("Please enter the quantity you would like to purchase(In number form):");

            while (!sc.hasNextInt()) {

                System.out.println("Enter a valid quantity: ");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
            if (number <= 0) {
                System.out.println("Enter a valid quantity below");
            }
        } while (number < 1);
        this.requestedQuantity = number;
    }

    
    /**
     * 
     * @throws IOException Error message for IOException
     * 
     *                     printOrder method will print the order form as required
     *                     of the project if the canBeProcessed variable is true. If
     *                     however a furniture cannot be processed and the
     *                     canbeProccessed method is false, then the method will
     *                     print out the suggested manufacturers for that specific
     *                     furniture; writes to an output file for both cases.
     */
    public String[] printOrder() throws IOException
    {   
        String failedChairOrder = ERRORMESSAGE + "Office Furnishings, Chairs R Us, Furniture Goods, Fine Office Supplies.";
        String failedDeskOrder = ERRORMESSAGE + "Academic Desks, Office Furnishings, Furniture Goods, Fine Office Supplies.";
        String failedLampOrder = ERRORMESSAGE + "Office Furnishings, Furniture Goods, Fine Office Supplies.";
        String failedFilingOrder = ERRORMESSAGE + "Office Furnishings, Furniture Goods, Fine Office Supplies.";

        StringBuffer terminalString = new StringBuffer("");
        StringBuffer fileString = new StringBuffer("");

        if(canBeProcessed == false)
        {
            if(furnitureRequested.toLowerCase().equals("chair"))
            {
                terminalString.append(failedChairOrder);
            }
            if(furnitureRequested.toLowerCase().equals("lamp"))
            {
                terminalString.append(failedLampOrder);
            }
            if(furnitureRequested.toLowerCase().equals("desk"))
            {
                terminalString.append(failedDeskOrder);
            }
            if(furnitureRequested.toLowerCase().equals("filing"))
            {
                terminalString.append(failedFilingOrder);
            }
            fileString = new StringBuffer(terminalString.toString());
            terminalString = new StringBuffer("\n"+ fileString.toString());
        }

        else if(canBeProcessed == true)
        {   
            terminalString.append("\n\n");
            terminalString.append("The ID of each furniture piece is: \n");
            for(int i=0; i<orderedItems.length; i++)
            {
                terminalString.append(orderedItems[i] + "\n");
            }
            terminalString.append("The total price is: " + totalPrice +"\n");
            fileString.append("Furniture Order Form" + "\n");
            fileString.append("\n");
            fileString.append("Faculty Name: " + "\n");
            fileString.append("Contact: " + "\n");
            fileString.append("Date: " + "\n");
            fileString.append("\n");
            fileString.append("Original Request: " + furnitureTypeRequested + " " + furnitureRequested + ", " + requestedQuantity + "\n");
            fileString.append("\n");
            fileString.append("Items Ordered" + "\n");
            for(int i=0; i < orderedItems.length; i++)
            {
                fileString.append("ID: " + orderedItems[i] + "\n");
            }
            fileString.append("\n");
            fileString.append("Total Cost: " + "$"+ totalPrice);
        }
        BufferedWriter outputOrder = new BufferedWriter(new FileWriter("OrderForm.txt"));
        outputOrder.write(fileString.toString());
        outputOrder.close();

        String[] retvals = {terminalString.toString(), fileString.toString()};
        return retvals;
    }

    /**
     * @throws IllegalArgumentException Error message for IOException
     * This method checks if the values stored in the furniture requested, furniture type,
     * and quantity are valid inputs and if they are not then it throws an IllegalArgumentException
     * A valid quantity should be greater than 0. As for the type and furniture piece, the requested ones must match the ones present
     * in the database.
     */
    public void checkIfValidEntry() throws IllegalArgumentException {
        //To check if the furnitureRequested is one of the four possibilities
        if(!furnitureRequested.equals("Chair") && !furnitureRequested.equals("Lamp") && !furnitureRequested.equals("Desk") && !furnitureRequested.equals("Filing")) 
        {
            throw new IllegalArgumentException("This is not a valid furniture piece.");
        }
        
        if(furnitureRequested.equals("Chair")) //If the furnitureRequested is chair, then it checks to see if the furnitureType is one of the valid types for chair
        {
            if(!furnitureTypeRequested.equals("Mesh") && !furnitureTypeRequested.equals("Task") && !furnitureTypeRequested.equals("Kneeling") && !furnitureTypeRequested.equals("Executive")
            && !furnitureTypeRequested.equals("Ergonomic"))
            {
                throw new IllegalArgumentException("This is not a valid furniture type for chair.");
            }
        }

        else if(furnitureRequested.equals("Lamp")) //if the furnitureRequested is lamp, then it checks to see if the furniture type is one of the valid ones for lamp
        {
            if(!furnitureTypeRequested.equals("Desk") && !furnitureTypeRequested.equals("Swing Arm") && !furnitureTypeRequested.equals("Study"))
            {
                throw new IllegalArgumentException("This is not a valid furniture type for lamp."); 
            }
        }

        else if(furnitureRequested.equals("Desk"))//if the furnitureRequested is desk, then it checks to see if the furniture type is one of the valid ones for desk
        {
            if(!furnitureTypeRequested.equals("Traditional") && !furnitureTypeRequested.equals("Adjustable") && !furnitureTypeRequested.equals("Standing"))
            {
                throw new IllegalArgumentException("This is not a valid furniture type for desk.");
            }
        }

        else if(furnitureRequested.equals("Filing"))//if the furnitureRequested is filing, then it checks to see if the furniture type is one of the valid ones for filing
        {
            if(!furnitureTypeRequested.equals("Small") && !furnitureTypeRequested.equals("Medium") && !furnitureTypeRequested.equals("Large"))
            {
                throw new IllegalArgumentException("This is not a valid furniture type for filing.");
            }
        }

        if(requestedQuantity <= 0) //Check for a valid quantity greater than 0
        {
            throw new IllegalArgumentException("This is not a valid furniture quantity.");
        }
    }

}

