package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:./src/carsharing/db/carsharing";
    static final String USER = "sa";
    static final String PASS = "";
    public static Connection connection = null;
    public static Statement statement = null;

    public static void startDB() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connected to database...");
    }

    public static void closeDB() {
        try {
            statement.close();
            connection.close();
            System.out.println("Database closed...");
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (statement != null) {
                    statement.close();
                }
            } catch(SQLException secondSe) {
                try {
                    if (connection != null) {
                        connection.close();
                        System.out.println("Database closed...");
                    }
                } catch(SQLException se){
                    se.printStackTrace();
                }
            }
        }
    }
}
