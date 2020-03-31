/*
 * @author Alex Lopez
*/
package AVL_Tree;
// Data class object for nodes within AVL Tree

public class Employee {
// fields for employee   
private String firstName;    
private String lastName;   
private String group;    
private double annualWage;   
private double efficiencyLevel;
    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------
    public Employee(){
    } 
    
    public Employee(String firstName, String lastName, String group, 
                    double annualWage, double efficiencyLevel) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.annualWage = annualWage;
        this.efficiencyLevel = efficiencyLevel;
    }
    //--------------------------------------------------------------------------
    // Setters and Getters
    //--------------------------------------------------------------------------
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getGroup(){
        return group;
    }
    
    public void setGroup(String group){
        this.group = group;
    }
    
    public double getAnnualWage(){
        return annualWage;
    }
    
    public void setAnnualWage(double annualWage){
        this.annualWage = annualWage;
    }
    
    public double getEfficiencyLevel(){
        return efficiencyLevel;
    }
    
    public void setEfficiencyLevel(double efficiencyLevel){
        this.efficiencyLevel = efficiencyLevel;
    }
    
    //--------------------------------------------------------------------------
    // Other Methods 
    //--------------------------------------------------------------------------
    
    // Displays the data in a readable format 
    public void Display(){
        System.out.printf("%-20s %-20s %-8s", firstName, lastName, group);
        System.out.printf(" %-20.2f %-2.2f\n", annualWage, efficiencyLevel);  
    }
    
    // Comparator Function that compares this employee with another employee 
    public int equals(Employee e2) {
        // if group fields are equal, compare efficiency levels
        if (group.equals(e2.group)) {
            if (Double.compare(efficiencyLevel, e2.efficiencyLevel) < 0) {
                return -1;
            }
            else if (Double.compare(efficiencyLevel, e2.efficiencyLevel) > 0){
                return 1;
            }
            else{
                return 0;
            }
            
        }
        
        /* if this group char is lexigraphically less than given group, then 
           return that this employee is greater than given employee.
           Else return less than. (A > B > C) 
        */ 
        else if (group.compareTo(e2.group) < 0) {
            return 1;
        }
        
        return -1;
    }
    
}//end file 
