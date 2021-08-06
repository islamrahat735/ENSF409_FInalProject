package edu.ucalgary.ensf409;

import org.junit.*;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

 /**
    @author     Rahat   Islam   <a href="mailto:rahat.islam@ucalgary.ca">rahat.islam@ucalgary.ca</a>
    @author     Mohammed  Kabir   <a href="mailto:mohammed.kabir@ucalgary.ca">mohammed.kabir@ucalgary.ca</a>
    @author     Dhyey   Lalseta <a href="mailto:dhyey.lalseta@ucalgary.ca">dhyey.lalseta@ucalgary.ca</a>
    @author     Aninda  Shome   <a href="mailto:aninda.shome@ucalgary.ca">aninda.shome@ucalgary.ca</a>

     @version   1.7
     @since     1.0
 
 */
/**
 * NOTE **VERY IMPORTANT**:
 * <p>
 * For the unit tests to work, you will need to use the database UNITTESTS which is supplied by us.
 * Essentially, the database is cleared before each test and the String fields are the inputs used in different tests
 * 
 * <p>
 * for the sake of convenience, whenever we are testing if a full object can be built,
 * we put a code in front of the test that relates to a string in the fields which holds 
 * the necessary database data for the particular test.
 * 
 * <p>
 * For example, our first test to see if a chair can be built starts with tc1,
 * which stands for (testChair1). the String TC1_CHAIRVALUES is the corresponding
 * set of database values that will built for that test. 
 * <p>
 * Also note that when the program prompts for user input, it is designed to keep asking
 * for input until the correct values were given. Since we weren't sure how to test user
 * inputs through unit testing, we created another function that would check valid entry once
 * all the data was already entered. Here we did some tests although, we didn't go extensively
 * into this because ideally the user input function would make it impossible to allow the user
 * to send invalid inputs
 */

public class TransactionTest {
    private static DatabaseConnection db;
    
    private final String TC1_CHAIRVALUES = 
    "('C1320',	'Kneeling',	'Y',	'N',	'N',	'N',	50,	'002'),"
    + "('C3405',	'Task',	'Y',	'Y',	'N',	'N',	100,	'003'),"
    + "('C9890',	'Mesh',	'N',	'Y',	'N',	'Y',	50,	'003'),"
    + "('C7268',	'Executive',	'N',	'N',	'Y',	'N',	75,	'004'),"
    + "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),"
    + "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),"
    + "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),"
    + "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),"
    + "('C3819',	'Kneeling',	'N',	'N',	'Y',	'N',	75,	'005'),"
    + "('C5784',	'Executive',	'Y',	'N',	'N',	'Y',	150,	'004'),"
    + "('C6748',	'Mesh',	'Y',	'N',	'N',	'N',	75,	'003'),"
    + "('C0914',	'Task',	'N',	'N',	'Y',	'Y',	50,	'002'),"
    + "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),"
    + "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003'),"
    + "('C8138',	'Mesh',	'N',	'N',	'Y',	'N',	75,	'005')";

    private final String TC2_CHAIRVALUES =
    "('C1320',	'Kneeling',	'Y',	'N',	'N',	'N',	50,	'002'),"
    + "('C3405',	'Task',	'Y',	'Y',	'N',	'N',	100,	'003'),"
    + "('C7268',	'Executive',	'N',	'N',	'Y',	'N',	75,	'004'),"
    + "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),"
    + "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),"
    + "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),"
    + "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),"
    + "('C3819',	'Kneeling',	'N',	'N',	'Y',	'N',	75,	'005'),"
    + "('C5784',	'Executive',	'Y',	'N',	'N',	'Y',	150,	'004'),"
    + "('C0914',	'Task',	'N',	'N',	'Y',	'Y',	50,	'002'),"
    + "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),"
    + "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003')";

