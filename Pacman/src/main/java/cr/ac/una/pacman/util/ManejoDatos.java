/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.util;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import cr.ac.una.pacman.model.Laberinto;
import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.model.Trofeo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ArauzKJ
 */
public class ManejoDatos {

    private static final String TXT_PATH = "src/main/java/cr/ac/una/pacman/model/datos_partida.txt";
    private static final String TXT_PATH_RECORDS = "src/main/java/cr/ac/una/pacman/model/records.txt";

    // Método para buscar un Jugador por su nombre.
    public static Partida buscarJugadorPorNombre(String nombre) {
        List<Partida> partidas = leerPartidas();

        for (Partida partida : partidas) {
            if (partida.getJugador().equals(nombre)) {
                return partida;
            }
        }
        return null; // Si el jugador no se encuentra
    }

    public static List<Partida> leerRecords() {
        List<Partida> partidas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH_RECORDS))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                String jugador = datos[0];
                String dificultad = datos[1];
                int totalPuntos = Integer.parseInt(datos[2]);
                int tiempoTotal = Integer.parseInt(datos[3]);

                Partida partida = new Partida(jugador, dificultad);
                partida.actualizarEstadistica("TotalPuntos", totalPuntos);
                partida.actualizarEstadistica("TiempoTotal", tiempoTotal);

                // Puedes agregar más actualizaciones de estadísticas según sea necesario
                partidas.add(partida);
            }

            // Ordenar la lista de partidas por TotalPuntos en orden descendente
            Collections.sort(partidas, Comparator.comparingInt(p -> -p.obtenerEstadistica("TotalPuntos")));

            // Obtener el top 10
            int topCount = Math.min(10, partidas.size());
            return partidas.subList(0, topCount);

        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return partidas;
    }

    public static void guardarPartidas(Partida partida) {
        List<Partida> partidas = leerPartidas();
        // Verificar si el jugador ya existe
        boolean jugadorExiste = false;
        for (Partida j : partidas) {
            if (j.getJugador().equals(partida.getJugador())) {
                j.setDificultad(partida.getDificultad());
                j.setEstadisticas(partida.getEstadisticas());
                j.setTrofeos(partida.getTrofeos());
                j.setLaberintos(partida.getLaberintos());
                jugadorExiste = true;
                break;
            }
        }

        if (!jugadorExiste) {
            partidas.add(partida);
        }

        escribirPartidas(partidas);
    }

    private static void escribirPartidas(List<Partida> partidas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH))) {
            // Se itera sobre la lista de jugadores.
            for (Partida partida : partidas) {
                // Se escribe en el archivo el nombre, modo de juego, puntuación, movimientos y tiempo del jugador.
                bw.write(partida.getJugador() + "," + partida.getDificultad() + ",");

                // Se obtienen los trofeos de la partida.
                Map<String, Trofeo> trofeos = partida.getTrofeos();

                // Se itera sobre los trofeos y se escribe cada uno en el formato adecuado.
                for (Map.Entry<String, Trofeo> entry : trofeos.entrySet()) {
                    Trofeo trofeo = entry.getValue();
                    bw.write(trofeo.getNombre() + "-" + trofeo.getDescripcion() + "-" + trofeo.isDesbloqueado() + "-" + trofeo.getCont() + ";");
                }
                bw.write(",");

                // Se obtienen las estadísticas del jugador.
                Map<String, Integer> estadisticas = partida.getEstadisticas();

                // Se itera sobre las estadísticas y se escribe cada una en el formato adecuado.
                for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
                    bw.write(entry.getKey() + "=" + entry.getValue() + ";");
                }

                bw.write(",");
                // Se obtiene la pila de movimientos del jugador.
                List<Laberinto> laberintos = partida.getLaberintos();

                // Se itera sobre los movimientos y se escribe cada uno en el formato adecuado.
                for (Laberinto laberinto : laberintos) {
                    // Se escribe el nivel, tema y matriz del laberinto.
                    bw.write(laberinto.getNivel() + "<" + laberinto.getTema() + "<" + laberinto.isDesbloqueado() + "<");

                    char[][] matriz = laberinto.getMatriz();
                    for (int i = 0; i < matriz.length; i++) {
                        for (int j = 0; j < matriz[0].length; j++) {
                            bw.write(matriz[i][j]);
                        }
                    }
                    bw.write(";");
                }
                bw.newLine(); // Se agrega una nueva línea al final de cada jugador para separarlos en el archivo.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Partida> leerPartidas() {
        // Se crea una lista para almacenar los objetos Partida.
        List<Partida> partidas = new ArrayList<>();

        // Se intenta abrir y leer el archivo.
        try (BufferedReader br = new BufferedReader(new FileReader(TXT_PATH))) {
            String line;

            // Se lee cada línea del archivo.
            while ((line = br.readLine()) != null) {
                // Se separa la línea en datos individuales utilizando coma como separador.
                String[] datos = line.split(",");

                String jugador = datos[0]; // Se obtiene el nombre del jugador.
                String dificultad = datos[1]; // Se obtiene la dificultad.

                // Se obtienen los trofeos de la partida.
                Map<String, Trofeo> trofeos = new HashMap<>();
                String[] trofeosData = datos[2].split(";");
                for (String trofeoData : trofeosData) {
                    String[] trofeoInfo = trofeoData.split("-");
                    String nombreTrofeo = trofeoInfo[0];
                    String descripcionTrofeo = trofeoInfo[1];
                    boolean desbloqueadoTrofeo = Boolean.parseBoolean(trofeoInfo[2]);
                    int contTrofeo = Integer.parseInt(trofeoInfo[3]);

                    Trofeo trofeo = new Trofeo(nombreTrofeo, descripcionTrofeo);
                    trofeo.setDesbloqueado(desbloqueadoTrofeo);
                    trofeo.setCont(contTrofeo);

                    trofeos.put(nombreTrofeo, trofeo);
                }

                // Se obtienen las estadísticas de la partida.
                Map<String, Integer> estadisticas = new HashMap<>();
                String[] estadisticasData = datos[3].split(";");
                for (String estadisticaData : estadisticasData) {
                    String[] estadisticaInfo = estadisticaData.split("=");
                    String nombreEstadistica = estadisticaInfo[0];
                    int valorEstadistica = Integer.parseInt(estadisticaInfo[1]);

                    estadisticas.put(nombreEstadistica, valorEstadistica);
                }

                // Se obtienen los laberintos de la partida.
                List<Laberinto> laberintos = new ArrayList<>();
                String[] laberintosData = datos[4].split(";");
                for (String laberintoData : laberintosData) {
                    String[] laberintoInfo = laberintoData.split("<");
                    int nivelLaberinto = Integer.parseInt(laberintoInfo[0]);
                    String temaLaberinto = laberintoInfo[1];
                    boolean desbloqueadoLaberinto = Boolean.parseBoolean(laberintoInfo[2]);

                    Laberinto laberinto = new Laberinto(ROWS, COLUMNS, nivelLaberinto, temaLaberinto);
                    laberinto.setDesbloqueado(desbloqueadoLaberinto);

                    // Se obtiene la matriz del laberinto.
                    char[][] matrizLaberinto = new char[ROWS][COLUMNS];
                    char[] caracteresMatriz = laberintoInfo[3].toCharArray();
                    int index = 0;
                    for (int i = 0; i < ROWS; i++) {
                        for (int j = 0; j < COLUMNS; j++) {
                            matrizLaberinto[i][j] = caracteresMatriz[index];
                            index++;
                        }
                    }

                    laberinto.setMatriz(matrizLaberinto);
                    laberintos.add(laberinto);
                }

                // Se crea un objeto Partida con los datos procesados y se agrega a la lista de partidas.
                Partida partida = new Partida(jugador, dificultad, laberintos, estadisticas, trofeos);
                partidas.add(partida);
            }
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no se encontró: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir datos: " + e.getMessage());
        }

        // Se retorna la lista de partidas.
        return partidas;
    }

    public static void guardarRecord(Partida partida) {
        String jugador = partida.getJugador();
        int nuevoTotalPuntos = partida.getEstadisticas().get("TotalPuntos");

        try {
            List<String> lineas = Files.readAllLines(Paths.get(TXT_PATH_RECORDS));

            // Buscar si ya existe un registro para el jugador
            boolean jugadorEncontrado = false;
            for (int i = 0; i < lineas.size(); i++) {
                String[] campos = lineas.get(i).split(",");
                if (campos.length >= 3 && campos[0].equals(jugador)) {
                    jugadorEncontrado = true;
                    int puntosAnteriores = Integer.parseInt(campos[2]);
                    if (nuevoTotalPuntos > puntosAnteriores) {
                        // Sobrescribir el registro solo si el nuevo total de puntos es mayor
                        lineas.set(i, jugador + "," + partida.getDificultad() + "," + nuevoTotalPuntos + "," + partida.getEstadisticas().get("TiempoTotal"));
                        break;
                    }
                }
            }

            // Si el jugador no se encontró en los registros, agregar un nuevo registro
            if (!jugadorEncontrado) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH_RECORDS, true))) {
                    bw.write(jugador + "," + partida.getDificultad() + "," + nuevoTotalPuntos + "," + partida.getEstadisticas().get("TiempoTotal"));
                    bw.newLine();
                }
            } else {
                // Sobrescribir todo el archivo con las nuevas líneas
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_PATH_RECORDS))) {
                    for (String linea : lineas) {
                        bw.write(linea);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
