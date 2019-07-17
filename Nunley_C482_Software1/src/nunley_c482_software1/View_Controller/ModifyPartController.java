/*Created by Chris Nunley for WGU performance assessment
 * C482 Software 1
 */
package nunley_c482_software1.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nunley_c482_software1.Model.Inventory;
import nunley_c482_software1.Model.Part;
import nunley_c482_software1.Model.InhousePart;
import nunley_c482_software1.Model.OutsourcedPart;


/**
 * MODIFY PART CONTROLLER
 *
 * @author Chris Nunley
 */
public class ModifyPartController implements Initializable {
    
    private Inventory inventory = new Inventory(); 
    private InhousePart inhousePart;
    private OutsourcedPart outsourcePart;
    private boolean partTypeInhouse = false;
    private Part toModify = inventory.getPartToModify();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTypeInhouse = inventory.checkPartType();
        setupPartToModify();
    }   
    
    @FXML private RadioButton inHouse;
    
    @FXML private RadioButton outsourced;
    
    @FXML private TextField idText;
    
    @FXML private TextField nameText;
    
    @FXML private TextField inventoryText;
    
    @FXML private TextField priceCostText;
    
    @FXML private TextField maxText;
    
    @FXML private TextField minText;
    
    @FXML private Label machCompLabel;
    
    @FXML private TextField machCompText;
    
    
    public void buttonCancel(ActionEvent event) throws IOException{
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
    
    public void radioButtonSelection(ActionEvent event){
        if(inHouse.isSelected()){
            machCompLabel.setText("Machine ID");
            machCompText.setPromptText("Mach ID");
        }
        else if(outsourced.isSelected()){
           machCompLabel.setText("Company Name");
           machCompText.setPromptText("Company Name");
        }
    }
    
    public void setupPartToModify(){
        
        if(partTypeInhouse){
            inHouse.setSelected(true);
            machCompLabel.setText("Machine ID");
            inhousePart = (InhousePart) toModify;
            machCompText.setText(Integer.toString(inhousePart.getMachineID()));
        } 
        else {
            outsourced.setSelected(true);
            machCompLabel.setText("Company Name");
            outsourcePart = (OutsourcedPart) toModify;
            machCompText.setText(outsourcePart.getCompanyName());
        }
        idText.setText(Integer.toString(toModify.getPartID()));
        nameText.setText(toModify.getName());
        inventoryText.setText(Integer.toString(toModify.getInStock()));
        priceCostText.setText(Double.toString(toModify.getPrice()));
        maxText.setText(Integer.toString(toModify.getMax()));
        minText.setText(Integer.toString(toModify.getMin()));
    }
    
    public void savePart(ActionEvent event)throws IOException {
        // used to save modified inhousePart from existing inhousePart
        if(partTypeInhouse&&inHouse.isSelected()){
            String name = nameText.getText();
            String inStockString = inventoryText.getText();
            String priceString = priceCostText.getText();
            String maxString = maxText.getText();
            String minString = minText.getText();
            String machComp = machCompText.getText();
            
        try {
            if(name.isEmpty()||inStockString.isEmpty()||priceString.isEmpty()||
               maxString.isEmpty()||minString.isEmpty()||machComp.isEmpty()){
                throw new IllegalArgumentException("All text fields must contain a value");
            }
        }
        catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
            
            toModify.setName(name);
            toModify.setInStock(Integer.parseInt(inStockString));
            toModify.setPrice(Double.parseDouble(priceString));
            toModify.setMax(Integer.parseInt(maxString));
            toModify.setMin(Integer.parseInt(minString));
            
            inhousePart = (InhousePart) toModify;
            inhousePart.setMachineID(Integer.parseInt(machComp));                      
        } else if(!partTypeInhouse&&outsourced.isSelected()){
            // used to save modified oursourcedPart from existing outsourcedPart
            String name = nameText.getText();
            String inStockString = inventoryText.getText();
            String priceString = priceCostText.getText();
            String maxString = maxText.getText();
            String minString = minText.getText();
            String machComp = machCompText.getText();
            
        try {
            if(name.isEmpty()||inStockString.isEmpty()||priceString.isEmpty()||
               maxString.isEmpty()||minString.isEmpty()||machComp.isEmpty()){
                throw new IllegalArgumentException("All text fields must contain a value");
            }
        }
        catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
            
            toModify.setName(name);
            toModify.setInStock(Integer.parseInt(inStockString));
            toModify.setPrice(Double.parseDouble(priceString));
            toModify.setMax(Integer.parseInt(maxString));
            toModify.setMin(Integer.parseInt(minString));
            
            outsourcePart = (OutsourcedPart) toModify;
            outsourcePart.setCompanyName(machComp);           
        } else {
        //used to save part if part type is modified from radio button selection
            inventory.deletePart(toModify);
            
            Part newPart = null;
        boolean inHousePart = inHouse.isSelected();
        
        String name = nameText.getText();
        String inStockString = inventoryText.getText();
        String priceString = priceCostText.getText();
        String maxString = maxText.getText();
        String minString = minText.getText();
        String machComp = machCompText.getText();
        
        int inStock = Integer.parseInt(inStockString);
        double price = Double.parseDouble(priceString);
        int max = Integer.parseInt(maxString);
        int min = Integer.parseInt(minString);
        
        try{
            if(inHousePart){
                int machineID = Integer.parseInt(machComp);
                newPart = new InhousePart(name, price, inStock, max, min, machineID);
            } else {
                newPart = new OutsourcedPart(name, price, inStock, max, min, machComp);
            }
        }
        catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        //add new part to the inventory
        inventory.addNewPart(newPart);
        }
        
        //return to the main screen
        Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        
//        Get the stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addPartScene);
        window.show();
    }
}