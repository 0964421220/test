/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class EmpDAO {
     private DBConnection myConnect;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;
    
        public EmpDAO() {
        myConnect = new DBConnection();
        ps = null;
    }
        
        public DBConnection getDBConnection() {
        return myConnect;
    }
        
           public List<Employee> getEmpByComCode(String code){
        List<Employee> list = new ArrayList<>();
        if (myConnect.openConnect()) {
            try {
                sql = "Select * from EMP_MST WHERE COM_CODE = '"+code+"'";
                ps = myConnect.getConnection().prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setFirst(rs.getString("EMP_FIRST_NAME"));
                    emp.setLast(rs.getString("EMP_LAST_NAME"));
                    emp.setEmpCode(rs.getString("EMP_CODE"));
                      emp.setComCode(rs.getString("COM_CODE"));
                    list.add(emp);
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
         public Employee getEmployee(String code){
            Employee emp = new Employee();
              if (myConnect.openConnect()) {
                  
              }
            try {
                sql = "Select * from EMP_MST WHERE EMP_CODE = '"+code+"'";
                ps = myConnect.getConnection().prepareStatement(sql);
                rs = ps.executeQuery();
                if(rs.next()){
                 emp.setFirst(rs.getString("EMP_FIRST_NAME"));
                    emp.setLast(rs.getString("EMP_LAST_NAME"));
                    emp.setComCode(rs.getString("COM_CODE"));
                    emp.setEmpCode(rs.getString("EMP_CODE"));
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
             return emp;
          
         } 
        
}
