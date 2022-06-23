package DentalClinic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Booking extends Application {

    private TextField nameField, phoneField, emailField, addressField, jobField, statusField, ResultOfCheckupField, paidField, remainingField, doctorNameField, timeField, searchField;
    private DatePicker date, birthdate;
    private RadioButton male, female;

    @Override
    public void start(Stage primaryStage) {

        //Components      
        Label nameLabel = new Label("Name");
        Label genderLabel = new Label("Gender");
        Label addressLabel = new Label("Address");
        Label birthdatelabel = new Label("Birthday");
        Label phoneLabel = new Label("Phone");
        Label emailLabel = new Label("Email");
        Label jobLabel = new Label("Job");
        Label appintmentLabel = new Label("Appointment");
        Label statusLabel = new Label("Status");
        Label ResultOfCheckupLabel = new Label("The result of checkup");
        Label paidLabel = new Label("Paid");
        Label remainingLabel = new Label("Remaining");
        Label doctorNameLabel = new Label("Doctor");

        nameField = new TextField();
        phoneField = new TextField();
        addressField = new TextField();
        jobField = new TextField();
        statusField = new TextField();
        ResultOfCheckupField = new TextField();
        paidField = new TextField();
        remainingField = new TextField();
        doctorNameField = new TextField();
        emailField = new TextField();
        timeField = new TextField();
        searchField = new TextField();
        searchField.setPromptText("Name,Phone");

        date = new DatePicker();
        birthdate = new DatePicker();
        date.setPromptText("MM/DD/YYYY");
        birthdate.setPromptText("MM/DD/YYYY");
        timeField.setPromptText("HHMMSS");

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
        ScrollPane scroll = new ScrollPane();

        //add components
        grid1.add(searchField, 1, 1);
        grid1.add(search, 2, 1);
        grid2.add(nameLabel, 0, 2);
        grid2.add(nameField, 1, 2);
        grid2.add(phoneLabel, 0, 3);
        grid2.add(phoneField, 1, 3);
        grid2.add(emailLabel, 0, 4);
        grid2.add(emailField, 1, 4);
        grid2.add(addressLabel, 0, 5);
        grid2.add(addressField, 1, 5);
        grid2.add(genderLabel, 0, 6);
        grid2.add(male, 1, 6);
        grid2.add(female, 2, 6);
        grid2.add(birthdatelabel, 0, 7);
        grid2.add(birthdate, 1, 7);
        grid2.add(jobLabel, 0, 8);
        grid2.add(jobField, 1, 8);
        grid2.add(doctorNameLabel, 0, 9);
        grid2.add(doctorNameField, 1, 9);
        grid2.add(statusLabel, 0, 10);
        grid2.add(statusField, 1, 10);
        grid2.add(ResultOfCheckupLabel, 0, 11);
        grid2.add(ResultOfCheckupField, 1, 11);
        grid2.add(appintmentLabel, 0, 12);
        grid2.add(date, 1, 12);
        grid2.add(timeField, 2, 12);
        grid2.add(paidLabel, 0, 13);
        grid2.add(paidField, 1, 13);
        grid2.add(remainingLabel, 0, 14);
        grid2.add(remainingField, 1, 14);
        grid3.add(register, 0, 1);
        grid3.add(addnew, 2, 1);
        grid3.add(update, 3, 1);
        grid3.add(remove, 4, 1);
        grid4.add(home, 0, 0);

        HBox hbox = new HBox(grid3, grid4);
        scroll.setContent(grid2);
        VBox root = new VBox(grid1, scroll, hbox);
        Scene scene = new Scene(root, 800, 600);

        //deseign
        grid1.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER_LEFT);
        grid3.setAlignment(Pos.CENTER);
        grid4.setAlignment(Pos.CENTER);

        imgsearch.setFitHeight(20);
        imgsearch.setFitWidth(20);
        imghome.setFitHeight(30);
        imghome.setFitWidth(30);

        home.setId("bhome");
        search.setId("bsearch");

        grid3.setPrefWidth(700);

        grid1.getStyleClass().add("grid-pane");
        grid2.getStyleClass().add("grid-pane");
        grid3.getStyleClass().add("grid");
        grid4.getStyleClass().add("grid-pane");
        scene.getStylesheets().addAll(this.getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Booking");
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
                    String query = "select pName from patient where pName = ?";
                    Search searchObj = new Search(query, nameField.getText());
                    ResultSet rs = searchObj.getResult();
                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText("Information Alert");
                        alert.setContentText("This name is exist, may be this patient is exist,\nIf not? Change this name");
                        alert.show();
                    } else if (!emailField.getText().equals("")) {
                        query = "select pEmail from patient where pEmail = ?";
                        searchObj = new Search(query, emailField.getText());
                        rs = searchObj.getResult();
                        if (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This email is exist, may be this patient is exist,\nIf not? Change this email");
                            alert.show();
                        } else if (phoneField.getText().equals("")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("Please, Enter the phone number.");
                            alert.show();
                        } else {
                            query = "select pPhone from patient where pPhone = ?";
                            searchObj = new Search(query, phoneField.getText());
                            rs = searchObj.getResult();
                            if (rs.next()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Message");
                                alert.setHeaderText("Information Alert");
                                alert.setContentText("This phone number is exist, may be this patient is exist,\nIf not? Change this number");
                                alert.show();
                            } else {
                                if (date.getValue() == null || timeField.getText().equals("")) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Message");
                                    alert.setHeaderText("Information Alert");
                                    alert.setContentText("Please, Enter the appointment.");
                                    alert.show();
                                } else {
                                    query = "select appDate,appTime from patient where appDate = ? and appTime = ?";
                                    searchObj = new Search(query, date.getValue().toString(), timeField.getText());
                                    rs = searchObj.getResult();
                                    if (rs.next()) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Message");
                                        alert.setHeaderText("Information Alert");
                                        alert.setContentText("This appointment is reserved");
                                        alert.show();
                                    } else {
                                        String birthday,
                                                paid,
                                                remaining,
                                                gender;
                                        if (birthdate.getValue() == null) {
                                            birthday = "";
                                        } else {
                                            birthday = birthdate.getValue().toString();
                                        }
                                        if (paidField.getText().equals("")) {
                                            paid = "0";
                                        } else {
                                            paid = paidField.getText();
                                        }
                                        if (remainingField.getText().equals("")) {
                                            remaining = "0";
                                        } else {
                                            remaining = remainingField.getText();
                                        }
                                        if (male.isSelected()) {
                                            gender = "male";
                                        } else if (female.isSelected()) {
                                            gender = "female";
                                        } else {
                                            gender = "";
                                        }

                                        if (doctorNameField.getText().equals("")) {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Message");
                                            alert.setHeaderText("Information Alert");
                                            alert.setContentText("Please, Enter doctor name.");
                                            alert.show();
                                        } else {

                                            String insertQuery = "insert into patient(pName,pPhone,pEmail,pAddress,pBD,job,dName,status,result,appDate,appTime,paid,remaining,pGender)"
                                                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                            Insert insertObj;
                                            insertObj = new Insert(insertQuery, nameField.getText().toLowerCase(),
                                                    phoneField.getText(),
                                                    emailField.getText().toLowerCase(),
                                                    addressField.getText().toLowerCase(),
                                                    birthday,
                                                    jobField.getText().toLowerCase(),
                                                    doctorNameField.getText().toLowerCase(),
                                                    statusField.getText().toLowerCase(),
                                                    ResultOfCheckupField.getText().toLowerCase(),
                                                    date.getValue().toString(),
                                                    timeField.getText(),
                                                    paid,
                                                    remaining,
                                                    gender);

                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Message");
                                            alert.setHeaderText("Information Alert");
                                            alert.setContentText("This patient inserted");
                                            alert.show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Booking.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        });

        addnew.setOnAction(
                (ActionEvent t) -> {
                    clear();
                }
        );

        search.setOnAction(
                (ActionEvent t) -> {
                    try {
                        String query;
                        if (Pattern.matches("\\d+", searchField.getText())) {
                            query = "select pName,pPhone,pEmail,pAddress,pBD,job,dName,status,result,appDate,appTime,paid,remaining,pGender"
                            + " from patient where pPhone = ?";
                        } else {
                            query = "select pName,pPhone,pEmail,pAddress,pBD,job,dName,status,result,appDate,appTime,paid,remaining,pGender"
                            + " from patient where pName = ?";
                        }
                        Search searchObj = new Search(query, searchField.getText().toLowerCase());
                        ResultSet result = searchObj.getResult();
                        if (result.next()) {
                            nameField.setText(result.getString(1));
                            phoneField.setText(result.getString(2));
                            emailField.setText(result.getString(3));
                            addressField.setText(result.getString(4));
                            if (result.getString(5) != null) {
                                birthdate.setValue(LocalDate.parse(result.getString(5)));
                            }
                            jobField.setText(result.getString(6));
                            doctorNameField.setText(result.getString(7));
                            statusField.setText(result.getString(8));
                            ResultOfCheckupField.setText(result.getString(9));
                            date.setValue(LocalDate.parse(result.getString(10)));
                            timeField.setText(result.getString(11));
                            paidField.setText(result.getString(12));
                            remainingField.setText(result.getString(13));
                            if ("male".equals(result.getString(14))) {
                                male.setSelected(true);
                            } else if ("female".equals(result.getString(14))) {
                                female.setSelected(true);
                            } else {
                                male.setSelected(false);
                                female.setSelected(false);
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This patient not exist!!");
                            alert.show();

                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Booking.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        remove.setOnAction(
                (ActionEvent t) -> {
                    try {
                        String query = "delete from patient where pPhone = ?";
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText("Delete this data?");
                        String s = "Are you sure you want to delete this patient ?";
                        alert.setContentText(s);
                        Optional<ButtonType> result = alert.showAndWait();
                        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                            Delete del = new Delete(query, phoneField.getText());
                            clear();

                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Booking.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        update.setOnAction(
                (ActionEvent t) -> {
                    String oldname = nameField.getText();
                    try {
                        String saerchQuery = "select appDate,appTime from patient where appDate = ? and appTime = ?";
                        Search searchObj = new Search(saerchQuery, date.getValue().toString(), timeField.getText());
                        ResultSet rs = searchObj.getResult();
                        if (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message");
                            alert.setHeaderText("Information Alert");
                            alert.setContentText("This appointment is reserved, Please change this appointment");
                            alert.show();
                        } else {

                            String updateQuery = "update patient Set pName=?, pPhone=?, pEmail=?, pAddress=?, pBD=?, job=?, dName=?,"
                            + " status=?, result=? , appDate=?, appTime=?, paid=?, remaining=?, pGender=?  where pName = ?";

                            String g = "";
                            if (male.isSelected()) {
                                g = "male";
                            }
                            if (female.isSelected()) {
                                g = "female";
                            }

                            Update updateObj = new Update(updateQuery, nameField.getText().toLowerCase(),
                                    phoneField.getText(),
                                    emailField.getText().toLowerCase(),
                                    addressField.getText().toLowerCase(),
                                    birthdate.getValue().toString(),
                                    jobField.getText().toLowerCase(),
                                    doctorNameField.getText().toLowerCase(),
                                    statusField.getText().toLowerCase(),
                                    ResultOfCheckupField.getText().toLowerCase(),
                                    date.getValue().toString(),
                                    timeField.getText(),
                                    paidField.getText(),
                                    remainingField.getText(),
                                    g,
                                    oldname);

                        }
                    } catch (SQLException e) {
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Booking.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        home.setOnAction(
                (ActionEvent t) -> {
                    StartingPage sp = new StartingPage();
                    if (sp.getUser().equals("admin")) {
                        doctorHome dh = new doctorHome();
                        try {
                            dh.start(primaryStage);

                        } catch (Exception ex) {
                            Logger.getLogger(Doctor.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        secretaryHome sh = new secretaryHome();
                        sh.start(primaryStage);
                    }
                }
        );
    }

    private void clear() {
        nameField.clear();
        male.setSelected(false);
        female.setSelected(false);
        birthdate.setValue(null);
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        jobField.clear();
        doctorNameField.clear();
        date.setValue(null);
        timeField.clear();
        paidField.clear();
        remainingField.clear();
        statusField.clear();
        ResultOfCheckupField.clear();
        searchField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
