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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nunley_c482_software1.Model.Inventory;
import static nunley_c482_software1.Model.Inventory.getAllParts;
import nunley_c482_software1.Model.Part;
import nunley_c482_software1.Model.Product;

/**
 * FXML Controller class
 *
 * @author mrc_m
 */
public class ModifyProductController implements Initializable {

    private Inventory inventory = new Inventory();
    public ObservableList<Part> partFound = FXCollections.observableArrayList();
    public ObservableList<Part> partsIncluded = FXCollections.observableArrayList();
    private static ObservableList<Part> product = FXCollections.observableArrayList();
    private Product toModify = inventory.getProductToModify();
    

    @FXML private TableView<Part> addPartsTableView;
    @FXML private TableColumn<Part, Integer> addColumnPartID;
    @FXML private TableColumn<Part, String> addColumnPartName;
    @FXML private TableColumn<Part, Integer> addColumnPartInvLevel;
    @FXML private TableColumn<Part, Double> addColumnPartPrice;
    
    @FXML private TableView<Part> deletePartsTableView;
    @FXML private TableColumn<Part, Integer> deleteColumnPartID;
    @FXML private TableColumn<Part, String> deleteColumnPartName;
    @FXML private TableColumn<Part, Integer> deleteColumnPartInvLevel;
    @FXML private TableColumn<Part, Double> deleteColumnPartPrice;
    
    @FXML private TextField TXTpartSearch;
    
    @FXML private TextField TXTprodID;
    
    @FXML private TextField TXTprodName;
    
    @FXML private TextField TXTprodInStock;
    
    @FXML private TextField TXTprodPrice;
    
    @FXML private TextField TXTprodMax;
    
    @FXML private TextField TXTprodMin;    
    

    public void initialize(URL url, ResourceBundle rb) {
        addColumnPartID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        addColumnPartName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	addColumnPartInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	addColumnPartPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject()); 
        addPartsTableView.setItems(inventory.getPartsList());
        
        setupProductToModify();
        
        deleteColumnPartID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        deleteColumnPartName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	deleteColumnPartInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	deleteColumnPartPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject());   
    }    
 
    public void setupProductToModify(){
        TXTprodID.setText(Integer.toString(toModify.getProductID()));
        TXTprodName.setText(toModify.getName());
        TXTprodInStock.setText(Integer.toString(toModify.getInStock()));
        TXTprodPrice.setText(Double.toString(toModify.getPrice()));
        TXTprodMax.setText(Integer.toString(toModify.getMax()));
        TXTprodMin.setText(Integer.toString(toModify.getMin()));
        partsIncluded = toModify.getAssociatedPart(); 
        deletePartsTableView.setItems(partsIncluded);
    }
    
    
    public void buttonPartsSearch(ActionEvent event) {
        String searchItem = TXTpartSearch.getText();
        boolean partIsFound = false;
        if (searchItem.equals("")){
                addPartsTableView.setItems(Inventory.getAllParts());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Part Found");
                alert.setContentText("Type in a part by exact name or ID");
                alert.showAndWait();
        }
        if(!partIsFound){ //search for part using part description
            String itemDescription = searchItem;
            for(Part p: getAllParts())
                if(itemDescription.toLowerCase().equals(p.getName().toLowerCase())){
                    partIsFound = true;
                    partFound.clear();
                    partFound.add(p);
                    addPartsTableView.setItems(partFound);
                }
        }
        
        if(!partIsFound){ //search for part using the part ID
            int itemNumber=Integer.parseInt(searchItem);
                for(Part p: getAllParts())
                    if(p.getPartID()==itemNumber){
                        partIsFound = true;
                        partFound.clear();
                        partFound.add(p);
                        addPartsTableView.setItems(partFound);
                    }
        }
    }
    
    public void buttonAddPart (ActionEvent event) {
        Part partToAdd = addPartsTableView.getSelectionModel().getSelectedItem();
        partsIncluded.add(partToAdd);
        deletePartsTableView.setItems(partsIncluded);
    }
    
    public void buttonDeletePart (ActionEvent event){
        Part partToRemove = deletePartsTableView.getSelectionModel().getSelectedItem();
        deletePartsTableView.getItems().remove(partToRemove);
        deletePartsTableView.setItems(partsIncluded);
    }
    
    
    public void buttonSave (ActionEvent event) throws IOException{
        int prodID = toModify.getProductID();
        
        inventory.deleteProduct(toModify);
        
        String name = TXTprodName.getText();
        
        String inStockString = TXTprodInStock.getText();
        int inStock = Integer.parseInt(inStockString);
        
        String priceString = TXTprodPrice.getText();
        Double price = Double.parseDouble(priceString);
        
        String maxString = TXTprodMax.getText();
        int max = Integer.parseInt(maxString);
        
        String minString = TXTprodMin.getText();
        int min = Integer.parseInt(minString);
        
        partsIncluded = deletePartsTableView.getItems();
        
        //modified product contains at least one part
        if(partsIncluded.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A product must contain at least one part");
            alert.showAndWait();
            return;
        }
        
        //modified part price is greater than the sum of the parts
        double partTotal = getTotalPartCost();
        if(partTotal > price){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Product price must be equal or greater than sum of it's parts");
            alert.showAndWait();
            return;
        }
        //add product to inventory
        Product newProduct = new Product(prodID, name, inStock, price, max, min, partsIncluded);
        inventory.addNewProduct(newProduct);
        
        //Return to the main screen
        Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();    
    }
    
    public void buttonCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel?");
        alert.setHeaderText("All changes will be lost?");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene addPartScene = new Scene(addPartParent);       
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();       
                window.setScene(addPartScene);
                window.show();
            }
    }
    
    public double getTotalPartCost(){
        double partTotal = 0;

        if(partsIncluded.isEmpty()){
            return partTotal;
        }
        
        for(Part part : partsIncluded) {
            partTotal += part.getPrice();
	}
        
        return partTotal;
    }
}
