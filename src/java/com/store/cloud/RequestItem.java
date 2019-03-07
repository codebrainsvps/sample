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
public class RequestItem extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        String sid = request.getParameter("prodid");

       // out.print("<form action=StoreItemRequest>");
        out.print("<form action=StoreItemRequest method=post>");
        out.print("<table bgcolor=yellow align=center> ");
        out.print("<tr>");
        out.print("<td>Product Id</td>");
       // out.print("<td><b style=color:red>" + sid + "</b></td>");
        out.print("<td ><b style=color:red name=ProductId >" + sid + "</b></td>");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<td>Comments</td>");
        out.print("<td><input type=text size=35 name=txtComments >");
        out.print("</tr>");
        out.print("<tr>");
        out.print("<td colspan=2 align=center><input type=submit value=REQUEST></td>");
        out.print("</tr>");
        out.print("</table>");
        out.print("</form>");
        getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }
}