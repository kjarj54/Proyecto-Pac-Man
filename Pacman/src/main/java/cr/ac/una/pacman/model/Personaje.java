/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANTHONY
 */
public class Personaje {
    private double x, y;
    private double velocidad;
    int direccion;
    private List<String> imagen;

    public Personaje() {
        this.x = 0;
        this.y = 0;
        this.velocidad = 0;
        this.direccion = 1;
        this.imagen = new ArrayList<>();
    }
    
    public Personaje(double x, double y, double velocidad, int direccion, List<String> imagen) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public List<String> getImagen() {
        return imagen;
    }

    public void setImagen(List<String> imagen) {
        this.imagen = imagen;
    }    
}
