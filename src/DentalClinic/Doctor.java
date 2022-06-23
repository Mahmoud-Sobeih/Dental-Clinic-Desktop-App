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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Doctor extends Application {

    private TextField nameField, phoneField, addressField, emailField, jobDescreptionField, searchField;
    private RadioButton male, female;
    private DatePicker birthdate;

    @Override
    public void start(Stage primaryStage) {

        //Components      
        Label nameLabel = new Label("Name");
        Label genderLabel = new Label("Gender");
        Label addressLabel = new Label("Address");
        Label birthdateLabel = new Label("Birthdate");
        Label phoneLabel = new Label("Phone");
        Label emailLabel = new Label("Email");
        Label jobDescreptionLabel = new Label("Job Describtion");

        nameField = new TextField();
        phoneField = new TextField();
        emailField = new TextField();
        addressField = new TextField();
        jobDescreptionField = new TextField();
        searchField = new TextField();
        searchField.setPromptText("Name,Phone");

        birthdate = new DatePicker();
        birthdate.setPromptText("MM/DD/YYYY");

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
        grid2.add(emailLabel, 0, 3);
        grid2.add(emailField, 1, 3);
        grid2.add(addressLabel, 0, 4);
        grid2.add(addressField, 1, 4);
        grid2.add(genderLabel, 0, 5);
        grid2.add(male, 1, 5);
        grid2.add(female, 2, 5);
        grid2.add(birthdateLabel, 0, 6);
        grid2.add(birthdate, 1, 6);
        grid2.add(jobDescreptionLabel, 0, 7);
        grid2.add(jobDescreptionField, 1, 7);
        grid3.add(register, 0, 1);
        grid3.add(addnew, 2, 1);
        grid3.add(update, 3, 1);
        grid3.add(remove, 4, 1);
        grid4.add(home, 0, 0);

        HBox hbox = new HBox(grid3, grid4);
        VBox root = new VBox(grid1, grid2, hbox);
        Scene scene = new Scene(root, 800, 550);

        //deseign
        grid1.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER_LEFT);
        grid3.setAlignment(Pos.CENTER);
        grid4.setAlignment(Pos.CENTER);

        grid1.getStyleClass().add("grid-pane");
        grid2.getStyleClass().add("grid2");
        grid3.getStyleClass().add("grid");
        grid4.getStyleClass().add("grid-pane");

        grid2.setPrefWidth(500);
        grid3.setPrefWidth(700);

        imgsearch.setFitHeight(20);
        imgsearch.setFitWidth(20);
        imghome.setFitHeight(30);
        imghome.setFitWidth(30);

        home.setId("bhome");
        search.setId("bsearch");
        scene.getStylesheets().addAll(this.getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Doctors");
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
                    String query = "select dName from doctor where dName = ?";
                    Search searchObj = new Search(query, nameField.getText());
                    ResultSet rs = searchObj.getResult();
                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Information Alert");
                        alert.setContentText("This name is exist, may be this doctor is exist,\nIf not? Change this name");
                        alert.show();
                    } else if (phoneField.getText().equals("")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Information Alert");
                        alert.setContentText("Please, Enter the phone number.");
                        alert.show();
                    } else {
                        query = "select dPhone from doctor where dPhone = ?";
                        searchObj = new Search(query, phoneField.getText());
                        rs = searchObj.getResult();
                        if (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This phone number is exist, may be this doctor is exist,\nIf not? Change this number");
                            alert.show();
                        } else if (!emailField.getText().equals("")) {
                            query = "select dEmail from doctor where dEmail = ?";
                            searchObj = new Search(query, emailField.getText());
                            rs = searchObj.getResult();
                            if (rs.next()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Message");
                                alert.setHeaderText("Information Alert");
                                alert.setContentText("This email is exist, may be this doctor is exist,\nIf not? Change this email");
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
                                if (birthdate.getValue() == null) {
                                    birthday = "";
                                } else {
                                    birthday = birthdate.getValue().toString();
                                }
                                String insertQuery = "insert into doctor(dName,dPhone,dEmail,dAddress,dGender,dBD,jobDescribtion) values(?,?,?,?,?,?,?)";
                                Insert insertObj = new Insert(insertQuery, nameField.getText().toLowerCase(),
                                        phoneField.getText(),
                                        emailField.getText().toLowerCase(),
                                        addressField.getText().toLowerCase(),
                                        gender,
                                        birthday,
                                        jobDescreptionField.getText().toLowerCase());
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This doctor inserted");
                            alert.show();
                        }
                    }
                }
            } catch (SQLException e) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addnew.setOnAction((ActionEvent t) -> {
            clear();
        });

        search.setOnAction((ActionEvent t) -> {
            try {
                String query;
                if (Pattern.matches("\\d+", searchField.getText().toLowerCase())) {
                    query = "select dName,dPhone,dEmail,dAddress,dGender,dBD,jobDescribtion"
                            + " from doctor where dPhone = ?";
                } else {
                    query = "select dName,dPhone,dEmail,dAddress,dGender,dBD,jobDescribtion"
                            + " from doctor where dName = ?";
                }
                Search searchObj = new Search(query, searchField.getText().toLowerCase());
                ResultSet result = searchObj.getResult();
                if (result.next()) {
                    nameField.setText(result.getString(1));
                    phoneField.setText(result.getString(2));
                    emailField.setText(result.getString(3));
                    addressField.setText(result.getString(4));
                    if ("male".equals(result.getString(5))) {
                        male.setSelected(true);
                    } else if ("female".equals(result.getString(5))) {
                        female.setSelected(true);
                    } else {
                        male.setSelected(false);
                        female.setSelected(false);
                    }
                    if (result.getString(6) != null) {
                        birthdate.setValue(LocalDate.parse(result.getString(6)));
                    }
                    jobDescreptionField.setText(result.getString(7));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information Alert");
                    alert.setContentText("This doctor not exist!!");
                    alert.show();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        remove.setOnAction((ActionEvent t) -> {
            try {
                String query = "delete from doctor where dPhone = ?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Delete this data?");
                alert.setContentText("Are you sure you want to delete this doctor ?");
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    Delete del = new Delete(query, phoneField.getText());
                    clear();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        update.setOnAction((ActionEvent t) -> {
            String oldname = nameField.getText();
            String query = "update doctor Set dName = ?, dPhone = ?, dEmail = ?, dAddress = ?, dGender = ?, dBD = ?, jobDescribtion = ?"
                    + " where dName = ?";
            String g = "";
            if (male.isSelected()) {
                g = "male";
            }
            if (female.isSelected()) {
                g = "female";
            }
            Update updateObj = new Update(query, nameField.getText().toLowerCase(),
                    phoneField.getText(),
                    emailField.getText().toLowerCase(),
                    addressField.getText().toLowerCase(),
                    g,
                    birthdate.getValue().toString(),
                    jobDescreptionField.getText().toLowerCase(),
                    oldname);
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
        emailField.clear();
        male.setSelected(false);
        female.setSelected(false);
        birthdate.setValue(null);
        jobDescreptionField.clear();
        searchField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
