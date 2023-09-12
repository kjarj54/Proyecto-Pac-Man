/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

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
//    private PacMan pacMan;
//    private List<Fantasma> fantasmas;

    private Laberinto laberinto;
    private double tiempo;
//    private List<String> trofeos;
//    private Map<String, Integer> estadisticas;
    private static final int ROWS = 20;
    private static final int COLUMNS = 32;

    public Juego(int rows, int columns) {
//    public Juego(PacMan pacMan, List<Fantasma> fantasmas, Laberinto laberinto) {
//        this.pacMan = pacMan;
//        this.fantasmas = fantasmas;
        this.laberinto = new Laberinto(rows, columns);
        this.tiempo = 0.0;
//        this.trofeos = new ArrayList<>();
//        this.estadisticas = new HashMap<>();
//        estadisticas.put("puntosTotales", 0);
//        estadisticas.put("vidasPerdidas", 0);
//        estadisticas.put("fantasmasComidos", 0);
    }

    // Getters y Setters para todos los atributos
//    public PacMan getPacMan() {
//        return pacMan;
//    }
//
//    public void setPacMan(PacMan pacMan) {
//        this.pacMan = pacMan;
//    }
    public void generarLaberinto() {
        Random random = new Random();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                laberinto.matriz[i][j] = ' ';
            }
        }

        for (int i = 0; i < COLUMNS; i++) {
            laberinto.matriz[0][i] = '#';
            laberinto.matriz[ROWS - 1][i] = '#';
        }

        for (int i = 1; i < ROWS - 1; i++) {
            laberinto.matriz[i][0] = '#';
            laberinto.matriz[i][COLUMNS - 1] = '#';
        }

        for (int i = 1; i < ROWS - 1; i++) {
            for (int j = 1; j < COLUMNS - 1; j++) {
//                if (i % 2 == 1 && j % 2 == 1 && random.nextInt(10) > 0) {
                if (i % 2 == 1 && j % 2 == 1) {

                    laberinto.matriz[i][j] = '#';
                    int vecino = random.nextInt(4);
                    if (j < COLUMNS - 1 && vecino == 0) {

                        laberinto.matriz[i][j + 1] = '#';
                    }
                    if (i < ROWS - 1 && vecino == 1) {

                        laberinto.matriz[i + 1][j] = '#';
                    }
                    if (j > 1 && vecino == 2) {

                        laberinto.matriz[i][j - 1] = '#';
                    }
                    if (i > 1 && vecino == 3) {

                        laberinto.matriz[i - 1][j] = '#';
                    }
                }
            }
        }
        for (int i = (ROWS / 2) - 4; i < (ROWS / 2) + 4; i++) {
            for (int j = (COLUMNS / 2) - 4; j < (COLUMNS / 2) + 4; j++) {
                if ((i == (ROWS / 2) - 3 || i == (ROWS / 2) + 2) && (j >= (COLUMNS / 2) - 3 && j <= (COLUMNS / 2) + 2)) {
                    laberinto.matriz[i][j] = '#';
                } else if ((j == (COLUMNS / 2) - 3 || j == (COLUMNS / 2) + 2) && (i >= (ROWS / 2) - 3 && i <= (ROWS / 2) + 2)) {
                    laberinto.matriz[i][j] = '#';
                } else {
                    laberinto.matriz[i][j] = ' ';
                }
            }
        }
        int vecino = random.nextInt(4);
//        int vecino = 0;
        if (vecino == 0) {
            vecino = random.nextInt((ROWS / 2 + 1) - (ROWS / 2 - 2) + 1) + (ROWS / 2 - 2);
            System.out.println(vecino);
            laberinto.matriz[vecino - 1][(COLUMNS / 2) + 2] = ' ';
            laberinto.matriz[vecino][(COLUMNS / 2) + 2] = ' ';
            laberinto.matriz[vecino + 1][(COLUMNS / 2) + 2] = ' ';
        }
        if (vecino == 1) {
            vecino = random.nextInt((COLUMNS / 2 + 1) - (COLUMNS / 2 - 2) + 1) + (COLUMNS / 2 - 2);
            laberinto.matriz[(ROWS / 2) - 3][vecino - 1] = ' ';
            laberinto.matriz[(ROWS / 2) - 3][vecino] = ' ';
            laberinto.matriz[(ROWS / 2) - 3][vecino + 1] = ' ';
        }
        if (vecino == 2) {
            vecino = random.nextInt((ROWS / 2 + 1) - (ROWS / 2 - 2) + 1) + (ROWS / 2 - 2);
            laberinto.matriz[vecino - 1][(COLUMNS / 2) - 3] = ' ';
            laberinto.matriz[vecino][(COLUMNS / 2) - 3] = ' ';
            laberinto.matriz[vecino + 1][(COLUMNS / 2) - 3] = ' ';
        }
        if (vecino == 3) {
            vecino = random.nextInt((COLUMNS / 2 + 1) - (COLUMNS / 2 - 2) + 1) + (COLUMNS / 2 - 2);
            laberinto.matriz[(ROWS / 2) + 2][vecino - 1] = ' ';
            laberinto.matriz[(ROWS / 2) + 2][vecino] = ' ';
            laberinto.matriz[(ROWS / 2) + 2][vecino + 1] = ' ';
        }
    }
//
//    public List<Fantasma> getFantasmas() {
//        return fantasmas;
//    }
//
//    public void setFantasmas(List<Fantasma> fantasmas) {
//        this.fantasmas = fantasmas;
//    }

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
