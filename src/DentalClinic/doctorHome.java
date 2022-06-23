package DentalClinic;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Mahmoud
 */
public class doctorHome extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //components
        Text text = new Text("Welcome To Our Clinic\n");

        Button patient = new Button("Patients");
        Button doctor = new Button("Doctors");
        Button secretary = new Button("Secretary");
        Button appointments = new Button("Appointments");

        Label logout = new Label("log out");

        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();
        
        //add components
        grid1.add(text, 0, 1);
        grid2.add(patient, 2, 0);
        grid2.add(doctor, 3, 0);
        grid2.add(secretary, 2, 1);
        grid2.add(appointments, 3, 1);
        grid2.add(logout, 5, 8);

        VBox root = new VBox(grid1, grid2);
        Scene scene = new Scene(root, 800, 500);

        //deseign
        grid1.setAlignment(Pos.CENTER);
        grid2.setAlignment(Pos.CENTER);
        
        text.setId("txt");
        logout.setId("help");

        grid1.getStyleClass().add("grid-pane");
        grid2.getStyleClass().add("grid");
        scene.getStylesheets().addAll(this.getClass().getResource("css/homeStyle.css").toExternalForm());

        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Action
        patient.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Booking book = new Booking();
                book.start(primaryStage);
            }

        });
        doctor.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Doctor rd = new Doctor();
                rd.start(primaryStage);
            }

        });
        secretary.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Secretary s = new Secretary();
                try {
                    s.start(primaryStage);
                } catch (Exception ex) {
                    Logger.getLogger(doctorHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        appointments.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                appointments rd = new appointments();
                try {
                    rd.start(primaryStage);
                } catch (Exception ex) {
                    Logger.getLogger(doctorHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        logout.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                StartingPage sp = new StartingPage();
                sp.start(primaryStage);
                
                try {
                    connectToDatabase connect_to_database = connectToDatabase.getObject();
                    System.out.println(connect_to_database);
                    connect_to_database.CloseConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(doctorHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    public static void main(String[] args) {
        launch(args);

    }

}
