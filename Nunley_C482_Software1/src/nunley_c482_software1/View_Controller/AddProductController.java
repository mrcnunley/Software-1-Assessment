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
import nunley_c482_software1.Model.Part;
import nunley_c482_software1.Model.Inventory;
import static nunley_c482_software1.Model.Inventory.getAllParts;
import nunley_c482_software1.Model.Product;



public class AddProductController implements Initializable {
    
    private Inventory inventory = new Inventory();
    private ObservableList<Part> selectedParts = FXCollections.observableArrayList();
    public ObservableList<Part> partFound = FXCollections.observableArrayList();

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addColumnPartID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        addColumnPartName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	addColumnPartInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	addColumnPartPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject());
        addPartsTableView.setItems(inventory.getPartsList());
        
        deleteColumnPartID.setCellValueFactory(cell -> cell.getValue().partIDProperty().asObject());
        deleteColumnPartName.setCellValueFactory(cell -> cell.getValue().partNameProperty());
	deleteColumnPartInvLevel.setCellValueFactory(cell -> cell.getValue().partInStockProperty().asObject());
	deleteColumnPartPrice.setCellValueFactory(cell -> cell.getValue().partPriceProperty().asObject()); 
    }    
    
    @FXML private TextField TXTprodName;
    
    @FXML private TextField TXTprodInStock;
    
    @FXML private TextField TXTprodPrice;
    
    @FXML private TextField TXTprodMax;
    
    @FXML private TextField TXTprodMin;
    
    @FXML private TextField TXTpartSearch;
    
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
        selectedParts.add(partToAdd);
        deletePartsTableView.setItems(selectedParts);
    }
    
    public void buttonDeletePart (ActionEvent event){
        Part partToRemove = deletePartsTableView.getSelectionModel().getSelectedItem();
        deletePartsTableView.getItems().remove(partToRemove);
        deletePartsTableView.refresh();
    }
    
    public void buttonSave (ActionEvent event) throws IOException{
        try{
            String nameString = TXTprodName.getText();
            String inStockString = TXTprodInStock.getText();
            String priceString = TXTprodPrice.getText();
            String maxString = TXTprodMax.getText();
            String minString = TXTprodMin.getText();
        
            //verify product must have a name, price and inventory level
            if(nameString.isEmpty()||inStockString.isEmpty()||priceString.isEmpty()){
                throw new IllegalArgumentException("Name, Inventory, and Price are mandatory fields");
            }
        
            int inStock = Integer.parseInt(inStockString);
            double price = Double.parseDouble(priceString);
            int max = Integer.parseInt(maxString);
            int min = Integer.parseInt(minString);
            selectedParts = deletePartsTableView.getItems();
            
         //check product price is greater than part cost
            double partTotal = getTotalPartCost();
            if(partTotal > price){
                throw new IllegalArgumentException("Product price must be equal or greater than sum of it's parts");
            }
            
            //Do not allow a user to save a product that does not have a part associated with it.
            else if(selectedParts.isEmpty()){
                throw new IllegalArgumentException("A product must have at least one part associated with it");
            }
        
        Product newProduct = new Product(nameString, inStock, price, max, min, selectedParts);
        inventory.addNewProduct(newProduct);
        }
        
        catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        
//        Get the stage information
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

        if(selectedParts.isEmpty()){
            return partTotal;
        }
        
        for(Part part : selectedParts) {
            partTotal += part.getPrice();
	}
        
        return partTotal;
    }
}