    private final String TC3_CHAIRVALUES =
    "('C1320',	'Kneeling',	'Y',	'N',	'N',	'N',	50,	'002'),"
    + "('C3405',	'Task',	'Y',	'Y',	'N',	'N',	100,	'003'),"
    + "('C9890',	'Mesh',	'N',	'Y',	'N',	'Y',	50,	'003'),"
    + "('C7268',	'Executive',	'N',	'N',	'Y',	'N',	75,	'004'),"
    + "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),"
    + "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),"
    + "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),"
    + "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),"
    + "('C3819',	'Kneeling',	'N',	'N',	'Y',	'N',	75,	'005'),"
    + "('C5784',	'Executive',	'Y',	'N',	'N',	'Y',	150,	'004'),"
    + "('C6748',	'Mesh',	'Y',	'N',	'N',	'N',	75,	'003'),"
    + "('C0914',	'Task',	'N',	'N',	'Y',	'Y',	50,	'002'),"
    + "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),"
    + "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003'),"
    + "('C8138',	'Mesh',	'N',	'N',	'Y',	'N',	75,	'005'),"
    + "('C9000',	'Executive',	'N',	'Y',	'Y',	'Y',	300,	'004'),"
    + "('C0001',	'Executive',	'N',	'N',	'N',	'Y',	300,	'004')";

    private final String TC4_CHAIRVALUES =
     "('C0942',	'Mesh',	'Y',	'N',	'Y',	'Y',	175,	'005'),"
    + "('C4839',	'Ergonomic',	'N',	'N',	'N',	'Y',	50,	'002'),"
    + "('C2483',	'Executive',	'Y',	'Y',	'N',	'N',	175,	'002'),"
    + "('C5789',	'Ergonomic',	'Y',	'N',	'N',	'Y',	125,	'003'),"
    + "('C1148',	'Task',	'Y',	'N',	'Y',	'Y',	125,	'003'),"
    + "('C5409',	'Ergonomic',	'Y',	'Y',	'Y',	'N',	200,	'003')";

    private final String TL1_LAMPVALUES =
        "('L132',	'Desk',	'Y',	'N',	18,	'005'),"
     +  "('L980',	'Study',	'N',	'Y',	2,	'004'),"
     +  "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L564',	'Desk',	'Y',	'Y',	20,	'004'),"
     +   "('L342',	'Desk',	'N',	'Y',	2,	'002'),"
     +   "('L982',	'Study',	'Y',	'N',	8,	'002'),"
     +   "('L879',	'Swing Arm',	'N',	'Y',	3,	'005'),"
     +   "('L208',	'Desk',	'N',	'Y',	2,	'005'),"
     +   "('L223',	'Study',	'N',	'Y',	2,	'005'),"
     +   "('L928',	'Study',	'Y',	'Y',	10,	'002'),"
     +   "('L013',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L053',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L112',	'Desk',	'Y',	'N',	18,	'005'),"
     +   "('L649',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L096',	'Swing Arm',	'N',	'Y',	3,	'002')";
     
     private final String TL2_LAMPVALUES = 
     "('L132',	'Desk',	'Y',	'N',	18,	'005'),"
     +  "('L980',	'Study',	'N',	'Y',	2,	'004'),"
     +  "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L564',	'Desk',	'Y',	'Y',	20,	'004'),"
     +   "('L342',	'Desk',	'N',	'Y',	2,	'002'),"
     +   "('L879',	'Swing Arm',	'N',	'Y',	3,	'005'),"
     +   "('L208',	'Desk',	'N',	'Y',	2,	'005'),"
     +   "('L013',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L053',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L112',	'Desk',	'Y',	'N',	18,	'005'),"
     +   "('L649',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L096',	'Swing Arm',	'N',	'Y',	3,	'002')";

