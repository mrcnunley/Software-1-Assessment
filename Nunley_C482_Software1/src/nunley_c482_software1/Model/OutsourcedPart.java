/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutsourcedPart extends Part {
    
    private StringProperty companyName = new SimpleStringProperty();
    
    //default constructor
    public OutsourcedPart(){
        //TODO
    }
    
    // contructor used for new part creation
    public OutsourcedPart(String name, double price, int inStock, int max, int min, String companyName){
        setPartID();
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMax(max);
        setMin(min);
        setCompanyName(companyName);
    }
    
    //constructor used for modified part
    public OutsourcedPart(int partID, String name, double price, int inStock, int max, int min, String companyName){
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMax(max);
        setMin(min);
        setCompanyName(companyName);
    }
    
    //setter and getter methods for comapny name
    public String getCompanyName() {
        return companyName.get();
    }
    
    public void setCompanyName(String coName) {
        this.companyName.set(coName);
    }
}
