package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import login.FXMLLoginController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class MainScreenController implements Initializable {

    //private static String ac = FXMLLoginController.acc;

    @FXML
    private Label name;
    @FXML
    private Label body;
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

                name.setText(rs.getString("Name"));

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
    public void transactionHistory(MouseEvent event) throws IOException {

        Parent fxml;
        fxml = load(getClass().getResource("/transactionhistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void transferMoney(MouseEvent event) throws IOException {

        Parent fxml;
        fxml = load(getClass().getResource("/transferamount/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        body.setText("The Home Bank is a Dutch multinational banking and financial \n" +
                "services corporation headquartered in Amsterdam. Its primary businesses are retail \n" +
                "banking, direct banking, commercial banking, investment banking, wholesale banking, \n" +
                "private banking, asset management, and insurance services. With total assets of US$1.1\n" +
                "trillion, it is one of the biggest banks in the world. It consistently ranks among \n" +
                "the top 30 largest banks globally. It is also among the top 10 in the list of largest\n" +
                "European companies by revenue.\n" +
                "\n" +
                "Home Bank is the Dutch member of the Inter-Alpha Group of Banks, a cooperative consortium \n" +
                "of 11 prominent most important European banks. Since the creation in 2012, Home Bank\n" +
                "is a member in the list of global systemically important banks.\n");
        setInfo();
    }
}
