/**
 * A implementation of a hash table using array
 * 
 * Note1: some methods were set as default in order to
 * perform tests from test class easily
 * 
 * Note2: 
 * String output should concatenated with StringBuilder or 
 * StringBuffer for using less heap memory. However, I
 * asked for permission on piazza but did not receive
 * permission or positive answer, so I used concatenation
 * instead.
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.12
 */

public class Hash {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private int size;
    private int capacity;
    private Record[] hashTable;
    private Record tombstone = new Record("TOMBSTONE", null);
    private int loadFactor;
    private int multiplier;
    
    // ~ Constructors ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialize the array of size capacity
     * capacity could be passed at instantiation
     * @param capacity
     *        Initial length for array
     */
    public Hash(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.hashTable = new Record[capacity];
        this.loadFactor = 2;
        this.multiplier = 2;
        
    }
    
    // ~ Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * A private function to deal with resizing the table
     * Resize multiplier is set to 2
     */
    void resizeTable() {
        int newCapacity = this.capacity * this.multiplier;
        Record [] newTable = new Record[newCapacity];
        
        for (Record record : this.hashTable) {
            if (record != null && !record.equals(this.tombstone)) {
                int index = Hash.h(record.getKey(), newCapacity);
                int i = 1;
                int newIndex = index;
                while (newTable[newIndex] != null) {
                    newIndex = this.quadraticCalculation(index, i, newCapacity);
                    i++;
                }
                newTable[newIndex] = record;
            }
        }
        // Update hash table to the new one
        this.hashTable = newTable;
        this.capacity = newCapacity;
    }
    
    // ~ Public Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Method to insert records into the hash table
     * @param key
     *        The key of the data needed to be inserted
     * @param node
     *        The graph node of the data
     * @return
     *        Return true if insert successfully
     */
    public boolean insert(String key, GraphNode node) {
        // Check if key already exist in the table
        if (find(key)) {
            return false;
        }
        // Check if size exceed current capacity
        if ((this.size + 1) > (this.capacity / this.loadFactor)) {
            this.resizeTable();
        }
        
        // Find index of slot available for insertion
        int index = this.findIndexToInsert(key);
        if (index == -1) {
            return false;
        }
        this.hashTable[index] = new Record(key, node);
        this.size++;
        
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove record from the table
     * @param key
     *        The key of the data needed to be removed
     * @return
     *        Return the removed data
     */         
    public Record remove(String key) {
        // Empty table case
        if (this.size <= 0) {
            return null;
        }
        // Check if key already exist in the table
        if (!find(key)) {
            return null;
        }
        // Get index
        int index = this.findIndexOfRecord(key);
        if (index == -1) {
            return null;
        }
        
        Record removed = this.hashTable[index];
        this.hashTable[index] = this.tombstone;
        this.size--;
        return removed;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to visualize the whole hash table in console output
     * 
     * @param type
     *        Different hash table, in this project is either artists or songs
     * @return
     *        Hash table represented in string
     */ 
    public String print(String type) {
        
        String outputString = "";

        for (int i = 0; i < this.hashTable.length; i++) {            
            if (this.hashTable[i] != null) {
                outputString += i + ": ";
                
                if (this.tombstone.equals(this.hashTable[i])) {
                    outputString += this.hashTable[i].getKey() + "\n";
                }
                else {
                    outputString += "|" + this.hashTable[i].getKey() + "|\n";
                }
            }
        }

        if ("artist".equals(type)) {
            outputString += "total artists: " + this.size;
        }
        else if ("song".equals(type)) {
            outputString += "total songs: " + this.size;
        }
        
        return outputString;

    }
    
    // ----------------------------------------------------------
    /**
     * Method to find designated record in the hash table
     * 
     * @param key
     *        The key of the data
     * @return
     *        Return the data if found, else return null
     */  
    public boolean find(String key) {
        if (key == null || key.isEmpty()) {
            return false;
        }
        // get hash code
        int index = Hash.h(key, this.capacity);
        int i = 1;
        int newIndex = index;
        while (this.hashTable[newIndex] != null) {
            if (key.equals(this.hashTable[newIndex].getKey())) {
                return true;
            }
            if (i == this.capacity) {
                return false;
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
        }
        return false;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find the index of given record by key
     * @param key
     *        The key of the data
     * @return
     *        Return the index of record if found
     *        Return -1 if else
     */
    public int findIndexOfRecord(String key) {
        if (key == null || key.isEmpty()) {
            return -1;
        }
        int index = Hash.h(key, this.capacity);
        int i = 1;
        int newIndex = index;
        while (this.hashTable[newIndex] != null) {
            if (key.equals(this.hashTable[newIndex].getKey()) 
                && !this.tombstone.equals(this.hashTable[newIndex])) {
                return newIndex;
            }
            if (i == this.capacity) {
                return -1;
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
        }
        return -1;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find the index of either empty slot 
     * or tomb stone slot
     * 
     * @param key
     *        The key of the data
     * @return
     *        Return the index of empty slot or tomb stone
     *        else return -1
     */
    public int findIndexToInsert(String key) {
        if (key == null || key.isEmpty()) {
            return -1;
        }
        // Get hash code
        int index = Hash.h(key, this.capacity);
        int i = 1;
        int newIndex = index;
        while (this.hashTable[newIndex] != null) {
            if (this.tombstone.equals(this.hashTable[newIndex])) {
                break;
            }
            // Prevent infinite loop
            if (i == this.capacity) {
                return -1;
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
        }
        return newIndex;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to compute index based on quadratic
     * @param index
     *        Hash code of input key
     * @param step
     *        Step to move to the next slot
     * @param length
     *        Length of the hash table
     * @return
     *        New hash code
     */
    public int quadraticCalculation(int index, int step, int length) {
        return (index + step * step) % length;
    }
    
    // ----------------------------------------------------------
    /**
     * Compute the hash function
     * 
     * @param s
     *        The string that we are hashing
     * @param length
     *        Length of the hash table (needed because this method is
     *        static)
     * @return
     *        The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for number of elements inside the hash table 
     * @return
     *         number of elements inside the hash table
     */
    public int getSize() {
        return this.size;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for setting the number of elements
     * @param size
     *        Number of elements
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting current capacity of table
     * @return
     *         Capacity of the table
     */
    public int getCapacity() {
        return this.capacity;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for setting current capacity of table
     * @param capacity
     *        Capacity of the table to be set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting hash table object
     * @return
     *         Array of Record objects
     */
    public Record[] getHashTable() {
        return this.hashTable;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for setting the hash table
     * @param hashTable 
     *        The hashTable to set
     */
    public void setHashTable(Record[] hashTable) {
        this.hashTable = hashTable;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting tomb stone object (to mark deleted)
     * @return
     *         Tomb stone
     */
    public Record getTombstone() {
        return this.tombstone;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the tomb stone
     * @param tombstone 
     *        The tombstone to set
     */
    public void setTombstone(Record tombstone) {
        this.tombstone = tombstone;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for load factor
     * @return
     *         Current load factor of the table
     */
    public int getLoadFactor() {
        return this.loadFactor;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the load factor of the table
     * @param loadFactor
     *        LoadFactor of the table to set
     */
    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the length multiplier of the table
     * @return
     *         Current length multiplier of the table
     */
    public int getMultiplier() {
        return this.multiplier;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the length multiplier of the table
     * @param multiplier
     *        Multiplier of the table
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
    
}
