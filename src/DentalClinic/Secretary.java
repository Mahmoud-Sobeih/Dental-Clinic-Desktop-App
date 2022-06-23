package DentalClinic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Mahmoud
 */
public class Secretary extends Application {

    private TextField nameField, phoneField, addressField, emailField, searchField;
    private PasswordField passwordField, confirmPasswordField;
    private RadioButton male, female;
    private DatePicker birthdateField;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Components      
        Label nameLabel = new Label("Name");
        Label genderLabel = new Label("Gender");
        Label addressLabel = new Label("Address");
        Label birthdateLabel = new Label("Birthday");
        Label phoneLabel = new Label("Phone");
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm Password");

        nameField = new TextField();
        phoneField = new TextField();
        addressField = new TextField();
        emailField = new TextField();
        searchField = new TextField();
        searchField.setPromptText("Name,Phone");

        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        birthdateField = new DatePicker();
        birthdateField.setPromptText("MM/DD/YYYY");

        male = new RadioButton("Male");
        female = new RadioButton("Female");
        ToggleGroup group = new ToggleGroup();
        male.setToggleGroup(group);
        female.setToggleGroup(group);

        ImageView imgsearch = new ImageView(new Image(getClass().getResourceAsStream("img/search.png")));
        ImageView imghome = new ImageView(new Image(getClass().getResourceAsStream("img/home.png")));

        Button search = new Button("", imgsearch);
        Button register = new Button("Register");
        Button home = new Button("", imghome);
        Button addnew = new Button("New");
        Button update = new Button("Update");
        Button remove = new Button("Delete");

        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        GridPane grid3 = new GridPane();
        GridPane grid4 = new GridPane();

        //add components
        grid1.add(searchField, 1, 1);
        grid1.add(search, 2, 1);
        grid2.add(nameLabel, 0, 1);
        grid2.add(nameField, 1, 1);
        grid2.add(phoneLabel, 0, 2);
        grid2.add(phoneField, 1, 2);
        grid2.add(addressLabel, 0, 3);
        grid2.add(addressField, 1, 3);
        grid2.add(genderLabel, 0, 4);
        grid2.add(male, 1, 4);
        grid2.add(female, 2, 4);
        grid2.add(birthdateLabel, 0, 5);
        grid2.add(birthdateField, 1, 5);
        grid2.add(emailLabel, 0, 6);
        grid2.add(emailField, 1, 6);
        grid2.add(passwordLabel, 0, 7);
        grid2.add(passwordField, 1, 7);
        grid2.add(confirmPasswordLabel, 0, 8);
        grid2.add(confirmPasswordField, 1, 8);
        grid3.add(register, 0, 1);
        grid3.add(addnew, 2, 1);
        grid3.add(update, 3, 1);
        grid3.add(remove, 4, 1);
        grid4.add(home, 0, 0);

        HBox hbox = new HBox(grid3, grid4);
        VBox root = new VBox(grid1, grid2, hbox);
        Scene scene = new Scene(root, 800, 600);

