/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANTHONY
 */
public class Partida {

    private String jugador;
    private String dificultad;
    private List<Laberinto> laberintos;
    private Map<String, Integer> estadisticas;
    private Map<String, Trofeo> trofeos;

    public Partida(String jugador, String dificultad) {
        this.jugador = jugador;
        this.dificultad = dificultad;
        this.laberintos = new ArrayList<>();
        this.estadisticas = new HashMap<>();
        this.trofeos = new HashMap<>();

        this.estadisticas.put("TotalPuntos", 0);
        this.estadisticas.put("MayorPuntosN1", 0);
        this.estadisticas.put("MayorPuntosN2", 0);
        this.estadisticas.put("MayorPuntosN3", 0);
        this.estadisticas.put("MayorPuntosN4", 0);
        this.estadisticas.put("MayorPuntosN5", 0);
        this.estadisticas.put("MayorPuntosN6", 0);
        this.estadisticas.put("MayorPuntosN7", 0);
        this.estadisticas.put("MayorPuntosN8", 0);
        this.estadisticas.put("MayorPuntosN9", 0);
        this.estadisticas.put("MayorPuntosN10", 0);
        this.estadisticas.put("MayorPuntosVidas", 0);
        this.estadisticas.put("TotalVidasPerdidas", 0);
        this.estadisticas.put("CantNivelJugadoN1", 0);
        this.estadisticas.put("CantNivelJugadoN2", 0);
        this.estadisticas.put("CantNivelJugadoN3", 0);
        this.estadisticas.put("CantNivelJugadoN4", 0);
        this.estadisticas.put("CantNivelJugadoN5", 0);
        this.estadisticas.put("CantNivelJugadoN6", 0);
        this.estadisticas.put("CantNivelJugadoN7", 0);
        this.estadisticas.put("CantNivelJugadoN8", 0);
        this.estadisticas.put("CantNivelJugadoN9", 0);
        this.estadisticas.put("CantNivelJugadoN10", 0);
        this.estadisticas.put("TotalFantasmasComidos", 0);
        this.estadisticas.put("MejorTiempoN1", 9999999);
        this.estadisticas.put("MejorTiempoN2", 9999999);
        this.estadisticas.put("MejorTiempoN3", 9999999);
        this.estadisticas.put("MejorTiempoN4", 9999999);
        this.estadisticas.put("MejorTiempoN5", 9999999);
        this.estadisticas.put("MejorTiempoN6", 9999999);
        this.estadisticas.put("MejorTiempoN7", 9999999);
        this.estadisticas.put("MejorTiempoN8", 9999999);
        this.estadisticas.put("MejorTiempoN9", 9999999);
        this.estadisticas.put("MejorTiempoN10", 9999999);
        this.estadisticas.put("TiempoTotal", 9999999);

        this.trofeos.put("Clasico", new Trofeo("Clasico", "Trofeo para el ganador del torneo Clásico"));
        this.trofeos.put("Cazador", new Trofeo("Cazador", "Trofeo para el jugador con más bajas en el torneo Cazador"));
        this.trofeos.put("Experto", new Trofeo("Experto", "Trofeo para el jugador con más puntos en el torneo Experto"));
        this.trofeos.put("Encierro", new Trofeo("Encierro", "Trofeo para el ganador del torneo Encierro"));
        this.trofeos.put("Flash", new Trofeo("Flash", "Trofeo para el jugador más rápido en el torneo Flash"));
        this.trofeos.put("Rey del PacMan", new Trofeo("Rey del PacMan", "Trofeo para el ganador del torneo Rey del PacMan"));
    }

    public Partida(String jugador, String dificultad, List<Laberinto> laberintos, Map<String, Integer> estadisticas, Map<String, Trofeo> trofeos) {
        this.jugador = jugador;
        this.dificultad = dificultad;
        this.laberintos = laberintos;
        this.estadisticas = estadisticas;
        this.trofeos = trofeos;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    // Actualiza una estadística en el mapa
    public void actualizarEstadistica(String estadistica, int valor) {
        if (estadisticas.containsKey(estadistica)) {
            estadisticas.put(estadistica, valor);
        }
    }

    // Obtén el valor de una estadística
    public int obtenerEstadistica(String estadistica) {
        return estadisticas.getOrDefault(estadistica, 0);
    }

    // Actualiza un Trofeo en el mapa
    public void actualizarTrofeo(String trofeo, Trofeo valor) {
        if (trofeos.containsKey(trofeo)) {
            trofeos.put(trofeo, valor);
        }
    }

    // Obtén el valor de un Trofeo
    public Trofeo obtenerTrofeo(String trofeo) {
        return trofeos.getOrDefault(trofeo, null);
    }

    public Laberinto getNivel(int nivel) {
        return laberintos.get(nivel);
    }

    public void generarNiveles() {
        String[] nombres = {
            "Joker.jpg",
            "Simpson.jpg",
            "IronMan.jpg",
            "SpiderMan.jpg",
            "Batman.jpg",
            "Superman.png",
            "DeadPool.jpg",
            "OnePiece.jpg",
            "NarutoShippuden.jpg",
            "DragonBallZ.jpg"
        };
        for (int i = 1; i <= 10; i++) {
            Laberinto laberinto = new Laberinto(ROWS, COLUMNS, i, nombres[i - 1]);
            laberinto.generarLaberinto();
            this.laberintos.add(laberinto);
        }
    }

    public List<Laberinto> getLaberintos() {
        return laberintos;
    }

    public void setLaberintos(List<Laberinto> laberintos) {
        this.laberintos = laberintos;
    }

    public Map<String, Integer> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Map<String, Integer> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Map<String, Trofeo> getTrofeos() {
        return trofeos;
    }

    public void setTrofeos(Map<String, Trofeo> trofeos) {
        this.trofeos = trofeos;
    }
    
    
}
