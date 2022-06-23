package DentalClinic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class StartingPage extends Application {

    private final TextField emailField = new TextField();
    private static String user = null;
    private final PasswordField passwordField = new PasswordField();

    @Override
    public void start(Stage primaryStage) {
        
        //components
        Text txt = new Text("User Login");

        Button logIn = new Button("Log in");

        ImageView userLogo = new ImageView(new Image(getClass().getResourceAsStream("img/user.png")));
        ImageView passLogo = new ImageView(new Image(getClass().getResourceAsStream("img/pass.png")));
        ImageView mainLogo = new ImageView(new Image(getClass().getResourceAsStream("img/mainLogo.png")));

        GridPane grid1 = new GridPane();
        GridPane grid2 = new GridPane();

       

        //add components
        grid1.add(mainLogo, 1, 1);
        grid1.add(txt, 1, 2);
        grid2.add(userLogo, 0, 1);
        grid2.add(emailField, 3, 1);
        grid2.add(passLogo, 0, 3);
        grid2.add(passwordField, 3, 3);
        grid2.add(logIn, 3, 5);

        HBox root = new HBox();
        HBox.setHgrow(grid1, Priority.ALWAYS);
        HBox.setHgrow(grid2, Priority.ALWAYS);
        root.getChildren().addAll(grid1, grid2);
        Scene scene = new Scene(root, 800, 500);

        //Design
        userLogo.setFitHeight(25);
        userLogo.setFitWidth(20);
        passLogo.setFitHeight(25);
        passLogo.setFitWidth(20);
        mainLogo.setFitHeight(175);
        mainLogo.setFitWidth(175);
        logIn.setPrefSize(250, 40);
        emailField.setPrefSize(250, 40);
        passwordField.setPrefSize(250, 40);
        grid1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid1.setId("grid1Id");
        grid2.setId("grid2Id");
        txt.setId("t1");
        emailField.setPromptText("Enter Email");
        passwordField.setPromptText("Enter Password");
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(5);
        grid1.setVgap(7);
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(3);
        grid2.setVgap(10);
        scene.getStylesheets().addAll(this.getClass().getResource("css/Style1.css").toExternalForm());
        

        primaryStage.setTitle("DENTAL CLINIC");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(StartingPage.class.getResourceAsStream("img/logo.png")));
        primaryStage.show();
        
         //Action
        logIn.setOnAction((ActionEvent t) -> {
            if ("".equals(emailField.getText()) || "".equals(passwordField.getText())) {
                JOptionPane.showMessageDialog(null, "Enter the e-mail and password!!");
            } else {
                connectToDatabase connect_to_database = connectToDatabase.getObject();
                System.out.println(connect_to_database);
                try {
                    PreparedStatement preparedstatement = connect_to_database.getConn().prepareStatement("select email from login where email = ? and password = ?");
                    preparedstatement.setString(1, emailField.getText().toLowerCase());
                    preparedstatement.setString(2, passwordField.getText());
                    ResultSet rs = preparedstatement.executeQuery();
                    if (rs.next()) {
                        if (emailField.getText().toLowerCase().equals("admin")) {
                            setUser(emailField.getText());
                            doctorHome d = new doctorHome();
                            try {
                                d.start(primaryStage);
                            } catch (Exception ex) {
                                Logger.getLogger(StartingPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            setUser(emailField.getText());
                            secretaryHome s = new secretaryHome();
                            try {
                                s.start(primaryStage);
                            } catch (Exception ex) {
                                Logger.getLogger(StartingPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Alert");
                        alert.setContentText("Check the e-mail and the password !!");
                        alert.show();
                    }

                    
                } catch (SQLException e) {
                }
                
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    // To save the user to make a session
    protected void setUser(String user){
        StartingPage.user = user;
    }

    protected String getUser() {
        return user;
    }

}
