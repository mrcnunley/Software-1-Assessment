/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import nunley_c482_software1.Model.Inventory;
import static nunley_c482_software1.Model.Inventory.getAllParts;
import static nunley_c482_software1.Model.Inventory.getProductList;
import nunley_c482_software1.Model.Part;
import nunley_c482_software1.Model.Product;


public class MainScreenController implements Initializable {
    
    private Inventory inventory = new Inventory();
    
    @FXML private TextField searchPartField;
    @FXML private TextField searchProductField;
    
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> columnPartID;
    @FXML private TableColumn<Part, String> columnPartName;
    @FXML private TableColumn<Part, Integer> columnPartInvLevel;
    @FXML private TableColumn<Part, Double> columnPartPrice;
    
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> columnProdID;
    @FXML private TableColumn<Product, String> columnProdName;
    @FXML private TableColumn<Product, Integer> columnProdInvLevel;
    @FXML private TableColumn<Product, Double> columnProdPrice;

    public ObservableList<Part> partFound = FXCollections.observableArrayList();
    public ObservableList<Product> productFound = FXCollections.observableArrayList();

    
    public void initialize(URL url, ResourceBundle rb) {
        //populate and initialize the parts tableview
        columnPartID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        columnPartName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	columnPartInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	columnPartPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject());        
        partsTableView.setItems(inventory.getPartsList());
        
        //populate and initialize the products tableview
        columnProdID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        columnProdName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	columnProdInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	columnProdPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject());
        productsTableView.setItems(inventory.getProductList());
    }    
    
    
    //Methods for buttons on mainscreen
    public void buttonAddPart(ActionEvent event) throws IOException{       
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    public void buttonModifyPart(ActionEvent event) throws IOException{
        int partArrayIndex = partsTableView.getSelectionModel().getSelectedIndex();
        inventory.setPartToModify(partArrayIndex);
        
        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    
    public void buttonDeletePart(ActionEvent event){
        Part partToDelete = null;
        partToDelete = partsTableView.getSelectionModel().getSelectedItem();
        //warn user if there is no part selected to delete.
        if(partToDelete == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        }else{
        //Set up alert for part deletion, and wait for user confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part?");
            alert.setHeaderText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    inventory.deletePart(partToDelete);
                    partsTableView.setItems(inventory.getPartsList());
                }
        }
    }
    
    public void buttonPartsSearch(ActionEvent event) {
        String searchItem=searchPartField.getText();
        boolean partIsFound = false;
        try{
            if(!partIsFound){ //search for part using part description
                String itemDescription = searchItem;
                for(Part p: getAllParts())
                    if(itemDescription.toLowerCase().equals(p.getName().toLowerCase())){
                        partIsFound = true;
                        partFound.clear();
                        partFound.add(p);
                        partsTableView.setItems(partFound);
                    }
            }      
            if(!partIsFound){ //search for part using the part ID
                int itemNumber=Integer.parseInt(searchItem);
                    for(Part p: getAllParts())
                        if(p.getPartID()==itemNumber){
                            partIsFound = true;
                            partFound.clear();
                            partFound.add(p);
                            partsTableView.setItems(partFound);
                        }
            }
            if (!partIsFound){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No match found");
                alert.setContentText("Type in a product by exact name or ID");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No search string entered");
            alert.setContentText("Type in a part by exact name or part ID");
            alert.showAndWait();
        }
    }
    
    public void buttonAddProduct(ActionEvent event) throws IOException{
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();        
        window.setScene(addPartScene);
        window.show();
    }
    
    public void buttonModifyProduct(ActionEvent event) throws IOException{
        int productArrayIndex = productsTableView.getSelectionModel().getSelectedIndex();
        inventory.setPartToModify(productArrayIndex);
        
        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();        
        window.setScene(addPartScene);
        window.show();
    }
    
    public void buttonDeleteProduct(ActionEvent event){
        Product productToDelete = null;
        try{
            productToDelete = productsTableView.getSelectionModel().getSelectedItem();
            partFound = productToDelete.getAssociatedPart();
            
            //check to make sure product has no parts before deleting
            if(!partFound.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Product still contains parts");
            alert.setContentText("Are you sure you want to delete a product containing parts");
            Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        inventory.deleteProduct(productToDelete);
                        productsTableView.setItems(inventory.getProductList());
                    }
                    return;
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        inventory.deleteProduct(productToDelete);
                        productsTableView.setItems(inventory.getProductList());
                    }
            } 
        
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }   
    }
    
    public void buttonProductSearch(ActionEvent event) {
        String searchItem = searchProductField.getText();
        boolean productIsFound = false;
        if (searchItem.equals("")){
                productsTableView.setItems(inventory.getProductList());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Product Found");
                alert.setContentText("Type in a product by exact name or ID");
                alert.showAndWait();
        }
        if(!productIsFound){ //search for part sing part description
            String itemDescription = searchItem;
            for(Product p: getProductList())
                if(itemDescription.toLowerCase().equals(p.getName().toLowerCase())){
                    productIsFound = true;
                    productFound.clear();
                    productFound.add(p);
                    productsTableView.setItems(productFound);
                }
        }
        
        if(!productIsFound){ //search for part using the part ID
            int itemNumber=Integer.parseInt(searchItem);
                for(Product p: getProductList())
                    if(p.getProductID()==itemNumber){
                        productIsFound = true;
                        productFound.clear();
                        productFound.add(p);
                        productsTableView.setItems(productFound);
                    }
        }
    }
    
    public void buttonExit(ActionEvent event) throws IOException{
        System.exit(0);
    }
    
    }