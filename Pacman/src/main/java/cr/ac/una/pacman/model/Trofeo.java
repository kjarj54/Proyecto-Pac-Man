package cr.ac.una.pacman.model;

/**
 *
 * @author ANTHONY
 */
public class Trofeo {

    private String nombre;
    private String descripcion;
    private boolean desbloqueado;
    private int cont;

    public Trofeo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.desbloqueado = false;
        this.cont = 0;
    }

    // Métodos getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont += cont;
    }

}
