package createacc;

import com.sun.glass.ui.CommonDialogs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.FileChooser;
import login.Banking;
import javafx.stage.FileChooser.ExtensionFilter;

public class CreateAccountController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private File file;
    private FileInputStream fileInputStream;

    @FXML
    private ImageView pic;

    @FXML
    private TextField name;
    @FXML
    private TextField idCardNo;
    @FXML
    private TextField mobileNo;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
    @FXML
    private TextField accountNo;
    @FXML
    private TextField pin;
    @FXML
    private TextField balance;
    @FXML
    private TextField answer;

    @FXML
    private DatePicker dob;

    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> martialStatus;
    @FXML
    private ComboBox<String> religion;
    @FXML
    private ComboBox<String> accountType;
    @FXML
    private ComboBox<String> questions;

    ObservableList<String> list = FXCollections.observableArrayList("Male", "Female", "Other");
    ObservableList<String> list1 = FXCollections.observableArrayList("Single", "Married");
    ObservableList<String> list2 = FXCollections.observableArrayList("Muslims", "Christian", "Hinduism", "Buddhism",
            "Jainism", "Taoisism", "Sikhism", "Spiritism", "Judaism", "Atheism");
    ObservableList<String> list3 = FXCollections.observableArrayList("Saving", "Current");
    ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?",
            "What is your childhood town?", "What is your lovely fruit?");


    public void backToLogin(MouseEvent event) throws IOException {
        Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/FXMLLogin.fxml")));
    }

    public void closeApp(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    public void setUpPic(MouseEvent event){
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images files", "*.png", "*.jpg", "*.jpeg"));
        file =fileChooser.showOpenDialog(null);
        if (file!=null){
            Image img = new Image(file.toURI().toString(), 150, 150, true, true);
            pic.setImage(img);
            pic.setPreserveRatio(true);
        }

    }

    public boolean validateName(){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Name");
            a.setHeaderText("Your name not created");
            a.setContentText("Please enter character only in name. Try again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateMobileN(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileNo.getText());
        if(m.find() && m.group().equals(mobileNo.getText())){
            return true;
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Mobile Number");
            a.setHeaderText("Your mobile number not created");
            a.setContentText("Please enter correct mobile number. Try again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateIdCardN(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(idCardNo.getText());
        if(m.find() && m.group().equals(idCardNo.getText())){
            return true;
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Id Card Number");
            a.setHeaderText("Your Id card number not created");
            a.setContentText("Please enter correct Id card number. Try again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateBalance(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(balance.getText());
        if(m.find() && m.group().equals(balance.getText())){
            return true;
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Invalid Balance");
            a.setHeaderText("Your balance is wrong");
            a.setContentText("Please enter correct Id card number. Try again!");
            a.showAndWait();
            return false;
        }
    }

    public void clearAllFields(){
        name.clear();
        idCardNo.clear();
        mobileNo.clear();
        gender.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        martialStatus.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        address.clear();
        pin.clear();
        accountType.getSelectionModel().clearSelection();
        balance.clear();
        questions.getSelectionModel().clearSelection();
        answer.clear();
        Image img = new Image("/images/150.png");
        pic.setImage(img);
        accountNo.setText(String.valueOf(generateAccountN()));
    }

    public int generateAccountN(){
        Random random = new Random();
        int num = random.nextInt(899999)+100000;
        return num;
    }

    public void newAccount(MouseEvent event){
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");

            if (validateName() && validateMobileN() && validateIdCardN() && validateBalance()) {
                String sql = "INSERT INTO userdata (Name, ICN, MobileNo, Gender, MartialStatus, Religion, DOB, City, Address, " +
                        "AccountNo, PIN, AccountType, Balance, SecurityQuestion, Answer, ProfilePicture ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);

                ps.setString(1, name.getText());
                ps.setString(2, idCardNo.getText());
                ps.setString(3, mobileNo.getText());
                ps.setString(4, gender.getValue());
                ps.setString(5, martialStatus.getValue());
                ps.setString(6, religion.getValue());
                ps.setString(7, ((TextField) dob.getEditor()).getText());
                ps.setString(8, city.getText());
                ps.setString(9, address.getText());
                ps.setString(10, accountNo.getText());
                ps.setString(11, pin.getText());
                ps.setString(12, accountType.getValue());
                ps.setString(13, balance.getText());
                ps.setString(14, questions.getValue());
                ps.setString(15, answer.getText());
                fileInputStream = new FileInputStream(file);
                ps.setBinaryStream(16, (InputStream) fileInputStream, (int) file.length());

                int i = ps.executeUpdate();
                if (i > 0) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Account Created");
                    a.setHeaderText("Account created successfully.");
                    a.setContentText("Your account has been created successfully.\n You can now login with your account number and PIN.");
                    a.showAndWait();

                    clearAllFields();

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setHeaderText("Account not created");
                    a.setContentText("Your account is not created. Try again!");
                    a.showAndWait();
                }
            }

        }catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Error in creating account");
            a.setContentText("Your account is not created.\n There is some technical issue.\n"+e.getMessage());
            a.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gender.setItems(list);
        religion.setItems(list2);
        martialStatus.setItems(list1);
        accountType.setItems(list3);
        questions.setItems(list4);
        accountNo.setText(String.valueOf(generateAccountN()));
        accountNo.setEditable(false);

    }
}
