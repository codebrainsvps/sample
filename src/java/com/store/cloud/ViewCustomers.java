/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shiva
 */
public class ViewCustomers extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        try {
            Connection con = DataBase.getCon();
            String query = "select * from customers";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            out.print("<table bgcolor=yellow align=center>");
            out.print("<tr style='color:white;background:blue'>");
            for (int i = 1; i <= cols; i++) {
                out.print("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.print("</tr>");
            while (rs.next()) {
                out.print("<tr>");
                //String sid = rs.getString("custid");
               
                
                //out.println("<td><a href=/CloudProduct/UpdatecustomersStatus?custid=" + sid + ">" + sid + "</a> </td>");
                for (int i = 1; i <= cols; i++) {
                    out.print("<td>" + rs.getString(i) + "</td>");
                }
                
                out.print("</tr>");
            }
            out.print("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }

}