     private final String TL3_LAMPVALUES =
        "('L132',	'Desk',	'Y',	'N',	18,	'005'),"
     +  "('L980',	'Study',	'N',	'Y',	2,	'004'),"
     +  "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L564',	'Desk',	'Y',	'Y',	20,	'004'),"
     +   "('L342',	'Desk',	'N',	'Y',	2,	'002'),"
     +   "('L982',	'Study',	'Y',	'N',	8,	'002'),"
     +   "('L879',	'Swing Arm',	'N',	'Y',	3,	'005'),"
     +   "('L208',	'Desk',	'N',	'Y',	2,	'005'),"
     +   "('L223',	'Study',	'N',	'Y',	2,	'005'),"
     +   "('L928',	'Study',	'Y',	'Y',	10,	'002'),"
     +   "('L013',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L053',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     +   "('L112',	'Desk',	'Y',	'N',	18,	'005'),"
     +   "('L649',	'Desk',	'Y',	'N',	18,	'004'),"
     +   "('L096',	'Swing Arm',	'N',	'Y',	3,	'002')";

     private final String TL4_LAMPVALUES = 
     "('L980',	'Study',	'N',	'Y',	2,	'004'),"
     + "('L982',	'Study',	'Y',	'N',	8,	'002'),"
     + "('L487',	'Swing Arm',	'Y',	'N',	27,	'002'),"
     + "('L342',	'Desk',	'N',	'Y',	2,	'002'),"
     + "('L223',	'Study',	'N',	'Y',	2,	'005'),"
     + "('L928',	'Study',	'Y',	'Y',	10,	'002'),"
     + "('L111',	'Study',	'Y',	'N',	2,	'005'),"
     + "('L165',	'Study',	'Y',	'N',	10,	'005'),"
     + "('L378',	'Study',	'N',	'Y',	2,	'005'),"
     + "('L096',	'Swing Arm',	'N',	'Y',	3,	'002')";
     
     private final String TD1_DESKVALUES = 
     "('D3820',	'Standing',	'Y',	'N',	'N',	150,	'001'),"
     + "('D4475',	'Adjustable',	'N',	'Y',	'Y',	200,	'002'),"
     + "('D0890',	'Traditional',	'N',	'N',	'Y',	25,	'002'),"
     + "('D2341',	'Standing',	'N',	'Y',	'N',	100,	'001'),"
     + "('D9387',	'Standing',	'Y',	'Y',	'N',	250,	'004'),"
     + "('D7373',	'Adjustable',	'Y',	'Y',	'N',	350,	'005'),"
     + "('D2746',	'Adjustable',	'Y',	'N',	'Y',	250,	'004'),"
     + "('D9352',	'Traditional',	'Y',	'N',	'Y',	75,	'002'),"
     + "('D4231',	'Traditional',	'N',	'Y',	'Y',	50,	'005'),"
     + "('D8675',	'Traditional',	'Y',	'Y',	'N',	75,	'001'),"
     + "('D1927',	'Standing',	'Y',	'N',	'Y',	200,	'005'),"
     + "('D1030',	'Adjustable',	'N',	'Y',	'N',	150,	'002'),"
     + "('D4438',	'Standing',	'N',	'Y',	'Y',	150,	'004'),"
     + "('D5437',	'Adjustable',	'Y',	'N',	'N',	200,	'001'),"
     + "('D3682',	'Adjustable',	'N',	'N',	'Y',	50,	'005')";

     private final String TD2_DESKVALUES = 
     "('D3820',	'Standing',	'Y',	'N',	'N',	150,	'001'),"
     + "('D4475',	'Adjustable',	'N',	'Y',	'Y',	200,	'002'),"
     + "('D0890',	'Traditional',	'N',	'N',	'Y',	25,	'002'),"
     + "('D2341',	'Standing',	'N',	'Y',	'N',	100,	'001'),"
     + "('D9387',	'Standing',	'Y',	'Y',	'N',	250,	'004'),"
     + "('D7373',	'Adjustable',	'Y',	'Y',	'N',	350,	'005'),"
     + "('D2746',	'Adjustable',	'Y',	'N',	'Y',	250,	'004'),"
     + "('D9352',	'Traditional',	'Y',	'N',	'Y',	75,	'002'),"
     + "('D4231',	'Traditional',	'N',	'Y',	'Y',	50,	'005'),"
     + "('D8675',	'Traditional',	'Y',	'Y',	'N',	75,	'001'),"
     + "('D1927',	'Standing',	'Y',	'N',	'Y',	200,	'005'),"
     + "('D1030',	'Adjustable',	'N',	'Y',	'N',	150,	'002'),"
     + "('D4438',	'Standing',	'N',	'Y',	'Y',	150,	'004'),"
     + "('D5437',	'Adjustable',	'Y',	'N',	'N',	200,	'001'),"
     + "('D3682',	'Adjustable',	'N',	'N',	'Y',	50,	'005')";
     
