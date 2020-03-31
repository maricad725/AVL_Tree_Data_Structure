/********************************************************************************
*
* Author: Alex Lopez
* FIU PantherID: 6132200
* 
* I declare that this is my work and I have not plagiarized this code from anyone.
* You may use and modify this code since it is on this public repository.
* This code is posted simply for the purpose of sharing it freely.
* But please do not copy this code and claim it as your own.  
* 
* Project Description:
* This is a Data Structures based project that utilizes an AVL tree: a balanced binary search tree.
* 
* The basic binary search tree is built from the basic node object that has two children leaves, 
* denoted as left node and right node, and a LinkList object that has insertion methods specifically
* designed to fill the respective node children for each node in LinkList object. 
* 
* This particular AVL tree code is built with the employee object as the data for the nodes in the tree. 
* The comparator built for determining which employee objects have greater value is based on two of the 
* object variables: Group(A-C, A being largest) and efficiency level(0.00-10.00), 
* with group having the higher priority for comparison.
* 
* The Binary Search Tree is a utility class that holds AVL tree functions, such as traversal functions 
* for traversing the trees and balancing rotation functions to keep the tree balanced when inserting new nodes. 
*
*******************************************************************************/

package AVL_Tree;

// Imported Java package code
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// Main running class
public class Controller {
    // debug display option that was left to show the process of building and balancing the AVL tree 
    private static boolean debug_mode;  // Debug display flag 
    
    public static void main(String[] args) throws InterruptedException {
        // Take input only here to ask if to turn on debug display mode 
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to turn debug mode on?(y/n)");
        String s = input.next();
        input.close();
        
        // If else statement to determine debug mode was turned on
        debug_mode = s.equalsIgnoreCase("y");
        if (debug_mode){
           System.out.println("Ok, debug mode is on.\n" + 
                   "This will print the logic behind the balancing algorithm for the AVL tree.\n"); 
        }
        else { 
            System.out.println("Ok, debug mode is off.\n"); 
        }

        // Hard coded name fields for each respective employee object
        String[] names = {"Daniel,Bernoulli", "Ernest,Rutherford", "Max,Planck",
                          "Lise,Meitner", "Grace,Hopper", "Heinrich,Hertz",
                          "David,Hilbert", "Leonhard,Euler", "Albert,Einstein",
                          "Marie,Curie"};
        
        // Generate employee objects and store them in an ArrayList
        ArrayList<Employee> employees = new ArrayList<>();
        generateEmployees(names, employees);
        
        // Print employee data from ArrayList
        TimeUnit.SECONDS.sleep(2);
        System.out.println("============================================================================================");
        System.out.println("Generated Employee Information");
        System.out.println("============================================================================================");
        System.out.printf("%-15s %13s %15s %15s %20s\n", "FirstName" ,"LastName",  "Group",  "AnnualWage", "EfficiencyLevel" );
        System.out.println("============================================================================================");
        for(Employee e:employees){
            e.Display();
        }
        
        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        
        // Now generating tree using employee objects from list as input
        Node root = new Node(employees.get(0));
        LinkList AVL_tree = new LinkList(root);
  
        for (int i = 1; i < employees.size(); i++){
            Node node = new Node(employees.get(i));
            AVL_tree.addAtEnd(node);
            // Balancing algorithm used only when tree has more than 2 nodes
            if (i >= 2){
                TimeUnit.SECONDS.sleep(1);
                
                BalanceTree(AVL_tree,AVL_tree.getHeadNode(), node, debug_mode);
                
                if(debug_mode){
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("========================================================================");
                }
                
            }
            
        }
        
        // Print balance of entire tree in debug mode
        if (debug_mode){
            TimeUnit.SECONDS.sleep(5);
            BalanceTree(AVL_tree,AVL_tree.getHeadNode(), null, debug_mode);
        }
        
        // Display AVL tree from all tree traversal methods
        BinarySearchTree.Display(AVL_tree.getHeadNode());
        
    }// end main
    
    //--------------------------------------------------------------------------
    // Random Employee Object Generator function
    //--------------------------------------------------------------------------
    public static void generateEmployees(String[] names, ArrayList<Employee> employees){
        Random c_rand = new Random();
        for (String name : names) {
            String[] Tokens = name.split("[,]");
            Employee e = new Employee();
            
            e.setFirstName(Tokens[0]);
            e.setLastName(Tokens[1]);
            
            char group = (char) (c_rand.nextInt((67 - 65) + 1) + 65);
            
            e.setGroup(String.valueOf(group));
            
            e.setAnnualWage(ThreadLocalRandom.current().nextDouble(100000.00, 500000.01));
            
            e.setEfficiencyLevel(ThreadLocalRandom.current().nextDouble(0.00, 10.01));
            
            employees.add(e);
        }
    }
    
