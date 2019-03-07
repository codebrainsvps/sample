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
@WebServlet(name = "ViewCustomerRequest", urlPatterns = {"/ViewCustomerRequest"})
public class ViewCustomerRequest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        try {
            Connection con = DataBase.getCon();
             HttpSession session = request.getSession();
        String soid = (String) session.getAttribute("ownerid");
        
            String query = "select * from reqitems where oid =? ";
            PreparedStatement st = con.prepareStatement(query);
             st.setInt(1, Integer.parseInt(soid));
            ResultSet rs = st.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            out.print("<table bgcolor=yellow align=center>");
            out.print("<tr style=color:red;background:pink>");
            for (int i = 1; i <= cols; i++) {
                out.print("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.print("</tr>");
            while (rs.next()) {
                out.print("<tr>");
                String sid = rs.getString("pid");
                String cid = rs.getString("cid");
               out.println("<td><a href=/CloudProduct/ShareConfidential?prodid=" + sid + "&oid="+soid+"&cid="+cid+">" + sid + "</a> </td>");
                for (int i = 2; i <= cols; i++) {
                    out.print("<td>" + rs.getString(i) + "</td>");
                }
                
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
