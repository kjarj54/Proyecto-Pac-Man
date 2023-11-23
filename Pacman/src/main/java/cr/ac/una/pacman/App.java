package cr.ac.una.pacman;

import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import cr.ac.una.pacman.model.Fantasma;
import cr.ac.una.pacman.model.Laberinto;
import cr.ac.una.pacman.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    int[][] padresX = null;
    int[][] padresY = null;
    int[][] distance = null;
    int miUltPosX;
    int miUltPosY;
    public int ultPosX;
    public int ultPosY;
    Laberinto laberinto;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/cr/ac/una/pacman/resources/FantasmaRojo.png")));
        stage.setTitle("Pac-Man");
        FlowController.getInstance().goMain();

        /*laberinto = new Laberinto(20, 16, 0, "");
        char[][] lab = {
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#'},
            {'#', '#', ' ', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', '#'},
            {'#', ' ', ' ', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', '#', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#'},
            {'#', '#', ' ', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#', '#', ' ', '#', ' ', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };
        laberinto.generarLaberinto();
//        laberinto.setMatriz(lab);

        algoritmoDijkstra(laberinto, 14, 18);*/
    }

    public static void main(String[] args) {
        launch();
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

    public void algoritmoDijkstra(Laberinto laberinto, int objetivoX, int objetivoY) {
        int filas = laberinto.getMatriz().length;
        int columnas = laberinto.getMatriz()[0].length;

        int[][] distancias = new int[filas][columnas];
        int[][][] distanciasCopia = new int[3][filas][columnas];
        padresX = new int[filas][columnas];
        padresY = new int[filas][columnas];
        int[][][] padresXCopia = new int[3][filas][columnas];
        int[][][] padresYCopia = new int[3][filas][columnas];

        for (int iteracion = 0; iteracion < 3; iteracion++) {
            PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
            boolean[][] visitado = new boolean[filas][columnas];
            for (int i = 0; i < filas; i++) {
                Arrays.fill(distancias[i], Integer.MAX_VALUE);
                Arrays.fill(distancias[i], Integer.MAX_VALUE);
                Arrays.fill(distancias[i], Integer.MAX_VALUE);
                Arrays.fill(visitado[i], false);
            }

            int x = objetivoX;
            int y = objetivoY;

            if (iteracion > 0) {
                Stack<int[]> camino = new Stack<>();
                Stack<int[]> camino2 = new Stack<>();
                while (x != 1 || y != 1) {
                    if (x < 0 || y < 0) {
                        break;  // Salir si se alcanza un valor no válido
                    }
                    camino.push(new int[]{x, y});
                    int tempX = padresXCopia[0][y][x];
                    int tempY = padresYCopia[0][y][x];
                    x = tempX;
                    y = tempY;
                }
                if (iteracion == 2) {
                    x = objetivoX;
                    y = objetivoY;
                    while (x != 1 || y != 1) {
                        if (x < 0 || y < 0) {
                            break;  // Salir si se alcanza un valor no válido
                        }
                        camino2.push(new int[]{x, y});
                        int tempX = padresXCopia[1][y][x];
                        int tempY = padresYCopia[1][y][x];
                        x = tempX;
                        y = tempY;
                    }
                }
                int itera = 0;
                int size = camino.size();

                while (!camino.isEmpty()) {
                    int[] nuevaPos = camino.pop();
                    if (itera > 5 && itera < size - 6) {
                        distancias[nuevaPos[1]][nuevaPos[0]] = distanciasCopia[0][nuevaPos[1]][nuevaPos[0]];
                    }
                    itera++;
                }
                if (iteracion == 2) {
                    size = camino2.size();
                    itera = 0;
                    while (!camino2.isEmpty()) {
                        int[] nuevaPos = camino2.pop();
                        if (itera > 9 && itera < size - 9) {
                            distancias[nuevaPos[1]][nuevaPos[0]] = distanciasCopia[1][nuevaPos[1]][nuevaPos[0]];
                        }
                        itera++;
                    }
                }
            }

            for (int i = 0; i < filas; i++) {
                Arrays.fill(padresX[i], -1);
                Arrays.fill(padresY[i], -1);
            }

            distancias[1][1] = 0;
            colaPrioridad.add(new Nodo(1, 1, 0));

            while (!colaPrioridad.isEmpty()) {
                Nodo nodoActual = colaPrioridad.poll();

                x = nodoActual.x;
                y = nodoActual.y;

                if (x == objetivoX && y == objetivoY) {
                    break;
                }

                if (visitado[y][x]) {
                    continue;
                }

                visitado[y][x] = true;

                int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

                for (int[] movimiento : movimientos) {
                    int nuevoX = x + movimiento[0];
                    int nuevoY = y + movimiento[1];

                    char celda = '#';

                    if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas) {
                        celda = laberinto.getMatrizCelda(nuevoY, nuevoX);
                    }

                    if (nuevoX >= 0 && nuevoX < columnas && nuevoY >= 0 && nuevoY < filas && !visitado[nuevoY][nuevoX]
                            && (celda == ' ' || celda == 'p' || celda == '*' || celda == '-')) {
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
            for (int i = 0; i < filas; i++) {
                System.arraycopy(distancias[i], 0, distanciasCopia[iteracion][i], 0, columnas);
                System.arraycopy(padresX[i], 0, padresXCopia[iteracion][i], 0, columnas);
                System.arraycopy(padresY[i], 0, padresYCopia[iteracion][i], 0, columnas);
            }

            if (distancias[objetivoY][objetivoX] == Integer.MAX_VALUE) {
                System.out.println("No se encontró un camino al objetivo en la iteración " + (iteracion + 1) + ".");
            } else {
                System.out.println("La distancia mínima al objetivo en la iteración " + (iteracion + 1) + " es: " + distancias[objetivoY][objetivoX]);
                System.out.println("Camino desde (1, 1) a (" + objetivoX + ", " + objetivoY + ") en la iteración " + (iteracion + 1) + ":");
                imprimirCamino(1, 1, objetivoX, objetivoY);
            }
        }
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
}
