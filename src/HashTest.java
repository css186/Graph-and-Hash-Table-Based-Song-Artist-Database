import static org.junit.Assert.assertNotEquals;
import student.TestCase;

/**
 * A test file for all the method implemented in Hash.class
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.06
 */
public class HashTest extends TestCase {
    // ~ Fields ................................................................
    private Hash testTable;
    private GraphNode node;
    
    // ~ Constructors ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        this.testTable = new Hash(10);
        this.node = new GraphNode(-1);
    }
    
    // ~ Public Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Check out the sfold method
     *
     * @throws Exception
     *         Either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }
    
    // ----------------------------------------------------------
    /**
     * Check table instantiation
     */
    public void testTableCreation() {
        assertTrue(this.testTable.getSize() == 0);
        assertTrue(this.testTable.getHashTable().length == 10);
        assertTrue(this.testTable.getCapacity() == 10);
        assertEquals(this.testTable.getTombstone().getKey(), "TOMBSTONE");
        assertEquals(this.testTable.getTombstone().getValue(), null);
        assertNotNull(this.testTable);
    }
    
    // ----------------------------------------------------------
    /**
     * Another check for table instantiation
     */
    public void testTableCreationInequality() {
        assertFalse(this.testTable.getSize() == 100);
        assertFalse(this.testTable.getSize() == -100);
        assertFalse(this.testTable.getCapacity() == 4);
        assertFalse(this.testTable.getCapacity() == -4);
        assertFalse(this.testTable.getHashTable().length == 4);
        assertFalse(this.testTable.getHashTable().length == -4);
        assertNotEquals(this.testTable.getTombstone().getKey(), "tombstone");
        assertNotEquals(this.testTable.getTombstone().getKey(), null);
        assertNotEquals(this.testTable.getTombstone().getKey(), "");
        assertNotEquals(this.testTable.getTombstone().getValue(), node);
    }
    
    // ----------------------------------------------------------
    /**
     * A test for triggering resize of table
     */
    public void testResizeCapacity() {
        this.testTable.insert("John1", node);
        this.testTable.insert("John2", node);
        this.testTable.insert("John3", node);
        this.testTable.insert("John4", node);
        assertTrue(this.testTable.getSize() < this.testTable.getCapacity() / 2 
            || this.testTable.getSize() == this.testTable.getCapacity() / 2);
    }
    
    // ----------------------------------------------------------
    /**
     * Simple insert test
     */
    public void testInsertRecord() {
        assertTrue(this.testTable.insert("John", node));
        assertTrue(this.testTable.getSize() == 1);
        
    }
    
    // ----------------------------------------------------------
    /**
     * A test for triggering collision after resize
     */
    public void  testCollisionOne() {
        this.testTable.insert("artist1", node);
        this.testTable.insert("artist2", node);
        this.testTable.insert("artist3", node);
        this.testTable.insert("artist4", node);
        this.testTable.insert("artist5", node);
        this.testTable.insert("John3", node); // collision
        this.testTable.insert("Joh37", node); // collision
        assertNotEquals(this.testTable.findIndexOfRecord("John3")
            , this.testTable.findIndexOfRecord("Joh37"));
    }
    
    // ----------------------------------------------------------
    /**
     * Another test for collision
     */
    public void testCollisionTwo() {
        this.testTable = new Hash(5);
        assertTrue((Hash.h("John1", 5)) == 3);
        assertTrue((Hash.h("John6", 5)) == 3);
        assertEquals(Hash.h("John1", 5), Hash.h("John6", 5));
        
        assertTrue(this.testTable.insert("John1", node));
        assertTrue(this.testTable.insert("John6", node));
        assertTrue(this.testTable.getSize() == 2);

    }
    
    // ----------------------------------------------------------
    /**
     * Test for collision with resizing
     */
    public void testResizeAndCollision() {
        this.testTable = new Hash(2);
        assertEquals(this.testTable.getCapacity(), 2);
        this.testTable.insert("John", node);
        assertEquals(this.testTable.getCapacity(), 2);
        // resize
        this.testTable.insert("Andrew", node);
        assertEquals(this.testTable.getCapacity(), 4);
        assertTrue((Hash.h("John", 4)) == 2);
        assertTrue((Hash.h("Andrew", 4)) == 2);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for collision without resizing
     */
    public void testInsertWithoutResize() {
        this.testTable = new Hash(10);
        assertEquals(this.testTable.getCapacity(), 10);
        this.testTable.insert("test1", node);
        assertEquals(this.testTable.getCapacity(), 10);
        this.testTable.insert("test2", node);
        assertEquals(this.testTable.getCapacity(), 10);
        assertTrue(this.testTable.getSize() < this.testTable.getCapacity());
    }
    
    // ----------------------------------------------------------
    /**
     * Test for checking tomb stone will be rehashed or not
     */
    public void testTombstoneNotRehashed() {
        this.testTable = new Hash(4);
        this.testTable.insert("John", node);
        this.testTable.insert("Andrew", node);
        this.testTable.remove("John");
        this.testTable.insert("test1", node);
        // trigger resize
        this.testTable.insert("test2", node);

        boolean foundTombstone = false;
        for (Record record : this.testTable.getHashTable()) {
            if (this.testTable.getTombstone().equals(record)) {
                foundTombstone = true;
            }
        }
        assertFalse(foundTombstone);
        
    }
    
    // ----------------------------------------------------------  
    /**
     * Test for removing element with normal case
     */
    public void testRemoveNormal() {
        this.testTable.insert("John", node);
        assertNotNull(this.testTable.remove("John"));
        assertNull(this.testTable.remove("Andrew"));
        assertNull(this.testTable.remove("John"));
    }
    
    // ----------------------------------------------------------  
    /**
     * Test for removing element from empty table
     */
    public void testEmptyTableRemoval() {
        this.testTable.remove("test");
        assertNull(this.testTable.remove("John"));
    }
    
    // ---------------------------------------------------------- 
    /**
     * Test for the find check in remove method
     */
    public void testRemoveFindTrue() {
        this.testTable = new Hash(5);
        this.testTable.insert("test", node);
        assertNull(this.testTable.remove("John"));
    }
    
    // ---------------------------------------------------------- 
    /**
     * Another test for the find check in remove method
     */
    public void testRemoveFindFalse() {
        this.testTable = new Hash(5);
        this.testTable.insert("test", node);
        assertNotNull(this.testTable.remove("test"));
    }
    
    // ---------------------------------------------------------- 
    /**
     * Test for size decrease when removing
     */
    public void testRemoveSizeDecrease() { 
        this.testTable.insert("test", node);
        int before = this.testTable.getSize();
        this.testTable.remove("test");
        int after = this.testTable.getSize();
        assertEquals(before - 1, after);
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test for removing existing element
     */
    public void testRemoveExistingRecord() {
        this.testTable.insert("John", node);
        Record result = this.testTable.remove("John");
        assertTrue(result.getKey().equals("John"));
        assertFalse("Andrew".equals(result.getKey()));
        assertFalse(this.testTable.find("John"));
        assertTrue(this.testTable.getSize() == 0);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for removing element not in the table
     */
    public void testRemoveNonExistingRecord() {
        Record record = new Record("John", node);
        this.testTable.insert(record.getKey(), node);
        Record result = this.testTable.remove("Andrew");
        assertTrue(this.testTable.getSize() == 1);
        assertNull(result);
    }
    
    // ----------------------------------------------------------
    /**
     * Test probing at an empty table
     */
    public void testFindEmptyTable() {
        assertEquals(this.testTable.getSize(), 0);
        assertEquals(this.testTable.findIndexOfRecord("key"), -1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test probing with non-empty table
     */
    public void testFindExistingKey() {
        this.testTable.insert("John", node);
        assertEquals(this.testTable.findIndexOfRecord("John"), 4);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for counting number of tomb stones
     */
    public void testTombstonCount() {
        this.testTable.insert("artist1", node);
        this.testTable.insert("artist2", node);
        this.testTable.insert("artist3", node);
        this.testTable.insert("artist4", node);
        this.testTable.insert("artist5", node);
        this.testTable.remove("artist1");
        this.testTable.remove("artist2");
        assertEquals(this.testTable.getSize(), 3);
        int count = 0;
        for (Record r : this.testTable.getHashTable()) {
            if (this.testTable.getTombstone().equals(r)) {
                count++;
            }
        }
        assertEquals(count, 2);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for the find method
     */
    public void testFind() {
        this.testTable.insert("artist1", node);
        assertTrue(this.testTable.find("artist1"));
        assertNotNull(this.testTable.findIndexOfRecord("artist1"));
        assertEquals(this.testTable.findIndexOfRecord("test1"), -1);
        assertFalse(this.testTable.find("test1"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test find method with invalid input
     */
    public void testFindWithInvalidInput() {
        assertFalse(this.testTable.find(null));
        assertFalse(this.testTable.find(""));
    }
    
    // ----------------------------------------------------------
    /**
     * Test for updating the tomb stone position
     */
    public void testInsertOnTombstone() {
        this.testTable.insert("artist1", node);
        this.testTable.insert("artist2", node);
        this.testTable.insert("artist3", node);
        this.testTable.insert("artist4", node);
        this.testTable.insert("artist5", node);
        this.testTable.insert("John", node);
        this.testTable.insert("Bob", node);
        int index = this.testTable.findIndexOfRecord("artist3");
        assertEquals(index, 4);
        index = this.testTable.findIndexOfRecord("artist2");
        assertEquals(index, 8);
        this.testTable.remove("artist1");
        this.testTable.remove("artist2");
        this.testTable.remove("artist3");
        this.testTable.remove("artist4");
        this.testTable.remove("artist5");
        this.testTable.remove("John");
        this.testTable.remove("Bob");
        
        this.testTable.insert("John6", node); //at index 8
        this.testTable.insert("John2", node); //at index 4
        
        assertEquals(this.testTable.findIndexOfRecord("John2"), 4);
        assertEquals(this.testTable.findIndexOfRecord("John6"), 8);
        
        this.testTable.insert("John3", node); //at index 5
        assertEquals(this.testTable.findIndexOfRecord("John3"), 5);
        this.testTable.insert("Joh37", node); // at 5, collision
        assertFalse(this.testTable.findIndexOfRecord("John3") 
            == this.testTable.findIndexOfRecord("Joh37"));
        assertTrue(
            this.testTable.findIndexOfRecord("Joh37") == 6); // probe to 6
    }
    
    // ----------------------------------------------------------
    /**
     * Test for edge case resulting infinite loop
     */
    public void testInfiniteLoop() {
        this.testTable = new Hash(2);
        this.testTable.setMultiplier(1);
        this.testTable.insert("John2", node);
        this.testTable.remove("John2");
        this.testTable.insert("John3", node);
        assertFalse(this.testTable.find("John2"));
        assertFalse(this.testTable.find("John4"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test for resize calculation
     */
    public void testIndexCalculation() {
        for (int i = 0; i < 7; i++) {
            this.testTable.insert("test" + i, node);
        }
        assertEquals(20, this.testTable.getCapacity());
        
        int prev = 0;
        for (Record record : this.testTable.getHashTable()) {
            if (record != null) {
                int i = this.testTable.findIndexOfRecord(record.getKey());
                prev += i;
            }
        }
        assertEquals(73, prev);
        // remove
        for (int i = 0; i < 7; i++) {
            this.testTable.remove("test" + i);
        }
        // re-inset
        for (int i = 0; i < 7; i++) {
            this.testTable.insert("test" + i, node);
        }
        int curr = 0;
        for (Record record : this.testTable.getHashTable()) {
            if (record != null) {
                int i = this.testTable.findIndexOfRecord(record.getKey());
                curr += i;
            }
        }
        assertTrue(curr == prev);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for calculating load factor
     */
    public void testLoadFactor() {
        // empty table
        assertTrue(this.testTable.getSize() 
            < this.testTable.getCapacity() 
            * this.testTable.getLoadFactor());
        
        assertTrue((this.testTable.getSize() + 1) 
            < (this.testTable.getCapacity() 
                / this.testTable.getLoadFactor()));
        
        // before resizing
        for (int i = 0; i < 4; i++) {
            this.testTable.insert("test" + i, node);
        }
        
        assertFalse((this.testTable.getSize() + 1) 
            < (this.testTable.getCapacity() 
                / this.testTable.getLoadFactor()));
        
        this.testTable.insert("John", node);
        
        assertTrue((this.testTable.getSize() + 1) 
            > (this.testTable.getCapacity() 
                / this.testTable.getLoadFactor()));
        
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test for print method
     */
    public void testPrint() {
        this.testTable.insert("artist", node);
        String s1 = this.testTable.print("artist");
        String expected = "8: |artist|\ntotal artists: 1";
        assertEquals(expected, s1);
        this.testTable.insert("song", node);
        String s2 = this.testTable.print("song");
        expected = "8: |artist|\ntotal artists: 1\n"
            + "9: |song|\ntotal songs: 1\n";
        assertNotEquals(expected, s2);
        expected = "8: |artist|\n"
            + "9: |song|\ntotal songs: 2";
        assertEquals(expected, s2);
        String s3 = this.testTable.print("somethingelse");
        assertFalse(s3.contains("songs"));
        assertFalse(s3.contains("artists"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test for probing related calculation
     */
    public void testResizeQuadraticProbing() {
        // set multiplier to 3
        this.testTable.setMultiplier(3);
        this.testTable.insert("artist1", node);
        int indexBefore = this.testTable.findIndexOfRecord("artist1");
        for (int i = 2; i <= 6; i++) {
            this.testTable.insert("artist" + i, node);
        }
        
        assertEquals(this.testTable.getCapacity(), 30);
        
        int indexAfter = this.testTable.findIndexOfRecord("artist1");
        assertNotEquals(indexBefore, indexAfter);
    }
    
    // ----------------------------------------------------------
    /**
     * Test for the calculation of quadratic probing
     */
    public void testQuadraticCalculation() {
        assertEquals(8
            , this.testTable.quadraticCalculation(7, 9, 10));
        assertEquals(3
            , this.testTable.quadraticCalculation(-3, 4, 10)); 
        assertEquals(-4
            , this.testTable.quadraticCalculation(-5, 1, 10));
        assertEquals(1
            , this.testTable.quadraticCalculation(2, -3, 10));
        assertEquals(-1
            , this.testTable.quadraticCalculation(-5, -2, 10));
    }
    
    // ----------------------------------------------------------
    /**
     * Test find index to insert method
     */
    public void testFindIndexToInsert() {
        assertEquals(-1, this.testTable.findIndexToInsert(null));
        assertEquals(-1, this.testTable.findIndexToInsert(""));
        int index = Hash.h("test1", this.testTable.getCapacity());
        int newIndex = this.testTable.findIndexToInsert("test1");
        assertEquals(newIndex, index);
        assertEquals(newIndex, 7);
    }
    
    // ----------------------------------------------------------
    /**
     * Test to find the index of record
     */
    public void testFindIndexOfRecord() {
        this.testTable = new Hash(2);
        assertEquals(-1, this.testTable.findIndexToInsert(null));
        assertEquals(-1, this.testTable.findIndexToInsert(""));
        this.testTable.insert("John2", node);
        this.testTable.remove("John2");
        this.testTable.insert("John3", node);
        assertNotNull(this.testTable.findIndexOfRecord("John3"));
        assertEquals(1, this.testTable.findIndexOfRecord("John3"));
        assertEquals(-1, this.testTable.findIndexOfRecord("John2"));
        // same index as tomb stone
        assertEquals(-1, this.testTable.findIndexOfRecord("John4"));
        
        // set multiplier = 1 to pause resize
        this.testTable = new Hash(3);
        this.testTable.setMultiplier(1);
        this.testTable.insert("John1", node);
        this.testTable.insert("John2", node);
        this.testTable.insert("John3", node);
        // "John4" will never stop at index 2
        assertEquals(-1, this.testTable.findIndexOfRecord("John4"));
        assertEquals(1, this.testTable.findIndexOfRecord("John1"));
        assertEquals(2, this.testTable.findIndexOfRecord("John2"));
        assertEquals(0, this.testTable.findIndexOfRecord("John3"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test insert with normal case
     */
    public void testInsertNormal() {
        assertTrue(this.testTable.insert("test", node));
        assertFalse(this.testTable.insert("test", node));
    }
    
    // ----------------------------------------------------------
    /**
     * Test for mutation in insert
     */
    public void testInsertAlwaysTrue() {
        assertTrue(this.testTable.insert("test", node));
    }
    
    // ----------------------------------------------------------
    /**
     * Test find index of record with null key
     */
    public void testFindIndexOfRecordNull() {
        assertNotNull(this.testTable.findIndexOfRecord(null));
        assertEquals(this.testTable.findIndexOfRecord(null), -1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test find index of record with empty key
     */
    public void testFindIndexOfRecordEmpty() {
        assertNotNull(this.testTable.findIndexOfRecord(""));
        assertEquals(this.testTable.findIndexOfRecord(""), -1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test find index of record with normal key
     */
    public void testFindIndexOfRecordNormal() {
        this.testTable.insert("test", node);
        assertNotNull(this.testTable.findIndexOfRecord("test"));
        assertNotEquals(this.testTable.findIndexOfRecord("test"), -1);
    }
}
