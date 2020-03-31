/*
 * @author Alex Lopez
*/
package AVL_Tree;

// AVL Tree node class object with employee object as data field 
public class Node {
    private Node leftNode;             // Point to left node
    private Node rightNode;            // Point to right node 
    private Employee employee;         // node data
    //--------------------------------------------------------------------------
    // Constructor with data (no empty constructor) 
    //--------------------------------------------------------------------------
    public Node(Employee employee) {
        this.employee = employee;
        leftNode = null;
        rightNode = null;
    }
    //--------------------------------------------------------------------------
    // Setters and getters
    //--------------------------------------------------------------------------
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Node getLeftNode() { 
        return leftNode;
    }
    
    public void setLeftNode(Node leftNode){
        this.leftNode = leftNode;
    }    

    public Node getRightNode() {
        return rightNode;
    }
    
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
    
}// end file 
