package com.store.cloud;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.net.ftp.FTPClient;
public class CloudConnect {
    FTPClient client = new FTPClient();
    FileInputStream fis = null;
    boolean status;
    public boolean upload(File file,String fname) {
        try {
            System.out.println("file is  your file "+fname);
            System.out.println("file is  your file "+file);
            System.out.println("successpavie");
            client.connect("ftp.drivehq.com");
            client.login("drive007", "drive007");
            System.out.println("file is  your file "+fname);
            System.out.println("file is  your file "+file);
            client.enterLocalPassiveMode();
            fis = new FileInputStream(file);
            status = client.storeFile("\\My Documents\\" + fname, fis);
            client.logout();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (status) {
            System.out.println("success");
            return true;
        } else {
            System.out.println("failed");
            return false;
        }
    }
}
