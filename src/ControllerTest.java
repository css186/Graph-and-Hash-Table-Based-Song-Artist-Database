import student.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test cases for the Controller
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.11
 */
public class ControllerTest extends TestCase {
    // ~ Fields .........................................................
    //
    // ----------------------------------------------------------
    private Controller controller;
    private ByteArrayOutputStream outContent;
    
    // ~ Set up .........................................................
    //
    // ----------------------------------------------------------  
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        this.controller = new Controller(10);
        this.outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outContent));
    }
    
    // ~ Test Method ...................................................
    //
    // ----------------------------------------------------------
    /**
     * Test case for controller instantiation
     * 
     */
    public void testContollerInstantiation() {
        assertNotNull(this.controller);
        assertEquals(this.controller.getArtistTable().getCapacity(), 10);
        assertEquals(this.controller.getArtistTable().getCapacity(), 10);
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for insert operation
     * 
     */
    public void testInsert() {
        this.controller.insert("artist", "song");
        this.controller.print("artist");
        assertTrue(outContent.toString()
            .contains("|artist| is added to the Artist database."));
        assertTrue(outContent.toString()
            .contains("|song| is added to the Song database."));
        assertEquals(this.controller.getArtistTable().getSize(), 1);
        assertEquals(this.controller.getSongTable().getSize(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for resizing
     * 
     */
    public void testResizeSongTable() {
        for (int i = 0; i < 5; i++) {
            this.controller.insert("artist", "song" + i);
        }
        assertEquals(this.controller.getSongTable().getSize(), 5);
        this.controller.insert("artist", "song6");
        assertTrue(this.outContent.toString()
            .contains("Song hash table size doubled."));
    }
    // ----------------------------------------------------------
    /**
     * Test case for another resizing
     * 
     */
    public void testResizeArtistTable() {
        for (int i = 0; i < 5; i++) {
            this.controller.insert("artist" + i, "song");
        }
        assertEquals(this.controller.getArtistTable().getSize(), 5);
        this.controller.insert("artist6", "song");
        assertTrue(this.outContent.toString()
            .contains("Artist hash table size doubled."));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for testing other input
     * 
     */
    public void testOtherInput() {
        this.controller.insert("artist", "song");
        this.controller.remove("movie", "John Wick");
        assertFalse(this.outContent.toString()
            .contains("|song| is removed from the Song database."));
        assertFalse(this.outContent.toString()
            .contains("|John Wichk| is removed from the Movie database."));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for removal on empty song table
     * 
     */
    public void testRemoveEmptySongTable() {      
        this.controller.remove("song", "song1");
        assertTrue(this.outContent.toString()
            .contains("|song1| does not exist in the Song database."));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for removal on empty artist table
     * 
     */
    public void testRemoveEmptyArtistTable() {
        this.controller.remove("artist", "John");
        assertTrue(this.outContent.toString()
            .contains("|John| does not exist in the Artist database."));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for normal removal 
     * 
     */
    public void testRemove() {
        this.controller.insert("John", "song1");
        this.controller.remove("artist", "John");
        this.controller.print("artist");
        assertTrue(this.outContent.toString()
            .contains("|John| is removed from the Artist database."));
        this.controller.remove("song", "song1");
        assertTrue(this.outContent.toString()
            .contains("|song1| is removed from the Song database."));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for normal input and print for artists
     * 
     */
    public void testNormalInputToPrintArtist() {
        this.controller.insert("Blind Lemon Jefferson"
            , "Long Lonesome Blues");  
        this.controller.print("artist");
        assertTrue(this.outContent.toString()
            .contains("0: |Blind Lemon Jefferson|"));
        assertFalse(this.outContent.toString()
            .contains("15: |Long Lonesome Blues|"));
    }
    
    // ----------------------------------------------------------
    /**
     * Another Test case for input and print for artists
     * 
     */
    public void testNormalInputToPrintSong() {
        this.controller.insert("Blind Lemon Jefferson"
            , "Ma Rainey's Black Bottom");
        this.controller.print("song");
        assertFalse(this.outContent.toString()
            .contains("0: |Blind Lemon Jefferson|"));
        assertTrue(this.outContent.toString()
            .contains("6: |Ma Rainey's Black Bottom|"));
    }
    
    // ----------------------------------------------------------
    /**
     * Another Test case for input and print for artists
     * 
     */
    public void testOtherInputToPrintArtist() {
        this.controller.insert("Blind Lemon Jefferson"
            , "Long Lonesome Blues");
        this.controller.print("animal");
        assertFalse(this.outContent.toString()
            .contains("0: |Blind Lemon Jefferson|"));
        assertFalse(this.outContent.toString()
            .contains("15: |Long Lonesome Blues|"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test case for input and print for songs
     * 
     */
    public void testOtherInputToPrintSong() {
        this.controller.insert("Blind Lemon Jefferson"
            , "Long Lonesome Blues");
        this.controller.print("movie");
        assertFalse(this.outContent.toString()
            .contains("0: |Blind Lemon Jefferson|"));
        assertFalse(this.outContent.toString()
            .contains("6: |Ma Rainey's Black Bottom|"));
    }
 
}
