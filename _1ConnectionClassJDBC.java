import java.sql.Connection;
import java.sql.DriverManager;

public class _1ConnectionClassJDBC {

    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Test";
    private static final String name = "root";
    private static final String pass = "1234";

    public static Connection getConnection(){
        Connection con = null;
        try{
            Class.forName(driverName);
            con = DriverManager.getConnection(url,name,pass);
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
