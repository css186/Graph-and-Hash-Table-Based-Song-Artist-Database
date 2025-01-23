/**
 * Graph node class
 *
 * @author Guann-Luen Chen
 * @version 2024.09.06
 */

public class GraphNode {
    
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private int index;
    
    // ~ Constructors ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization for graph node
     */
    public GraphNode() {
        
    }
    
    // ----------------------------------------------------------
    /**
     * Another initialization for graph node
     * @param index
     *        Index in graph
     */
    public GraphNode(int index) {
        this.index = index;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for index
     * @return
     *         Index in graph
     */
    public int getIndex() {
        return this.index;
    }

    // ----------------------------------------------------------
    /**
     * Setter for index
     * @param index
     *        Index in graph
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
