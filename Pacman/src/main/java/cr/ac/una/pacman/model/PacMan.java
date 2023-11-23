/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
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
    public boolean superVelocidad;

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
        this.superVelocidad = false;
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
        if (this.getDireccion() != direccion
                && (int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
            cambioDireccion(direccion, laberinto);
        }
        switch (this.getDireccion()) {
            case 0 -> {
                char compro1 = laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() + SIZE) / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + SIZE) / SIZE);
                if (this.getDireccion() == 0 && compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getX() + SIZE >= COLUMNS * SIZE - 1) {
                        this.setX(20);
                    } else {
                        this.setX(getX() + this.getVelocidad());
                    }
                }
            }
            case 1 -> {
                char compro1 = laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) getX() / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
                if (this.getDireccion() == 1 && compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getY() + SIZE >= ROWS * SIZE - 1) {
                        this.setY(20);
                    } else {
                        this.setY(getY() + this.getVelocidad());
                    }
                }
            }
            case 2 -> {
                char compro1 = laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() - 1) / SIZE);
                char compro2 = laberinto.getMatrizCelda(((int) getY() + (SIZE - 1)) / SIZE, (int) (getX() - 1) / SIZE);
                if (this.getDireccion() == 2 && compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getX() <= 20) {
                        this.setX((COLUMNS - 1) * SIZE);
                    } else {
                        this.setX(getX() - this.getVelocidad());
                    }
                }
            }
            case 3 -> {
                char compro1 = laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) getX() / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
                if (this.getDireccion() == 3 && compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getY() <= 20) {
                        this.setY((ROWS - 1) * SIZE);
                    } else {
                        this.setY(getY() - this.getVelocidad());
                    }
                }
            }
            default -> {
            }
        }
    }

    public void cambioDireccion(int direccion, Laberinto laberinto) {
        switch (direccion) {
            case 0 -> {
                char compro1 = laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() + SIZE) / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() + (SIZE - 1)) / SIZE, (int) (getX() + SIZE) / SIZE);
                if (compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getX() + SIZE >= COLUMNS * SIZE - 1) {
                        this.setX(20);
                    } else {
                        this.setX(getX() + this.getVelocidad());
                    }
                    this.setDireccion(direccion);
                }
            }
            case 1 -> {
                char compro1 = laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) getX() / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() + SIZE) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
                if (compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getY() + SIZE >= ROWS * SIZE - 1) {
                        this.setY(20);
                    } else {
                        this.setY(getY() + this.getVelocidad());
                    }
                    this.setDireccion(direccion);
                }
            }
            case 2 -> {
                char compro1 = laberinto.getMatrizCelda((int) getY() / SIZE, (int) (getX() - 1) / SIZE);
                char compro2 = laberinto.getMatrizCelda(((int) getY() + (SIZE - 1)) / SIZE, (int) (getX() - 1) / SIZE);
                if (compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getX() <= 20) {
                        this.setX((COLUMNS - 1) * SIZE);
                    } else {
                        this.setX(getX() - this.getVelocidad());
                    }
                    this.setDireccion(direccion);
                }
            }
            case 3 -> {
                char compro1 = laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) getX() / SIZE);
                char compro2 = laberinto.getMatrizCelda((int) (getY() - 1) / SIZE, (int) (getX() + (SIZE - 1)) / SIZE);
                if (compro1 != '#' && compro2 != '#' && compro1 != '-' && compro2 != '-') {
                    if (this.getY() <= 20) {
                        this.setY((ROWS - 1) * SIZE);
                    } else {
                        this.setY(getY() - this.getVelocidad());
                    }
                    this.setDireccion(direccion);
                }
            }
            default -> {
            }
        }
    }

    private boolean enCeldaCentro() {
        return (int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE
                && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE;
    }

    public void comer(Laberinto laberinto, Juego juego, Partida partida) { // Come Pac-dots y Power Pellets ademas revisa si come fantasmas y si son consecutivos
        int x = (int) getX() / SIZE;
        int x1 = (int) (getX() + (SIZE - 1)) / SIZE;
        int y = (int) getY() / SIZE;
        int y1 = (int) (getY() + (SIZE - 1)) / SIZE;
        char celda = laberinto.getMatrizCelda(y, x);
        char celda1 = laberinto.getMatrizCelda(y1, x1);

        if ((this.getDireccion() == 0 || this.getDireccion() == 1) && celda == 'p') {
            laberinto.setMatrizCelda(' ', y, x);
            this.puntos += (10 * juego.multiplicadorPuntaje);
            juego.puntosActuales -= 1;
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && celda1 == 'p') {
            laberinto.setMatrizCelda(' ', y1, x1);
            this.puntos += (10 * juego.multiplicadorPuntaje);
            juego.puntosActuales -= 1;
        } else if ((this.getDireccion() == 0 || this.getDireccion() == 1) && celda == '*') {
            laberinto.setMatrizCelda(' ', y, x);
            juego.powerPellet();
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3) && celda1 == '*') {
            laberinto.setMatrizCelda(' ', y1, x1);
            juego.powerPellet();
        }
        if ((this.getDireccion() == 0 || this.getDireccion() == 1)) {
            for (Fantasma fant : juego.getFantasmas()) {
                if (!fant.isEncerrado() && !fant.isMuerto() && fant.isVulnerable() && ((int) fant.getY() / SIZE == y && (int) fant.getX() / SIZE == x)) {
                    fant.ultPosY = ROWS / 2;
                    fant.ultPosX = COLUMNS / 2;
                    fant.setMuerto(true);
                    fant.setVelocidad(0.9);
                    juego.comeFantasmasConsecutivos();
                    juego.fantasmasConsecutivos++;
                    this.puntos += (300 * juego.multiplicadorPuntaje);
                    if (juego.fantasmasConsecutivos > 1) {
                        this.superVelocidad = true;
                        this.puntos += (100 * juego.multiplicadorPuntaje);
                    }
                    Trofeo trofeo = partida.obtenerTrofeo("Cazador");
                    if (!trofeo.isDesbloqueado()) {
                        trofeo.setCont(1);
                        if (trofeo.getCont() > 5) {
                            trofeo.setDesbloqueado(true);
                        }
                        partida.actualizarTrofeo("Cazador", trofeo);
                    }
                    partida.actualizarEstadistica("TotalFantasmasComidos", partida.obtenerEstadistica("TotalFantasmasComidos") + 1);
                }
            }
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3)) {
            for (Fantasma fant : juego.getFantasmas()) {
                if (!fant.isEncerrado() && !fant.isMuerto() && fant.isVulnerable() && ((int) (fant.getY() + (SIZE - 1)) / SIZE == y1 && (int) (fant.getX() + (SIZE - 1)) / SIZE == x1)) {
                    fant.ultPosY = ROWS / 2;
                    fant.ultPosX = COLUMNS / 2;
                    fant.setMuerto(true);
                    fant.setVelocidad(0.9);
                    juego.comeFantasmasConsecutivos();
                    juego.fantasmasConsecutivos++;
                    this.puntos += (300 * juego.multiplicadorPuntaje);
                    if (juego.fantasmasConsecutivos > 1) {
                        this.superVelocidad = true;
                        this.puntos += (100 * juego.multiplicadorPuntaje);
                    }
                    Trofeo trofeo = partida.obtenerTrofeo("Cazador");
                    if (!trofeo.isDesbloqueado()) {
                        trofeo.setCont(1);
                        if (trofeo.getCont() > 5) {
                            trofeo.setDesbloqueado(true);
                        }
                        partida.actualizarTrofeo("Cazador", trofeo);
                    }
                    partida.actualizarEstadistica("TotalFantasmasComidos", partida.obtenerEstadistica("TotalFantasmasComidos") + 1);
                }
            }
        }
    }

    public void pintar(GraphicsContext graficos, double segundoAct, double segundoAnt) {
        double angulo = 0;
        switch (this.getDireccion()) {
            case 0 ->
                angulo = 0;
            case 1 ->
                angulo = 90;
            case 2 ->
                angulo = 180;
            case 3 ->
                angulo = -90;
            default -> {
            }
        }
        Affine oldTransform = graficos.getTransform();
        Rotate rotate = new Rotate(angulo, getX() + SIZE / 2, getY() + SIZE / 2);
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
