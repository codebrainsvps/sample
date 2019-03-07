/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shiva
 */
@WebServlet(name = "DisplayPhoto", urlPatterns = {"/DisplayPhoto"})
public class DisplayPhoto extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");

        try {
            
            Connection con = DataBase.getCon();
            PreparedStatement ps = con.prepareStatement("select photo from products where pid = ?");
            String name = request.getParameter("prodid");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Blob b = rs.getBlob("photo");

            response.setContentLength((int) b.length());
            InputStream is = b.getBinaryStream();
            OutputStream os = response.getOutputStream();
            byte buf[] = new byte[(int) b.length()];
            is.read(buf);
            os.write(buf);
            os.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
