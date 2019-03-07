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

/**
 *
 * @author shiva
 */
public class StoreOwner extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        String su = request.getParameter("txtUsr");
        String sp = request.getParameter("txtPwd");
        String se = request.getParameter("txtEmail");
        String sph = request.getParameter("txtPhone");
        String sa = request.getParameter("txtAdd");
        String si = request.getParameter("txtIncome");
        String sr = request.getParameter("txtReg");
        String span = request.getParameter("txtPan");
        String sty = request.getParameter("txtType");

        try {
            Connection con = DataBase.getCon();
            String query = "insert into owners(oid,username,password,email,phone,address,income,regnumber,pan,typeofproducts) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, getMax());
            st.setString(2, su);
            st.setString(3, sp);
            st.setString(4, se);
            st.setString(5, sph);
            st.setString(6, sa);
            st.setString(7, si);
            st.setString(8, sr);
            st.setString(9, span);
            st.setString(10, sty);



            int c = st.executeUpdate();
            if (c > 0) {
                out.print("Successfully Registered");
                out.print("Please <a href=/CloudProduct/OwnerLogin.html>SingIn</a>");
            }
            st.close();
            con.close();
            getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMax() {
        int big = 0;
        try {
            Connection con = DataBase.getCon();
            String query = "select max(oid) from owners";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                big = rs.getInt(1) + 1;
            } else {
                big = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return big;
    }
}