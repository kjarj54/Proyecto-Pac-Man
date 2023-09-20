/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

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
        switch (this.getDireccion()) {
            case 0:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 0 && laberinto.getMatrizCelda((int) getY() / 20, (int) (getX() + 20) / 20) != '#'
                        && laberinto.getMatrizCelda((int) (getY() + 19) / 20, (int) (getX() + 20) / 20) != '#') {
                    this.setX(getX() + this.getVelocidad());
                }
                break;
            case 1:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 1 && laberinto.getMatrizCelda((int) (getY() + 20) / 20, (int) getX() / 20) != '#'
                        && laberinto.getMatrizCelda((int) (getY() + 20) / 20, (int) (getX() + 19) / 20) != '#') {
                    this.setY(getY() + this.getVelocidad());
                }
                break;
            case 2:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 2 && laberinto.getMatrizCelda((int) getY() / 20, (int) (getX() - 1) / 20) != '#'
                        && laberinto.getMatrizCelda(((int) getY() + 19) / 20, (int) (getX() - 1) / 20) != '#') {
                    this.setX(getX() - this.getVelocidad());
                }
                break;
            case 3:
                if (this.getDireccion() != direccion) {
                    cambioDireccion(direccion, laberinto);
                }
                if (this.getDireccion() == 3 && laberinto.getMatrizCelda((int) (getY() - 1) / 20, (int) getX() / 20) != '#'
                        && laberinto.getMatrizCelda((int) (getY() - 1) / 20, (int) (getX() + 19) / 20) != '#') {
                    this.setY(getY() - this.getVelocidad());
                }
                break;

            default:
                break;
        }
    }

    public void cambioDireccion(int direccion, Laberinto laberinto) {
        if (direccion == 0 && laberinto.getMatrizCelda((int) getY() / 20, (int) (getX() + 20) / 20) != '#'
                && laberinto.getMatrizCelda((int) (getY() + 19) / 20, (int) (getX() + 20) / 20) != '#') {
            this.setX(getX() + this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 1 && laberinto.getMatrizCelda((int) (getY() + 20) / 20, (int) getX() / 20) != '#'
                && laberinto.getMatrizCelda((int) (getY() + 20) / 20, (int) (getX() + 19) / 20) != '#') {
            this.setY(getY() + this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 2 && laberinto.getMatrizCelda((int) getY() / 20, (int) (getX() - 1) / 20) != '#'
                && laberinto.getMatrizCelda((int) (getY() + 19) / 20, (int) (getX() - 1) / 20) != '#') {
            this.setX(getX() - this.getVelocidad());
            this.setDireccion(direccion);
        } else if (direccion == 3 && laberinto.getMatrizCelda((int) (getY() - 1) / 20, (int) getX() / 20) != '#'
                && laberinto.getMatrizCelda((int) (getY() - 1) / 20, (int) (getX() + 19) / 20) != '#') {
            this.setY(getY() - this.getVelocidad());
            this.setDireccion(direccion);
        }
    }

    public void comer(Laberinto laberinto) {
        if ((this.getDireccion() == 0 || this.getDireccion() == 1) && laberinto.getMatrizCelda((int) getY() / 20, (int) getX() / 20) == 'p') {
            laberinto.setMatrizCelda(' ', (int) getY() / 20, (int) getX() / 20);
            this.puntos += 10;
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && laberinto.getMatrizCelda((int) (getY() + 19) / 20, (int) (getX() + 19) / 20) == 'p') {
            laberinto.setMatrizCelda(' ', (int) (getY() + 19) / 20, (int) (getX() + 19) / 20);
            this.puntos += 10;
        } else if ((this.getDireccion() == 0 || this.getDireccion() == 1) && laberinto.getMatrizCelda((int) getY() / 20, (int) getX() / 20) == '*') {
            laberinto.setMatrizCelda(' ', (int) getY() / 20, (int) getX() / 20);
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && laberinto.getMatrizCelda((int) (getY() + 19) / 20, (int) (getX() + 19) / 20) == '*') {
            laberinto.setMatrizCelda(' ', (int) (getY() + 19) / 20, (int) (getX() + 19) / 20);
        }
    }

    public void pintar(GraphicsContext graficos, double segundoAct, double segundoAnt) {
        double angulo = 0;
        switch (this.getDireccion()) {
            case 0:
                angulo = 0;
                break;
            case 1:
                angulo = 90;
                break;
            case 2:
                angulo = 180;
                break;
            case 3:
                angulo = -90;
                break;
            default:
                break;
        }
        Affine oldTransform = graficos.getTransform();
        Rotate rotate = new Rotate(angulo, getX() + 10, getY() + 10);
        Affine transform = new Affine();
        transform.append(rotate);
        graficos.setTransform(transform);

        if (segundoAct > segundoAnt + 0.25) {
            graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, 15, 15);
        } else {
            graficos.drawImage(new Image(getImagen().get(1)), getX() + 2, getY() + 2, 15, 15);
        }
        graficos.setTransform(oldTransform);
    }
}
