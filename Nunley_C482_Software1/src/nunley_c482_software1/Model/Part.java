/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {
    
    private IntegerProperty partID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty inStock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();
    
    // Counter for the next partID 
    private static int incPartID = 1;
   

    public int getPartID(){
        return partID.get();
    }
    
    public void setPartID(){
        this.partID.set(incPartID++);
    }
    
    public void setPartID(int partID){
        this.partID.set(partID);
    }
    
    
    public String getName(){
        return name.get();
    }   
    
    public void setName(String nm){
        if(nm == null || nm.isEmpty()){
            nm = "New Part";
        }
        this.name.set(nm);
    }
    
    public Double getPrice(){
        return price.get();
    }
    
    public void setPrice(double pr)throws IllegalArgumentException {
        if(pr < 0) {
            throw new IllegalArgumentException("Price must be a positive number!");
        }
        this.price.set(pr);
    }
    
        
    public int getMin(){
        return min.get();
    }
    
    public void setMin(int mn){
        this.min.set(mn);
    }
    
    public int getMax(){
        return max.get();
    }
    
    public void setMax(int mx){
        this.max.set(mx);
    }
    
    public int getInStock(){
        return inStock.get();
    }
    
    public void setInStock(int inStk){
        this.inStock.set(inStk);
    }
    
    public void setInStock(int inStk, int min, int max)throws IllegalArgumentException {
        if(inStk > max){
            throw new IllegalArgumentException("In stock value should be less than max value.");
        } else if(inStk < min){
            throw new IllegalArgumentException("In stock value should be greater than minimum value");
        }else if(min > max){
            throw new IllegalArgumentException("Minimum inventory level must be less then max value");
        }
        this.inStock.set(inStk);
    }

    
    // Used for table columns
    
    public IntegerProperty partIDProperty(){
        return partID;
    }
    
    public StringProperty partNameProperty(){
        return name;
    }
    
    public IntegerProperty partInStockProperty(){
        return inStock;
    }
    
    public DoubleProperty partPriceProperty(){
        return price;
    }
}
