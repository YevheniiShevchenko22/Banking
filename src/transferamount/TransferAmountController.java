package transferamount;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.FXMLLoginController;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class TransferAmountController implements Initializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    //private static String ac = FXMLLoginController.acc;

    @FXML
    private Label account_no;
    @FXML
    private Label balance;
    @FXML
    private PasswordField pin_field;
    @FXML
    private TextField account_no_field;
    @FXML
    private TextField amt_field;

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int hour = cal.get(Calendar.HOUR);
    int minutes = cal.get(Calendar.MINUTE);
    int seconds = cal.get(Calendar.SECOND);
    int daynight = cal.get(Calendar.AM_PM);

    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    Date d = new Date();
    String date = dateformat.format(d);

    LocalTime localTime = LocalTime.now();
    DateTimeFormatter dt = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time = localTime.format(dt);

    public void setInfo(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);

            rs = ps.executeQuery();

            if (rs.next()){

                account_no.setText(rs.getString("AccountNo"));
                balance.setText(rs.getString("Balance"));

            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Account not created");
            a.setContentText("Your account is not created. Try again!");
            a.showAndWait();
        }
    }

    public void searchButton(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? ";
            ps = con.prepareStatement(sql);

            ps.setString(1, account_no_field.getText());

            rs = ps.executeQuery();

            if (rs.next()){

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Account Information");
                a.setHeaderText("Below is the information of account:");
                a.setContentText("Account number: "+account_no_field.getText()+"\nName: "+rs.getString("Name")+"\nMobile number: "+rs.getString("MobileNo"));
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

    public void transferAmountButton() throws SQLException, ClassNotFoundException{

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, FXMLLoginController.acc);
            ps.setString(2, pin_field.getText());

            rs = ps.executeQuery();

            if (rs.next()){

                int transfer_amt = Integer.parseInt(amt_field.getText());
                int ta = Integer.parseInt(balance.getText());
                if (transfer_amt>ta){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setHeaderText("Error in Transfer Money.");
                    a.setContentText("You don't have enough balance. Enter again!");
                    a.showAndWait();
                }else{

                    int total = ta-transfer_amt;
                    String sql1 = "UPDATE userdata SET Balance ='"+total+"'WHERE AccountNo='"+FXMLLoginController.acc+"'";
                    ps= con.prepareStatement(sql1);
                    ps.execute();

                    String sql2 = " SELECT * FROM userdata Where AccountNo=?";
                    ps = con.prepareStatement(sql2);
                    ps.setString(1, account_no_field.getText());
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        int current_amt = Integer.parseInt(amt_field.getText());
                        int previous_amt = Integer.parseInt(rs.getString("Balance"));


                        int total1 = current_amt + previous_amt;

                        String sql3 = "UPDATE userdata SET Balance ='" + total1 + "'WHERE AccountNo='" + account_no_field.getText() + "'";
                        ps = con.prepareStatement(sql3);
                        ps.execute();

                        String sql4 = "INSERT INTO transferamount (AccountNO, Amount, SendTo, Date, Time) VALUES(?,?,?,?,?)";
                        ps = con.prepareStatement(sql4);

                        ps.setString(1,FXMLLoginController.acc);
                        ps.setString(2,String.valueOf(amt_field.getText()));
                        ps.setString(3,String.valueOf(account_no_field.getText()));
                        ps.setString(4,date);
                        ps.setString(5,time);

                        int i = ps.executeUpdate();
                        if (i>0){
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Amount Transfer");
                            a.setHeaderText("Amount Transfer Successfully");
                            a.setContentText("Amount "+current_amt+" has been successfully transferred.\n"+"To account number = "+account_no_field.getText());
                            a.showAndWait();

                            account_no_field.setText("");
                            amt_field.setText("");
                            pin_field.setText("");
                            balance.setText(String.valueOf(total));

                        }

                    }else{
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setHeaderText("Error in Login");
                        a.setContentText("Your account number is wrong. Enter again!!! ");
                        a.showAndWait();

                    }
                }

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
        setInfo();
    }
}