     private final String TD3_DESKVALUES = 
     "('D3820',	'Standing',	'Y',	'N',	'N',	150,	'001'),"
     + "('D4475',	'Adjustable',	'N',	'Y',	'Y',	200,	'002'),"
     + "('D0890',	'Traditional',	'N',	'N',	'Y',	25,	'002'),"
     + "('D2341',	'Standing',	'N',	'Y',	'N',	100,	'001'),"
     + "('D9387',	'Standing',	'Y',	'Y',	'N',	250,	'004'),"
     + "('D7373',	'Adjustable',	'Y',	'Y',	'N',	350,	'005'),"
     + "('D2746',	'Adjustable',	'Y',	'N',	'Y',	250,	'004'),"
     + "('D9352',	'Traditional',	'Y',	'N',	'Y',	75,	'002'),"
     + "('D4231',	'Traditional',	'N',	'Y',	'Y',	50,	'005'),"
     + "('D8675',	'Traditional',	'Y',	'Y',	'N',	75,	'001'),"
     + "('D1927',	'Standing',	'Y',	'N',	'Y',	200,	'005'),"
     + "('D1030',	'Adjustable',	'N',	'Y',	'N',	150,	'002'),"
     + "('D4438',	'Standing',	'N',	'Y',	'Y',	150,	'004'),"
     + "('D5437',	'Adjustable',	'Y',	'N',	'N',	200,	'001'),"
     + "('D3682',	'Adjustable',	'N',	'N',	'Y',	50,	'005')";

     private final String TF1_FILINGVALUES = 
     "('F001',	'Small',	'Y',	'Y',	'N',	50,	'005'),"
    + "('F002',	'Medium',	'N',	'N',	'Y',	100,	'004'),"
    + "('F003',	'Large',	'N',	'N',	'Y',	150,	'002'),"
    + "('F004',	'Small',	'N',	'Y',	'Y',	75,	'004'),"
    + "('F005',	'Small',	'Y',	'N',	'Y',	75,	'005'),"
    + "('F006',	'Small',	'Y',	'Y',	'N',	50,	'005'),"
    + "('F007',	'Medium',	'N',	'Y',	'Y',	150,	'002'),"
    + "('F008',	'Medium',	'Y',	'N',	'N',	50,	'005'),"
    + "('F009',	'Medium',	'Y',	'Y',	'N',	100,	'004'),"
    + "('F010',	'Large',	'Y',	'N',	'Y',	225,	'002'),"
    + "('F011',	'Large',	'N',	'Y',	'Y',	225,	'005'),"
    + "('F012',	'Large',	'N',	'Y',	'N',	75,	'005'),"
    + "('F013',	'Small',	'N',	'N',	'Y',	50,	'002'),"
    + "('F014',	'Medium',	'Y',	'Y',	'Y',	200,	'002'),"
    + "('F015',	'Large',	'Y',	'N',	'N',	75,	'004')";

    private final String TF2_FILINGVALUES = 
     "('F003',	'Large',	'N',	'N',	'Y',	150,	'002'),"
    + "('F010',	'Large',	'Y',	'N',	'Y',	225,	'002'),"
    + "('F011',	'Large',	'N',	'Y',	'Y',	225,	'005'),"
    + "('F012',	'Large',	'N',	'Y',	'N',	75,	'005'),"
    + "('F015',	'Large',	'Y',	'N',	'N',	75,	'004')";

    private final String TF3_FILINGVALUES = 
     "('F002',	'Medium',	'N',	'N',	'Y',	100,	'004'),"
    + "('F009',	'Medium',	'Y',	'Y',	'N',	150,	'004'),"
    + "('F014',	'Medium',	'Y',	'Y',	'Y',	200,	'002')";

