import java.sql.*;
import java.util.Scanner;

public class EmployeeTableCRUD {
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Industry";
    private static final String user = "root";
    private static final String pass = "1234";
    Scanner sc = new Scanner(System.in);
    Statement st;
    PreparedStatement pst;
    
    public void createTable(Connection con){
        String query = "create table Employee(id int AUTO_INCREMENT Primary Key, name varchar(20), department varchar(20), salary double(10,2))";
        try{
        st = con.createStatement();
        int rows = st.executeUpdate(query);
        System.out.println("number of rows affected "+rows);
        }catch(Exception e){
            System.out.println("can not create the table!");
        }
    }

    // Add employee's data to the employee table: 
    public void insertEmployeeData(Connection con){
      //  sc = new Scanner(System.in);
        System.out.println("Enter employee name, department and salary");
        String query = "insert into Employee(name,department,salary) values(?,?,?)";
        try{
        pst =con.prepareStatement(query);
        pst.setString(1,sc.nextLine());
        pst.setString(2,sc.nextLine());
        pst.setDouble(3, sc.nextDouble());
        int rows = pst.executeUpdate();
        System.out.println("Number of rows affected "+rows);

    }catch(Exception e){
        e.printStackTrace();
    }
}

// retrieve the records of Emp,oyee table:
public ResultSet retrieveEmployee(Connection con){
    String query = "Select*from Employee";
    ResultSet result = null;
    try{
    pst = con.prepareStatement(query);
        result = pst.executeQuery();
    }catch(Exception e){
        e.printStackTrace();
    }
    return result;
}

public void printEmployeeTable(ResultSet result){
    try{
    System.out.printf("%-5s %-10s %-20s %-10s%n", "ID", "Name", "Department", "Salary");
    System.out.println("---------------------------------------------------------");
    while (result.next()) {
        System.out.printf("%-5d %-10s %-20s %-10.2f%n",
                result.getInt("id"),
                result.getString("name"),
                result.getString("department"),
                result.getDouble("salary"));
    }
    System.out.println("---------------------------------------------------------");
}catch(Exception e){
    e.printStackTrace();
}
}

//  Update an employee's salary based on their ID
    public void updateSalary(Connection con){
        String query= "Update Employee set salary = ? where id=?";
        System.out.println("Enter new salary and employeeID:");
        try{
            pst = con.prepareStatement(query);
            pst.setDouble(1,sc.nextDouble());
            pst.setInt(2,sc.nextInt());
            int rows = pst.executeUpdate();
            System.out.println("Salary updated successfully");
            System.out.println("Number of rows affected "+rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

// Delete an employee record
    public void deleteRecord(Connection con){
        String query = "delete from Employee where id = ?";
        System.out.print("Enter employeeID: ");
        try{
            pst = con.prepareStatement(query);
            pst.setInt(1,sc.nextInt());
            int rows = pst.executeUpdate();
            System.out.println("Number of rows of affected "+rows);
            System.out.println("Deleted successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    public static void main(String[] args) {
        Connection con = null;
        Scanner sc = new Scanner(System.in);
        EmployeeTable et = new EmployeeTable();

        try{
        Class.forName(driverName);
        con = DriverManager.getConnection(url,user,pass);

        System.out.println("Which operation would you like to performe: ");
        System.out.println("1. Create table Employee");
        System.out.println("2. Add new employee:");
        System.out.println("3. Retrieve employee data");
        System.out.println("4. Update salary");
        System.out.println("5. Delete an Employee's record");

        int choice = sc.nextInt();
        switch(choice){

            case 1:
                  System.out.println("Create table Employee:");
                  et.createTable(con);
                  break;

           case 2:
                  System.out.println("Add new employee:");
                  et.insertEmployeeData(con);
                  break;

            case 3:
                  System.out.println("Retrieve employee data");
                  ResultSet result = et.retrieveEmployee(con);
                    if (result != null) {
                        et.printEmployeeTable(result);
                    }
                  break;

            case 4:
                  System.out.println("Update salary");
                  et.updateSalary(con);
                  break;

            case 5:
                  System.out.println("Delete an Employee's record");
                  et.deleteRecord(con);
                  break;

            default:
                  System.out.println("Invalid Input");
        }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sc.close();
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Connection not closed!");
            }
        }
    }
}