        //deseign
        grid1.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER_LEFT);
        grid2.setHgap(2);
        grid2.setVgap(5);
        grid3.setAlignment(Pos.CENTER);
        grid4.setAlignment(Pos.CENTER);

        imgsearch.setFitHeight(20);
        imgsearch.setFitWidth(20);
        imghome.setFitHeight(30);
        imghome.setFitWidth(30);

        home.setId("bhome");
        search.setId("bsearch");

        grid1.getStyleClass().add("grid-pane");
        grid2.getStyleClass().add("grid2");
        grid3.getStyleClass().add("grid");
        grid4.getStyleClass().add("grid-pane");

        grid3.setPrefWidth(700);
        scene.getStylesheets().addAll(this.getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Secretary");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Action
        register.setOnAction((ActionEvent t) -> {
            try {
                if (nameField.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information Alert");
                    alert.setContentText("Please, Enter the name.");
                    alert.show();
                } else {
                    String query = "select sName from secretary where sName = ?";
                    Search searchObj = new Search(query, nameField.getText());
                    ResultSet rs = searchObj.getResult();
                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Information Alert");
                        alert.setContentText("This name is exist, may be this secretary is exist,\nIf not? Change this name");
                        alert.show();
                    } else if (phoneField.getText().equals("")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Information Alert");
                        alert.setContentText("Please, Enter the phone number.");
                        alert.show();
                    } else {
                        query = "select sPhone from secretary where sPhone = ?";
                        searchObj = new Search(query, phoneField.getText());
                        rs = searchObj.getResult();
                        if (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This phone number is exist, may be this secretary is exist,\nIf not? Change this number");
                            alert.show();
                        } else if (emailField.getText().equals("")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("Please, Enter the email.");
                            alert.show();
                        } else {
                            query = "select sEmail from secretary where sEmail = ?";
                            searchObj = new Search(query, emailField.getText());
                            rs = searchObj.getResult();
                            if (rs.next()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Message");
                                alert.setHeaderText("Information Alert");
                                alert.setContentText("This email is exist, may be this secretary is exist,\nIf not? Change this email");
                                alert.show();
                            } else if (passwordField.getText().equals("")) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Message");
                                alert.setHeaderText("Information Alert");
                                alert.setContentText("Please, Enter the password.");
                                alert.show();
                            } else {
                                String gender,
                                        birthday;
                                if (male.isSelected()) {
                                    gender = "male";
                                } else if (female.isSelected()) {
                                    gender = "female";
                                } else {
                                    gender = "";
                                }
                                if (birthdateField.getValue() == null) {
                                    birthday = "";
                                } else {
                                    birthday = birthdateField.getValue().toString();
                                }

                                if (passwordField.getText().equals(confirmPasswordField.getText())) {
                                    String insertQuery = "insert into secretary(sName,sPhone,sAddress,sGender,sBD,sEmail) values(?,?,?,?,?,?)";
                                    Insert insertObj = new Insert(insertQuery, nameField.getText().toLowerCase(),
                                            phoneField.getText(),
                                            addressField.getText().toLowerCase(),
                                            gender,
                                            birthday,
                                            emailField.getText().toLowerCase());
                                    insertQuery = "insert into login(email,password) values(?,?)";
                                    insertObj = new Insert(insertQuery, emailField.getText().toLowerCase(),
                                            passwordField.getText());

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Message");
                                    alert.setHeaderText("Information Alert");
                                    alert.setContentText("This secretary inserted");
                                    alert.show();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Message");
                                    alert.setHeaderText("Information Alert");
                                    alert.setContentText("check the password!!!");
                                    alert.show();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Secretary.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addnew.setOnAction((ActionEvent t) -> {
            clear();
        });

        search.setOnAction((ActionEvent t) -> {
            try {
                String query;
                String getPasswordQuery = "select password from login where email=?";
                if (Pattern.matches("\\d+", searchField.getText().toLowerCase())) {
                    query = "select sName,sPhone,sAddress,sGender,sBD,sEmail"
                            + " from secretary where sPhone = ?";
                } else {
                    query = "select sName,sPhone,sAddress,sGender,sBD,sEmail"
                            + " from secretary where sName = ?";
                }
                Search searchSecretary = new Search(query, searchField.getText().toLowerCase());
                ResultSet result = searchSecretary.getResult();
                if (result.next()) {
                    nameField.setText(result.getString(1));
                    phoneField.setText(result.getString(2));
                    addressField.setText(result.getString(3));
                    if ("male".equals(result.getString(4))) {
                        male.setSelected(true);
                    } else if("female".equals(result.getString(4))) {
                        female.setSelected(true);
                    }else{
                        male.setSelected(false);
                        female.setSelected(false);
                    }
                    if(birthdateField.getValue() != null){
                        birthdateField.setValue(LocalDate.parse(result.getString(5)));
                    }
                    emailField.setText(result.getString(6));
                }
                Search searchPassword = new Search(getPasswordQuery, emailField.getText());
                result = searchPassword.getResult();
                if (result.next()) {
                    passwordField.setText(result.getString(1));
                    confirmPasswordField.setText(passwordField.getText());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information Alert");
                    alert.setContentText("This secretary not exist !!!");
                    alert.show();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Secretary.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        remove.setOnAction((ActionEvent t) -> {
            connectToDatabase connect_to_database = connectToDatabase.getObject();
            try {
                String query = "delete from secretary where sPhone = ?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Delete this data?");
                alert.setContentText("Are you sure you want to delete this secretary ?");
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    Delete del = new Delete(query, phoneField.getText());
                    query = "delete from login where email = ?";
                    del = new Delete(query, emailField.getText().toLowerCase());
                    clear();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Secretary.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        update.setOnAction((ActionEvent t) -> {
            String oldname = nameField.getText();

            String secretaryUpdateQuery = "update secretary Set sName=? ,sPhone=? ,sAddress=? ,sGender=? ,sBD=? ,sEmail=?"
                    + " where sName = ?";
            String loginUpdateQuery = "update login Set password=? where email=?";

            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                String gender,
                        birthday;
                if (male.isSelected()) {
                    gender = "male";
                }else if (female.isSelected()) {
                    gender = "female";
                }else{
                    gender = "";
                }
                if(birthdateField.getValue() == null){
                    birthday = "";
                }else{
                    birthday = birthdateField.getValue().toString();
                }

                Update updateSecretary = new Update(secretaryUpdateQuery, nameField.getText().toLowerCase(),
                        phoneField.getText(),
                        addressField.getText().toLowerCase(),
                        gender,
                        birthday,
                        emailField.getText().toLowerCase(),
                        oldname);

                Update updateLogin = new Update(loginUpdateQuery, passwordField.getText(), emailField.getText().toLowerCase());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Information Alert");
                alert.setContentText("check the password!!!");
                alert.show();
            }
        });

        home.setOnAction((ActionEvent t) -> {
            StartingPage sp = new StartingPage();
            if (sp.getUser().equals("admin")) {
                doctorHome dh = new doctorHome();
                try {
                    dh.start(primaryStage);
                } catch (Exception ex) {
                    Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                secretaryHome sh = new secretaryHome();
                sh.start(primaryStage);
            }
        });

    }

    private void clear() {
        nameField.clear();
        phoneField.clear();
        addressField.clear();
        male.setSelected(false);
        female.setSelected(false);
        birthdateField.setValue(null);
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        searchField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
