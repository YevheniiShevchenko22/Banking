package accountinfo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import login.FXMLLoginController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class AccountInfoController implements Initializable {

    //private static String ac = FXMLLoginController.acc;

    @FXML
    private Text accont_no;
    @FXML
    private Text sex;
    @FXML
    private Text account_type;
    @FXML
    private Text religion;
    @FXML
    private Label balance;
    @FXML
    private Pane dashboard_main;

    public void setInfo(){

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();
            if (rs.next()){

                balance.setText(rs.getString("balance"));
                accont_no.setText(rs.getString("AccountNo"));
                sex.setText(rs.getString("Gender"));
                account_type.setText(rs.getString("AccountType"));
                religion.setText(rs.getString("Religion"));

            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("Error in login");
                a.setContentText("Your account your account number or password is wrong. Enter again!");
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

    @FXML
    public void withdraw(MouseEvent event) throws IOException {

        Parent fxml;
        fxml = load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void deposit(MouseEvent event) throws IOException {

        Parent fxml;
        fxml = load(getClass().getResource("/deposit/DepositAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfo();
    }
}
