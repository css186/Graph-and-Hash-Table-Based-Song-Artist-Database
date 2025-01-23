import student.TestCase;

/**
 * A test file for all the method implemented in Graph.class
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.10
 */
public class GraphTest extends TestCase {
    // ~ Fields .............................................................
    private Graph testGraph;
    private int length;
    private int[] testRoots;
    private int[] testSize;
    
    // ~ Set Up..............................................................
    //
    // ----------------------------------------------------------
    /**
     * Set up the test
     */
    public void setUp() {
        this.testGraph = new Graph(10);
        length = this.testGraph.getAdjacencyList().length;
        
        this.testRoots = new int[length];
        this.testSize = new int[length];
        
        for (int i = 0; i < length; i++) {
            this.testRoots[i] = i;
            this.testSize[i] = 1;
        }      
        this.testGraph.setRootArray(testRoots);
        this.testGraph.setSizeArray(testSize);
    }
    
    // ~ Test Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Test constructor initialization
     */
    public void testInit() {
        assertEquals(0, this.testGraph.getNumOfVertices());
        assertEquals(10, this.testGraph.getAdjacencyList().length);
        assertEquals(0, this.testGraph.getFreeList().getSize());
        assertEquals(0, this.testGraph.getQueue().getSize());
    }
    
    // ----------------------------------------------------------
    /**
     * Test add node
     */
    public void testAddNode() {
        GraphNode node1 = this.testGraph.addNode();
        assertNotNull(node1);
        assertEquals(node1.getIndex(), 0);
        
        GraphNode node2 = this.testGraph.addNode();
        assertNotNull(node2);
        assertEquals(node2.getIndex(), 1);
        
        // expand
        for (int i = 2; i < 10; i++) {
            this.testGraph.addNode();
        }
        assertEquals(this.testGraph.getNumOfVertices(), 10);
        assertEquals(this.testGraph.getAdjacencyList().length, 20);
    }
    
    // ----------------------------------------------------------
    /**
     * Test contains node
     */
    public void testContainNode() {
        GraphNode node = this.testGraph.addNode();
        assertTrue(this.testGraph.containNode(node));
        
        GraphNode otherNode = new GraphNode(1);
        assertFalse(this.testGraph.containNode(otherNode));
    }
    
