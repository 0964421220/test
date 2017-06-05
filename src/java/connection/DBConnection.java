/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author dinhgiabao28081997
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private Connection conn;
    private String portNumber;
    private String username;
    private String password;
    private String database;
    private String server;
    private String url;

    public DBConnection() {
        portNumber = "1433";
        username = "sa";
        password = "123456";
        database = "TimeCard";
        server = "localhost";
        url = "jdbc:sqlserver://" + server + ":"
                + portNumber + ";database=" + database;
    }
    
    public Connection getConnection(){
        return conn;
    }

    public boolean openConnect() {
        boolean ok = false;
        if (conn == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(url, username, password);
                if (conn != null) {
                    ok = true;
                }
            } catch (Exception e) {
                ok = false;
            }
        }
        return ok;
    }

    public void closeConnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
            }
        }
    }

    public boolean checkConnect() {
        boolean ok = false;
        if (conn != null) {
            ok = true;
        }
        return ok;
    }

}
