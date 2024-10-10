import java.sql.*;
import java.util.Scanner;

 class HostelManagement{
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/HostelManagement";
    private static final String  user = "root";
    private static final String  pass = "1234";
    
    static ResultSet rs;
    static PreparedStatement pst;

    public static void createTable(Connection con){
        String query = "Create table Students(sid int NOT NULL AUTO_INCREMENT primary key, name varchar(20) NOT NULL, city varchar(20), feesSt varchar(10), month varchar(10))";
        try{
            Statement st = con.createStatement();
            int rows = st.executeUpdate(query);
            System.out.println("Number of rows affected: "+ rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void insertData(Connection con,String name,String city,String feesSt, String month){
        String query = "Insert into Students(name, city, feesSt, month) values(?,?,?,?)";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,city);
            pst.setString(3,feesSt);
            pst.setString(4,month);
            int rows = pst.executeUpdate();
            System.out.println("Number of rows affected "+rows);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Display record
    public static ResultSet displayRecord(Connection con){
        rs = null;
        String query = "Select*from Students";
        try{
            pst= con.prepareStatement(query);
            rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }


    // display student from jaipur:
    public static ResultSet displayStudentOfSpecificCity(Connection con, String city){
        rs = null;
        String query = "Select*from Students where city=?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1,city);
            rs = pst.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet displayFeesPending(Connection con){
            rs = null;
        String query = "Select*from Students where feesSt='pending'";
        try{
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public static void ShowStudents(ResultSet rs){
        try{
            System.out.println("--------------------------------------------------------");
            System.out.printf("%-5s %-10s %-10s %-10s %-10s%n","sid","name","city","feesSt","month");
            System.out.println("--------------------------------------------------------");
        while(rs.next()){
            System.out.printf("%-5d %-10s %-10s %-10s %-10s%n",rs.getInt("sid"),rs.getString("name"),rs.getString("city")
                                    ,rs.getString("feesSt"),rs.getString("month"));
        }
            System.out.println("--------------------------------------------------------");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteStudentWithChar(Connection con, String ch){
        String query = "Delete from Students where name like '"+ch+"%'";
        try{
            pst = con.prepareStatement(query);
            int rows = pst.executeUpdate();
            System.out.println("Number of rows affected "+rows);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Connection con = null;
            try{
                Class.forName(driverName);
                con = DriverManager.getConnection(url,user,pass);

            int choosed = 1;
            while(choosed==1){
            System.out.println("Which operation would you like to perform:");
            System.out.println("1. Create a Table.");
            System.out.println("2. Insert data into the table.");
            System.out.println("3. Display all records.");
            System.out.println("4. Show students of a specific city.");
            System.out.println("5. Show Students whose fees is pending.");
            System.out.println("6. Delete Students record whose name starts with a specific character.");
            
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();

            switch(choice){

                case 1:
                        sc.nextLine();
                        System.out.println("\nCreate a table 'Students':");
                        createTable(con);
                    break;

                case 2:
                        System.out.println("\nInsert the student record into the table:");
                            sc.nextLine();
                        System.out.println("Enter name:");
                            String name = sc.nextLine();
                        System.out.println("Enter city:");
                            String city = sc.nextLine();
                        System.out.println("Enter fees status (e.g., paid/pending):");
                            String feesSt = sc.nextLine();
                        System.out.println("Enter month:");
                            String month = sc.nextLine();
                        insertData(con, name, city, feesSt, month);
                    break;

                case 3:
                        System.out.println("Record Of All The Students:");
                        rs = displayRecord(con);
                            if(rs!= null){
                            ShowStudents(rs);
                        }
                    break;
            
                case 4:
                        sc.nextLine();
                        System.out.print("\nStudents from a specific city:\nEnter the city: ");
                        String city = sc.nextLine();
                        rs = displayStudentOfSpecificCity(con,city);
                            if(rs!= null){
                                ShowStudents(rs);
                            }
                    break;

                case 5:
                        System.out.println("\nFees is 'Pending'");
                        rs = displayFeesPending(con);
                            if(rs!= null){
                                ShowStudents(rs);
                            }
                    break;

                case 6:
                        sc.nextLine();
                        System.out.println("\nDelete Students record whose name starts with a specific character.");
                        System.out.print("Enter the character: ");
                        String ch = sc.nextLine();
                        deleteStudentWithChar(con, ch);
                    break;

                default:
                        System.out.println("Invalid choice!");
                }

                System.out.println("Do you want to continue:\n1. Yes\n2. No");
                    choosed = sc.nextInt();
                    if(choosed!=1){
                        break;
                    }
            }

        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sc.close();
       }
    }
}
