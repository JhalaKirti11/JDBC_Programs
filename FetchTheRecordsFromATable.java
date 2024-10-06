import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class FetchTheRecordsFromATable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the table name: ");
        String tname = sc.nextLine();
        try{
            Connection con = _1ConnectionClassJDBC.getConnection();
            String query = "Select*from "+tname;
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("sname")+" "+ rs.getInt("marks"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sc.close();
        }
    }   
}
