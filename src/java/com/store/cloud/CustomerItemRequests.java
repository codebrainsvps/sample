/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shiva
 */
@WebServlet(name = "CustomerItemRequests", urlPatterns = {"/CustomerItemRequests"})
public class CustomerItemRequests extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        try {
            Connection con = DataBase.getCon();
             HttpSession session = request.getSession();
        String scid = (String) session.getAttribute("custId");
        
            String query = "select * from reqitems where cid =? ";
            PreparedStatement st = con.prepareStatement(query);
             st.setInt(1, Integer.parseInt(scid));
            ResultSet rs = st.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            out.print("<table bgcolor=yellow align=center>");
            out.print("<tr style=color:red;background:pink>");
            for (int i = 1; i <= cols; i++) {
                out.print("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.print("<th>File</th>");
            out.print("</tr>");
            while (rs.next()) {
                out.print("<tr>");
               
              
                for (int i = 1; i <= cols; i++) {
                    out.print("<td>" + rs.getString(i) + "</td>");
                }
              //  response.sendRedirect("/CloudProduct/DownloadFile?filename="+rs.getString("filelocation"));
            
                out.print("<td><a href=/CloudProduct/DownloadFile?filename="+rs.getString("filelocation")+">DownLoad</a></td>");
                out.print("</tr>");
                
            }
            out.print("</table>");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
       // getServletContext().getRequestDispatcher("/Back.html").include(request, response);
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
