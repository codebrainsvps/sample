/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class ShareConfidential extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sp = request.getParameter("prodid");
        String so = request.getParameter("oid");
        String sc = request.getParameter("cid");
        try {
            Connection con = DataBase.getCon();
            String query = "select * from products where oid=? and pid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, Integer.parseInt(so));
            st.setInt(2, Integer.parseInt(sp));

            ResultSet rs = st.executeQuery();
            rs.next();
            String sf = rs.getString("filelocation");
            String sk = rs.getString("key");

            query = "update reqitems set status='yes',filelocation=?,key=? where pid=? and cid=? and oid=?";
            st = con.prepareStatement(query);
            st.setString(1, sf);
            st.setString(2, sk);
            st.setInt(3, Integer.parseInt(sp));
            st.setInt(4, Integer.parseInt(sc));
            st.setInt(5, Integer.parseInt(so));
            int c = st.executeUpdate();
            if (c > 0) {
                response.sendRedirect("/CloudProduct/ViewCustomerRequest");
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
