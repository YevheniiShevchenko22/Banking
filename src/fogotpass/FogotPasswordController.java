package fogotpass;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import login.Banking;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FogotPasswordController implements Initializable {

    @FXML
    private TextField accountNo;
    @FXML
    private TextField answerr;
    @FXML
    private ComboBox<String> securityQ;

    ObservableList<String> list = FXCollections.observableArrayList("What is your pet name?",
            "What is your childhood town?", "What is your lovely fruit?");

    public void backToLogin(MouseEvent event) throws IOException {
        Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/FXMLLogin.fxml")));
    }

    public void closeApp(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void recoverPassword(MouseEvent event){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and SecurityQuestion=? and Answer=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, accountNo.getText());
            ps.setString(2, securityQ.getValue());
            ps.setString(3, answerr.getText());


            rs = ps.executeQuery();
            if (rs.next()){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Password recovery");
                a.setHeaderText("Below is your password.");
                a.setContentText("Account N: " + rs.getString("AccountNo")+"\nPIN: "+ rs.getString("PIN"));
                a.showAndWait();
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("Wrong Data.");
                a.setContentText("Please try again!");
                a.showAndWait();
            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error recovery");
            a.setContentText("There is some error. Try again!\n"+ e.getMessage());
            a.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        securityQ.setItems(list);
    }
}
