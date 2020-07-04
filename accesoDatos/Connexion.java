package accesoDatos;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connexion
 * @author Yazmin
 * @version 04/07/2020
 */

public class Connexion {
    private Connection connexion;
    private String dataBase;
    private String user;
    private String password;
    
    public Connection getConnection() throws SQLException{
        connect();
        return connexion;
    }
    
    private void connect() throws SQLException{
        Properties properties = new Properties();
        try {
            properties.load( accesoDatos.Connexion.class.getClassLoader().getResourceAsStream("dataaccess/propertiesDataAccess") );
            dataBase = properties.getProperty("dataBase");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        }catch(IOException ex)
        {
            System.err.println("Error accessing properties file");
            ex.printStackTrace();

        }
        try {
            connexion=DriverManager.getConnection (dataBase,user,password);
        } catch (CommunicationsException ex){
            Logger.getLogger(accesoDatos.Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(){
        if(connexion!=null){
            try {
                if(!connexion.isClosed()){
                    connexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(accesoDatos.Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
