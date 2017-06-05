/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author dinhgiabao28081997
 */
public class WorkTimeOt {
   private String emp_code;
   private String com_code;
   private String calendar;
   private String start;
   private String end;
   private double duration;
   private String note;
   private String date;
   private String wrk_flag;

    public String getWrk_flag() {
        return wrk_flag;
    }

    public void setWrk_flag(String wrk_flag) {
        this.wrk_flag = wrk_flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
   
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
   

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

  
  

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    
}
