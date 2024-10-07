import java.sql.*;
import java.util.Scanner;

public class SupplierOrderCRUD {
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Supply";
    private static final String user = "root";
    private static final String pass = "1234";

    Scanner sc = new Scanner(System.in);
    PreparedStatement pst;
    ResultSet rs;

    public void createTable(Connection con){
        String query = "create table SupplierOrder(orderID int AUTO_INCREMENT NOT NULL primary key, supplier_name varchar(20) NOT NULL, product varchar(20), order_date date)";
        try{
            Statement st = con.createStatement();
            int rows = st.executeUpdate(query);
            System.out.println("Number of rows affected "+rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void placeOrder(Connection con){
        String query = "insert into SupplierOrder(supplier_name,product,order_date,quantity) values(?,?,?,?)";
        System.out.println("Enter Supplier name, product name, date of order and quantity:");
        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,sc.nextLine());
            pst.setString(2, sc.nextLine());

            String date = sc.nextLine();
            Date sqlDate = Date.valueOf(date);
            pst.setDate(3, sqlDate);
            pst.setInt(4,sc.nextInt());

            int rows = pst.executeUpdate();
            System.out.println("Number of rows affected "+rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet retrieveOrder(Connection con){
            rs = null;
            String query = "select*from supplierorder where supplier_name =?";
            System.out.print("Enter supplier name: ");
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, sc.nextLine());
            rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public void printOutput(ResultSet rs){
        System.out.println("Output");
        try{
            System.out.printf("%-10s %-20s %-10s %-10s %-10s%n","orderID","supplier_name","product","quantity","order_date");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            while(rs.next()){
            System.out.printf("%-10d %-20s %-10s %-10d %-10s%n",rs.getInt("orderID"),rs.getString("supplier_name"),
                rs.getString("product"), rs.getInt("quantity"), rs.getDate("order_date"));
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet retrieveOrderDate(Connection con){
            rs = null;
            String query = "select*from SupplierOrder where order_date = ?";
            System.out.print("Enter date: ");
            String date = sc.nextLine();
        try{
            pst = con.prepareStatement(query);
            Date inDate = Date.valueOf(date);
            pst.setDate(1,inDate);
            rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public void updateQuantity(Connection con){
        String query = "update supplierOrder set quantity = ? where orderID = ?";
        System.out.println("Enter new quantity and orderID:");
        try{
            pst = con.prepareStatement(query);
            pst.setInt(1,sc.nextInt());
            pst.setInt(2,sc.nextInt());
            int rows = pst.executeUpdate();
            System.out.println("Number of rows affected "+rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void cancelOrder(Connection con){
        String query = "delete from supplierOrder where orderID = ?";
        System.out.print("Enter orderID: ");
        try{
            pst = con.prepareStatement(query);
            pst.setInt(1,sc.nextInt());
            int rows = pst.executeUpdate();
            System.out.println("Number of rows affected "+rows);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        SupplierOrder order = new SupplierOrder();
        Scanner sc = new Scanner(System.in);
        ResultSet rs;
        try{
            Class.forName(driverName);
            con = DriverManager.getConnection(url,user,pass);
            
            // if(con == null){
            //     System.out.println("no connected!");
            // }else{
            //     System.out.println("Connected!");
            // }
            int choose = 1;
            while(choose==1){
                System.out.println("Which operation would you like to perform?");
                System.out.println("1. Create Table");
                System.out.println("2. Place a new order");
                System.out.println("3. Retrieve all orders from a specific supplier");
                System.out.println("4. Retrieve all orders from a specific date");
                System.out.println("5. Update the quantity of a specific order");
                System.out.println("6. Cancel an order based on its ID");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                        order.createTable(con);
                        break;
                case 2:
                        order.placeOrder(con);
                        break;

                case 3:
                        rs = order.retrieveOrder(con);
                        if(rs!= null){
                            order.printOutput(rs);
                        }
                        break;

                case 4:
                        rs = order.retrieveOrderDate(con);
                        if(rs!=null){
                            order.printOutput(rs);
                        }
                        break;

                case 5:
                        order.updateQuantity(con);
                        break;

                case 6:
                        order.cancelOrder(con);
                        break;

                default:
                        System.out.println("Invalid Input!");
            }

                System.out.println("Want to continue...\n1.Yes\n2.No");
                choose = sc.nextInt();
                    if(choose != 1){
                        break;
                    }else if(choose!=1 ||choose != 2){
                        System.out.println("Invalid Input!");
                    }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sc.close();
        }
    }
}
