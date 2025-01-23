/**
 * A implementation of doubly linked list
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.03
 */

public class DoublyLinkedList {

    // ~ Inner Classes .................................................
    //
    // ----------------------------------------------------------
    /**
     * List Node class
     */
    public class ListNode {
        // ~ Fields ....................................................
        //
        // ----------------------------------------------------------
        private GraphNode graphNode;
        private ListNode prev;
        private ListNode next;
        
        // ~ Constructors ..............................................
        //
        // ----------------------------------------------------------
        /**
         * Initialize the ListNode with GraphNode as data 
         * and references of previous / next ListNode
         * @param node
         *        Graph node object containing index
         */
        public ListNode(GraphNode node) {
            this.graphNode = node;
            this.prev = null;
            this.next = null;
        }

        // ~ Public Method .............................................
        //
        // ----------------------------------------------------------
        /**
         * Getter to obtain graph node object
         * @return
         *         Graph node object
         */
        public GraphNode getGraphNode() {
            return this.graphNode;
        }
        
        // ----------------------------------------------------------
        /**
         * Getter to get the reference of previous list node
         * @return
         *         Reference of previous list node
         */
        public ListNode getPrev() {
            return this.prev;
        }
        
        // ----------------------------------------------------------
        /**
         * Setter to set the reference to the previous list node
         * @param prev
         *        List node to be set to the previous position
         */
        public void setPrev(ListNode prev) {
            this.prev = prev;
        }
        
        // ----------------------------------------------------------
        /**
         * Getter to get the reference of next list node
         * @return
         *         Reference of next list node
         */
        public ListNode getNext() {
            return this.next;
        }
        
        // ----------------------------------------------------------
        /**
         * Setter to set the reference to the next list node
         * @param next
         *        List node to be set to the next position
         */
        public void setNext(ListNode next) {
            this.next = next;
        }

    }
    
    // ~ Fields ..........................................................
    //
    // ----------------------------------------------------------
    private ListNode head;
    private ListNode tail;
    private int size;
    
    // ~ Constructors ....................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialize a doubly linked list
     * set head and tail as null
     * set size as 0
     * 
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    // ----------------------------------------------------------
    /**
     * Another initialization of doubly linked list
     * (initialized with graph node)
     * @param node
     *        A graph node to be insert into
     */
    public DoublyLinkedList(GraphNode node) {
        ListNode newNode = new ListNode(node);
        this.head = newNode;
        this.tail = newNode;
        this.size++;
    }
    
    // ~ Public Method ....................................................
    //
    // ----------------------------------------------------------
    /**
     * Method to know the list is empty or not
     * @return
     *         True if the length of the linked list is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    // ----------------------------------------------------------
    /**
     * Method to insert node at the tail of the list
     * @param node
     *        Graph node object to be inserted
     * @return
     *         Return true if successfully inserted
     */
    public boolean insertTail(GraphNode node) {
        
        ListNode newNode = new ListNode(node);
        
        // Empty case
        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        }
        
        else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        
        this.size++;
        return true;
        
    }
    
    // ----------------------------------------------------------
    /**
     * Method to insert node at the beginning of the list
     * @param node
     *        Graph node object to be inserted
     * @return 
     *         Return true if successfully inserted
     */

    public boolean insertHead(GraphNode node) {
        
        ListNode newNode = new ListNode(node);
        
        // Empty case
        if (this.isEmpty()) {
            this.head = newNode;
            this.tail = newNode; 
        }
        
        else {
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = newNode;
        }
        
        this.size++;
        return true;
        
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove the last node in list
     * @return
     *         ListNode removed
     */
    public ListNode removeTail() {
        // Empty case
        if (this.isEmpty()) {
            return null;
        }
        
        ListNode oldTail = this.tail;
        
        // One node case
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        }
        
        else {
            this.tail = this.tail.prev;
            this.tail.next = null;
        }
        
        this.size--;
        return oldTail;
        
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove the first node in list
     * 
     * @return
     *         ListNode removed
     */
    public ListNode removeHead() {
        // Empty case
        if (this.isEmpty()) {
            return null;
        }
        
        ListNode oldHead = this.head;
        
        // One node case
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        }
        
        else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        
        this.size--;
        return oldHead;
  
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find specific graph node in the linked list
     * @param node
     *        The graph node to be found
     * @return
     *        Return true if found, else false
     */
    public boolean containListNode(GraphNode node) {
        ListNode current = this.head;
        
        while (current != null) {
            if (node.equals(current.getGraphNode())) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove specific graph node from the linked list
     * @param node
     *        The graph node to be removed
     * @return
     *         The removed graph node
     */
    public ListNode removeListNode(GraphNode node) {
        // Check empty or null
        if (this.isEmpty() || node == null) {
            return null;
        }
        
        ListNode current = this.head;
        while (current != null) {
            if (node.equals(current.getGraphNode())) {
                // Head case
                if (current == this.head) {
                    return this.removeHead();
                }
                // Tail case
                else if (current == this.tail) {
                    return this.removeTail();
                }
                else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    this.size--;
                    return current;
                }
            }
            current = current.next;
        }
        return null;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Method to find the ListNode at index "index"
     * @param index
     *           location to look up in the list
     * @return
     *         LintNode
     */
    public ListNode findNodeAt(int index) {
        // Check if the index is out of bounds
        if (index < 0 || index >= this.size) {
            return null;
        }
      
        ListNode current = this.head;
        
        // Move reference for index times
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current;
    }
    
    // ----------------------------------------------------------
    /**
     * Print out the list sequentially in console
     * @return
     *         String representation of the list
     */
    public String displayList() {
        ListNode current = this.head;
        String output = "";
        while (current != null) {
            output += current.getGraphNode().getIndex();
            current = current.next;
        }
        return output;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the first node;
     * @return
     *         The first list node of the linked list
     */
    public ListNode getHead() {
        return this.head;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to set the first node
     * @param head
     *        ListNode to be set to head
     */
    public void setHead(ListNode head) {
        this.head = head;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the last node
     * @return
     *         The last list node of the linked list
     */   
    public ListNode getTail() {
        return this.tail;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to set the last node
     * @param tail
     *        ListNode to be set to tail
     */
    public void setTail(ListNode tail) {
        this.tail = tail;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the numbers of list nodes in list
     * @return
     *         Number of list nodes in the list
     */
    public int getSize() {
        return this.size;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to set new size for the list
     * @param size
     *        Number of list nodes to be set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the next node
     * @param node
     *        Current list node
     * @return
     *         The next list node
     */
    public ListNode getNextNode(ListNode node) {
        return node.next;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to get the previous node
     * @param node
     *        Current list node
     * @return
     *         The previous list node
     */
    public ListNode getPrevNode(ListNode node) {
        return node.prev;
    }

}
