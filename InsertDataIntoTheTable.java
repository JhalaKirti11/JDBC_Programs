import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertDataIntoTheTable {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Table Name: ");
        String tname = sc.nextLine();
        try{
            Connection con = _1ConnectionClassJDBC.getConnection();
            String query = "insert into "+tname+ "(Sname, marks) values(?,?)";
            PreparedStatement st = con.prepareStatement(query);

            System.out.println("Enter student name and marks: ");
            st.setString(1,sc.nextLine());
            st.setInt(2,sc.nextInt());
            int r = st.executeUpdate();
            System.out.println("Number of rows affected "+r);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sc.close();
        }
    }   
}
