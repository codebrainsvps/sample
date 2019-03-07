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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shiva
 */
public class CheckOwner extends HttpServlet {
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        String su = request.getParameter("txtUsr");
        String sp = request.getParameter("txtPwd");
        try {
            Connection con = DataBase.getCon();
            String query = "select oid,status from owners where username=? and password=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, su);
            st.setString(2, sp);
            ResultSet rs = st.executeQuery();
            //Authentication
            if (rs.next()) {
                if(rs.getString("status").equals("n")){
                    out.print("Your Records Are Under Processing.Please Wait......");
                }
                else
                {
                    HttpSession session=request.getSession();
                    session.setAttribute("ownerid", rs.getString("oid"));
                response.sendRedirect("/CloudProduct/OwnerOptions.html");
                }

            } else {
                out.print("Credentials are wrong.");
                out.print("Please <a href=/CloudProduct/CloudLogin.html>SingUp</a>");
            }
            st.close();
            con.close();
            getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}