/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.CompanyDAO;
import dao.EmpDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "A05Servlet", urlPatterns = {"/A05Servlet"})
public class A05Servlet extends HttpServlet {

    private EmpDAO emp = new EmpDAO();
    private CompanyDAO com = new CompanyDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        request.setAttribute("empCode", session.getAttribute("Emp_Code"));
        request.setAttribute("company", emp.getEmployee(session.getAttribute("Emp_Code").toString()).getComCode().trim());
        request.setAttribute("allCom", com.getAll());
        request.setAttribute("allEmp", emp.getEmpByComCode(emp.getEmployee(session.getAttribute("Emp_Code").toString()).getComCode()));
        request.getRequestDispatcher("/A09.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
