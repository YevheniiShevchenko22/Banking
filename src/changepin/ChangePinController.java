package changepin;

import dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import login.FXMLLoginController;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ChangePinController implements Initializable {

    //private static String ac = FXMLLoginController.acc;

    @FXML
    private PasswordField old_pin;
    @FXML
    private PasswordField new_pin;
    @FXML
    private PasswordField confirm_pin;

    DashboardController d = new DashboardController();

    public void changePin(MouseEvent event){

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);
            ps.setString(2, old_pin.getText());

            rs = ps.executeQuery();

            if (rs.next()){

                if (new_pin.getText().equals(confirm_pin.getText())){

                    String sql1 = "UPDATE userdata SET pin ='"+new_pin.getText()+"'WHERE AccountNo='"+FXMLLoginController.acc+"'";
                    ps= con.prepareStatement(sql1);
                    ps.execute();

                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("PIN Changed");
                    a.setHeaderText("PIN Changed Successfully.");
                    a.setContentText("You PIN has been changed\nnow you have to login again with new PIN.");
                    a.showAndWait();

                    old_pin.setText("");
                    new_pin.setText("");
                    confirm_pin.setText("");

                    d.logout(event);

                }

            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("Error in PIN");
                a.setContentText("You don't have enough balance. Enter again!");
                a.showAndWait();
            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Account not created");
            a.setContentText("Your account is not created. Try again!");
            a.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
