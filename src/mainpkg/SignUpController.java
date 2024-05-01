package mainpkg;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

    @FXML
    private TextField newUserNameTextField;
    @FXML
    private TextField newUserPhoneNumberTextField;

    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private DatePicker newUserDob;
    @FXML
    private Button goback;
    @FXML
    private TextField newNameTextField;
    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private Button signup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTypeComboBox.getItems().addAll("Navigator", "Field Service Officer",
                "Medical Assistant", "Case Handler",
                "Commanding Officer",
                "Flight Operation Officer",
                "IT Specialist",
                "Supply Officer", "Pilot", "Tower Operator");
    }

    @FXML
    private void goBackButtonOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginSc.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) goback.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (IOException e) {
            showAlert("Error", "Error Loading Scene", "An error occurred while loading the login scene.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void createAccountButtonOnClick(ActionEvent event) {
        String fullName = newNameTextField.getText();
        String userName = newUserNameTextField.getText();
        String phoneNumber = newUserPhoneNumberTextField.getText();
        String password = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        LocalDate dob = newUserDob.getValue();
        String userType = userTypeComboBox.getValue(); // Get selected user type

        // Check if any of the fields are empty
        if (fullName.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty() || dob == null || userType == null) {
            showAlert("Error", "Incomplete Information", "Please fill in all fields.");
            return; // Exit the method if any field is empty
        }

        // Check if name contains any number
        if (fullName.matches(".*\\d.*")) {
            showAlert("Error", "Invalid Name", "Name should not contain any numbers.");
            return; // Exit the method if name contains a number
        }

        // Check phone number format and length
        if (!phoneNumber.matches("01\\d{9}")) {
            showAlert("Error", "Invalid Phone Number", "Phone number should start with '01' and have a total of 11 digits.");
            return; // Exit the method if phone number format is invalid
        }

        // Check password length
        if (password.length() < 8) {
            showAlert("Error", "Invalid Password", "Password should be at least 8 characters long.");
            return; // Exit the method if password length is less than 8
        }

        // Check minimum age (18 years) for date of birth
        LocalDate currentDate = LocalDate.now();
        if (dob.plusYears(18).isAfter(currentDate)) {
            showAlert("Error", "Invalid Date of Birth", "Minimum age requirement is 18 years old.");
            return; // Exit the method if age requirement is not met
        }

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Password Mismatch", "Passwords do not match.");
            return; // Exit the method if passwords don't match
        }

        User newUser = new User(fullName, userName, phoneNumber, password, dob);
        Boolean success = SignUpFile.SignUpFileWrite(newUser, userTypeComboBox.getValue());
        if (success) {
            if (success) {
                // Show congratulatory alert
                showAlert("Congratulations", "Signup Successful", "Your signup is complete.");
            } else {
                // Show error alert if signup failed
                showAlert("Error", "Signup Failed", "An error occurred while signing up. Please try again.");
            }

            // Clear input fields after signup attempt
            newNameTextField.clear();
            newUserNameTextField.clear();
            newUserPhoneNumberTextField.clear();
            newPasswordTextField.clear();
            confirmPasswordTextField.clear();
            newUserDob.setValue(null);
            userTypeComboBox.getSelectionModel().clearSelection(); // Clear user type selection
        }
    }
}
