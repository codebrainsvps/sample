/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.cloud;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author shiva
 */
public class DataBase {

    public static Connection getCon() {
        Connection con = null;
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Class.forName("oracle.jdbc.driver.OracleDriver");//loading Driver
            con = DriverManager.getConnection(url, "cloud", "cloud");//getting connection
        } catch (Exception e) {
            return null;
        }
        return con;
    }
}
