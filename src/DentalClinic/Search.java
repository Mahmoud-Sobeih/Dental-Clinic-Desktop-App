/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DentalClinic;

import java.sql.*;
import java.util.regex.Pattern;
/**
 *
 * @author User
 */
public class Search {
    
    private ResultSet result;
    Search(String query, String ... param) throws ClassNotFoundException, SQLException{
        connectToDatabase conn = connectToDatabase.getObject();   //singleton object to connect to database
            PreparedStatement preparedStatement = conn.getConn().prepareStatement(query);
          
            // put parameters that are we search
            for(int i=0; i<param.length; i++){
                if(Pattern.matches("//d+", param[i])){
                    preparedStatement.setInt(i+1, Integer.parseInt(param[i]));
                }else{
                    preparedStatement.setString(i+1, param[i]);
                }
            }
            
            result = preparedStatement.executeQuery();
    }
 
    public ResultSet getResult(){
        return result;
    }
}
