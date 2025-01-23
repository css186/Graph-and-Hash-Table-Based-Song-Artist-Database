/**
 * Graph class
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
 * @version 2024.09.10
 */
public class Graph {
    // ~ Fields ............................................................
    //
    // ----------------------------------------------------------
    private DoublyLinkedList[] adjacencyList;
    private int numOfVertices;
    private DoublyLinkedList freeList; // To store remove node's index
    private DoublyLinkedList queue; // Queue for BFS
    private int[] rootArray; // Array for root nodes for Union-Find
    private int[] sizeArray; // Array for calculating connected components
    
    
    // ~ Constructors ......................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization of the following variables / data structures:
     * 1. numbers of vertexes
     * 2. an adjacent list with the length of numbers of vertexes
     * 3. a free list to store index using doubly linked list
     * 4. a queue to perform BFS iteratively
     * 5. an array to store the index of root graph nodes
     * 6. an array to store the size of connected components
     * 
     * @param initSize
     *        Initial size for adjacency list
     */
    public Graph(int initSize) {
        this.numOfVertices = 0;
        this.adjacencyList = new DoublyLinkedList[initSize];
        
        // Store index for deleted graph node
        this.freeList = new DoublyLinkedList();
        
        // For BFS
        this.queue = new DoublyLinkedList(); 
        
        // For Union-Find algorithm
        this.rootArray = new int[initSize];
        this.sizeArray = new int[initSize];
    }
    
    // ~ Default Method ...................................................
    //
    // ----------------------------------------------------------
    /**
     * Method to expand the adjacency list if size exceeds
     * capacity
     * 
     */
    void expandList() {
        int oldLength = this.adjacencyList.length;
        int newLength = oldLength * 2;
        DoublyLinkedList[] newList = new DoublyLinkedList[newLength];
        // Copy existing elements 
        for (int i = 0; i < oldLength; i++) {
            newList[i] = this.adjacencyList[i];
        }

        // Update reference
        this.adjacencyList = newList;
        
        // Resize the root array and size array
        this.rootArray = new int[newLength];
        this.sizeArray = new int[newLength];
    }
    
