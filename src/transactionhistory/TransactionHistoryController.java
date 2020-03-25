package transactionhistory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.FXMLLoginController;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;

public class TransactionHistoryController implements Initializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @FXML
    private TableView<History> withdraw_table;
    @FXML
    private TableColumn<History, String> withdraw_account_number;
    @FXML
    private TableColumn<History, String> withdraw_amount;
    @FXML
    private TableColumn<History, String> withdraw_remaining_amount;
    @FXML
    private TableColumn<History, String> withdraw_date;
    @FXML
    private TableColumn<History, String> withdraw_time;

    @FXML
    private TableView<History> deposit_table;
    @FXML
    private TableColumn<History, String> deposit_account_number;
    @FXML
    private TableColumn<History, String> deposit_amount;
    @FXML
    private TableColumn<History, String> deposit_new_amount;
    @FXML
    private TableColumn<History, String> deposit_date;
    @FXML
    private TableColumn<History, String> deposit_time;

    @FXML
    private TableView<History> transfer_table;
    @FXML
    private TableColumn<History, String> transfer_account_number;
    @FXML
    private TableColumn<History, String> transfer_amount;
    @FXML
    private TableColumn<History, String> transfer_send_to;
    @FXML
    private TableColumn<History, String> transfer_date;
    @FXML
    private TableColumn<History, String> transfer_time;

    @FXML
    private TableView<History> money_table;
    @FXML
    private TableColumn<History, String> money_account_number;
    @FXML
    private TableColumn<History, String> money_amount;
    @FXML
    private TableColumn<History, String> money_received_from;
    @FXML
    private TableColumn<History, String> money_date;
    @FXML
    private TableColumn<History, String> money_time;

    public TransactionHistoryController() {
    }

    public void withdraw(){
        withdraw_account_number.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
        withdraw_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
        withdraw_remaining_amount.setCellValueFactory(new PropertyValueFactory<History,String>("generetic"));
        withdraw_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
        withdraw_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM withdraw WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();

            while (rs.next()){

                list.add(new History(rs.getString("AccountNo"), rs.getString("WithdrawAmount"),
                        rs.getString("RemainingAmount"), rs.getString("Date"), rs.getString("Time")));

            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error in Fetching Data");
            a.setContentText("There is error in fetching data. Try again!");
            a.showAndWait();
        }
        withdraw_table.setItems(list);
    }

    public void deposit(){
        deposit_account_number.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
        deposit_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
        deposit_new_amount.setCellValueFactory(new PropertyValueFactory<History,String>("generetic"));
        deposit_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
        deposit_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM deposit WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();

            while (rs.next()){

                list.add(new History(rs.getString("AccountNo"), rs.getString("DepositAmount"),
                        rs.getString("NewAmount"), rs.getString("Date"), rs.getString("Time")));

            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error in Fetching Data");
            a.setContentText("There is error in fetching data. Try again!");
            a.showAndWait();
        }
        deposit_table.setItems(list);
    }

    public void transferAmount(){
        transfer_account_number.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
        transfer_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
        transfer_send_to.setCellValueFactory(new PropertyValueFactory<History,String>("generetic"));
        transfer_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
        transfer_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM transferamount WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();

            while (rs.next()){

                list.add(new History(rs.getString("AccountNo"), rs.getString("Amount"),
                        rs.getString("SendTo"), rs.getString("Date"), rs.getString("Time")));

            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error in Fetching Data");
            a.setContentText("There is error in fetching data. Try again!");
            a.showAndWait();
        }
        transfer_table.setItems(list);
    }

    public void recieveMoney(){
        money_account_number.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
        money_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
        money_received_from.setCellValueFactory(new PropertyValueFactory<History,String>("generetic"));
        money_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
        money_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM transferamount WHERE SendTo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();

            while (rs.next()){

                list.add(new History(rs.getString("SendTo"), rs.getString("DepositAmount"),
                        rs.getString("AccountNo"), rs.getString("Date"), rs.getString("Time")));

            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error in Fetching Data");
            a.setContentText("There is error in fetching data. Try again!");
            a.showAndWait();
        }
        money_table.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        withdraw();
        deposit();
        transferAmount();
        recieveMoney();
    }
}
