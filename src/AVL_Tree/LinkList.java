/*
 * @author Alex Lopez
*/
package AVL_Tree;

// Linklist object that acts as an AVL tree
public class LinkList {
    private Node headNode; // node at head of LinkList
    
    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------
    public LinkList() {
        headNode = null;
    }

    public LinkList(Node headNode) {
        this.headNode = headNode;
    }
    
    //--------------------------------------------------------------------------
    // Setters and Getters
    //--------------------------------------------------------------------------
    public Node getHeadNode(){
        return headNode;
    }
    
    public void setHeadNode(Node headNode){
        this.headNode = headNode;
    }
    
    //--------------------------------------------------------------------------
    // Insertion Methods 
    //--------------------------------------------------------------------------
     public void addAtEnd(Node node) {
        // Flags to tell where to put new node in
        // Note: only one can be true.
        boolean left = false;
        boolean right = false;
        
        // base case: no head node means new node is at head
        if (headNode == null) {
            headNode = node;
            return;
        }
        
        // Node fields used to go down the linklist
        Node currentNode = headNode;
        Node previousNode = null;
        
        // Going down linklist until at bottom of current linklist tree
        while (currentNode != null) {
            previousNode = currentNode;
            
            // go to left node and set left flag true and right flag false
            if (node.getEmployee().equals(previousNode.getEmployee()) < 0) {
                currentNode = currentNode.getLeftNode();
                left = true;
                right = false;
            }
            // go to right node and set right flag true and left flag false
            else {
                currentNode = currentNode.getRightNode();
                left = false;
                right = true;
            }
        }// end while loop
        
        // Sett node to bottom node's left or right node
        if (left) {
            previousNode.setLeftNode(node);
        }
        
        else if (right){
            previousNode.setRightNode(node);
        }
        
    }//addAtEnd()

    public void addAtFront(Node node) {
        // Base case: set node to head node
        if (headNode == null) {
            headNode = node;
            return;
        }
        // set headnode to appropriate node leaf and set node as new head
        if (headNode.getEmployee().equals(node.getEmployee()) < 0){
            node.setLeftNode(headNode);
            setHeadNode(node);
        }
        else {
            node.setRightNode(headNode);
            setHeadNode(node);    
        }
        
    }//addAtFront()

}// end file
