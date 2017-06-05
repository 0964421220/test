/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bean.Company;
import bean.Employee;
import bean.WorkTimeOt;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import dao.CompanyDAO;
import dao.EmpDAO;
import dao.WorkTime_OtDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dinhgiabao28081997
 */
@WebServlet(name = "A09Servlet", urlPatterns = {"/A09Servlet"})
public class A09Servlet extends HttpServlet {

    private EmpDAO emp = new EmpDAO();
    private CompanyDAO com = new CompanyDAO();
    private WorkTime_OtDAO worktimeOT = new WorkTime_OtDAO();

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
           HttpSession session = request.getSession(true);
        if (action.equals("getEmp")) {
            String comCode = request.getParameter("comCode");

            request.setAttribute("company", comCode.trim());
            request.setAttribute("allCom", com.getAll());
            request.setAttribute("allEmp", emp.getEmpByComCode(comCode));

            request.getRequestDispatcher("/A09.jsp").forward(request, response);
        }
        if (action.equals("getworkTime_OT")) {
            String empCode = request.getParameter("employee").trim();
            String dateFrom = request.getParameter("dateFrom").replace("-", "");
            String dateTo = request.getParameter("dateTo").replace("-", "");
            request.setAttribute("empCode", empCode);
            request.setAttribute("company", emp.getEmployee(empCode).getComCode().trim());
            request.setAttribute("from", request.getParameter("dateFrom"));
            request.setAttribute("to", request.getParameter("dateTo"));
            request.setAttribute("allCom", com.getAll());
            request.setAttribute("allEmp", emp.getEmpByComCode(emp.getEmployee(empCode).getComCode()));
            if(!worktimeOT.getWorkTime_Ot(empCode,dateFrom,dateTo).isEmpty()){
            request.setAttribute("listOT", worktimeOT.getWorkTime_Ot(empCode,dateFrom,dateTo));
            }else{
                request.setAttribute("error", "No data found!");
            }
          
            request.getRequestDispatcher("/A09.jsp").forward(request, response);
        }
        if(action.equals("print")){
              String company = request.getParameter("company");
              String employee = request.getParameter("employee");
              String empCode = request.getParameter("empCode").trim();
              String from = request.getParameter("from").replace("-", "");
              String to = request.getParameter("to").replace("-", "");
            try {
                Document doc = new Document();
                File file = new File("OT.pdf");
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(file));
                } catch (DocumentException ex) {
                    Logger.getLogger(A09Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                doc.setPageSize(PageSize.A4);
                doc.open();
                Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
                Paragraph docTitle = new Paragraph("Employee Overtime", titleFont);
                doc.add(new Paragraph(docTitle));
                DottedLineSeparator separator = new DottedLineSeparator();
                separator.setPercentage(59500f / 523f);
                Chunk linebreak = new Chunk(separator);
                doc.add(linebreak);
             
               
                doc.add(new Paragraph("Company : " + company));
                doc.add(new Paragraph("Employee :" + employee));
                doc.add(new  Paragraph("From : " + request.getParameter("from") +" ~ "+ "To :" + request.getParameter("to")));
                doc.add(new Paragraph(" "));
                PdfPTable table = new PdfPTable(6);
                table.setWidths(new float[] { 1, 3, 3, 2, 2, 2 });
                table.addCell("No");
                table.addCell("Date");
                table.addCell("From - To");
                table.addCell("Duration");
                table.addCell("Note");
                 table.addCell("Remark");
                List<WorkTimeOt> list = worktimeOT.getWorkTime_Ot(empCode,from,to);
                int no = 0;
                for (WorkTimeOt ot : list) {
                    table.addCell(String.valueOf(no));
                    no++;
                       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                    String dateInString = ot.getDate().trim();
                                    Date dt = formatter.parse(dateInString);
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(dt);
                                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                                    String day = "";

                                    if (dayOfWeek == 2) {
                                        day = "Mon";
                                    } else if (dayOfWeek == 3) {
                                        day = "Tues";
                                    } else if (dayOfWeek == 4) {
                                        day = "Wed";
                                    } else if (dayOfWeek == 5) {
                                        day = "Thurs";
                                    } else if (dayOfWeek == 6) {
                                        day = "Friday";
                                    } else if (dayOfWeek == 7) {
                                        day = "Sat";
                                    } else if (dayOfWeek == 8) {
                                        day = "Sun";
                                    }

                                    String date = day + " - " + ot.getDate().trim().substring(0, 4) + "/" + ot.getDate().trim().substring(4, 6) + "/" + ot.getDate().trim().substring(6, 8);
                    table.addCell(date);
                     String start = ot.getStart().substring(0, 2) + ":" + ot.getStart().substring(2, 4);
                     String end = ot.getEnd().substring(0, 2) + ":" + ot.getEnd().substring(2, 4);
                    table.addCell(start+"--"+end);
                    double duration = ot.getDuration();
                    String note = ot.getNote();
                    String wrk_flag = ot.getWrk_flag().trim();
                     if (wrk_flag.equals("1")) {
                                        wrk_flag = "Yes";
                                    } else {
                                        wrk_flag = "No";
                                    }
                    table.addCell(String.valueOf(duration));
                    table.addCell(note);
                    table.addCell(wrk_flag);
                
                }
            
                doc.add(table);
                doc.close();
                String pdfFile= file.getAbsolutePath();
                if (pdfFile.endsWith(".pdf")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile);
                }
            } catch (DocumentException ex) {
                  Logger.getLogger(A09Servlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(A09Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("empCode", empCode);
            request.setAttribute("company", emp.getEmployee(empCode).getComCode().trim());
            request.setAttribute("allCom", com.getAll());
            request.setAttribute("allEmp", emp.getEmpByComCode(emp.getEmployee(empCode).getComCode()));
            request.setAttribute("from", request.getParameter("from"));
            request.setAttribute("to", request.getParameter("to"));
            request.setAttribute("listOT", worktimeOT.getWorkTime_Ot(empCode,from,to));
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
