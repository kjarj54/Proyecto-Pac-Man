/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import java.util.Random;

/**
 *
 * @author ANTHONY
 */
public class Laberinto {
    private int nivel;
    private String tema;
    private char[][] matriz;
    private boolean desbloqueado;

    public Laberinto(int rows, int columns, int nivel, String tema) {
        this.nivel = nivel;
        this.tema = tema;
        this.matriz = new char[rows][columns];
        this.desbloqueado = nivel == 1;
    }
    
    public void actualizar(Laberinto laberinto) {
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                setMatrizCelda(laberinto.getMatrizCelda(i, j), i, j);
            }
        }
        this.desbloqueado = laberinto.isDesbloqueado();
        this.nivel = laberinto.getNivel();
        this.tema = laberinto.getTema();
    }
    
    public void generarLaberinto() {
        Random random = new Random();

        for (int i = 1; i < ROWS; i++) { // Se inicializa toda la matriz con " "
            for (int j = 1; j < COLUMNS; j++) {
                setMatrizCelda(' ', i, j);
            }
        }
        for (int i = 0; i < ROWS; i++) {
            setMatrizCelda('n', i, 0);
        }
        for (int j = 0; j < COLUMNS; j++) { // Se ponen las filas y columnas 0 en "n" para evitar bugs
            setMatrizCelda('n', 0, j);
        }

        for (int i = 1; i < COLUMNS; i++) {
            setMatrizCelda('#', 1, i);
            setMatrizCelda('#', ROWS - 1, i);
        }
        for (int i = 1; i < ROWS - 1; i++) { // Se ponen los muros alrededor del laberinto
            setMatrizCelda('#', i, 1);
            setMatrizCelda('#', i, COLUMNS - 1);
        }

        for (int i = 2; i < ROWS - 1; i++) { // se ponen muros el las localizaciones impares y sus vecinos dependiendo la probabilidad
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (i % 2 == 1 && j % 2 == 1 && random.nextInt(10) >= 3) {
//                if (i % 2 == 1 && j % 2 == 1) {
                    setMatrizCelda('#', i, j);
                    int vecino = random.nextInt(4);
                    if (j < COLUMNS - 1 && vecino == 0) {
                        setMatrizCelda('#', i, j + 1);
                    }
                    if (i < ROWS - 1 && vecino == 1) {
                        setMatrizCelda('#', i + 1, j);
                    }
                    if (j > 1 && vecino == 2) {
                        setMatrizCelda('#', i, j - 1);
                    }
                    if (i > 1 && vecino == 3) {
                        setMatrizCelda('#', i - 1, j);
                    }
                }
            }
        }
        for (int i = 2; i < ROWS - 1; i++) { // Se marcan todos lo campos vacios como "p" puntos
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (getMatrizCelda(i, j) == ' ') {
                    setMatrizCelda('p', i, j);
                }
            }
        }
        for (int i = (ROWS / 2) - 2; i < (ROWS / 2) + 4; i++) { // Se crea la casa de los fantasmas
            for (int j = (COLUMNS / 2) - 3; j < (COLUMNS / 2) + 4; j++) {
                if ((i == (ROWS / 2) - 1 || i == (ROWS / 2) + 2) && (j >= (COLUMNS / 2) - 2 && j <= (COLUMNS / 2) + 2)) {
                    setMatrizCelda('#', i, j);
                } else if ((j == (COLUMNS / 2) - 2 || j == (COLUMNS / 2) + 2) && (i >= (ROWS / 2) - 1 && i <= (ROWS / 2) + 2)) {
                    setMatrizCelda('#', i, j);
                } else {
                    setMatrizCelda(' ', i, j);
                }
            }
        }
        setMatrizCelda('-', (ROWS / 2) - 1, COLUMNS / 2); // La salida de la casa de los fantasmas

        int salida = random.nextInt(2); // La salida a los exteriores del laberinto
//        int salida = 0;
        switch (salida) {
            case 0 -> {
                for (int i = ROWS / 2 - 1; i <= ROWS / 2 + 1; i++) {
                    for (int j = 1; j <= 2; j++) {
                        setMatrizCelda(' ', i, j);
                    }
                    for (int j = COLUMNS - 2; j <= COLUMNS - 1; j++) {
                        setMatrizCelda(' ', i, j);
                    }
                }
            }

            case 1 -> {
                for (int i = 1; i <= 2; i++) {
                    for (int j = COLUMNS / 2 - 1; j <= COLUMNS / 2 + 1; j++) {
                        setMatrizCelda(' ', i, j);
                    }
                }
                for (int i = ROWS - 2; i <= ROWS - 1; i++) {
                    for (int j = COLUMNS / 2 - 1; j <= COLUMNS / 2 + 1; j++) {
                        setMatrizCelda(' ', i, j);
                    }
                }
            }

            default -> {
            }
        }
        int contPP = 0;
        while (contPP < 4) { // Coloca aleatoriamente los "*" o Power Pullets
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (getMatrizCelda(randomY, randomX) == 'p') {
                setMatrizCelda('*', randomY, randomX);
                contPP++;
            }
        }
        setMatrizCelda(' ', 1, 1);
        setMatrizCelda(' ', 18, 14);
    }

    // Getters y Setters para todos los atributos

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(char[][] matriz) {
        this.matriz = matriz;
    }
    
    public char getMatrizCelda(int row, int col) {
        return matriz[row][col];
    }
    
    public void setMatrizCelda(char valor, int row, int col) {
        matriz[row][col] = valor;
    }
    
    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public void imprimirLaberinto() {
        for (char[] matriz1 : matriz) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz1[j]);
            }
            System.out.println();
        }
    }
}