    public TransactionTest() {
        
    }

    @BeforeClass
    /**
     * switches database used to UNITTESTS
     */
    public static void startTests(){
        DatabaseConnection.testing = true; //tells program to use the UNITTESTS database
        db = new DatabaseConnection();
    }
    /**
     * Clears the tables in the database before each test
     * @throws SQLException
     */
    @Before
    public void clearDatabase()throws SQLException{
        db.emptyTable("CHAIR");
        db.emptyTable("DESK");
        db.emptyTable("LAMP");
        db.emptyTable("FILING");
    }

    /**
    Testing the process method in the transaction class.
    test to see if one mesh chair can be built.
    The parts chosen should be removed leaving "C092" left in the db.
    The expected price of the order is 200.
    */
    @Test
    public void tc1_testProcess_1MeshChair() throws SQLException {
        db.insertValuesInTable("CHAIR", TC1_CHAIRVALUES);
        Transaction test = new Transaction("Chair", "Mesh", 1);
        int expectedResult = 200;
        String remainingDbItems = "C0942";
        String[] orderedItems_expectedResult = {"C6748", "C8138", "C9890"};
        test.process();
        assertArrayEquals("Ordered items don't match", orderedItems_expectedResult, test.getOrderedItem());
        assertEquals("total price don't match", expectedResult, test.getTotalPrice());
        assertEquals("Remaining mesh chair item should be C0942",remainingDbItems, db.selectAllFromTableOfType_OrderByID("CHAIR", "Mesh"));
    }

    /**
    Testing the process method in the transaction class.
    test to see if one mesh chair can be built.
    With the remaining Mesh chair items, there shouldn't be enough mesh chair parts left
    we expect the canBeProcessed field to be false.
    we still expect the remaing mesh chair item in the db to be "C0942".
    */
    @Test
    public void tc2_testProcess_1MeshChair() throws SQLException {
        db.insertValuesInTable("CHAIR", TC2_CHAIRVALUES);
        Transaction test = new Transaction("Chair", "Mesh", 1);
        boolean canBeProcessedExpectedResult = false;
        String remainingDbItemsExpectedResult = "C0942";
        test.process();
        assertEquals("canBeProcessed should be false", canBeProcessedExpectedResult, test.getCanBeProcessed());
        assertEquals("The remaining Mesh item should be C0942",remainingDbItemsExpectedResult, db.selectAllFromTableOfType_OrderByID("CHAIR", "Mesh"));
    }

    /**
    Testing the process method in the transaction class.
     test to see if 2 Executive Chairs can be made : process should be successful.
     canBeProcessed should be true.
    there should be 1 Executive chair item left in the database.
    expected price is 650.
    */
    @Test
    public void tc3_testProcess_2ExecutiveChairs() throws SQLException
    {
        db.insertValuesInTable("CHAIR", TC3_CHAIRVALUES);
        Transaction test = new Transaction("Chair", "Executive", 2);
        int expectedPrice = 700;
        String[] expectedOrderedItems = {"C2483", "C5784", "C7268", "C9000"};
        String expectedRemainingDbItems = "C0001";
        test.process();
        assertArrayEquals("ordered items don't match", expectedOrderedItems, test.getOrderedItem());
        assertEquals("Price should be 700", expectedPrice, test.getTotalPrice());
        assertEquals("C0001 should be remaining", expectedRemainingDbItems, db.selectAllFromTableOfType_OrderByID("CHAIR", "Executive"));
    }

