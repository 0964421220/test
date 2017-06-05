/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.WorkTimeOt;
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
public class WorkTime_OtDAO {
    
    private DBConnection myConnect;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;
    
    public WorkTime_OtDAO() {
        myConnect = new DBConnection();
        ps = null;
    }
    
    public DBConnection getDBConnection() {
        return myConnect;
    }
    
    public List<WorkTimeOt> getWorkTime_Ot(String empCode,String from,String to) {
        List<WorkTimeOt> listWorkTime_OT = new ArrayList<WorkTimeOt>();
        if (myConnect.openConnect()) {
            try {
                sql = "SELECT * FROM WORK_OT WHERE EMP_CODE = '" + empCode + "' AND WRK_DATE_CHECK BETWEEN '"+from+"' AND '"+to+"'";
                ps = myConnect.getConnection().prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    WorkTimeOt ot = new WorkTimeOt();
                    double startlast2 = Double.parseDouble(rs.getString("WRK_OT_START_TIME")) % 100 / 60;
                    double endlast2 = Double.parseDouble(rs.getString("WRK_OT_END_TIME")) % 100 / 60;
                    
                    double startTime = Double.parseDouble(rs.getString("WRK_OT_START_TIME").substring(0, 2)) + startlast2;
                    double endTime = Double.parseDouble(rs.getString("WRK_OT_END_TIME").substring(0, 2)) + endlast2;
                    ot.setNote(rs.getString("NOTE"));
                    ot.setCalendar(rs.getString("CAL_YMD"));
                    ot.setStart(rs.getString("WRK_OT_START_TIME"));
                    ot.setEnd(rs.getString("WRK_OT_END_TIME"));
                    ot.setDate(rs.getString("WRK_DATE_CHECK"));
                    ot.setWrk_flag(rs.getString("WRK_FLAG_CHECK"));
                    if(rs.getString("WRK_OT_TIME") != null){
                        ot.setDuration(rs.getDouble("WRK_OT_TIME"));
                    }else{
                    ot.setDuration(endTime - startTime);
                    }
                    listWorkTime_OT.add(ot);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myConnect.closeConnect();
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmpDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return listWorkTime_OT;
    }
    
    public List<WorkTimeOt> getAll() {
        List<WorkTimeOt> list = new ArrayList<>();
        if (myConnect.openConnect()) {
            try {
                sql = "Select * from COMPANY_MST";
                ps = myConnect.getConnection().prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    WorkTimeOt com = new WorkTimeOt();
                    
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
