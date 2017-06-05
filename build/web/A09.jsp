<%-- 
    Document   : A09
    Created on : Jun 2, 2017, 12:55:03 AM
    Author     : dinhgiabao28081997
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.WorkTimeOt"%>
<%@page import="bean.Company"%>
<%@page import="bean.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Overtime</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/jquery-ui.css">

        <script src="js/jquery.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script>
            $(function () {
                $("#datepicker").datepicker({
                    dateFormat: "yy-mm-dd"
                });
                $("#datepicker1").datepicker({
                    dateFormat: "yy-mm-dd"
                });
            });
        </script>
        <script>
            jQuery(document).ready(function () {
            <%if (request.getAttribute("empCode") != null) {%>
                var emp = document.getElementById('employee');
                emp.value = '<%=request.getAttribute("empCode")%>';
            <%}%>
            <%if (request.getAttribute("company") != null) {%>
                var com = document.getElementById('company');
                com.value = '<%=request.getAttribute("company")%>';
            <%}%>
            <%if (request.getAttribute("from") != null) {%>
                var from = document.getElementById('datepicker');
                from.value = '<%=request.getAttribute("from")%>';
            <%}%>
            <%if (request.getAttribute("to") != null) {%>
                var to= document.getElementById('datepicker1');
                to.value = '<%=request.getAttribute("to")%>';
            <%}%> 
            });

        </script>
        <script>
            function getEmplist() {
                var code = document.getElementById('company').value;
                window.location = "A09Servlet?action=getEmp&comCode=" + code + "";
            }
            
            function Back(){
                window.history.back();
                
            }
            
            function Print(){
                var company = $("#company option:selected").text();
                var employee = $("#employee option:selected").text();
                var empCode = document.getElementById('employee').value;
                var from = document.getElementById('datepicker').value;
                var to = document.getElementById('datepicker1').value;
                window.location.replace("A09Servlet?action=print&company="+company+"&employee="+employee+"&empCode="+empCode+"&from="+from+"&to="+to+"");
            }
      
            function Next(){
                
            }
            
            function Previous(){
                
            }
        </script>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
                alignment-adjust: central;
            }
            th {
                background-color: black;
                color: white;
            } 
            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            .hover:hover{background-color:#007fff}
        </style>
    </head>
    <body>

        <div class="container">
            <div class="header" style="text-align: center">
                <h2>List Overtime</h2>
            </div>
            <div class="subheader">
                <form action="A09Servlet" method="post">
                    <input type="hidden" name="action" value="getworkTime_OT"> 
                    <ul class="qty">
                        <li>
                            Company Name
                        </li>
                        <li>
                            <select name="company" id="company" onchange="getEmplist()">

                                <%
                                    List<Company> listCom = (ArrayList<Company>) request.getAttribute("allCom");
                                    for (Company com : listCom) {
                                        String name = com.getName();
                                        String code = com.getCode().trim();
                                %>
                                <option value="<%=code%>"><%=name%></option>
                                <%
                                    }
                                %>
                            </select>
                        </li>
                    </ul>

                    <ul class="qty">
                        <li>
                            Employee Name
                        </li>
                        <li>
                            <select id="employee" name="employee">
                                <%
                                    List<Employee> listEmp = (ArrayList<Employee>) request.getAttribute("allEmp");
                                    for (Employee emp : listEmp) {
                                        String name = emp.getFirst() + " " + emp.getLast();
                                        String empCode = emp.getEmpCode();
                                %>

                                <option value="<%=empCode%>"><%=name%></option>

                                <%
                                    }
                                %>
                            </select>
                        </li>
                    </ul>

                    <ul class="qty">
                        <li>
                            Date
                        </li>
                        <li>
                            From: <input type="text" name="dateFrom" id="datepicker"/>
                        </li>
                        <li>
                            <h3>~</h3>
                        </li>
                        <li>
                            To: <input type="text" name="dateTo" id="datepicker1"/>
                        </li>
                        <li>
                            <input type="submit"  value="View"/>
                        </li>
                    </ul>
                </form>

            </div>
            <div class="body">
                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Duration</th>
                            <th>Note</th>
                            <th>Remark</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%                        if (request.getAttribute("listOT") != null) {
                                List<WorkTimeOt> listOT = (ArrayList<WorkTimeOt>) request.getAttribute("listOT");
                                int no = 1;
                                for (WorkTimeOt ot : listOT) {

                                    String start = ot.getStart().substring(0, 2) + ":" + ot.getStart().substring(2, 4);
                                    String end = ot.getEnd().substring(0, 2) + ":" + ot.getEnd().substring(2, 4);
                                    double duration = ot.getDuration();
                                    String note = ot.getNote();
                                    String wrk_flag = ot.getWrk_flag().trim();
                                    if (wrk_flag.equals("1")) {
                                        wrk_flag = "Yes";
                                    } else {
                                        wrk_flag = "No";
                                    }
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
                        %>


                        <tr class="hover">
                            <td><%=no%></td>
                            <%if (wrk_flag.equals("Yes")) {%>
                            <td><%=date%></td>
                            <%} else {%>
                            <td style="color: red"><%=date%></td>
                            <%}%>
                            <td><%=start%>--<%=end%></td>
                            <td><%=duration%></td>
                            <td><%=note%></td>
                            <td><a href="#"><%=wrk_flag%></a></td>
                        </tr>
                        <%
                                    no++;
                                }
                            }else if(request.getAttribute("error") != null){
                            String error = request.getAttribute("error").toString();
                                
                        %>
                        <tr>
                            <td style="color: red"><%=error%></td>
                        </tr>
  
                    <%}%>
                    </tbody>

                </table>
           
            </div>
                    <input type="button" value="Back" onclick="Back()" style="float:left"/>
                    
                    <input type="button" value="Print" onclick="Print()" style="float:right"/>
                    <input type="button" value="Previous Month" onclick="Previous()" style="float:right"/>
                    <input type="button" value="Next Month" onclick="Next()" style="float:right"/>
        </div>
                    

    </body>
</html>
