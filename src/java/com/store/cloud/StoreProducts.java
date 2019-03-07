/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author shiva
 */
@MultipartConfig
@WebServlet(name = "StoreProducts", urlPatterns = {"/StoreProducts"})
public class StoreProducts extends HttpServlet {

    String UPLOAD_DIRECTORY1 = "E://SecureProduct//secure//";
    String ENCRYPT_DIRECTORY1 = "E://SecureProduct//secure//Encrypt//";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String soid = (String) session.getAttribute("ownerid");

        String sn = request.getParameter("txtName");
        String sc = request.getParameter("txtCompany");
        String st = request.getParameter("txtType");
        Part photo = request.getPart("Photo");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        final Part filePart = request.getPart("confidential");
        //final String fileName = filePart.getName();
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        OutputStream out1 = null;
        InputStream filecontent = null;

        System.out.println(fileName);
        final PrintWriter writer = response.getWriter();
        UPLOAD_DIRECTORY1 = UPLOAD_DIRECTORY1 + fileName;
        System.out.println(UPLOAD_DIRECTORY1);
        try {
            out1 = new FileOutputStream(new File(UPLOAD_DIRECTORY1));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out1.write(bytes, 0, read);
            }
            String key=EncryptFile(fileName);
            CloudConnect cloud=new CloudConnect();
            File file=new File(ENCRYPT_DIRECTORY1+fileName);
            cloud.upload(file,fileName);
            
            System.out.println(key);
            Connection con = DataBase.getCon();
            PreparedStatement ps = con.prepareStatement("insert into products(oid,pid,name,company,prodtype,filelocation,key,photo) values(?,?,?,?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(soid));
            ps.setInt(2, getMax());
            ps.setString(3, sn);
            ps.setString(4, sc);
            ps.setString(5, st);
            ps.setString(6, fileName);
            ps.setString(7, key);
            ps.setBinaryStream(8, photo.getInputStream(), (int) photo.getSize());
            int c = ps.executeUpdate();
            con.close();
            if (c > 0) {
                response.sendRedirect("/CloudProduct/Product.html");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public int getMax() {
        int big = 0;
        try {
            Connection con = DataBase.getCon();
            String query = "select max(pid) from products";
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

    String EncryptFile(String fileName) {

        ArrayList pin = new ArrayList();
        String s = "";
        pin.add("1");
        pin.add("2");
        pin.add("3");
        pin.add("4");
        pin.add("5");
        pin.add("6");
        pin.add("7");
        pin.add("8");
        pin.add("9");
        pin.add("0");
        pin.add("1");
        pin.add("2");
        pin.add("3");
        pin.add("4");
        pin.add("5");
        pin.add("6");
        pin.add("7");
        pin.add("8");
        pin.add("9");
        pin.add("0");

        Collections.shuffle(pin);

        for (int i = 0; i < 16; i++) {
            s = s + pin.get(i);
        }

        String key = s;
        File inputFile = new File(UPLOAD_DIRECTORY1);
        File encryptedFile = new File(ENCRYPT_DIRECTORY1 + fileName);

        Crypto.fileProcessor(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFile);
        return key;
    }
  
}
