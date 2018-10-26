package JavaApi.dao;
import java.sql.*;

/**
 *
 * @author svasquez
 */
public class DB {
    
    private static Connection cnx = null;
    private static ResultSet rs = null;
    private static Statement stm = null;
    
    private static String server = "repuestos.mssql.somee.com";
    private static String port = "1433";
    private static String database = "repuestos";
    private static String user = "svasquez_SQLLogin_1";
    private static String pass = "23hy5e61z2";
    
    public static void main(String[] args){
        Connection conn = null;
        String query = "Select * from repuestos";
        
        DB data = new DB();
        data.dbConnect();
        conn = data.getConnection();
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM repuesto");
            ResultSet resp = sql.executeQuery();
            while(resp.next()){
                System.out.println(resp.getString("sku"));
            }
        } catch(Exception e){
            System.out.println("error: "+e);
        }
        
    }
    
    public void dbConnect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String cadena="jdbc:sqlserver://"+server+":"+port+";"+"database="+database+";user="+user+";password="+pass+";";
            cnx=DriverManager.getConnection(cadena);
            stm=cnx.createStatement();
            System.out.println("Conectado a la Base de datos");
            
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
    public Connection getConnection(){
        return cnx;
    }
    
}