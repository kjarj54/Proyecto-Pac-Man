/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

/**
 *
 * @author ANTHONY
 */
public class PacMan extends Personaje {

    private int vidas;
    private int puntos;
    private String color;

    public PacMan() {
        super(0, 0, 1, 1, null);
        this.vidas = 1;
        this.puntos = 0;
        this.color = "";
    }

    public PacMan(int vidas, int puntos, String color, double x, double y, double velocidad, int direccion, List<String> imagen) {
        super(x, y, velocidad, direccion, imagen);
        this.vidas = vidas;
        this.puntos = puntos;
        this.color = color;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Métodos
    public void mover(int direccion, Laberinto laberinto) {
        if (this.getDireccion() != direccion && (int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
            cambioDireccion(direccion, laberinto);
        }
        switch (this.getDireccion()) {
            case 0 -> {
                if (this.getDireccion() == 0 && laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() + SIZE) / SIZE) != '#'
                        && laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + SIZE) / SIZE) != '#') {
                    this.setX(getX() + this.getVelocidad());
                }
            }
            case 1 -> {
                if (this.getDireccion() == 1 && laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) getX() / SIZE) != '#'
                        && laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE) != '#') {
                    this.setY(getY() + this.getVelocidad());
                }
            }
            case 2 -> {
                if (this.getDireccion() == 2 && laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() - 1) / SIZE) != '#'
                        && laberinto.getMatrizCelda(((int) getY() + (SIZE - 1)) / SIZE, (int) (getX() - 1) / SIZE) != '#') {
                    this.setX(getX() - this.getVelocidad());
                }
            }
            case 3 -> {
                if (this.getDireccion() == 3 && laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) getX() / SIZE) != '#'
                        && laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE) != '#') {
                    this.setY(getY() - this.getVelocidad());
                }
            }

            default -> {
            }
        }
    }

    public void cambioDireccion(int direccion, Laberinto laberinto) {
        if (direccion == 0 && laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() + SIZE) / SIZE) != '#'
                && laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + SIZE) / SIZE) != '#') {
            this.setX(getX() + this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 1 && laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) getX() / SIZE) != '#'
                && laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE) != '#') {
            this.setY(getY() + this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 2 && laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() - 1) / SIZE) != '#'
                && laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() - 1) / SIZE) != '#') {
            this.setX(getX() - this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 3 && laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) getX() / SIZE) != '#'
                && laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE) != '#') {
            this.setY(getY() - this.getVelocidad());
            this.setDireccion(direccion);
        }
    }

    public void comer(Laberinto laberinto, Juego juego) {
        char celda = laberinto.getMatrizCelda((int) getY() / SIZE, (int) getX() / SIZE); 
        char celda1 = laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
        if ((this.getDireccion() == 0 || this.getDireccion() == 1) && celda == 'p') {
            laberinto.setMatrizCelda(' ', (int) getY() / SIZE, (int) getX() / SIZE);
            this.puntos += 10;
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && celda1 == 'p') {
            laberinto.setMatrizCelda(' ', (int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
            this.puntos += 10;
        } else if ((this.getDireccion() == 0 || this.getDireccion() == 1) && celda == '*') {
            laberinto.setMatrizCelda(' ', (int) getY() / SIZE, (int) getX() / SIZE);
            juego.powerPellet();
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && celda1 == '*') {
            laberinto.setMatrizCelda(' ', (int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
            juego.powerPellet();
        }
    }

    public void pintar(GraphicsContext graficos, double segundoAct, double segundoAnt) {
        double angulo = 0;
        switch (this.getDireccion()) {
            case 0 -> angulo = 0;
            case 1 -> angulo = 90;
            case 2 -> angulo = 180;
            case 3 -> angulo = -90;
            default -> {
            }
        }
        Affine oldTransform = graficos.getTransform();
        Rotate rotate = new Rotate(angulo, getX() + 10, getY() + 10);
        Affine transform = new Affine();
        transform.append(rotate);
        graficos.setTransform(transform);

        if (segundoAct > segundoAnt + 0.25) {
            graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
        } else {
            graficos.drawImage(new Image(getImagen().get(1)), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
        }
        graficos.setTransform(oldTransform);
    }
}
