/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DentalClinic;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class Update {
    public Update(String query, String ... param){
        try {
            connectToDatabase connectObj = connectToDatabase.getObject();
            PreparedStatement preparedStatement = connectObj.getConn().prepareStatement(query);
            preparedStatement.setString(param.length, param[param.length-1]);
            for(int i=0; i<param.length-1; i++){
                if(Pattern.matches("\\d+", param[i])){
                    preparedStatement.setInt(i+1, Integer.parseInt(param[i]));
                }else if(param[i].equals("")){
                    preparedStatement.setString(i+1, null);
                }else{
                    preparedStatement.setString(i+1, param[i]);
                }
            }
            preparedStatement.executeUpdate();
            
            //get table name from update query
//            ResultSetMetaData resultMetaData = preparedStatement.getMetaData();
//            System.out.println(resultMetaData.getTableName(1));
//            if(!resultMetaData.getTableName(1).equals("login")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Information Alart");
            alert.setContentText("Data Updated");
            alert.show();
//            }
            
        } catch ( SQLException ex) {
            Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
