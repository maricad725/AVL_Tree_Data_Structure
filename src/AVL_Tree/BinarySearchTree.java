/*
Author: Alex Lopez
*/
package AVL_Tree;

import java.util.concurrent.TimeUnit;

// AVL tree class with tree related helper functions 
public class BinarySearchTree {
    
    // Key function that prints the AVL tree in all traversal orders  
    public static void Display(Node rootNode) throws InterruptedException {
        String orders [] = {"Level Order", "Pre Order", "In Order", "Post Order"};
        
        //Order of traversals printed: Level, inorder, pre-order, post-order
        for (String order : orders) {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("============================================================================================");
            System.out.printf("Generated Employee Information (%s)\n", order);
            System.out.println("============================================================================================");
            System.out.printf("%-15s %13s %15s %15s %20s\n", "FirstName" ,"LastName",  "Group", "AnnualWage", "EfficiencyLevel");
            System.out.println("============================================================================================");
            
            switch (order) {
                case "Level Order":
                    LevelorderTraversal(rootNode);
                    break;
                case "Pre Order":
                    PreorderTraversal(rootNode);
                    break;
                case "In Order":
                    InorderTraversal(rootNode);
                    break;
                case "Post Order":
                    PostorderTraversal(rootNode);
                    break;    
                default:
                    break;
            }// end switch
        }// end for loop
    }
    
    /* Traversal method: 
         Prints the tree nodes starting at the bottom of the root's left subtree, 
         printing the left to right node before going up the left subtree.
         Then it traverses and prints the right subtree nodes in the same manner.
         Finally, the root node is printed out after both sub trees are done.         
         Ex:       A
                /     \
               B       C
             /  \    /  \
            D    E  F    G
         Print Order: D, E, B, F, G, C, A
    */
    public static void PostorderTraversal(Node rootNode){
         if (rootNode != null){
            PostorderTraversal(rootNode.getLeftNode());
            PostorderTraversal(rootNode.getRightNode());
            
            System.out.printf("%-20s %-20s %-8s",rootNode.getEmployee().getFirstName(),
                                                 rootNode.getEmployee().getLastName(), 
                                                 rootNode.getEmployee().getGroup());
            System.out.printf(" %-20.2f %.2f\n",rootNode.getEmployee().getAnnualWage(), 
                                                rootNode.getEmployee().getEfficiencyLevel());  
         }
         
     }
    
     /* Traversal method: 
         Prints the tree nodes in order of their data from least to greatest.
         Accomplishes this by printing the nodes in the left subtree, then prints the
         root node, and then prints the nodes in the right subtree. 
         Ex:       A
                /     \
               B       C
             /  \    /  \
            D    E  F    G
         Print Order: D, B, E, A, F, C, G
         Note: The AVL rotation algorithms done on this tree to balance it 
         will not affect how this method will print the tree.
     */
     public static void InorderTraversal(Node rootNode) {
         if (rootNode != null){
            InorderTraversal(rootNode.getLeftNode());
            System.out.printf("%-20s %-20s %-8s",rootNode.getEmployee().getFirstName(),
                                                 rootNode.getEmployee().getLastName(), 
                                                 rootNode.getEmployee().getGroup());
            System.out.printf(" %-20.2f %.2f\n",rootNode.getEmployee().getAnnualWage(), 
                                                rootNode.getEmployee().getEfficiencyLevel());
            InorderTraversal(rootNode.getRightNode());
         }
         
     } 
     
      /* Traversal method that prints the root first,
      then prints all the nodes going down the left subtree 
      and all the nodes going down the right subtree.
      Ex:       A
             /     \
            B       C
           /  \    /  \
          D    E  F    G
     Print Order: A, B, D, E, C, F, G
     */
     public static void PreorderTraversal(Node rootNode){
         if (rootNode != null){
            System.out.printf("%-20s %-20s %-8s",rootNode.getEmployee().getFirstName(),
                                                 rootNode.getEmployee().getLastName(), 
                                                 rootNode.getEmployee().getGroup());
            System.out.printf(" %-20.2f %.2f\n",rootNode.getEmployee().getAnnualWage(), 
                                                rootNode.getEmployee().getEfficiencyLevel());
            PreorderTraversal(rootNode.getLeftNode());
            PreorderTraversal(rootNode.getRightNode());
         }
         
     }
     
     /* Traversal method that prints the tree's leaf nodes at each tree level
      Ex:       A
             /     \
            B       C
           /  \    /  \
          D    E  F    G
     Print Order: A, B, C, D, E, F, G
     */
     public static void LevelorderTraversal(Node rootNode){
         int rootHeight = TreeHeight(rootNode);
         for (int i = 1; i <= rootHeight; i++){
             PrintLevelOrder(rootNode, i, i);
         }
            
     }
     
     // Helper method for LevelorderTraversal:
     // Generates the tree height for iterating each level in the tree      
     public static int TreeHeight(Node root){
          if (root == null) {
              return 0;
          }
          return (1 + Math.max(TreeHeight(root.getLeftNode()),TreeHeight(root.getRightNode())));
     }
     
