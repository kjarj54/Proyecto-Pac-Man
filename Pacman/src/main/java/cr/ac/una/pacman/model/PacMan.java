/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import java.awt.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

/**
 *
 * @author ANTHONY
 */
public class PacMan extends Personaje {

    private int vidas;
    private int puntos;
    private String color;

    public PacMan() {
        super(0, 0, 1, 1, "");
        this.vidas = 1;
        this.puntos = 0;
        this.color = "";
    }

    public PacMan(int vidas, int puntos, String color, int x, int y, int velocidad, int direccion, String imagen) {
        super(x, y, velocidad, direccion, imagen);
        this.vidas = vidas;
        this.puntos = puntos;
        this.color = color;
    }

    // Métodos
    public void mover(int direccion, Laberinto laberinto) {
        switch (this.getDireccion()) {
            case 0:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 0 && laberinto.getMatrizCelda(getY() / 20, (getX() + 1) / 20) != '#' && laberinto.getMatrizCelda(getY() / 20, (getX() + 20) / 20) != '#'
                        && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() + 1) / 20) != '#' && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() + 20) / 20) != '#') {
                    this.setX(getX() + 1);
                }
                break;
            case 1:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 1 && laberinto.getMatrizCelda((getY() + 1) / 20, getX() / 20) != '#' && laberinto.getMatrizCelda((getY() + 20) / 20, getX() / 20) != '#') {
                    this.setY(getY() + 1);
                }
                break;
            case 2:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (direccion == 2 && laberinto.getMatrizCelda(getY() / 20, (getX() - 1) / 20) != '#'
                        && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() - 1) / 20) != '#') {
                    this.setX(getX() - 1);
                }
                break;
            case 3:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 3
                        && laberinto.getMatrizCelda((getY() - 1) / 20, getX() / 20) != '#') {
                    this.setY(getY() - 1);
                }
                break;

            default:
                break;
        }
    }

    public void cambioDireccion(int direccion, Laberinto laberinto) {
        if (direccion == 0 && laberinto.getMatrizCelda(getY() / 20, (getX() + 1) / 20) != '#' && laberinto.getMatrizCelda(getY() / 20, (getX() + 20) / 20) != '#'
                && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() + 1) / 20) != '#' && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() + 20) / 20) != '#') {
            this.setX(getX() + 1);
            this.setDireccion(direccion);
        } else if (direccion == 1 && laberinto.getMatrizCelda((getY() + 1) / 20, getX() / 20) != '#' && laberinto.getMatrizCelda((getY() + 20) / 20, getX() / 20) != '#') {
            this.setY(getY() + 1);
            this.setDireccion(direccion);
        } else if (direccion == 2 && laberinto.getMatrizCelda(getY() / 20, (getX() - 1) / 20) != '#'
                && laberinto.getMatrizCelda((getY() + 19) / 20, (getX() - 1) / 20) != '#') {
            this.setX(getX() - 1);
            this.setDireccion(direccion);
        } else if (direccion == 3 && laberinto.getMatrizCelda((getY() - 1) / 20, getX() / 20) != '#') {
            this.setY(getY() - 1);
            this.setDireccion(direccion);
        }
    }

    public void comer() {
        // Código para que Pac-Man coma puntos
    }

    public void pintar(GraphicsContext graficos) {
        graficos.drawImage(new Image(getImagen()), getX(), getY());
//        graficos.setStroke(Color.RED);
        graficos.strokeRect(getX(), getY(), 20, 20);
    }
}

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package cr.ac.una.pacman.model;
//
//import java.awt.Color;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import static javafx.scene.input.KeyCode.DOWN;
//import static javafx.scene.input.KeyCode.LEFT;
//import static javafx.scene.input.KeyCode.RIGHT;
//import static javafx.scene.input.KeyCode.UP;
//
///**
// *
// * @author ANTHONY
// */
//public class PacMan extends Personaje {
//
//    private int vidas;
//    private int puntos;
//    private String color;
//
//    public PacMan() {
//        super(0, 0, 1, 1, "");
//        this.vidas = 1;
//        this.puntos = 0;
//        this.color = "";
//    }
//
//    public PacMan(int vidas, int puntos, String color, int x, int y, int velocidad, int direccion, String imagen) {
//        super(x, y, velocidad, direccion, imagen);
//        this.vidas = vidas;
//        this.puntos = puntos;
//        this.color = color;
//    }
//
//    // Métodos
//    public void mover(int direccion, Laberinto laberinto) {
//        switch (direccion) {
//            case 0:
//                if (laberinto.getMatrizCelda(getY(), getX() + 1) != '#') {
//                    this.setX(getX() + 1);
//                }
//                break;
//            case 1:
//                if (laberinto.getMatrizCelda(getY() + 1, getX()) != '#') {
//                    this.setY(getY() + 1);
//                }
//                break;
//            case 2:
//                if (laberinto.getMatrizCelda(getY(), getX() - 1) != '#') {
//                    this.setX(getX() - 1);
//                }
//                break;
//            case 3:
//                if (laberinto.getMatrizCelda(getY() - 1, getX()) != '#') {
//                    this.setY(getY() - 1);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void comer() {
//        // Código para que Pac-Man coma puntos
//    }
//
//    public void pintar(GraphicsContext graficos) {
//        graficos.drawImage(new Image(getImagen()), getX() * 20, getY() * 20);
//    }
//}
