package Conect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConection {

    /**
     * private String user = "root";
     * private String password = "root";
     * private String url = "jdbc:mysql://localhost:3307/AD2021";
     */
    private String user = "";
    private String password = "";
    private String url = "";
    private static Connection con = null;

    private static DatabaseConection INSTANCE;

    private static DatabaseConection getInstance() {

        if (INSTANCE == null) {
            try {
                INSTANCE = new DatabaseConection();
            } catch (IOException e ) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public DatabaseConection() throws IOException {


        String nombreFichero = "database.properties";
        BufferedReader reader = new BufferedReader(new FileReader(nombreFichero));
        String linea;


        /**
         try {
         while ((linea = reader.readLine()) != null) {
         if (linea.contains("user=")) {
         user = linea.split("=")[1];
         }
         if (linea.contains("password=")) {
         password = linea.split("=")[1];
         }
         if (linea.contains("url=")) {
         url = linea.split("=")[1];
         }

         }
         reader.close();
         } catch (IOException e) {
         e.printStackTrace();
         }
         */

        Properties properties = new Properties();
        properties.load(new FileReader(nombreFichero));

        user = properties.getProperty("user");
        password = properties.getProperty("password");
        url = properties.getProperty("url");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

            if (con != null) {
                System.out.println("la conexion funciona correctamente");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {

        getInstance();
        return con;

    }


}