    // AVL balancing algorithm
    public static void BalanceTree(LinkList tree, Node root, Node node, boolean debug){        
        // Subtree LinkLists for re-inserting into main tree linklinst 
        LinkList leftSubTree = null;
        LinkList rightSubTree = null;
        // Base Case: if no node in linklist tree
        if (root == null){
            return;
        }
        // if the most recently inserted node is not given, go through entire tree 
        if (node != null){
             // Search left subtree if node should be there 
            if (node.getEmployee().equals(root.getEmployee()) < 0){
                leftSubTree = new LinkList(root.getLeftNode());
                BalanceTree(leftSubTree, root.getLeftNode(), node, debug);
            }
            // Search right subtree if node should be there 
            else if (node.getEmployee().equals(root.getEmployee()) > 0){
                rightSubTree = new LinkList(root.getRightNode());
                BalanceTree(rightSubTree, root.getRightNode(), node, debug);
            }
            
        } else {
            leftSubTree = new LinkList(root.getLeftNode());
            rightSubTree = new LinkList(root.getRightNode());
            BalanceTree(leftSubTree, root.getLeftNode(), node, debug);
            BalanceTree(rightSubTree, root.getRightNode(), node, debug);
        }
        
        // re-insert subtrees back into main tree to save any modified changes.
        if (leftSubTree != null){
            tree.getHeadNode().setLeftNode(leftSubTree.getHeadNode());
        }
        if (rightSubTree != null){
            tree.getHeadNode().setRightNode(rightSubTree.getHeadNode());
        }
  
        // Balance Factor algorithm 
        int leftFactor;
        int rightFactor;
        
        // Determining values of left and right factor 
        if(root.getLeftNode() == null){
            leftFactor = 0;
        }else{
            leftFactor = BinarySearchTree.TreeHeight(root.getLeftNode());
        }
        if (root.getRightNode() == null){
            rightFactor = 0;
        }else{
            rightFactor = BinarySearchTree.TreeHeight(root.getRightNode());
        }
        
        // Get balance Factor 
        int balance = leftFactor - rightFactor;
        
        // Debug Mode: Print balance factor 
        if (debug){
            System.out.println("Balance is " + balance + " for this node:");
            root.getEmployee().Display();
        }
        
        // If tree is unbalanced from the left, determine appropriate left rotation algorithm
        if (balance >= 2){
            Node next = root.getLeftNode();
        
            if (next.getLeftNode() == null){
               leftFactor = 0;  
            } else{
                leftFactor = BinarySearchTree.TreeHeight(next.getLeftNode()); 
            }
            
            if (next.getRightNode() == null){
                rightFactor = 0;
            } else{
                 rightFactor = BinarySearchTree.TreeHeight(next.getRightNode());
            }
            
            int next_balance = leftFactor - rightFactor;
            
            if (debug){
                System.out.println("Balance is greater than 2, finding appropriate left rotation");
                System.out.println("Balance of next is " + next_balance);
            }
            
            if (next_balance == 1){
                if (debug){
                    System.out.println("Doing left left rotation");
                }
        
                BinarySearchTree.LeftLeftRotation(tree);
            }
            else if (next_balance == -1) {
                 if (debug){
                    System.out.println("Doing left right rotation");
                 }
                 
                 BinarySearchTree.LeftRightRotation(tree);
            }
            
        }
        // If tree is unbalanced from the right, determine appropriate right rotation algorithm
        else if(balance <= -2){
            Node next = root.getRightNode();
            
            if (next.getLeftNode() == null) {
               leftFactor = 0;  
            }else{
               leftFactor = BinarySearchTree.TreeHeight(next.getLeftNode()); 
            }
            
            if (next.getRightNode() == null){
                rightFactor = 0;
            }else{
                rightFactor = BinarySearchTree.TreeHeight(next.getRightNode());
            }
            
            int next_balance = leftFactor - rightFactor;
            
            if (debug){
                System.out.println("Balance is less than -2, finding appropriate right rotation");
                System.out.println("Balance of next is " + next_balance);
            }
            
            if (next_balance == 1) {
                if (debug){
                    System.out.println("Doing right left rotation");
                }
                
                BinarySearchTree.RightLeftRotation(tree);
            }
            else if (next_balance == -1) {
                if (debug){ 
                    System.out.println("Doing right right rotation");
                }
                
                BinarySearchTree.RightRightRotation(tree);
            }
            
        }
        
    }// end file
    
}
