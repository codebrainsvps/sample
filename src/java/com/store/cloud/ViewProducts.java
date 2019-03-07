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
@WebServlet(name = "ViewProducts", urlPatterns = {"/ViewProducts"})
public class ViewProducts extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String soid = (String) session.getAttribute("ownerid");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        try {
            Connection con = DataBase.getCon();
            //String query = "select * from Owners where username = ?";
            // PreparedStatement st = con.prepareStatement(query);
            // st.setString(parameterIndex, query);
            String query1 = "select oid,pid,name,company,prodtype,photo from products where oid=?";

            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setInt(1, Integer.parseInt(soid));
            ResultSet rs = st1.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            out.print("<table background=yellow align=center>");
            out.print("<tr>");
            for (int i = 1; i <= cols; i++) {
                out.print("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.print("</tr>");
            while (rs.next()) {
                out.print("<tr>");
                for (int i = 1; i <= cols - 1; i++) {
                    out.print("<td>" + rs.getString(i) + "</td>");
                }
                out.println("<td><img width='120' height='110' src=DisplayPhoto?prodid=" + rs.getString("pid") + "></img> </td>");
                out.print("</tr>");
            }
            out.print("</table>");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