    // ----------------------------------------------------------
    /**
     * Test contains node with index out of bounds
     */
    public void testContainExceedSize() {
        GraphNode node = this.testGraph.addNode();
        assertTrue(this.testGraph.containNode(node));
        GraphNode node1 = this.testGraph.addNode();
        node1.setIndex(100);
        assertFalse(this.testGraph.containNode(node1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test contains node with index out of bounds
     */
    public void testContainNegative() {
        GraphNode node = this.testGraph.addNode();
        assertTrue(this.testGraph.containNode(node));
        GraphNode node1 = this.testGraph.addNode();
        node1.setIndex(-100);
        assertFalse(this.testGraph.containNode(node1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test add edge between two nodes
     */
    public void testAddEdge() {
        GraphNode node1 = this.testGraph.addNode();
        GraphNode node2 = this.testGraph.addNode();
        assertTrue(this.testGraph.addEdge(node1, node2));
        assertTrue(this.testGraph.hasEdge(node1, node2));
        // duplicate edge
        assertFalse(this.testGraph.addEdge(node1, node2));
       
    }
    
    // ----------------------------------------------------------
    /**
     * Test remove edge between two nodes
     */
    public void testRemoveEdge() {
        GraphNode node1 = this.testGraph.addNode();
        GraphNode node2 = this.testGraph.addNode();
        this.testGraph.addEdge(node1, node2);
        assertTrue(this.testGraph.hasEdge(node1, node2));
        assertTrue(this.testGraph.removeEdge(node1, node2));
        assertFalse(this.testGraph.hasEdge(node1, node2));
        // cannot remove edge that has been removed
        assertFalse(this.testGraph.removeEdge(node1, node2));
    }
    
    // ----------------------------------------------------------
    /**
     * Test remove node 
     */
    public void testRemoveNode() {
        GraphNode node1 = this.testGraph.addNode();
        this.testGraph.removeNode(node1);
        assertTrue(this.testGraph.getFreeList().getSize() == 1);
        
        assertEquals(this.testGraph.getFreeList()
            .getTail().getGraphNode().getIndex(), 0);
        
        assertNull(this.testGraph
            .getAdjacencyList()[node1.getIndex()]);
        
        assertEquals(this.testGraph.getNumOfVertices(), 0);
        
        // add another node
        GraphNode node2 = this.testGraph.addNode();
        // index should be 0
        assertEquals(this.testGraph.getAdjacencyList()[0]
            .getHead().getGraphNode(), node2);
        // remove last node from free list
        assertTrue(this.testGraph.getFreeList().getSize() == 0);
        
        // remove the same node twice
        GraphNode node3 = this.testGraph.addNode();
        assertTrue(this.testGraph.removeNode(node3));
        int before = this.testGraph.getNumOfVertices();
        assertFalse(this.testGraph.removeNode(node3));
        int after = this.testGraph.getNumOfVertices();
        assertEquals(before, after);
        
    }
    
    
    // ----------------------------------------------------------
    /**
     * Test BFS and print
     */
    public void testPrintWithBFS() {
        String output = this.testGraph.printWithBFS();
        assertEquals(output
            , "There are 0 connected components\n"
            + "The largest connected component has 0 elements");
        
        GraphNode node1 = this.testGraph.addNode();
        GraphNode node2 = this.testGraph.addNode();
        output = this.testGraph.printWithBFS();
        assertEquals(output
            , "There are 2 connected components\n"
            + "The largest connected component has 1 elements");
        
        this.testGraph.addEdge(node1, node2);
        this.testGraph.addNode();
        output = this.testGraph.printWithBFS();
        assertEquals(output
            , "There are 2 connected components\n"
            + "The largest connected component has 2 elements");
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test Union-Find and print
     */
    public void testPrintWithUnionFind() {
        String output = this.testGraph.printWithUnionFind();
        assertEquals(output
            , "There are 0 connected components\n"
            + "The largest connected component has 0 elements");
        
        GraphNode node1 = this.testGraph.addNode();
        GraphNode node2 = this.testGraph.addNode();
        output = this.testGraph.printWithUnionFind();
        assertEquals(output
            , "There are 2 connected components\n"
            + "The largest connected component has 1 elements");
        
        this.testGraph.addEdge(node1, node2);
        this.testGraph.addNode();
        output = this.testGraph.printWithUnionFind();
        assertEquals(output
            , "There are 2 connected components\n"
            + "The largest connected component has 2 elements");
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test single graph node in union-find
     */
    public void testRootSingleNode() {
        assertEquals(this.testGraph.root(0), 0);
    }
    
    // ----------------------------------------------------------
    /**
     * Test root after union
     */
    public void testAfterUnion() {
        this.testGraph.union(0,  1);
        assertEquals(this.testGraph.root(0), this.testGraph.root(1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test success union
     */
    public void testUnionTrue() {
        assertTrue(this.testGraph.union(0, 1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test failed union
     */
    public void testUnionFalse() {
        this.testGraph.union(0,  1);
        assertFalse(this.testGraph.union(0,  1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test getSize method before union
     */
    public void testSizeBeforeUnion() {
        assertEquals(this.testGraph.getSize(0), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test getSize method after union
     */
    public void testSizeAfterUnion() {
        this.testGraph.union(0, 1);
        assertEquals(this.testGraph.getSize(0), 2);
        this.testGraph.union(0, 2);
        assertEquals(this.testGraph.getSize(0), 3);
    }
    
    // ----------------------------------------------------------
    /**
     * Test union from smaller set of nodes to larger ones
     */
    public void testUnionToLargerSize() {
        this.testGraph.union(0, 1);
        assertEquals(this.testGraph.getSize(0), 2);
        this.testGraph.union(2, 3);
        this.testGraph.union(2, 4);
        assertEquals(this.testGraph.getSize(2), 3);
        // union smaller to larger
        this.testGraph.union(0, 2);
        assertEquals(this.testGraph.root(2), this.testGraph.root(0));
        assertEquals(this.testGraph.getSize(2), 5);
        assertEquals(this.testGraph.getRootArray()[this.testGraph.root(0)]
            , this.testGraph.root(2));
    }
    
    // ----------------------------------------------------------
    /**
     * Test union from larger set of nodes to smaller ones
     */
    public void testUnionToSmallerSize() {
        this.testGraph.union(0, 1);
        this.testGraph.union(0, 2);
        assertEquals(this.testGraph.getSize(0), 3);
        this.testGraph.union(3, 4);
        assertEquals(this.testGraph.getSize(3), 2);
        // union larger to smaller
        this.testGraph.union(0, 3);
        assertEquals(this.testGraph.root(0), this.testGraph.root(3));
        assertEquals(this.testGraph.getSize(0), 5);
        assertEquals(this.testGraph.getRootArray()[this.testGraph.root(3)]
            , this.testGraph.root(0));
    }
    
    // ----------------------------------------------------------
    /**
     * Test union between equal size
     */
    public void testUnionEqualSize() {
        this.testGraph.union(0, 1);
        assertEquals(this.testGraph.getSize(0), 2);
        this.testGraph.union(2, 3);
        assertEquals(this.testGraph.getSize(2), 2);
        // union equals
        this.testGraph.union(0, 2);
        assertEquals(this.testGraph.root(0), this.testGraph.root(2));
        assertEquals(this.testGraph.getSize(0), 4);
        assertEquals(this.testGraph.getRootArray()[this.testGraph.root(2)]
            , this.testGraph.root(0));
    }
    
    
}
