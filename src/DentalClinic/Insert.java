/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DentalClinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class Insert {
    Insert(String query, String ... param){
        try {
            connectToDatabase connectObj = connectToDatabase.getObject();
            Connection connect = connectObj.getConn();
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for(int i=0; i<param.length; i++){
                if(Pattern.matches("\\d+", param[i])){
                    preparedStatement.setInt(i+1, Integer.parseInt(param[i]));
                }else if(param[i].equals("")){
                    preparedStatement.setString(i+1, null);
                }else{
                    preparedStatement.setString(i+1, param[i]);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
