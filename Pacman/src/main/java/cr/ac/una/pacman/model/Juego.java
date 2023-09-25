/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author ANTHONY
 */
public class Juego {

    private PacMan pacMan;
    private List<Fantasma> fantasmas;
    private Laberinto laberinto;
    private double tiempo;
//    private List<String> trofeos;
//    private Map<String, Integer> estadisticas;

    public Juego(PacMan pacMan, List<Fantasma> fantasmas, int rows, int columns) {
//    public Juego(PacMan pacMan, List<Fantasma> fantasmas, Laberinto laberinto) {
        this.pacMan = pacMan;
        this.fantasmas = fantasmas;
        this.laberinto = new Laberinto(rows, columns);
        this.tiempo = 0.0;
//        this.trofeos = new ArrayList<>();
//        this.estadisticas = new HashMap<>();
//        estadisticas.put("puntosTotales", 0);
//        estadisticas.put("vidasPerdidas", 0);
//        estadisticas.put("fantasmasComidos", 0);
    }

//     Getters y Setters para todos los atributos
    public PacMan getPacMan() {
        return pacMan;
    }

    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    public void generarLaberinto() {
        Random random = new Random();

        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                laberinto.setMatrizCelda(' ', i, j);
            }
        }

        for (int i = 1; i < COLUMNS; i++) {
            laberinto.setMatrizCelda('#', 1, i);
            laberinto.setMatrizCelda('#', ROWS - 1, i);
        }

        for (int i = 1; i < ROWS - 1; i++) {
            laberinto.setMatrizCelda('#', i, 1);
            laberinto.setMatrizCelda('#', i, COLUMNS - 1);
        }

        for (int i = 2; i < ROWS - 1; i++) {
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (i % 2 == 1 && j % 2 == 1 && random.nextInt(10) >= 4) {
//                if (i % 2 == 1 && j % 2 == 1) {
                    laberinto.setMatrizCelda('#', i, j);
                    int vecino = random.nextInt(4);
                    if (j < COLUMNS - 1 && vecino == 0) {
                        laberinto.setMatrizCelda('#', i, j + 1);
                    }
                    if (i < ROWS - 1 && vecino == 1) {
                        laberinto.setMatrizCelda('#', i + 1, j);
                    }
                    if (j > 1 && vecino == 2) {
                        laberinto.setMatrizCelda('#', i, j - 1);
                    }
                    if (i > 1 && vecino == 3) {
                        laberinto.setMatrizCelda('#', i - 1, j);
                    }
                }
            }
        }
        for (int i = 2; i < ROWS - 1; i++) {
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (laberinto.getMatrizCelda(i, j) == ' ') {
                    laberinto.setMatrizCelda('p', i, j);
                }
            }
        }
        for (int i = (ROWS / 2) - 3; i < (ROWS / 2) + 3; i++) {
            for (int j = (COLUMNS / 2) - 3; j < (COLUMNS / 2) + 4; j++) {
                if ((i == (ROWS / 2) - 2 || i == (ROWS / 2) + 1) && (j >= (COLUMNS / 2) - 2 && j <= (COLUMNS / 2) + 2)) {
                    laberinto.setMatrizCelda('#', i, j);
                } else if ((j == (COLUMNS / 2) - 2 || j == (COLUMNS / 2) + 2) && (i >= (ROWS / 2) - 2 && i <= (ROWS / 2) + 1)) {
                    laberinto.setMatrizCelda('#', i, j);
                } else {
                    laberinto.setMatrizCelda(' ', i, j);
                }
            }
        }
        laberinto.setMatrizCelda(' ', (ROWS / 2) - 2, COLUMNS / 2 - 1);
        laberinto.setMatrizCelda(' ', (ROWS / 2) - 2, COLUMNS / 2);
        laberinto.setMatrizCelda(' ', (ROWS / 2) - 2, COLUMNS / 2 + 1);
        int vecino = random.nextInt(2);
//        int vecino = 0;
        switch (vecino) {
            case 0:
                for (int i = ROWS / 2 - 1; i <= ROWS / 2 + 1; i++) {
                    for (int j = 1; j <= 2; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                    for (int j = COLUMNS - 2; j <= COLUMNS - 1; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                }
                break;

            case 1:
                for (int i = 1; i <= 2; i++) {
                    for (int j = COLUMNS / 2 - 1; j <= COLUMNS / 2 + 1; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                }
                for (int i = ROWS - 2; i <= ROWS - 1; i++) {
                    for (int j = COLUMNS / 2 - 1; j <= COLUMNS / 2 + 1; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                }
                break;

            default:
                break;
        }
        int contPP = 0;
        while (contPP < 4) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (laberinto.getMatrizCelda(randomY, randomX) == ' ' || laberinto.getMatrizCelda(randomY, randomX) == 'p') {
                laberinto.setMatrizCelda('*', randomY, randomX);
                contPP++;
            }
        }
        boolean pacmanListo = false;
        while (!pacmanListo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (laberinto.getMatrizCelda(randomY, randomX) == ' ' || laberinto.getMatrizCelda(randomY, randomX) == 'p') {
                this.getPacMan().setX(randomX * SIZE);
                this.getPacMan().setY(randomY * SIZE);
                pacmanListo = true;
            }
        }
//        this.getPacMan().setX(3 * SIZE);
//        this.getPacMan().setY(10 * SIZE);
    }

    public List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public void setFantasmas(List<Fantasma> fantasmas) {
        this.fantasmas = fantasmas;
    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public void setLaberinto(Laberinto laberinto) {
        this.laberinto = laberinto;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

//    public List<String> getTrofeos() {
//        return trofeos;
//    }
//
//    public void setTrofeos(List<String> trofeos) {
//        this.trofeos = trofeos;
//    }
//
//    public Map<String, Integer> getEstadisticas() {
//        return estadisticas;
//    }
//
//    public void setEstadisticas(Map<String, Integer> estadisticas) {
//        this.estadisticas = estadisticas;
//    }
}
