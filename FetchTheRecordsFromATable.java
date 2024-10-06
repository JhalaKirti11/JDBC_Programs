import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class FetchTheRecordsFromATable {
    Connection con = _1ConnectionClassJDBC.getConnection();
    PreparedStatement st;
    ResultSet rs;

    public ResultSet getAllRecords(String tname){
        try{
            String getAllRecord = "Select*from "+tname;
            st = con.prepareStatement(getAllRecord);
            rs = st.executeQuery();
            System.out.println("rs1 "+rs);
        return rs;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getSpecificRecord(String tname, String sname){
        try{
            String query = "Select*from "+tname+" where sname = ?";
            st = con.prepareStatement(query);
            st.setString(1, sname);
            rs = st.executeQuery();
            System.out.println("rs2 "+rs);
            return rs;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Want all records");
        System.out.println("2. Want a specific Student's record");
            int choice = sc.nextInt();
            sc.nextLine();

        System.out.print("Enter the table name: ");
        String tname = sc.nextLine();
            FetchTheRecordsFromATable record = new FetchTheRecordsFromATable();

        try{
            switch(choice){
                case 1:         // want all records:
                    ResultSet rs = record.getAllRecords(tname);
                    while(rs.next()){
                        System.out.println(rs.getString("sname")+" "+ rs.getInt("marks"));
                    }
                break;
                case 2:         // want a single student record:
                    System.out.print("Enter student name: ");
                    String sname = sc.nextLine();
                    rs = record.getSpecificRecord(tname, sname);
                        while(rs.next()){
                            System.out.println(rs.getString("sname")+" "+ rs.getInt("marks"));
                        }
                    break;
                default:
                        System.out.println("Invalid input!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            // Close resources
            try {
                if (record.rs != null) record.rs.close();
                if (record.st != null) record.st.close();
                if (record.con != null) record.con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sc.close();
        }
    }
}
