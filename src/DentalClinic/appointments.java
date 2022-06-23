package DentalClinic;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Mahmoud
 */
public class appointments extends Application {

    private final TextField doctorNameField = new TextField();
    private DatePicker date;
    private ObservableList<Person> data;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Components
        Label doctorNameLabel = new Label("Enter The Doctor name");
        Label dateLabel = new Label("Enter the Date ");

        date = new DatePicker();
        date.setPromptText("MM/DD/YYYY");

        TableView table = new TableView();

        TableColumn<String, Person> patient_column = new TableColumn<>("Patient");
        patient_column.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<String, Person> time_column = new TableColumn<>("Time");
        time_column.setCellValueFactory(new PropertyValueFactory<>("time"));

        table.getColumns().addAll(patient_column, time_column);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        data = FXCollections.observableArrayList();

        ImageView imgsearch = new ImageView(new Image(getClass().getResourceAsStream("img/search.png")));
        ImageView imghome = new ImageView(new Image(getClass().getResourceAsStream("img/home.png")));

        Button search = new Button("", imgsearch);
        Button home = new Button("", imghome);

        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        GridPane grid3 = new GridPane();

        //add components
        grid1.add(doctorNameLabel, 0, 1);
        grid1.add(doctorNameField, 1, 1);
        grid1.add(dateLabel, 0, 2);
        grid1.add(date, 1, 2);
        grid1.add(search, 2, 2);
        grid2.add(table, 2, 0);
        grid3.add(home, 0, 0);

        VBox root = new VBox(grid1, grid2, grid3);
        Scene scene = new Scene(root, 800, 500);

        //deseign
        grid1.setAlignment(Pos.TOP_CENTER);
        grid2.setAlignment(Pos.CENTER);
        grid3.setAlignment(Pos.CENTER);

        imgsearch.setFitHeight(20);
        imgsearch.setFitWidth(20);
        imghome.setFitHeight(30);
        imghome.setFitWidth(30);

        doctorNameLabel.setId("labelSearch");
        dateLabel.setId("labelSearch");
        home.setId("bhome");
        search.setId("bsearch");

        doctorNameField.setPromptText("Enter Doctor Name");
        grid1.getStyleClass().add("grid-pane");
        grid2.getStyleClass().add("grid2");
        grid3.getStyleClass().add("grid");
        scene.getStylesheets().addAll(this.getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Appointments");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Action
        search.setOnAction((ActionEvent t) -> {
            try {
                String query = "select pName,appTime from patient where dName = ? and appDate = ?";
                Search searchObj = new Search(query, doctorNameField.getText().toLowerCase(), date.getValue().toString());
                ResultSet rs = searchObj.getResult();
                if (rs.first()) {
                    // to add first patient
                    data.add(new Person(
                            rs.getString(1),
                            rs.getString(2)
                    ));
                    table.setItems(data);
                    // to add rest of the pateints
                    while (rs.next()) {
                        data.add(new Person(
                                rs.getString(1),
                                rs.getString(2)
                        ));
                        table.setItems(data);
                    }
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Information Alert");
                    alert.setContentText("No appointments in this date !!!");
                    alert.show();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(appointments.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void main(String[] args) {
        launch(args);

    }

}