     // Helper method for LevelorderTraversal that prints nodes based on level
     public static void PrintLevelOrder(Node root, int currentLevel, int realLevel){
         //This is used to check the nodes at each level
         //Commented out since this was used only for debugging the function.  
         /*
         if (realLevel == currentLevel) {
           System.out.printf("Level %s:\n", realLevel);
         }
         */
         
         // base case: if root node does not exist, pop back up to caller
         if (root == null){
             return;
         }
         //base case: if currentLevel at one, print node data 
         if (currentLevel == 1){
            System.out.printf("%-20s %-20s %-8s",root.getEmployee().getFirstName(),
                                                 root.getEmployee().getLastName(), 
                                                 root.getEmployee().getGroup());
            System.out.printf(" %-20.2f %.2f\n",root.getEmployee().getAnnualWage(), 
                                                root.getEmployee().getEfficiencyLevel()); 
         }
         
         //recursive call case: call function for root's left and right node 
         else if (currentLevel > 1) {
             PrintLevelOrder(root.getLeftNode(), currentLevel - 1, realLevel);
             PrintLevelOrder(root.getRightNode(), currentLevel - 1,realLevel);
         }
     }
     
     //-------------------------------------------------------------------------
     // AVL Rotation algorithms
     //-------------------------------------------------------------------------
     
     public static void LeftLeftRotation(LinkList head){
         Node left = head.getHeadNode().getLeftNode();
         
         // if left has a right child, it needs to be moved in the rotation
         Node left_right = null;
         
         boolean insert = false;
         
         if (left.getRightNode() != null){
             left_right = left.getRightNode();
             left.setRightNode(null);
             insert = true;
         }
          
         //Rotation algortihm put left node at front of tree and reenter left right into tree 
         head.getHeadNode().setLeftNode(null);
         head.addAtFront(left);
         
         if (insert){
            head.addAtEnd(left_right);
         }
         
    }
      
     public static void LeftRightRotation(LinkList head) {
         // it is necessary to copy both left node and left's right node
         Node left = head.getHeadNode().getLeftNode();
         Node left_right = head.getHeadNode().getLeftNode().getRightNode();
         
         // Let A be the root node, B be A's left node, and C be B's right node
         // If C has children, they need to be moved within the rotation as well; 
         // Therefore, the code below detects if C has left and right leaves
         Node left_right_left = null;
         Node left_right_right = null;
         
         if(left_right.getLeftNode() != null) {
            left_right_left = left_right.getLeftNode();
            left_right.setLeftNode(null);
         }
         
         if (left_right.getRightNode() != null) {
             left_right_right = left_right.getRightNode();
             left_right.setRightNode(null);
         }
         
         //Rotation algorithm
         head.getHeadNode().setLeftNode(null);
         left.setRightNode(null);
         
         head.addAtFront(left_right);
         head.getHeadNode().setLeftNode(left);
         
         //C's left node becomes B's right node 
         if(left_right_left != null){
             head.getHeadNode().getLeftNode().setRightNode(left_right_left);
         }
         // C's right node becomes A's left node
         if(left_right_right != null) {
             head.getHeadNode().getRightNode().setLeftNode(left_right_right);
         }
         
     }
     
     
     // AVL rotation algorithm that happens when right subtree is too heavy compared to left subtree.
     // Offending main nodes to be rotated are the root, its right node, and right's right node.
     public static void RightRightRotation(LinkList head){
         Node right = head.getHeadNode().getRightNode();
         
         // if right has a left child, it needs to be moved in the rotation
         Node right_left = null;
         boolean insert = false;
         if (right.getLeftNode() != null){
             right_left = right.getLeftNode();
             right.setLeftNode(null);
             insert = true;
         }
          
         //Rotation algortihm put right node at front of tree //reenter right left into tree 
         head.getHeadNode().setRightNode(null);
         head.addAtFront(right);
         
         //re-enter right left into tree: Should be left right node of modified root tree 
         if (insert){
            head.addAtEnd(right_left);
         }
        
     }
     
     // AVL rotation algorithm that happens when right subtree is too heavy compared to left subtree.
     // Offending main nodes to be rotated are the root, its right node, and right's left node.
     public static void RightLeftRotation(LinkList head) {
         // it is necessary to copy both right node and right's left node
         Node right = head.getHeadNode().getRightNode();
         Node right_left = head.getHeadNode().getRightNode().getLeftNode();
         
         // Let A be the root node, B be A's right node, and C be B's left node
         // If C has children, they need to be moved within the rotation as well; 
         // Therefore, the code below detects if C has left and right leaves
         Node right_left_left = null;
         Node right_left_right = null;
         
         if(right_left.getLeftNode() != null) {
            right_left_left = right_left.getLeftNode();
            right_left.setLeftNode(null);
         }
         
         if (right_left.getRightNode() != null) {
             right_left_right = right_left.getRightNode();
             right_left.setRightNode(null);
         }
         
         //Rotation algorithm: 
         //right_left as root of tree, original root as left subtree, right as right subtree 
         head.getHeadNode().setRightNode(null);
         right.setLeftNode(null);
         
         head.addAtFront(right_left);
         head.getHeadNode().setRightNode(right);
         
         //C's left node becomes A's right node 
         if(right_left_left != null){
             head.getHeadNode().getLeftNode().setRightNode(right_left_left);
         }
         // C's right node becomes B's left node
         if(right_left_right != null) {
             head.getHeadNode().getRightNode().setLeftNode(right_left_right);
         }
         
     }
}  

