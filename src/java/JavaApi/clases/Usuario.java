package JavaApi.clases;

/**
 *
 * @author svasquez
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String apellido;
    private Boolean estado;
    
    public Usuario(){
        
    }
    
    public Usuario(int id, String nombre, String apellido, Boolean estado){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
    }
    
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    
    public Boolean getEstado(){
        return estado;
    }
    
    public void setEstado(Boolean estado){
        this.estado = estado;
    }
    
    public String getFullName(){
        return nombre + " " + apellido;
    }
    
    
    
}
