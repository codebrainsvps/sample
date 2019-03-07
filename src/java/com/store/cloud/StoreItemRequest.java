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

public class StoreItemRequest extends HttpServlet {

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
          getServletContext().getRequestDispatcher("/Header.html").include(request, response);
          String soid = request.getParameter("oid");
       String prodid = request.getParameter("ProductId");
       String comment = request.getParameter("txtComments");
       System.out.println(comment);
        System.out.println(prodid);
         HttpSession session = request.getSession();
        //HttpSession session = request.getSession();
        String custid = (String) session.getAttribute("custId");
       
        
       try
       {
           Connection con = DataBase.getCon();
            String query = "insert into reqitems(cid,oid,pid,comments) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, custid);
            st.setString(2, soid);
            st.setString(3, prodid);
            st.setString(4, comment);

            int c=st.executeUpdate();// ResultSet rs = st.executeQuery();
            if (c>0)
            {
                out.println("your request is sent");
                
                //response.sendRedirect("/CloudProduct/CustomerOptions.html");
            }
               getServletContext().getRequestDispatcher("/Footer.html").include(request, response);
            
           }catch(Exception e){
               e.printStackTrace();
       }
       
        
    }

}

