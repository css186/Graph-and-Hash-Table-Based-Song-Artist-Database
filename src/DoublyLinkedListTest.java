import student.TestCase;

/**
 * Test cases for doubly linked list
 * 
 * @author Guann-Luen Chen
 * @version 2024.09.10
 */
public class DoublyLinkedListTest extends TestCase {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private DoublyLinkedList dList;
    private GraphNode node1;
    private GraphNode node2;
    private GraphNode node3;
    
    // ~ Set Up ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        this.dList = new DoublyLinkedList();
        this.node1 = new GraphNode(1);
        this.node2 = new GraphNode(2);
        this.node3 = new GraphNode(3);
    }
    
    // ~ Test Method .........................................................
    //
    // ----------------------------------------------------------
    /**
     * Test method with no arguments
     */
    public void testInitNoArgs() {
        assertTrue(this.dList.getSize() == 0);
        assertNull(this.dList.getTail());
        assertNull(this.dList.getHead());
    }
    
    // ----------------------------------------------------------
    /**
     * Test method with arguments
     */
    public void testInitWithArgs() {
        GraphNode node = new GraphNode(0);
        this.dList = new DoublyLinkedList(node);
        assertFalse(this.dList.getSize() == 0);
        assertTrue(this.dList.getSize() == 1);
        assertNotNull(this.dList.getTail());
        assertEquals(this.dList.getTail().getGraphNode(), node);
        assertNotNull(this.dList.getHead());
        assertEquals(this.dList.getHead().getGraphNode(), node);
        this.dList.insertHead(node1);
        assertFalse(this.dList.getSize() == 1);
        assertTrue(this.dList.getSize() == 2);
        
        DoublyLinkedList dList1 = new DoublyLinkedList(this.node1);
        DoublyLinkedList dList2 = new DoublyLinkedList(this.node2);
        assertEquals(dList1.getSize(), dList2.getSize());
    }
    
    // ----------------------------------------------------------
    /**
     * Another test method with arguments
     */
    public void testInitWithArgs2() {
        this.dList = new DoublyLinkedList(new GraphNode(0));
        assertEquals(this.dList.getSize() - 1, 0);
    }
    // ----------------------------------------------------------
    /**
     * Test empty list
     */
    public void testEmpty() {
        assertTrue(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 0);
        this.dList.insertHead(this.node1);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test inserting tail into empty list
     */
    public void testInsertTailToEmptyList() {      
        this.dList.insertTail(this.node1);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 1);
        
        this.dList.insertTail(this.node2);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 2);
        
        this.dList.insertTail(this.node3);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 3);
        
        assertEquals(this.dList.displayList(), "123");
        assertEquals(this.dList.getHead().getGraphNode().getIndex(), 1);
        assertEquals(this.dList.getTail().getGraphNode().getIndex(), 3);
    }
    
    // ----------------------------------------------------------
    /**
     * Test inserting head into empty list
     */
    public void testInsertHeadToEmptyList() {
        this.dList.insertHead(this.node1);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 1);
        
        this.dList.insertHead(this.node2);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 2);
        
        this.dList.insertHead(this.node3);
        assertFalse(this.dList.isEmpty());
        assertEquals(this.dList.getSize(), 3);
        
        assertEquals(this.dList.displayList(), "321");
        assertEquals(this.dList.getHead().getGraphNode().getIndex(), 3);
        assertEquals(this.dList.getTail().getGraphNode().getIndex(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test removing the last node from list
     */
    public void testRemoveTailFromList() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        
        String output = "";
        while (!this.dList.isEmpty()) {
            DoublyLinkedList.ListNode listNode = this.dList.removeTail();
            output += listNode.getGraphNode().getIndex();
        }
        assertTrue(this.dList.isEmpty());
        assertEquals(this.dList.displayList(), "");
        assertEquals(output, "123");
        assertNull(this.dList.getHead());
        assertNull(this.dList.getTail());
    }
    
    // ----------------------------------------------------------
    /**
     * Test removing the first node from list
     */
    public void testRemoveHeadFromList() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        
        String output = "";
        while (!this.dList.isEmpty()) {
            DoublyLinkedList.ListNode listNode = this.dList.removeHead();
            output += listNode.getGraphNode().getIndex();
        }
        assertTrue(this.dList.isEmpty());
        assertEquals(this.dList.displayList(), "");
        assertEquals(output, "321");
        assertNull(this.dList.getHead());
        assertNull(this.dList.getTail());
    }
    
    // ----------------------------------------------------------
    /**
     * Test finding node at given index
     */
    public void testFindNodeAt() {        
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertEquals(this.dList.findNodeAt(0).getGraphNode().getIndex(), 3);
        assertEquals(this.dList.findNodeAt(1).getGraphNode().getIndex(), 2);
        assertEquals(this.dList.findNodeAt(2).getGraphNode().getIndex(), 1);
        
        assertNull(this.dList.findNodeAt(-100));
        assertNull(this.dList.findNodeAt(100));
        assertNotNull(this.dList.findNodeAt(1));
    }
    
    // ----------------------------------------------------------
    /**
     * Test a single node list
     */
    public void testSingleNode() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        this.dList.removeTail();
        assertNotNull(this.dList.getTail());
        this.dList.removeTail();
        assertNotNull(this.dList.getTail());
        DoublyLinkedList.ListNode curr = this.dList.getTail();
        assertNull(curr.getNext());
        assertEquals(this.dList.getSize(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test removing head node from an empty list
     */
    public void testRemoveHeadFromEmptyList() {
        assertEquals(this.dList.getSize(), 0);
        assertNull(this.dList.removeHead());
    }
    
    // ----------------------------------------------------------
    /**
     * Test removing tail node from an empty list
     */
    public void testRemoveTailFromEmptyList() {
        assertEquals(this.dList.getSize(), 0);
        assertNull(this.dList.removeTail());
    }
    
    // ----------------------------------------------------------
    /**
     * Test to get the next node
     */
    public void testGetNext() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        DoublyLinkedList.ListNode curr = dList.getHead();
        assertNull(curr.getPrev());
        assertNotNull(curr.getNext());
        assertEquals(curr.getNext().getGraphNode().getIndex(), 2);  
    }
    
    // ----------------------------------------------------------
    /**
     * Test to get the previous node
     */
    public void testGetPrev() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        DoublyLinkedList.ListNode curr = dList.getTail();
        assertNull(curr.getNext());
        assertNotNull(curr.getPrev());
        assertEquals(curr.getPrev().getGraphNode().getIndex(), 2);

    }
    
    // ----------------------------------------------------------
    /**
     * Test if list contains the designated node
     */
    public void testContains() {
        GraphNode node4 = new GraphNode(4);
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertTrue(this.dList.containListNode(this.node2));
        assertFalse(this.dList.containListNode(node4));
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test to remove a designated node (normal case)
     */
    public void testRemoveListNodeNormal() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertEquals(this.dList.getSize(), 3);
        this.dList.removeListNode(this.node2);
        assertEquals(this.dList.getSize(), 2);
        assertEquals(this.dList.displayList(), "31");
    }
    
    // ----------------------------------------------------------
    /**
     * Test to remove a designated node (head case)
     */
    public void testRemoveListNodeHead() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertEquals(this.dList.getSize(), 3);
        this.dList.removeListNode(this.node3);
        assertEquals(this.dList.getSize(), 2);
        assertEquals(this.dList.displayList(), "21");
    }
    
    // ----------------------------------------------------------
    /**
     * Test to remove a designated node (Tail case)
     */
    public void testRemoveListNodeTail() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertEquals(this.dList.getSize(), 3);
        this.dList.removeListNode(this.node1);
        assertEquals(this.dList.getSize(), 2);
        assertEquals(this.dList.displayList(), "32");
    }
    
    // ----------------------------------------------------------
    /**
     * Test to remove a designated node (other case)
     */
    public void testRemoveListNodeOther() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        assertEquals(this.dList.getSize(), 3);
        GraphNode node4 = new GraphNode(4);
        assertEquals(this.dList.getSize(), 3);
        assertNull(this.dList.removeListNode(node4));
    }
    
    // ----------------------------------------------------------
    /**
     * Test remove list node with invalid input
     */
    public void testRemoveListNodeInvalid() {
        // null
        assertNull(this.dList.removeListNode(null));
        this.dList.insertHead(node1);
        assertNotSame(this.dList.removeListNode(null)
            , this.dList.getHead());
    }
    
    // ----------------------------------------------------------
    /**
     * Test remove list node with invalid input
     */
    public void testRemoveListNodeEmpty() {
        // empty
        assertNull(this.dList.removeListNode(this.node1));
        this.dList.insertHead(node1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test to get the next ListNode
     */
    public void testGetNextNode() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        
        assertNull(this.dList.getNextNode(this.dList.getTail()));
        
        assertNotNull(this.dList.getNextNode(this.dList.getHead()));
        
        assertNotNull(this.dList.getNextNode(
            this.dList.getHead().getNext()));
        
        assertEquals(this.dList.getNextNode(
            this.dList.getHead()).getGraphNode().getIndex(), 2);
        
        assertEquals(this.dList.getNextNode(
            this.dList.getHead().getNext())
            .getGraphNode().getIndex(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test to get the next ListNode with condition
     */
    public void testGetNodeNull() {
        GraphNode node = new GraphNode(0);
        this.dList.insertHead(node);
        assertNull(this.dList.getNextNode(this.dList.getTail()));
        assertNull(this.dList.getPrevNode(this.dList.getHead()));
    }
    
    // ----------------------------------------------------------
    /**
     * Test to get the previous ListNode
     */
    public void testGetPrevNode() {
        this.dList.insertHead(this.node1);
        this.dList.insertHead(this.node2);
        this.dList.insertHead(this.node3);
        
        assertNull(this.dList.getPrevNode(this.dList.getHead()));
        
        assertNotNull(this.dList.getPrevNode(this.dList.getTail()));
        
        assertNotNull(this.dList.getPrevNode(
            this.dList.getTail().getPrev()));
        
        assertEquals(this.dList.getPrevNode(
            this.dList.getTail()).getGraphNode().getIndex(), 2);
        
        assertEquals(this.dList.getPrevNode(
            this.dList.getTail().getPrev())
            .getGraphNode().getIndex(), 3);
    }
}
