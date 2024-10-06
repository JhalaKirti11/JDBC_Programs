/* Create table in the database "Test" */

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

 public class CreateTable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Table Name: ");
        String tname = sc.nextLine();

        try{
            Connection con = _01ConnectDB.getConnection();
            String query = "create table "+tname+ "(So int NOT NULL AUTO_INCREMENT PRIMARY KEY, Sname varchar(10) NOT NULL, marks int)";
            Statement st = con.createStatement();
            int r = st.executeUpdate(query);
            System.out.println("Number of rows affected "+r);  
        }catch(Exception e){
           e.printStackTrace();
        }
        sc.close();
    }
}
