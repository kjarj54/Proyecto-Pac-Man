package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import cr.ac.una.pacman.util.SoundUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<int[][]> distanciasL = new ArrayList<>();
    List<int[][]> padresXL = new ArrayList<>();
    List<int[][]> padresYL = new ArrayList<>();

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
    public void mover(Laberinto laberinto, PacMan pacman, double objetivoX, double objetivoY, String dificultad) {
        int distancia = calcularDistancia(ultPosX, ultPosY, objetivoX / SIZE, objetivoY / SIZE);
        int distanciaFantasma = calcularDistancia(ultPosX, ultPosY, miUltPosX, miUltPosY);

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
                    if (this.muerto && enCeldaCentro()) {
                        algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    } else if ((distancia >= 5 || distanciaFantasma <= 7) && enCeldaCentro()) {
                        distanciasL.add(0, algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE));
                        padresXL.add(0, padresX);
                        padresYL.add(0, padresY);
                        if ((distancia >= 5 || distanciaFantasma > 3) && ("Medio".equals(dificultad) || "Facil".equals(dificultad))) {
                            distanciasL.add(1, algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE));
                            padresXL.add(1, padresX);
                            padresYL.add(1, padresY);
                        }
                        if ((distancia >= 5 || distanciaFantasma > 3) && "Facil".equals(dificultad)) {
                            algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        }
                        distanciasL.clear();
                        padresXL.clear();
                        padresYL.clear();
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    } else if (enCeldaCentro()) {
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        eraVulnerable = false;
                    }
                }
                case DIJKSTRAALTERNATIVO -> {
                    if (this.muerto && enCeldaCentro()) {
                        algoritmoDijkstra(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE);
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    } else if ((distancia >= 5 || distanciaFantasma <= 7) && enCeldaCentro()) {
                        distanciasL.add(0, algoritmoDijkstraAlternativo(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE));
                        padresXL.add(0, padresX);
                        padresYL.add(0, padresY);
                        if ((distancia >= 5 || distanciaFantasma > 3) && ("Medio".equals(dificultad) || "Facil".equals(dificultad))) {
                            distanciasL.add(1, algoritmoDijkstraAlternativo(laberinto, (int) objetivoX / SIZE, (int) objetivoY / SIZE));
                            padresXL.add(1, padresX);
                            padresYL.add(1, padresY);
                        }
                        distanciasL.clear();
                        padresXL.clear();
                        padresYL.clear();
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        ultPosX = (int) objetivoX / SIZE;
                        ultPosY = (int) objetivoY / SIZE;
                        eraVulnerable = false;
                    } else if (enCeldaCentro()) {
                        miUltPosX = (int) this.getX() / SIZE;
                        miUltPosY = (int) this.getY() / SIZE;
                        eraVulnerable = false;
                    }
                }
                case FLOYD -> {
                    if (enCeldaCentro()) {
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

    private int calcularDistancia(int x1, int y1, double x2, double y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private boolean enCeldaCentro() {
        return (int) this.getX() / SIZE == (int) (this.getX() + (SIZE - 1)) / SIZE
                && (int) this.getY() / SIZE == (int) (this.getY() + (SIZE - 1)) / SIZE;
    }

    public int[][] algoritmoDijkstra(Laberinto laberinto, int objetivoX, int objetivoY) {
        int filas = laberinto.getMatriz().length;
        int columnas = laberinto.getMatriz()[0].length;
        int[][] distancias = new int[filas][columnas];

        for (int i = 0; i < filas; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
        }

        for (int l = 0; l < distanciasL.size(); l++) {
            procesarDistanciasL(distanciasL.get(l), distancias, padresXL.get(l), padresYL.get(l), objetivoX, objetivoY, filas, columnas);
        }
        padresX = new int[filas][columnas];
        padresY = new int[filas][columnas];
        boolean[][] visitado = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
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
                    int pesoCelda = 1;

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
//        }
        return distancias;
    }

    private void procesarDistanciasL(int[][] distanciasL, int[][] distancias, int[][] padresXl, int[][] padresYl, int objetivoX, int objetivoY, int filas, int columnas) {
        int[][] distanciasCopia = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(distanciasL[i], 0, distanciasCopia[i], 0, columnas);
        }

        Stack<int[]> camino = new Stack<>();
        int x = objetivoX;
        int y = objetivoY;

        // Verificar si hay un camino válido antes de intentar imprimirlo
        if (padresXl != null && padresYl != null && x >= 0 && x < padresXl[0].length && y >= 0 && y < padresYl.length) {
            while (x != miUltPosX || y != miUltPosY) {
                if (x < 0 || y < 0) {
                    break;  // Salir si se alcanza un valor no válido
                }
                camino.push(new int[]{x, y});
                int tempX = padresXl[y][x];
                int tempY = padresYl[y][x];
                x = tempX;
                y = tempY;
            }
        }

        int tam = camino.size();
        int conta = 0;
        while (!camino.isEmpty()) {
            int[] nuevaPos = camino.pop();
            if (conta > 6 && conta < tam - 6) {
                int xDestino = nuevaPos[0];
                int yDestino = nuevaPos[1];
                distancias[yDestino][xDestino] = distanciasCopia[yDestino][xDestino];
            }
            conta++;
        }
    }

    public int[][] algoritmoDijkstraAlternativo(Laberinto laberinto, int objetivoX, int objetivoY) {
        int filas = laberinto.getMatriz().length;
        int columnas = laberinto.getMatriz()[0].length;

        int[][] distancias = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
        }

        for (int l = 0; l < distanciasL.size(); l++) {
            procesarDistanciasL(distanciasL.get(l), distancias, padresXL.get(l), padresYL.get(l), objetivoX, objetivoY, filas, columnas);
        }
        padresX = new int[filas][columnas];
        padresY = new int[filas][columnas];
        boolean[][] visitado = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
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
                    if (probabilidad < 0.4) {
                        pesoCelda *= 5; // Aumenta el peso (reduce la probabilidad de elegir este camino)
                    } else if (probabilidad < 0.6) {
                        pesoCelda *= 3; // Aumenta el peso (reduce la probabilidad de elegir este camino)
                    } else if (probabilidad < 0.9) {
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
//            imprimirCamino((int) this.getX() / SIZE, (int) this.getY() / SIZE, objetivoX, objetivoY);
//        }
        return distancias;
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

            if (padresX != null && padresY != null && x >= 0 && x < padresX[0].length && y >= 0 && y < padresY.length) {
                camino = construirCamino(padresX, padresY, objetivoX, objetivoY);
            }
            if (!camino.isEmpty()) {
                int[] nuevaPos = camino.pop();
                int xDestino = nuevaPos[0] * SIZE; // Multiplicamos por SIZE para obtener la coordenada real del destino
                int yDestino = nuevaPos[1] * SIZE;

                direccion = moverHaciaDestino(xDestino, yDestino);
            }
        } else if (("dijkstra".equals(algoritmo) || "dijkstraAlternativo".equals(algoritmo))
                && padresX != null && padresY != null && !this.eraVulnerable) {
            Stack<int[]> camino = construirCamino(padresX, padresY, objetivoX, objetivoY);

            int[] nuevaPos = camino.pop();
            int xDestino = nuevaPos[0] * SIZE; // Multiplicamos por SIZE para obtener la coordenada real del destino
            int yDestino = nuevaPos[1] * SIZE;

            direccion = moverHaciaDestino(xDestino, yDestino);
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

            direccion = moverHaciaDestino(xDestino, yDestino);
//            System.out.println("Direccion Actual: " + this.getDireccion());
//            System.out.println("Direccion Nueva: " + direccion);
//            System.out.println("X: " + miUltPosX + " | Y: " + miUltPosY);
//            System.out.println("X: " + this.getX() + " | Y: " + this.getY());
//            System.out.println("Objetivo X: " + ultPosX + " | Objetivo Y: " + ultPosY);
//            System.out.println("Vulnerable: " + this.isVulnerable());
//            System.out.println("Era vulnerable: " + this.eraVulnerable);
//            System.out.println("");
        }
        if (this.getDireccion() != direccion) {
            cambioDireccion(direccion, laberinto);
        }
        switch (this.getDireccion()) {
            case 0 ->
                moverEnDireccion(laberinto, getX() + this.getVelocidad(), getY(), getX() + SIZE, getY() + (SIZE - 1));
            case 1 ->
                moverEnDireccion(laberinto, getX(), getY() + this.getVelocidad(), getX() + (SIZE - 1), getY() + SIZE);
            case 2 ->
                moverEnDireccion(laberinto, getX() - this.getVelocidad(), getY(), getX() - 1, getY() + (SIZE - 1));
            case 3 ->
                moverEnDireccion(laberinto, getX(), getY() - this.getVelocidad(), getX() + (SIZE - 1), getY() - 1);
            default -> {
            }
        }
    }

    public int moverHaciaDestino(int xDestino, int yDestino) {
        int xActual = (int) this.getX();
        int yActual = (int) this.getY();

        int xMovimiento = xDestino - xActual;
        int yMovimiento = yDestino - yActual;

        int direccion = 3;

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
        return direccion;
    }

    private Stack<int[]> construirCamino(int[][] padresX, int[][] padresY, int objetivoX, int objetivoY) {
        int x = objetivoX;
        int y = objetivoY;
        Stack<int[]> camino = new Stack<>();

        while (x != miUltPosX || y != miUltPosY) {
            if (x < 0 || y < 0) {
                break;
            }
            camino.push(new int[]{x, y});
            int tempX = padresX[y][x];
            int tempY = padresY[y][x];
            x = tempX;
            y = tempY;
        }
        return camino;
    }

    private void moverEnDireccion(Laberinto laberinto, double xNuevo, double yNuevo, double xCheck, double yCheck) {
        int xNuevoGrid = (int) xNuevo / SIZE;
        int yNuevoGrid = (int) yNuevo / SIZE;

        if (laberinto.getMatrizCelda(yNuevoGrid, xNuevoGrid) != '#'
                && laberinto.getMatrizCelda((int) yCheck / SIZE, (int) xCheck / SIZE) != '#') {
            setX(xNuevo);
            setY(yNuevo);
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

    public void comer(Laberinto laberinto, Juego juego, Partida partida) { // Come Pacman y vuelve a pacman y los fantasmas a una posicion inicial
        int x = (int) getX() / SIZE;
        int x1 = (int) (getX() + (SIZE - 1)) / SIZE;
        int y = (int) getY() / SIZE;
        int y1 = (int) (getY() + (SIZE - 1)) / SIZE;

        if ((this.getDireccion() == 0 || this.getDireccion() == 1)) {
            if ((int) juego.getPacMan().getY() / SIZE == y && (int) juego.getPacMan().getX() / SIZE == x) {
                SoundUtil.pacmanDeath();
                partida.actualizarEstadistica("TotalVidasPerdidas", partida.obtenerEstadistica("TotalVidasPerdidas") + 1);
                juego.cambiarVidas(-1);
                juego.pacmanMurio = true;
                juego.posIniPacman();
                juego.posIniFantasmas();
                if (juego.isHiloAnimacionInicialFantasmas()) {
                    juego.interrumpirAnimacionInicialFantasmas();
                }
                juego.animacionInicialFantasmas();
            }
        } else if ((this.getDireccion() == 2 || this.getDireccion() == 3)) {
            if ((int) (juego.getPacMan().getY() + 19) / SIZE == y1 && (int) (juego.getPacMan().getX() + 19) / SIZE == x1) {
                SoundUtil.pacmanDeath();
                juego.cambiarVidas(-1);
                partida.actualizarEstadistica("TotalVidasPerdidas", partida.obtenerEstadistica("TotalVidasPerdidas") + 1);
                juego.pacmanMurio = true;
                juego.posIniPacman();
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

        if (this.muerto) {
            graficos.drawImage(new Image("cr/ac/una/pacman/resources/FantasmaMuerto.png"), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
        } else if (this.vulnerable) {
            graficos.drawImage(new Image("cr/ac/una/pacman/resources/FantasmaVulnerable.png"), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
        } else {
            graficos.drawImage(new Image(getImagen().get(0)), getX() + 2, getY() + 2, SIZE - 5, SIZE - 5);
        }
        graficos.setTransform(oldTransform);
    }
}
