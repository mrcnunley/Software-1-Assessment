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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nunley_c482_software1.Model.InhousePart;
import nunley_c482_software1.Model.Inventory;
import nunley_c482_software1.Model.OutsourcedPart;
import nunley_c482_software1.Model.Part;


public class AddPartController implements Initializable {

    private Inventory inventory = new Inventory();

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML private RadioButton inHouse;
    
    @FXML private RadioButton outsourced;
    
    @FXML private Label machineLabel;
    
    @FXML public TextField idText;
    
    @FXML public TextField nameText;
    
    @FXML public TextField inventoryText;
    
    @FXML public TextField priceCostText;
    
    @FXML public TextField maxText;
    
    @FXML public TextField minText;
    
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
            machineLabel.setText("Machine ID");
            machCompText.setPromptText("Mach ID");
        }
        else if(outsourced.isSelected()){
           machineLabel.setText("Company Name");
           machCompText.setPromptText("Company Name");
        }
    }
    
    @SuppressWarnings("static-access")
    public void savePart(ActionEvent event)throws IOException {
        Part newPart = null;
        boolean inHousePart = inHouse.isSelected();
        
        //Gather all of the values from text fields
        String name = nameText.getText();
        String inStockString = inventoryText.getText();
        String priceString = priceCostText.getText();
        String maxString = maxText.getText();
        String minString = minText.getText();
        String machComp = machCompText.getText();
        
        //make sure all test fields are not empty
        try {
            if(name.isEmpty()||inStockString.isEmpty()||priceString.isEmpty()||
               maxString.isEmpty()||minString.isEmpty()||machComp.isEmpty()){
                throw new IllegalArgumentException("All text fields must contain a value");
            }
        }
        catch (IllegalArgumentException e){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        
        int inStock = Integer.parseInt(inStockString);

        double price = Double.parseDouble(priceString);
        int max = Integer.parseInt(maxString);
        int min = Integer.parseInt(minString);
        
        //make sure min, max and inventory levels are correct
        try {
            if(inHousePart){
                int machineID = Integer.parseInt(machComp);
                newPart = new InhousePart(name, price, inStock, max, min, machineID);
            } else {
                newPart = new OutsourcedPart(name, price, inStock, max, min, machComp);
            }
        }
        catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        
        //add new part to the inventory
        inventory.addNewPart(newPart);
        
        //return to the main screen
        Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

}