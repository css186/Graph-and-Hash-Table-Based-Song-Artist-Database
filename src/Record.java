/**
 * Record class
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.06
 */
public class Record {
    // ~ Fields ....................................................
    //
    // ----------------------------------------------------------
    private String key;
    private GraphNode value;
    
    // ~ Constructors ..............................................
    //
    // ----------------------------------------------------------
    /**
     * Initiate a Record object using key and graph node
     * as arguments
     * 
     * @param key
     *        The key of data
     * @param value
     *        Graph node
     */
    public Record(String key, GraphNode value) {
        this.key = key;
        this.value = value;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter to get key
     * @return
     *        Key of the data
     */
    public String getKey() {
        return key;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for key
     * @param key
     *        Key of the data
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter to the value
     * @return
     *        Value of the data
     */
    public GraphNode getValue() {
        return value;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the value
     * @param value
     *        Value of the data
     */
    public void setValue(GraphNode value) {
        this.value = value;
    }
    
}
