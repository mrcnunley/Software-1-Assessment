/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    //Create empty Inventory object and initialize lists
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int toModify;
    

    // @@@@@ Methods used for products @@@@@   
    public void addNewProduct(Product productAddition) {
        products.add(productAddition);
    }
    
    public boolean deleteProduct(Product productRemoval) {
        boolean results = false;
        if(productRemoval == null){
            return results;
        }       
        results = this.products.remove(productRemoval);
        return results;
    }
    
    public static ObservableList<Product> getProductList() {
        return products;
    }
    
    public Product getProductToModify(){
        Product productToModify = null;
        productToModify = products.get(toModify);
        return productToModify;
    }
    
    
    // @@@@@ Methods used for parts @@@@@
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static void addNewPart(Part Part){
        allParts.add(Part);
    }
    
    public boolean deletePart(Part partRemoval){
        boolean results = false;
        if(partRemoval == null){
            return results;
        }
        results = allParts.remove(partRemoval);
        return results;
    }
    
    public void setPartToModify(int partToModify){
        toModify = partToModify;
    }
    
    public boolean checkPartType(){
        boolean isInhouse = false;
        if(allParts.get(toModify) instanceof InhousePart){
            isInhouse = true;
            return isInhouse;
        }
        return isInhouse;
    }
    
    public Part getPartToModify() {
        Part partToModify = null;
        partToModify = allParts.get(toModify);
        return partToModify;
    }
        
    public ObservableList<Part> getPartsList() {
        return allParts;
    }
}
