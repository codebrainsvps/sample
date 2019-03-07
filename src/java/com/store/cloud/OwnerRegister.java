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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CloudProduct", urlPatterns = {"/CloudProduct"})
public class OwnerRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String su = request.getParameter("txtUsr");
        String sp = request.getParameter("txtPwd");
        String se = request.getParameter("txtEmail");
        String sph = request.getParameter("txtPhone");
        String sa = request.getParameter("txtAdd");
        String si = request.getParameter("txtIncome");
        String sr = request.getParameter("txtRegnum");
        String spa = request.getParameter("txtpan");
        try {
            Connection con = DataBase.getCon();
            String query = "insert into cloud(cid,username,password,email,phone,address,Income) values(?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
             st.setInt(1, getMax());
            st.setString(2, su);
            st.setString(3, sp);
            st.setString(4, se);
            st.setString(5, sph);
            st.setString(6, sa);
            st.setString(7,si);
            st.setString(8, sr);
            st.setString(9, spa);
            
            int c = st.executeUpdate();
            if (c > 0) {
                out.print("Successfully Registered.");
                out.print("Please <a href=/CloudProduct/Register.html>SingIn</a>");
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getMax()
    {
        int big=0;
        try{
           Connection con=DataBase.getCon(); 
           String query = "select max(custid) from CloudProduct";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if(rs.next())
            {
                big=rs.getInt(1)+1;
            }
            else
            {
                big=1;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return big;
    }
}
