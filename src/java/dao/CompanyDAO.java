/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Company;
import bean.Employee;
import connection.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinhgiabao28081997
 */
public class CompanyDAO {
         private DBConnection myConnect;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;
    
        public CompanyDAO() {
        myConnect = new DBConnection();
        ps = null;
    }
        
        public DBConnection getDBConnection() {
        return myConnect;
    }
        
        
             public List<Company> getAll(){
        List<Company> list = new ArrayList<>();
        if (myConnect.openConnect()) {
            try {
                sql = "Select * from COMPANY_MST";
                ps = myConnect.getConnection().prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Company com = new Company();
                    com.setName(rs.getString("COM_NAME"));
                    com.setCode(rs.getString("COM_CODE"));
                    list.add(com);
                }
            } catch (Exception e) {
            } finally {
                myConnect.closeConnect();
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmpDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }
}
