package DentalClinic;

import java.sql.*;

public class connectToDatabase {

    private static connectToDatabase obj;
    private Connection conn = null;

    private connectToDatabase() {
    }

    public static connectToDatabase getObject() {
        if (obj == null) {
            obj = new connectToDatabase();
        }
        return obj;
    }

    Connection getConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dental_clinic_database?useUnicode=yes&characterEncoding=UTF-8", "root", "root");
            return conn;
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found");
            return conn;
        } catch (SQLException ex) {
            System.out.println("Error in connection");
            return conn;
        }
    }

    void CloseConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
