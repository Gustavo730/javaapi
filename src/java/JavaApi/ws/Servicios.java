package JavaApi.ws;
import JavaApi.dao.*;
import JavaApi.clases.Usuario;
import JavaApi.utilitarios.Hash;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author svasquez
 */
@Path("/ws")
public class Servicios {
    
    Usuario u = new Usuario();
    Connection conn = null;
    String query = null;
    DB data = new DB();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    JSONObject json_obj = null;
    JSONArray json_arr = null;
    
    public static void main(String[] args) throws SQLException {
        System.out.println("test");
    }
    
    @Path("/login")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_HTML)
    public String doLogin(String postData) throws SQLException, JSONException {
        
        String[] params = postData.split("&");
        String param_1 = params[0];
        String param_2 = params[1];
        
        params = param_1.split("=");
        String user = params[1];
        
        params = param_2.split("=");
        String pass = Hash.sha1(params[1]);
        
        if (verificaUsuario(user, pass)) {
            json_obj = new JSONObject();
            json_obj.put("ok",true);
            json_obj.put("id", u.getId());
            json_obj.put("nombre", u.getFullName());
            
            return json_obj.toString();
        } else {
            json_obj = new JSONObject();
            json_obj.put("ok", false);
            json_obj.put("error", "usuario invalido");
            return json_obj.toString();
        }
    }
    
    @Path("/repuestos")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getRepuestos() throws SQLException{
        
        query = "Select * from repuesto";
        data.dbConnect();
        conn = data.getConnection();
        json_arr = new JSONArray();
        
        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
               json_obj = new JSONObject();
               json_obj.put("id",rs.getString("id"));
               json_obj.put("sku",rs.getString("sku"));
               json_obj.put("descripcion",rs.getString("descripcion"));
               json_obj.put("valor",rs.getString("valor_neto"));
               json_arr.put(json_obj);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            conn.close();
        }
        return json_arr.toString();
    }
    
    @Path("/usuarios")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getUsuarios() throws SQLException{
        query = "select * from usuario";
        data.dbConnect();
        conn = data.getConnection();
        
        json_arr = new JSONArray();
        
        try{
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               json_obj = new JSONObject();
               json_obj.put("id",rs.getString("id"));
               json_obj.put("rut",rs.getString("rut"));
               json_obj.put("dv",rs.getString("dv"));
               json_obj.put("correo",rs.getString("correo"));
               json_obj.put("nombre",rs.getString("nombre"));
               json_obj.put("apellido",rs.getString("apellido"));
               json_obj.put("telefono",rs.getString("telefono"));
               json_obj.put("direccion",rs.getString("direccion"));
               json_obj.put("perfil",rs.getString("id_perfil"));
               json_obj.put("pass",rs.getString("pass"));
               json_arr.put(json_obj);
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return json_arr.toString();
    }
    
    @Path("/servicios")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getServicios() throws SQLException{
        query = "SELECT t1.*, t2.nombre as nombre, t2.apellido as apellido, "
                + "t3.patente as patente, t5.rut as rut_cli, t5.dv as dv_cli, "
                + "t4.nombre as estado "
                + "FROM servicio as t1 "
                + "INNER JOIN usuario as t2 on t1.id_usuario = t2.id "
                + "INNER JOIN auto as t3 on t1.id_auto = t3.id "
                + "INNER JOIN estado_servicio as t4 on t1.id_estado = t4.id "
                + "INNER JOIN cliente as t5 on t3.id_cliente = t5.id";
        data.dbConnect();
        conn = data.getConnection();
        
        json_arr = new JSONArray();
        
        try{
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               json_obj = new JSONObject();
               json_obj.put("id",rs.getString("id"));
               json_obj.put("descripcion",rs.getString("descripcion"));
               json_obj.put("fecha_ingreso",rs.getString("fecha_ingreso"));
               json_obj.put("fecha_entrega",rs.getString("fecha_entrega"));
               json_obj.put("valor_neto",rs.getString("valor_neto"));
               json_obj.put("valor_iva",rs.getString("valor_iva"));
               json_obj.put("valor_total",rs.getString("valor_total"));
               json_obj.put("cancelado",rs.getString("cancelado"));
               json_obj.put("mano_obra",rs.getString("mano_obra"));
               json_obj.put("id_sucursal",rs.getString("id_sucursal"));
               json_obj.put("usuario",rs.getString("nombre")+" "+ rs.getString("apellido"));
               json_obj.put("id_auto",rs.getString("id_auto"));
               json_obj.put("patente",rs.getString("patente"));
               json_obj.put("id_estado",rs.getString("id_estado"));
               json_obj.put("estado",rs.getString("estado"));
               json_obj.put("rut_cliente",rs.getString("rut_cli")+"-"+rs.getString("dv_cli"));
               json_arr.put(json_obj);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return json_arr.toString();
    }
    
    public Boolean verificaUsuario(String user, String pass) throws SQLException {
        
        query = "SELECT * FROM usuario WHERE rut = ? AND pass = ? ";
        data.dbConnect();
        conn = data.getConnection();
        
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            //pstmt.setInt(2, 4);
            rs = pstmt.executeQuery();
            System.out.println("validando");
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                return true;
            }
            
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return false;
    }
    
    
}
