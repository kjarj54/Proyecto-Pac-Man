/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

/**
 *
 * @author ANTHONY
 */
public class Laberinto {
    private int nivel;
    private String tema;
    public char[][] matriz;

    public Laberinto(int rows, int columns) {
        this.nivel = 0;
        this.tema = "N/A";
        this.matriz = new char[rows][columns];
    }
    
    public Laberinto(int nivel, String tema, char[][] matriz) {
        this.nivel = nivel;
        this.tema = tema;
        this.matriz = matriz;
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
    
    public char getMatrizCelda(int row, int col) {
        return matriz[row][col];
    }

    public void setMatriz(char[][] matriz) {
        this.matriz = matriz;
    }

    // Otras funciones relevantes para el laberinto

    public void imprimirLaberinto() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }

    // Otras funciones y métodos relacionados con el juego en el laberinto
}