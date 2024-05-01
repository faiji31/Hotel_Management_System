package mainpkg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScController implements Initializable {

    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private Button login;
    @FXML
    private Button signupButton;
    @FXML
    private Button aboutUsSc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTypeComboBox.getItems().addAll("Navigator", "Field Service Officer",
                "Medical Assistant", "Case Handler", "Commanding Officer",
                 "Flight operation Officer", "IT  Specialist", "Supply Officer"
                ,"Pilot","Tower Operator");
        //defaultIdpass
        userIdTextField.setText("admin");
        passwordTextField.setText("12345678");
    }


    @FXML
    private void loginButtonOnClick(ActionEvent event) throws IOException {
        String userType = userTypeComboBox.getValue();
        if (userType == null) {
            showErrorAlert("Error", "Please select a user type.");
            return;
        }

        String username = userIdTextField.getText();
        String password = passwordTextField.getText();

        boolean isValidLogin = SignUpFile.SignUpFileRead(userType, username, password);

        if (isValidLogin) {

            String userFullName = SignUpFile.FileRead_forUserName(userType, username, password);

            Stage currentStage = (Stage) login.getScene().getWindow();
            if (userType.equals("Navigator")) {
                //SceneChange newScene= new SceneChange();
                //newScene.Scenechanger(currentStage, "User_1_MainDashboard.fxml");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Jubaer/NavigatorDashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("Field Service Officer")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Jubaer.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("Medical Assistant")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Faiji/MedicalAssistantDashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("Case Handler")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Faiji/CasehandlerDashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("Commanding Officer")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Nazir.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("Flight Operation Officer")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Nazir.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
            }
            if (userType.equals("IT Specialist")) {
                SceneChange newScene = new SceneChange();
                newScene.Scenechanger(currentStage, "Atique.fxml");
            }
           
             if (userType.equals("Pilot")) {
                SceneChange newScene = new SceneChange();
                newScene.Scenechanger(currentStage, "Iram.fxml");
            }
              if (userType.equals("Tower Operator")) {
                SceneChange newScene = new SceneChange();
                newScene.Scenechanger(currentStage, "/Iram/TowerOperator.fxml");
            }
        } else {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Unable to Login");
            a.setContentText("UserID and Password is Invalid");
            a.showAndWait();

        }
    }

    @FXML
    private void signUpButtonOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sign Up.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) signupButton.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void AboutUSScOnCLk(ActionEvent event) {
    }
}
