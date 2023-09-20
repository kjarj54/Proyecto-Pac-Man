/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

/**
 *
 * @author ANTHONY
 */
public class Fantasma extends Personaje {

    public static final String ROJO = "rojo";
    public static final String ROSA = "rosa";
    public static final String CIAN = "cian";
    public static final String NARANJA = "naranja";

    public static final String DIJKSTRA = "dijkstra";
    public static final String FLOYD = "floyd";
    public static final String ALEATORIO = "aleatorio";

    private String color;
    private String algoritmo;
    private boolean vulnerable;
    int[][] padresX;
    int[][] padresY;

    public Fantasma(double x, double y, double velocidad, int direccion, List<String> imagenes, String color, String algoritmo) {
        super(x, y, velocidad, direccion, imagenes);
        this.color = color;
        this.algoritmo = algoritmo;
        this.vulnerable = false;
    }

    public String getColor() {
        return this.color;
    }

    public String getAlgoritmo() {
        return this.algoritmo;
    }

    public boolean isVulnerable() {
        return this.vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    // Este método mueve al fantasma según su algoritmo y el estado del juego
    public void mover(Laberinto laberinto, int objetivoX, int objetivoY) {

        switch (this.algoritmo) {
            case DIJKSTRA:
                encontrarCamino(laberinto, objetivoX, objetivoY);
                break;
            case FLOYD:
//        nuevaPosicion = laberinto.floyd(filaActual, columnaActual, this.vulnerable);
                break;
            case ALEATORIO:
//        nuevaPosicion = laberinto.aleatorio(filaActual, columnaActual);
                break;
            default:
//        nuevaPosicion = new int[] {filaActual, columnaActual};
                break;
        }
    }

    public void encontrarCamino(Laberinto laberinto, int objetivoX, int objetivoY) {
        int filas = laberinto.getMatriz().length;
        int columnas = laberinto.getMatriz()[0].length;

        int[][] distancias = new int[filas][columnas];
        padresX = new int[filas][columnas];
        padresY = new int[filas][columnas];
        boolean[][] visitado = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
            Arrays.fill(padresX[i], -1);
            Arrays.fill(padresY[i], -1);
            Arrays.fill(visitado[i], false);
        }

        distancias[(int)this.getY() / 20][(int)this.getX() / 20] = 0;

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new Nodo((int)this.getX() / 20, (int)this.getY() / 20, 0));

        int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Movimientos arriba, abajo, izquierda, derecha

        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();

            int x = nodoActual.x;
            int y = nodoActual.y;

            if (x == objetivoX && y == objetivoY) {
                // Llegamos al objetivo, detener la búsqueda
                break;
            }

            if (visitado[y][x]) {
                continue;
            }

            visitado[y][x] = true;

            for (int[] movimiento : movimientos) {
                int nuevoX = x + movimiento[0];
                int nuevoY = y + movimiento[1];

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas && !visitado[nuevoY][nuevoX] && (laberinto.getMatriz()[nuevoY][nuevoX] == ' ' || laberinto.getMatriz()[nuevoY][nuevoX] == 'p' || laberinto.getMatriz()[nuevoY][nuevoX] == '*')) {
                    int distanciaActual = distancias[y][x];
                    int pesoCelda = 1; // Costo uniforme para todos los caminos

                    if (distanciaActual + pesoCelda < distancias[nuevoY][nuevoX]) {
                        distancias[nuevoY][nuevoX] = distanciaActual + pesoCelda;
                        padresX[nuevoY][nuevoX] = x;
                        padresY[nuevoY][nuevoX] = y;
                        colaPrioridad.add(new Nodo(nuevoX, nuevoY, distancias[nuevoY][nuevoX]));
                    }
                }
            }
        }

        if (distancias[objetivoY][objetivoX] == Integer.MAX_VALUE) {
            System.out.println("No se encontró un camino al objetivo.");
        } else {
            System.out.println("La distancia mínima al objetivo es: " + distancias[objetivoY][objetivoX]);
            System.out.println("Camino desde (" + this.getX() / 20 + ", " + this.getY() / 20 + ") a (" + objetivoX + ", " + objetivoY + "):");
//            imprimirCamino(padresX, padresY, this.getX() / 20, this.getY() / 20, objetivoX, objetivoY);
            mover(laberinto, padresX, padresY, (int)this.getX() / 20, (int)this.getY() / 20, objetivoX, objetivoY);
        }
    }

    static class Nodo implements Comparable<Nodo> {

        int x, y, distancia;

        Nodo(int x, int y, int distancia) {
            this.x = x;
            this.y = y;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

    public void imprimirCamino(int[][] padresX, int[][] padresY, int inicioX, int inicioY, int objetivoX, int objetivoY) {
        Stack<String> camino = new Stack<>();
        int x = objetivoX;
        int y = objetivoY;

        while (x != inicioX || y != inicioY) {
            camino.push("(" + x + ", " + y + ")");
            int tempX = padresX[y][x];
            int tempY = padresY[y][x];
            x = tempX;
            y = tempY;
        }

        camino.push("(" + inicioX + ", " + inicioY + ")");

        while (!camino.isEmpty()) {
            System.out.print(camino.pop());
            if (!camino.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public void mover(Laberinto laberinto, int[][] padresX, int[][] padresY, int inicioX, int inicioY, int objetivoX, int objetivoY) {
        Stack<int[]> camino = new Stack<>();
        int x = objetivoX;
        int y = objetivoY;

        while (x != inicioX || y != inicioY) {
            camino.push(new int[]{x, y});
            int tempX = padresX[y][x];
            int tempY = padresY[y][x];
            x = tempX;
            y = tempY;
        }
        int[] nuevaPos = camino.pop();
        int xMovimiento = nuevaPos[0] - (int)this.getX() / 20;
        int yMovimiento = nuevaPos[1] - (int)this.getY() / 20;
        int direccion = 0;
        if (xMovimiento == 1 && yMovimiento == 0) {
            direccion = 0;
        } else if (xMovimiento == 0 && yMovimiento == 1) {
            direccion = 1;
        } else if (xMovimiento == -1 && yMovimiento == 0) {
            direccion = 2;
        } else if (xMovimiento == 0 && yMovimiento == -1) {
            direccion = 3;
        }
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

        graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, 15, 15);
//        if (segundoAct > segundoAnt + 0.25) {
//            graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, 15, 15);
//        } else {
//            graficos.drawImage(new Image(getImagen().get(1)), getX() + 2, getY() + 2, 15, 15);
//        }
        graficos.setTransform(oldTransform);
    }
}