    // ----------------------------------------------------------
    /**
     * Method to perform BFS and count the number of connected
     * component
     * 
     * @param startNode
     *        Node to start in BFS
     * @param visited
     *        An array to mark whether a node is visited or not
     * @return
     *        Count of connected component
     */
    int bfs(GraphNode startNode, boolean[] visited) {
        int componentCount = 0;
        
        // Enqueue starting node
        this.queue.insertTail(startNode);
        visited[startNode.getIndex()] = true;
        
        // BFS
        while (!this.queue.isEmpty()) {
            GraphNode node = this.queue.removeHead().getGraphNode();
            
            // increment connected component count
            componentCount++;
            
            // Find adjacency list
            DoublyLinkedList adjList = 
                this.adjacencyList[node.getIndex()];
            
            if (adjList != null) {
                // Assign curr as the next node from head of the adjacency list
                DoublyLinkedList.ListNode curr = adjList.getHead().getNext();
                
                while (curr != null) {
                    GraphNode neighbor = curr.getGraphNode();
                    
                    if (neighbor != null && !visited[neighbor.getIndex()]) {
                        // If the node is not null and has not been visited,
                        // enqueue the queue
                        this.queue.insertTail(neighbor);
                        
                        // Update to visited
                        visited[neighbor.getIndex()] = true;
                    }
                    // Move the next
                    curr = curr.getNext();
                }
            }

        }
        return componentCount;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find the root of components that contains 
     * node at index i
     * @param i
     *        The index of node to find root for
     * @return
     *        The index of the root
     */
    int root(int i) {
        while (i != this.rootArray[i]) {
            // Root path compression to flatten the tree
            // Can improve algorithm performance
            this.rootArray[i] = this.rootArray[this.rootArray[i]];
            i = this.rootArray[i];
        }
        return i;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to unite two node with index p and q
     * @param p
     *        Index of the first graph node
     * @param q
     *        Index of the second graph node
     * @return
     *        Return true if successfully united two graph nodes
     *        Return false if the two nodes are already connected
     */
    boolean union(int p, int q) {
        int rootOfP = this.root(p);
        int rootOfQ = this.root(q);
        
        if (rootOfP == rootOfQ) {
            return false; // already connected
        }
        
        // Union by size
        if (this.sizeArray[rootOfP] > this.sizeArray[rootOfQ]) {
            this.rootArray[rootOfQ] = rootOfP;
            this.sizeArray[rootOfP] += this.sizeArray[rootOfQ];
        }
        else {
            this.rootArray[rootOfP] = rootOfQ;
            this.sizeArray[rootOfQ] += this.sizeArray[rootOfP];
        }
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the size of connected component at index p
     * @param p
     *        The index of node
     * @return
     *        The size of connected component
     */
    int getSize(int p) {
        return this.sizeArray[this.root(p)];
    }
    
    // ----------------------------------------------------------
    /**
     * Method to initialize the arrays for union-find algorithms
     */
    void initUnionFind() {
        // Initialize roots and check size of each set of nodes
        for (int i = 0; i < this.adjacencyList.length; i++) {
            this.rootArray[i] = i;
            
            // if adjacency list is null, set size as 0
            if (this.adjacencyList[i] == null) {
                this.sizeArray[i] = 0;
            }
            else {
                this.sizeArray[i] = 1;
            }
        }
    }
     
    // ~ Public Method ....................................................  
    //
    // ----------------------------------------------------------
    /**
     * Method to add a graph node into adjacency list
     * @return
     *         Return the added graph node
     */
    public GraphNode addNode() {
        // Check if adding a new vertex would expand the adjacency list
        if ((this.numOfVertices + 1) >= this.adjacencyList.length) {
            this.expandList();
        }
        
        int index;
        // Check if free list is empty
        // If not empty, there is a space to add
        if (!this.freeList.isEmpty()) {
            DoublyLinkedList.ListNode lastNode = this.freeList.removeTail();
            index = lastNode.getGraphNode().getIndex();
        }
        else {
            index = this.numOfVertices;
            
        }
        // Use the index to create a new graph node
        GraphNode newNode = new GraphNode(index);
        this.adjacencyList[index] = new DoublyLinkedList(newNode);
        this.numOfVertices++;
        
        return newNode;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to check if a graph node exists in graph
     * @param node
     *        Graph node to be checked existence;      
     * @return
     *        True if graph node exists
     */
    public boolean containNode(GraphNode node) {
        // Get remove node's index
        int index = node.getIndex(); 
        // Check index out of bounds cases
        if (index >= this.numOfVertices 
            || index < 0) {
            return false;
        }
        return (this.adjacencyList[index] != null);
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove designated graph node
     * @param node
     *        Graph node to be removed
     * @return
     *        Return true if node is removed, else false
     */
    public boolean removeNode(GraphNode node) {
        // Get remove node's index
        int index = node.getIndex();
        // Check if node exists
        if (!this.containNode(node)) {
            return false;
        }
        
        for (int i = 0; i < this.adjacencyList.length; i++) {
            if (this.adjacencyList[i] != null) {
                this.adjacencyList[i].removeListNode(node);
            }
        }
        // Remove the corresponding index on the adjacency list
        this.adjacencyList[index] = null; 
        
        // Store the index of removed node onto the free list
        this.freeList.insertTail(new GraphNode(index));
        this.numOfVertices--;
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to determine whether an edge exists between two nodes
     * @param u
     *        The first graph node
     * @param v
     *        The second graph node
     * @return
     *        Return true if two nodes are connected
     */
    public boolean hasEdge(GraphNode u, GraphNode v) {
        return (this.adjacencyList[u.getIndex()].containListNode(v)
            && this.adjacencyList[v.getIndex()].containListNode(u));
    }
        
    // ----------------------------------------------------------
    /**
     * Method to add an edge between two nodes
     * @param u
     *        The first graph node
     * @param v
     *        The second graph node
     * @return
     *        Return true after successfully add an edge
     *        Return false if edge already exists
     */
    public boolean addEdge(GraphNode u, GraphNode v) {
        // Check if edge exists
        if (this.hasEdge(u, v)) {
            return false;
        }
        this.adjacencyList[u.getIndex()].insertTail(v);
        this.adjacencyList[v.getIndex()].insertTail(u);
        return true;
    }
   
    // ----------------------------------------------------------
    /**
     * Method to remove an edge between two nodes
     * @param u
     *        The first graph node
     * @param v
     *        The second graph node
     * @return
     *        Return true if successfully remove an edge
     */
    public boolean removeEdge(GraphNode u, GraphNode v) {
        // Check if edge exists
        if (!this.hasEdge(u, v)) {
            return false;
        }
        this.adjacencyList[u.getIndex()].removeListNode(v);
        this.adjacencyList[v.getIndex()].removeListNode(u);
        return true;
    }
        
    // ----------------------------------------------------------
    /**
     * Method to print the following information of the graph
     * using BFS algorithm
     * 
     * 1. number of connected component
     * 2. largest connected component
     * 
     * @return
     *         Output string
     */
    public String printWithBFS() {
        String output = "";
        boolean[] visited = new boolean[this.adjacencyList.length];
        int numOfConnected = 0;
        int largestConnected = 0;
        
        for (int i = 0; i < this.adjacencyList.length; i++) {
            
            if (this.adjacencyList[i] != null && !visited[i]) {
                GraphNode start = this.adjacencyList[i].getHead()
                    .getGraphNode();
                
                // Get th number of connected component with BFS
                int connNum = this.bfs(start, visited);
                numOfConnected++;
                largestConnected = Math.max(largestConnected, connNum);
            }
            
        }
        output += 
            String.format("There are %d connected components\n"
                , numOfConnected); 
        output += 
            String.format("The largest connected component has %d elements"
                , largestConnected);
        return output;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to print the following information of the graph
     * using Union-Find algorithm
     * 
     * 1. number of connected component
     * 2. largest connected component
     * 
     * @return
     *         output string
     */
    public String printWithUnionFind() {
        String output = "";
        int numOfConnected = 0;
        int largestConnected = 0;
        
        // Initialize two arrays for union-find
        this.initUnionFind();
        
        // Set neighbors as union 
        for (int i = 0; i < this.adjacencyList.length; i++) {
            // Iterate the adjacency list and connect neighbors
            DoublyLinkedList adjList = this.adjacencyList[i];
            
            if (adjList != null) {
                DoublyLinkedList.ListNode curr = adjList.getHead().getNext();
                
                while (curr != null) {
                    GraphNode neighbor = curr.getGraphNode();
                    
                    if (neighbor != null) {
                        this.union(i, neighbor.getIndex());
                    }               
                    curr = curr.getNext();
                }
            }
        }
        
        // Count connected components
        for (int i = 0; i < this.adjacencyList.length; i++) {
            if (this.adjacencyList[i] != null) {
                int root = this.root(i);
                if (i == root) {
                    numOfConnected++;
                    largestConnected = 
                        Math.max(largestConnected, this.getSize(i));
                }
            }
        }
        
        output += 
            String.format("There are %d connected components\n"
                , numOfConnected); 
        output += 
            String.format("The largest connected component has %d elements"
                , largestConnected);
        return output;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the adjacency list
     * @return the adjacencyList
     *         Return current adjacency list
     */
    public DoublyLinkedList[] getAdjacencyList() {
        return this.adjacencyList;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the adjacency list
     * @param adjacencyList 
     *        The adjacencyList to set
     */
    public void setAdjacencyList(DoublyLinkedList[] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the number of vertices
     * @return The numOfVertices
     */
    public int getNumOfVertices() {
        return numOfVertices;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the number of vertices
     * @param numOfVertices 
     *        The numOfVertices to set
     */
    public void setNumOfVertices(int numOfVertices) {
        this.numOfVertices = numOfVertices;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the free list to store index of removed node
     * @return The freeList
     */
    public DoublyLinkedList getFreeList() {
        return freeList;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the free list to store index of removed node
     * @param freeList 
     *        The freeList to set
     */
    public void setFreeList(DoublyLinkedList freeList) {
        this.freeList = freeList;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the queue to perform BFS
     * @return The queue
     */
    public DoublyLinkedList getQueue() {
        return queue;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the queue to perform BFS
     * @param queue 
     *        The queue to set
     */
    public void setQueue(DoublyLinkedList queue) {
        this.queue = queue;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for the root array for union-find
     * @return the rootArray
     *         The root array of union-find
     */
    public int[] getRootArray() {
        return rootArray;
    }

    // ----------------------------------------------------------
    /**
     * Setter for the root array for union-find
     * @param rootArray the rootArray to set
     *        The array to be set
     */
    public void setRootArray(int[] rootArray) {
        this.rootArray = rootArray;
    }
    
    // ----------------------------------------------------------
    /**
     * Getter for getting the size array for union-find
     * @return the sizeArray
     *         This size array for union-find
     */
    public int[] getSizeArray() {
        return sizeArray;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for setting the size array for union-find
     * @param sizeArray the sizeArray to set
     *        The size  array to be set
     */
    public void setSizeArray(int[] sizeArray) {
        this.sizeArray = sizeArray;
    }
    
    
}