    /**
    Testing the process method in the transaction class.
    test to see if 1 ergonomic chair can be built.
    test should be successful in producing chair.
    after function process() is called only ergonomic chair in the db should be C5789.
    */
    @Test
    public void tc4_testProcess_1ErgonomicChair() throws SQLException {
        db.insertValuesInTable("CHAIR", TC4_CHAIRVALUES);
        Transaction test = new Transaction("Chair", "Ergonomic", 1);
        int expectedPrice = 250;
        String[] expectedOrderedItems = {"C4839", "C5409"};
        String expectedRemainingDbItems = "C5789";
        test.process();
        assertArrayEquals("ordered items don't match", expectedOrderedItems, test.getOrderedItem());
        assertEquals("Price should be 700", expectedPrice, test.getTotalPrice());
        assertEquals("C0001 should be remaining", expectedRemainingDbItems, db.selectAllFromTableOfType_OrderByID("CHAIR", "Ergonomic"));
    }

    /**
    Testing the process method in the transaction class.
    test to see if 1 Task chair can be built.
    the db will be empty so canBeProcessed must be false.
    */
    @Test
    public void tc5_testProcess_1TaskChair() throws SQLException {
        Transaction test = new Transaction("Chair", "Task", 1);
        boolean expectedCanbeProcessed = false;
        test.process();
        assertEquals("canBeProcessed should be false", expectedCanbeProcessed, test.getCanBeProcessed());
    }

    /**
    Testing the process method in the transaction class.
    test to see if 2 swing arm lamps can be built.
    This test should be successful and be able to produce the 2 swing arm lamps.
    At the end of this test, no swing arm parts should be left in the database (the ordered items will get deleted).
    */
    @Test
    public void tl1_testProcess_2SwingArmLamps() throws SQLException {
        db.insertValuesInTable("LAMP", TL1_LAMPVALUES);
        Transaction test = new Transaction("Lamp", "Swing Arm", 2);
        int expectedPrice = 60;
        String [] expectedOrderedItems = {"L053", "L096", "L487", "L879"};
        test.process();
        assertArrayEquals("The expected ordered items does not match", expectedOrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match", expectedPrice, test.getTotalPrice());
    }


    /**
    Testing the process method in the transaction class.
    Test to see if one study lamp can be made.
    A study lamp should not be able to be built with the available parts in the database.
    canBeProcessed should be false.
    */
    @Test
    public void tl2_testProcess_1StudyLamp() throws SQLException {
        db.insertValuesInTable("LAMP", TL2_LAMPVALUES);
        Transaction test = new Transaction("Lamp", "Study", 1);
        boolean canBeProcessed_expectedResult = false;
        test.process();
        assertEquals("The value of canBeProcessed should be false", canBeProcessed_expectedResult, test.getCanBeProcessed());
    }
    
    /**
    Testing the process method in the transaction class.
    Test to see if two desk lamps can be made.
    Two desk lamps should be successfully made.
    There should be 4 desk lamp parts left in the database.
    The expected total price is 40.
    */
    @Test
    public void tl3_testProcess_2DeskLamps() throws SQLException {
        db.insertValuesInTable("LAMP", TL3_LAMPVALUES);  
        Transaction test = new Transaction("Lamp", "Desk", 2);
        String [] expected_OrderedItems = {"L342", "L564", "L649"};
        String expected_RemainingItems = "L013 L112 L132 L208";
        int expected_totalPrice = 40;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaining items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("LAMP", "Desk"));
    }

