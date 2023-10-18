/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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
    public static final String DIJKSTRAALTERNATIVO = "dijkstraAlternativo";
    public static final String FLOYD = "floyd";
    public static final String ALEATORIO = "aleatorio";

    private String color;
    private String algoritmo;
    private boolean encerrado;
    private boolean vulnerable;
    private boolean eraVulnerable = false;
    private boolean muerto = false;
    int[][] padresX = null;
    int[][] padresY = null;
    int[][] distance = null;
    int miUltPosX;
    int miUltPosY;
    public int ultPosX;
    public int ultPosY;

    public Fantasma(double x, double y, double velocidad, int direccion, List<String> imagenes, String color, String algoritmo) {
        super(x, y, velocidad, direccion, imagenes);
        this.color = color;
        this.algoritmo = algoritmo;
        this.vulnerable = false;
        this.encerrado = false;
    }

    public String getColor() {
        return this.color;
    }

    public String getAlgoritmo() {
        return this.algoritmo;
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public boolean isVulnerable() {
        return this.vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    // Este método mueve al fantasma según su algoritmo y el estado del juego
    public void mover(Laberinto laberinto, PacMan pacman, double objetivoX, double objetivoY) {
        if (this.vulnerable && !this.encerrado && !this.muerto) {
            if ((int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
                escaparDePacman(laberinto, pacman, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                miUltPosX = (int) this.getX() / SIZE;
                miUltPosY = (int) this.getY() / SIZE;
                ultPosX = (int) objetivoX / SIZE;
                ultPosY = (int) objetivoY / SIZE;
                eraVulnerable = true;
            }
        } else {
            switch (this.algoritmo) {
                case DIJKSTRA -> {
                    if ((int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
                        algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    }
                }
                case DIJKSTRAALTERNATIVO -> {
                    if ((int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
                        algoritmoDijkstraAlternativo(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    }
                }
                case FLOYD -> {
                    if ((int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE) {
                        algoritmoFloyd(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    }
                }
                default -> {
                }
            }
        }
        moverFantasma(laberinto, ultPosX, ultPosY);
    }

    public void algoritmoDijkstra(Laberinto laberinto, int objetivoX, int objetivoY) {
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

        distancias[(int) this.getY() / SIZE][(int) this.getX() / SIZE] = 0;

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new Nodo((int) this.getX() / SIZE, (int) this.getY() / SIZE, 0));

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

                char celda = '#';

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas) {
                    celda = laberinto.getMatrizCelda(nuevoY, nuevoX);
                }

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas && !visitado[nuevoY][nuevoX]
                        && (celda == ' ' || celda == 'p'
                        || celda == '*' || celda == '-')) {
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

//        if (distancias[objetivoY][objetivoX] == Integer.MAX_VALUE) {
//            System.out.println("No se encontró un camino al objetivo.");
//        } else {
//            System.out.println("La distancia mínima al objetivo es: " + distancias[objetivoY][objetivoX]);
//            System.out.println("Camino desde (" + this.getX() / SIZE + ", " + this.getY() / SIZE + ") a (" + objetivoX + ", " + objetivoY + "):");
//            imprimirCamino(padresX, padresY, (int) this.getX() / SIZE, (int) this.getY() / SIZE, objetivoX, objetivoY);
//        }
    }

    public void algoritmoDijkstraAlternativo(Laberinto laberinto, int objetivoX, int objetivoY) {
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

        distancias[(int) this.getY() / SIZE][(int) this.getX() / SIZE] = 0;

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new Nodo((int) this.getX() / SIZE, (int) this.getY() / SIZE, 0));

        int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Movimientos arriba, abajo, izquierda, derecha

        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();

            int x = nodoActual.x;
            int y = nodoActual.y;

            if (x == objetivoX && y == objetivoY && visitado[objetivoY][objetivoX]) {
                // Llegamos al objetivo y ya se visitó antes, detener la búsqueda
                break;
            }

            if (visitado[y][x]) {
                continue;
            }

            visitado[y][x] = true;

            for (int[] movimiento : movimientos) {
                int nuevoX = x + movimiento[0];
                int nuevoY = y + movimiento[1];

                char celda = '#';

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas) {
                    celda = laberinto.getMatrizCelda(nuevoY, nuevoX);
                }

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas && !visitado[nuevoY][nuevoX]
                        && (celda == ' ' || celda == 'p'
                        || celda == '*' || celda == '-')) {
                    int distanciaActual = distancias[y][x];
                    int pesoCelda = 1; // Costo uniforme para todos los caminos

                    // Aplicar el factor de peso aleatorio
                    double probabilidad = Math.random();
                    if (probabilidad < 0.3) {
                        pesoCelda *= 5; // Aumenta el peso (reduce la probabilidad de elegir este camino)
                    } else if (probabilidad < 0.5) {
                        pesoCelda *= 3; // Aumenta el peso (reduce la probabilidad de elegir este camino)
                    } else if (probabilidad < 0.8) {
                        pesoCelda *= 2; // Aumenta el peso (reduce la probabilidad de elegir este camino)
                    }

                    if (distanciaActual + pesoCelda < distancias[nuevoY][nuevoX]) {
                        distancias[nuevoY][nuevoX] = distanciaActual + pesoCelda;
                        padresX[nuevoY][nuevoX] = x;
                        padresY[nuevoY][nuevoX] = y;
                        colaPrioridad.add(new Nodo(nuevoX, nuevoY, distancias[nuevoY][nuevoX]));
                    }
                }
            }
        }

//        if (distancias[objetivoY][objetivoX] == Integer.MAX_VALUE) {
//            System.out.println("No se encontró un camino al objetivo.");
//        } else {
//            System.out.println("La distancia mínima al objetivo es: " + distancias[objetivoY][objetivoX]);
//            System.out.println("Camino desde (" + this.getX() / SIZE + ", " + this.getY() / SIZE + ") a (" + objetivoX + ", " + objetivoY + "):");
//            // Imprimir el camino o realizar otras acciones aquí
//        }
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

    public void algoritmoFloyd(Laberinto laberinto, int objetivoX, int objetivoY) {
        int rows = laberinto.getMatriz().length;
        int cols = laberinto.getMatriz()[0].length;
        distance = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            Arrays.fill(distance[i], Integer.MAX_VALUE);
        }

        distance[(int) this.getY() / SIZE][(int) this.getX() / SIZE] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{(int) this.getX() / SIZE, (int) this.getY() / SIZE});

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentX = current[0];
            int currentY = current[1];

            for (int[] dir : directions) {
                int newX = currentX + dir[0];
                int newY = currentY + dir[1];
                char celda = '#';

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {
                    celda = laberinto.getMatrizCelda(newY, newX);
                }

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows
                        && (celda == 'p' || celda == '*'
                        || celda == ' ' || celda == '-')
                        && distance[newY][newX] == Integer.MAX_VALUE) {
                    distance[newY][newX] = distance[currentY][currentX] + 1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }

//        if (distance[objetivoY][objetivoX] == Integer.MAX_VALUE) {
//            System.out.println("No se encontró un camino válido al objetivo.");
//        } else {
//            System.out.println("La distancia más corta al objetivo es: " + distance[objetivoY][objetivoX]);
//            System.out.println("Camino desde (" + this.getX() / SIZE + ", " + this.getY() / SIZE + ") a (" + objetivoX + ", " + objetivoY + "):");
//            imprimirCaminoFloyd(laberinto, objetivoX, objetivoY);
//        }
    }

    public void escaparDePacman(Laberinto laberinto, PacMan pacman, int objetivoX, int objetivoY) {
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

        distancias[(int) this.getY() / SIZE][(int) this.getX() / SIZE] = 0;

        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new Nodo((int) this.getX() / SIZE, (int) this.getY() / SIZE, 0));

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
                char celda = '#';

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas) {
                    celda = laberinto.getMatrizCelda(nuevoY, nuevoX);
                }
                int distancia = (int) Math.sqrt(Math.pow((pacman.getX() / SIZE - nuevoX), 2)
                        + Math.pow((pacman.getY() / SIZE - nuevoY), 2));

                if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas && !visitado[nuevoY][nuevoX]
                        && (celda == ' ' || celda == 'p'
                        || celda == '*' || celda == '-'
                        && ((int) pacman.getX() / SIZE != nuevoX || (int) pacman.getY() / SIZE != nuevoY))
                        && distancia > 1) {
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

//        if (distancias[objetivoY][objetivoX] == Integer.MAX_VALUE) {
//            System.out.println("No se encontró un camino al objetivo.");
//        } else {
//            System.out.println("La distancia mínima al objetivo es: " + distancias[objetivoY][objetivoX]);
//            System.out.println("Camino desde (" + this.getX() / SIZE + ", " + this.getY() / SIZE + ") a (" + objetivoX + ", " + objetivoY + "):");
//            imprimirCamino((int) this.getX() / SIZE, (int) this.getY() / SIZE, objetivoX, objetivoY);
//            System.out.println("X: " + pacman.getX() / SIZE + " - Y: " + pacman.getY() / SIZE);
//            System.out.println("");
//        }
    }

    public void imprimirCamino(int inicioX, int inicioY, int objetivoX, int objetivoY) {
        Stack<String> camino = new Stack<>();
        int x = objetivoX;
        int y = objetivoY;

        // Verificar si hay un camino válido antes de intentar imprimirlo
        if (padresX != null && padresY != null && x >= 0 && x < padresX[0].length && y >= 0 && y < padresY.length) {
            while (x != inicioX || y != inicioY) {
                if (x < 0 || y < 0) {
                    break;  // Salir si se alcanza un valor no válido
                }
                camino.push("(" + x + ", " + y + ")");
                int tempX = padresX[y][x];
                int tempY = padresY[y][x];
                x = tempX;
                y = tempY;
            }
            if (x >= 0 && y >= 0) {
                camino.push("(" + inicioX + ", " + inicioY + ")");
            } else {
                camino.push("No hay camino válido");
            }
        } else {
            camino.push("No hay camino válido");
        }

        while (!camino.isEmpty()) {
            System.out.print(camino.pop());
            if (!camino.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public void imprimirCaminoFloyd(Laberinto laberinto, int objetivoX, int objetivoY) {
        Stack<int[]> camino = new Stack<>();
        int x = objetivoX;
        int y = objetivoY;

        while (x != (int) this.getX() / SIZE || y != (int) this.getY() / SIZE) {
            camino.push(new int[]{x, y});
            int minDist = distance[y][x];

            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newY >= 0 && newY < laberinto.getMatriz().length && newX >= 0 && newX < laberinto.getMatriz()[0].length && distance[newY][newX] == minDist - 1) {
                    x = newX;
                    y = newY;
                    break;
                }
            }
        }

        camino.push(new int[]{(int) this.getX() / SIZE, (int) this.getY() / SIZE});

        while (!camino.isEmpty()) {
            int[] step = camino.pop();
            int posX = step[0];
            int posY = step[1];
            System.out.print("(" + posX + ", " + posY + ") ->");
        }
        System.out.println("");
    }

    public void moverFantasma(Laberinto laberinto, int objetivoX, int objetivoY) {
        int direccion = this.getDireccion();
        int x = objetivoX;
        int y = objetivoY;
        if ((this.vulnerable && !this.encerrado) && !this.muerto && this.eraVulnerable && padresX != null && padresY != null) {
            Stack<int[]> camino = new Stack<>();

            // Verificar si hay un camino válido antes de intentar imprimirlo
            if (padresX != null && padresY != null && x >= 0 && x < padresX[0].length && y >= 0 && y < padresY.length) {
                while (x != miUltPosX || y != miUltPosY) {
                    if (x < 0 || y < 0) {
                        break;  // Salir si se alcanza un valor no válido
                    }
                    camino.push(new int[]{x, y});
                    int tempX = padresX[y][x];
                    int tempY = padresY[y][x];
                    x = tempX;
                    y = tempY;
                }
            }
            int[] nuevaPos = camino.pop();
            int xDestino = nuevaPos[0] * SIZE; // Multiplicamos por SIZE para obtener la coordenada real del destino
            int yDestino = nuevaPos[1] * SIZE;

            int xActual = (int) this.getX();
            int yActual = (int) this.getY();

            int xMovimiento = xDestino - xActual;
            int yMovimiento = yDestino - yActual;

            if (xMovimiento > 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 0; // Mover hacia la derecha
            } else if (xMovimiento < 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 2; // Mover hacia la izquierda
            } else if (yMovimiento > 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 1; // Mover hacia abajo
            } else if (yMovimiento < 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 3; // Mover hacia arriba
            }
        } else if (("dijkstra".equals(algoritmo) || "dijkstraAlternativo".equals(algoritmo))
                && padresX != null && padresY != null
                && !this.eraVulnerable) {
            Stack<int[]> camino = new Stack<>();

            while (x != miUltPosX || y != miUltPosY) {
                camino.push(new int[]{x, y});
                int tempX = padresX[y][x];
                int tempY = padresY[y][x];
                x = tempX;
                y = tempY;
            }
            int[] nuevaPos = camino.pop();
            int xDestino = nuevaPos[0] * SIZE; // Multiplicamos por SIZE para obtener la coordenada real del destino
            int yDestino = nuevaPos[1] * SIZE;

            int xActual = (int) this.getX();
            int yActual = (int) this.getY();

            int xMovimiento = xDestino - xActual;
            int yMovimiento = yDestino - yActual;

            if (xMovimiento > 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 0; // Mover hacia la derecha
            } else if (xMovimiento < 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 2; // Mover hacia la izquierda
            } else if (yMovimiento > 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 1; // Mover hacia abajo
            } else if (yMovimiento < 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 3; // Mover hacia arriba
            }
        } else if ("floyd".equals(algoritmo) && distance != null
                && !this.eraVulnerable) {
            Stack<int[]> camino = new Stack<>();

            while (x != miUltPosX || y != miUltPosY) {
                camino.push(new int[]{x, y});
                int minDist = distance[y][x];

                int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    if (newY >= 0 && newY < laberinto.getMatriz().length && newX >= 0 && newX < laberinto.getMatriz()[0].length && distance[newY][newX] == minDist - 1) {
                        x = newX;
                        y = newY;
                        break;
                    }
                }
            }
            int[] nuevaPos = camino.pop();
            int xDestino = nuevaPos[0] * SIZE; // Multiplicamos por SIZE para obtener la coordenada real del destino
            int yDestino = nuevaPos[1] * SIZE;

            int xActual = (int) this.getX();
            int yActual = (int) this.getY();

            int xMovimiento = xDestino - xActual;
            int yMovimiento = yDestino - yActual;

            if (xMovimiento > 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 0; // Mover hacia la derecha
            } else if (xMovimiento < 0) {
                this.setY(miUltPosY * SIZE);
                direccion = 2; // Mover hacia la izquierda
            } else if (yMovimiento > 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 1; // Mover hacia abajo
            } else if (yMovimiento < 0) {
                this.setX(miUltPosX * SIZE);
                direccion = 3; // Mover hacia arriba
            }
        }
//        System.out.println("Direccion Actual: " + this.getDireccion());
//        System.out.println("Direccion Nueva: " + direccion);
//        System.out.println("X: " + this.getX() + " | Y: " + this.getY());
//        System.out.println("Objetivo X: " + ultPosX + " | Objetivo Y: " + ultPosY);
//        System.out.println("Vulnerable: " + this.isVulnerable());
//        System.out.println("Era vulnerable: " + this.eraVulnerable);
//        System.out.println("");
        if (this.getDireccion() != direccion) {
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

    public void comer(Laberinto laberinto, Juego juego) { // Come Pacman y vuelve a pacman y los fantasmas a una posicion inicial
        int x = (int) getX() / SIZE;
        int x1 = (int) (getX() + (SIZE - 1)) / SIZE;
        int y = (int) getY() / SIZE;
        int y1 = (int) (getY() + (SIZE - 1)) / SIZE;

        if ((this.getDireccion() == 0 || this.getDireccion() == 1)) {
            if ((int) juego.getPacMan().getY() / SIZE == y && (int) juego.getPacMan().getX() / SIZE == x) {
                juego.cambiarVidas(-1);
                juego.nuevaPosIniPacman();
                juego.posIniFantasmas();
                if (juego.isHiloAnimacionInicialFantasmas()) {
                    juego.interrumpirAnimacionInicialFantasmas();
                }
                juego.animacionInicialFantasmas();
            }
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3)) {
            if ((int) (juego.getPacMan().getY() + 19) / SIZE == y1 && (int) (juego.getPacMan().getX() + 19) / SIZE == x1) {
                juego.cambiarVidas(-1);
                juego.nuevaPosIniPacman();
                juego.posIniFantasmas();
                if (juego.isHiloAnimacionInicialFantasmas()) {
                    juego.interrumpirAnimacionInicialFantasmas();
                }
                juego.animacionInicialFantasmas();
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

        graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
//        if (segundoAct > segundoAnt + 0.25) {
//            graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, 15, 15);
//        } else {
//            graficos.drawImage(new Image(getImagen().get(1)), getX() + 2, getY() + 2, 15, 15);
//        }
        graficos.setTransform(oldTransform);
    }
}
