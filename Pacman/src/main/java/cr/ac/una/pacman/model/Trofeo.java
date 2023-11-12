/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

/**
 *
 * @author ANTHONY
 */
public class Trofeo {

    private String nombre;
    private int anio;
    private String descripcion;
    private boolean desbloqueado;
    private int cont;

    public Trofeo(String nombre, int anio, String descripcion) {
        this.nombre = nombre;
        this.anio = anio;
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

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
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
