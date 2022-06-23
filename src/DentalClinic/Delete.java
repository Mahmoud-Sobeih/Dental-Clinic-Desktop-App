/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DentalClinic;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class Delete {
    Delete(String query, String param) throws ClassNotFoundException, SQLException{
        connectToDatabase connect = connectToDatabase.getObject();
        PreparedStatement preparedStatement = connect.getConn().prepareStatement(query);
        if(Pattern.matches("\\d+", param)){
            preparedStatement.setInt(1, Integer.parseInt(param));
        }else{
            preparedStatement.setString(1, param);
        }
        
        preparedStatement.executeUpdate();
    }
}