    /** 
    Testing the process method in the transaction class.
    Test to see if four study lamps can be made.
    The four study lamps should be made successfully.
    There should be no study lamp parts left in the database.
    The expected price is 36.
    */
    @Test
    public void tl4_testProcess_4StudyLamps() throws SQLException {
        db.insertValuesInTable("LAMP", TL4_LAMPVALUES);
        Transaction test = new Transaction("Lamp", "Study", 4);
        String [] expected_OrderedItems = {"L111", "L165", "L223", "L378", "L928", "L980", "L982"};
        int expected_totalPrice = 36;
        String expected_RemainingItems = "";
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaining items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("LAMP", "Study"));
    }

    /**Testing the process method in the transaction class.
     Test to see if two traditional desks can be made.
    Two traditional desks should be made successfully.
    There should be 1 traditional desk part left in the database.
    The expected price is 200.
    */
    @Test
    public void td1_testProcess_2TraditionalDesks() throws SQLException {
        db.insertValuesInTable("DESK", TD1_DESKVALUES);
        Transaction test = new Transaction("Desk", "Traditional", 2);
        String [] expected_OrderedItems = {"D4231", "D8675", "D9352"};
        String expected_RemaningItems = "D0890";
        int expected_totalPrice = 200;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaning items does not match the resultant remaining items", expected_RemaningItems, db.selectAllFromTableOfType_OrderByID("DESK", "Traditional"));
    }

    /**
    Testing the process method in the transaction class.
    Test to see if three standing desks can be made.
    Should not be able to build three standing desks.
    canBeProcessed should be false.
    the remaining parts in the database should be all 5 standing desk items.
    */
    @Test
    public void td2_testProcess_3StandingDesks() throws SQLException {
        db.insertValuesInTable("DESK", TD2_DESKVALUES);
        Transaction test = new Transaction("Desk", "Standing", 3);
        boolean expected_canBeProcessed = false;
        String expected_RemainingItems = "D1927 D2341 D3820 D4438 D9387";
        test.process();
        assertEquals("The expected value of canBeProcessed should be false", expected_canBeProcessed, test.getCanBeProcessed());
        assertEquals("The expected value of remaining parts does not match the remaining parts", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("DESK", "Standing"));
    }


    /**
    Testing the process method in the transaction class.
    Test to see if one adjustable desk can be made.
    Should be able to successfully build one adjustable desk.
    There is expected to be 4 parts remining in the database.
    The expected price is 400.
    */
    @Test
    public void td3_testProcess_1AdjustableDesk() throws SQLException {
        db.insertValuesInTable("DESK", TD3_DESKVALUES);
        Transaction test = new Transaction("Desk", "Adjustable", 1);
        String expected_RemainingItems = "D1030 D2746 D3682 D7373";
        String [] expected_OrderedItems = {"D4475", "D5437"};
        int expected_totalPrice = 400;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaning items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("DESK", "Adjustable"));
    }

    /**
    Testing the process method in the transaction class.
    Test to see if three Small Filings can be made.
    Test should be successful.
    After the test, there will be no remaining Small Filings in the db.
    There will be 5 ordered items.
    total price should be $300.
    */
    @Test
    public void tf1_testProcess_3SmallFilings() throws SQLException {
        db.insertValuesInTable("FILING", TF1_FILINGVALUES);
        Transaction test = new Transaction("Filing", "Small", 3);
        String expected_RemainingItems = "";
        String [] expected_OrderedItems = {"F001", "F004", "F005", "F006", "F013"};
        int expected_totalPrice = 300;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaning items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("FILING", "Small"));
    }

    /** 
    Testing the process method in the transaction class.
    Test to see if two Large Filings can be made.
    Test should be successful.
    After the test, there will be one remaining Large Filing in the db.
    There will be 4 ordered items.
    total price should be $600.
    */
    @Test
    public void tf2_testProcess_2LargeFilings() throws SQLException {
        db.insertValuesInTable("FILING", TF2_FILINGVALUES);
        Transaction test = new Transaction("Filing", "Large", 2);
        String expected_RemainingItems = "F003";
        String [] expected_OrderedItems = {"F010", "F011", "F012", "F015"};
        int expected_totalPrice = 600;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaning items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("FILING", "Large"));
    }

    /**
    Testing the process method in the transaction class.
    Test to see if one medium Filing can be made.
    Test should be successful.
    After the test, there will be no remaining Medium Filing in the db.
    There will be 1 ordered item.
    total price should be $200.
    */
    @Test
    public void tf3_testProcess_1MediumFiling() throws SQLException {
        db.insertValuesInTable("FILING", TF3_FILINGVALUES);
        Transaction test = new Transaction("Filing", "Medium", 1);
        String expected_RemainingItems = "F002 F009";
        String [] expected_OrderedItems = {"F014"};
        int expected_totalPrice = 200;
        test.process();
        assertArrayEquals("The expected ordered items does not match the result", expected_OrderedItems, test.getOrderedItem());
        assertEquals("The expected price does not match the result price", expected_totalPrice, test.getTotalPrice());
        assertEquals("The expected remaning items does not match the resultant remaining items", expected_RemainingItems, db.selectAllFromTableOfType_OrderByID("FILING", "Medium"));
    }

    /**
    Testing checkIfValidEntry method in the transaction class.
    Test to see what the program does when an invalid furniture piece is entered.
    The program will throw an IllegalArgumentException.
    Test should be a success and assert the fail message with the IllegalArgumentException message
    */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIfValidEntry_ForInvalidFurniturePiece() throws SQLException {

        Transaction test = new Transaction("hi", "Mesh", 1);
        test.checkIfValidEntry();

    }

    /**
    Testing checkIfValidEntry method in the transaction class.
    Test to see what program does when an invalid type of furniture is entered.
    The program will throw an IllegalArgumentException.
    Test should be a success and assert the fail message with the IllegalArgumentException message
    */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIfValidEntry_ForInvalidFurnitureType() throws SQLException {

        Transaction test = new Transaction("Filing", "Huge", 1);
        test.checkIfValidEntry();
    }

    /**
    Testing checkIfValidEntry method in the transaction class.
    Test to see if an invalid quantity of furniture pieces is entered.
    The program will throw an IllegalArgumentException.
    Test should be a success and assert the fail message with the IllegalArgumentException message
    */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIfValidEntry_ForInvalidFurnitureQuantity() throws SQLException {

        Transaction test = new Transaction("Lamp", "Swing Arm", -2);
        test.checkIfValidEntry();
    }


    /**
    Testing checkIfValidEntry method in the transaction class.
    Test to see a valid entry of furniture piece, type, and quantity is entered.
    The program will have all valid inputs.
    */
    @Test
    public void testCheckIfValidEntry_AllValuesAreValid() throws SQLException {
        Transaction test = new Transaction("Chair", "Mesh", 2);
        test.checkIfValidEntry();
    }

    /**
    Testing printOrder method in the Transaction class.
    dummy return values of the transaction object will be set(orderedItems, canBeProcessed etc.)
    and the test will check if the strings returned by printOrder() (which represent the strings in the terminal and orderform.txt)
    match expected results.
    This case, we should have a successful order of 1 mesh chair.
    we will test the output printed to the orderfrom
    */
    @Test
    public void testPrintOrder_SuccessfulOrderFormMessage() throws IOException{
        Transaction test = new Transaction("Chair", "Mesh", 1);
        test.setCanBeProcessed(true);
        String[] items = {"C1030", "C2000"};
        test.setOrderedItems(items);
        test.setTotalPrice(150);

        String expectedResult =
        "Furniture Order Form\n\n"
        +"Faculty Name: \n"
        +"Contact: \n"
        +"Date: \n\n"
        +"Original Request: Mesh Chair, 1\n\n"
        +"Items Ordered\n"
        +"ID: C1030\n"
        +"ID: C2000\n\n"
        +"Total Cost: $150";

        String actualResult = test.printOrder()[1];

        assertEquals("expected message doesn't match actual message", expectedResult, actualResult);
    }

    /**
    Testing printOrder method in the Transaction class.
    dummy return values of the transaction object will be set(orderedItems, canBeProcessed etc.)
    and the test will check if the strings returned by printOrder() (which represent the strings in the terminal and orderform.txt)
    match expected results.
    This scenario, we suppose we have a successful order of 1 Desk Lamp.
    we will test the output printed to the orderfrom
    */
    @Test
    public void testPrintOrder_UnsuccessfulOrderFormMessage() throws IOException{
        Transaction test = new Transaction("Lamp", "Desk", 1);
        test.setCanBeProcessed(false);

        String expectedResult = "Your order could not be fulfilled based on current inventory. Suggested manufacturers are: Office Furnishings, Furniture Goods, Fine Office Supplies.";

        String actualResult = test.printOrder()[1];

        assertEquals("expected message doesn't match actual message", expectedResult, actualResult);
    }

    
}
