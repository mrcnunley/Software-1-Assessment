/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class InhousePart extends Part{
    
    private IntegerProperty machineID = new SimpleIntegerProperty();
    
    //default constructor
    public InhousePart(){
        //TODO
    }
    
    //constructor for new part
    public InhousePart(String name, double price, int inStock, int max, int min, int machineID){
        setPartID();
        setName(name);
        setPrice(price);
        setInStock(inStock, min, max);
        setMax(max);
        setMin(min);
        setMachineID(machineID);
    }
    
    //constructor for modified part
    public InhousePart(int partID, String name, double price, int inStock, int max, int min, int machineID){
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock, min, max);
        setMax(max);
        setMin(min);
        setMachineID(machineID);
    }
    // getter and setter for machineID
    public int getMachineID() {
        return machineID.get();
    }
    
    public void setMachineID(int machID) {
        this.machineID.set(machID);
    }
}
