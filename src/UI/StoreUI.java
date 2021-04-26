package UI;

import GenericStore.RentalAgreement;
import GenericStore.Tool;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * UI for the program. Not necessary, but it felt a lot easier for inputting values for field testing
 */
public class StoreUI extends Application implements Initializable {
    String datePattern = "M/d/yy";

    //Error messages
    private final String toolCodeError = "Please select a tool code from the list.";
    private final String dateError = "Please select a checkout date.";
    private final String rentalError = "Please input a number of rental days greater than 0.";
    private final String discountError = "Please input a discount percentage between 0-100";


    @FXML
    private ComboBox toolList;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField discountPercentField;
    @FXML
    private TextField rentalDayField;
    @FXML
    private Button checkout;

    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("StoreUI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Generic Hardware Store");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toolList.getItems().addAll("LADW", "CHNS", "JAKR", "JAKD");
        numericOnly(rentalDayField);
        numericOnly(discountPercentField);
        datePicker.setConverter(
            new StringConverter<LocalDate>() {
                final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

                @Override
                public String toString(LocalDate date) {
                    return (date != null) ? dateFormatter.format(date) : "";
                }

                @Override
                public LocalDate fromString(String string) {
                    return (string != null && !string.isEmpty())
                            ? LocalDate.parse(string, dateFormatter)
                            : null;
                }
            }
        );
        checkout.setOnAction(event -> checkout());
    }

    /**
     * Only allows inputted values to the passed TextField to be numerical
     * @param field TextField
     */
    public static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Checks all fields for empty values, or invalid inputs.
     * @return error message built based on inputted values.
     */
    private String fieldsFilled(){
        StringBuilder errorMessage = new StringBuilder();
        if(toolList.getValue() == null){
            errorMessage.append(toolCodeError + "\n");
        }
        if(datePicker.getValue() == null){
            errorMessage.append(dateError + "\n");
        }
        if(discountPercentField.getText().isEmpty()){
            errorMessage.append(discountError + "\n");
        }
        try{
            if(Integer.parseInt(discountPercentField.getText()) > 100){
                errorMessage.append(discountError + "\n");
            }
        } catch (NumberFormatException e){
            //Can never happen here due to limiting of values in input box
        }

        if(rentalDayField.getText().isEmpty()){
            errorMessage.append(rentalError + "\n");
        }

        return errorMessage.toString();
    }

    /**
     * Action taken when button is pressed. Generates a rental agreement and outputs the information to the user in a dialog.
     */
    private void checkout(){

        //check for empty fields or invalid inputs
        String errors = fieldsFilled();

        //display error to user if applicable
        if(!errors.isEmpty()){
            alert.contentTextProperty().set(errors);
            alert.show();
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        String toolCode = this.toolList.getValue().toString();
        String date = formatter.format(this.datePicker.getValue());
        int rentalDays = Integer.parseInt(this.rentalDayField.getText());
        int discountPercent = Integer.parseInt(this.discountPercentField.getText());
        try{
            Tool tool = new Tool(toolCode);
            RentalAgreement agreement = new RentalAgreement(rentalDays, date, tool, discountPercent);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Rental Agreement");
            alert.setTitle("Generated Rental Agreement");
            alert.contentTextProperty().set(agreement.getRentalAgreementOutput());
            alert.show();
        } catch (Exception e){
            //Cannot happen due to error catching above and user input restrictions.
            //Unit testing for RentalAgreement tests tests this exception throw
        }

    }
}
