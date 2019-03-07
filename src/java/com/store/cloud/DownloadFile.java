/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DownloadFile extends HttpServlet {

    String UPLOAD_DIRECTORY1 = "E://SecureProduct//secure//";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
      //   getServletContext().getRequestDispatcher("/Header.html").include(request, response);
        String fileName = request.getParameter("filename");
        
        
       
        System.out.println(fileName+" Dwn_File");
      
        HttpSession session = request.getSession();
        String su = (String) session.getAttribute("username");

      
       String p=UPLOAD_DIRECTORY1+ fileName;
        System.out.println(p);
         FileInputStream in = new FileInputStream(p);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + fileName + "\"");

       
int i;   
while ((i=in.read()) != -1) {  

    out.write(i); 

}   
in.close();   
out.close();  
       //  getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
    }
}
