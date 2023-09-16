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
    private int x, y;
    private int velocidad;
    private int direccion;
    private List<String> imagen;

    public Personaje() {
        this.x = 0;
        this.y = 0;
        this.velocidad = 0;
        this.direccion = 1;
        this.imagen = new ArrayList<>();
    }
    
    public Personaje(int x, int y, int velocidad, int direccion, List<String> imagen) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.direccion = direccion;
        this.imagen = imagen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
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
