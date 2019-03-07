/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shiva
 */
public class UpdateOwnerStatus extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String si = request.getParameter("ownerid");
        try {
            Connection con = DataBase.getCon();
            String query = "update owners set status='y' where oid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1,Integer.parseInt(si)) ;
            int c = st.executeUpdate();
            if (c > 0) {
               response.sendRedirect("/CloudProduct/ViewOwners");
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}