/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();
    private IntegerProperty productID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty inStock = new SimpleIntegerProperty();
    private IntegerProperty min = new SimpleIntegerProperty();
    private IntegerProperty max = new SimpleIntegerProperty();
    
    private static int incProductID = 1;
    //default constructor
    public Product(){
        //TODO
    }
    //used to create a new product.
    public Product(String name, int inStock, double price, int max, int min, List<Part> selectedParts){
        setProductID();
        setName(name);
        setInStock(inStock);
        setPrice(price);
        setMax(max);
        setMin(min);  
        //setAssociatedPart(selectedParts);
        this.selectedParts.setAll(selectedParts);
    }
    
    //used to modify an existing product
    public Product(int prodID, String name, int inStock, double price, int max, int min, List<Part> selectedParts){
        setProductID(prodID);
        setName(name);
        setInStock(inStock);
        setPrice(price);
        setMax(max);
        setMin(min);
        this.selectedParts.setAll(selectedParts);
    }
    
    // Getter and Setters
    public int getProductID() {
        return productID.get();
    }
    
    public void setProductID(){
        this.productID.set(incProductID++);
    }
    
    public void setProductID(int prodID) {
        this.productID.set(prodID);
    }
    
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String nm) {
        this.name.set(nm);
    }
    
    
    public double getPrice() {
        return price.get();
    }
    
    public int getInStock() {
        return inStock.get();
    }
    
    public void setInStock(int inStk) {
        this.inStock.set(inStk);
    }    
    
    public void setPrice(double pr) {
        this.price.set(pr);
    }
    
    public int getMax() {
        return max.get();
    }
    
    public void setMax(int mx) {
        this.max.set(mx);
    }
    
    public int getMin() {
        return min.get();
    }
    
    public void setMin(int mn) {
        this.min.set(mn);
    }
    
    public void setAssociatedPart(List<Part>partAddition){
        this.selectedParts.setAll(partAddition);
    }
    
    public ObservableList<Part> getAssociatedPart() {
        return this.selectedParts;
    }
    
    public boolean removeAssociatedPart(int partID){
        boolean results = false;
        
        if(selectedParts == null || selectedParts.isEmpty()){
            return results;
        }
        Part partRemoval = lookupPart(partID);
        
        if(partRemoval != null){
            results = this.selectedParts.remove(partRemoval);
        }
        return results;
    }
    
    public Part lookupPart(int partID){
        Part partIsFound = null;
        
        for(Part part : selectedParts){
            if(part.getPartID() == partID){
                partIsFound = part;
                break;
            }
        }
        
        return partIsFound;
    }
    
    public IntegerProperty partIDProperty(){
        return productID;
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
