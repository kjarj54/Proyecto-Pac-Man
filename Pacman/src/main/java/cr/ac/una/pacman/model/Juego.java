/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import static io.github.palexdev.materialfx.utils.RandomUtils.random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author ANTHONY
 */
public class Juego {

    private PacMan pacMan;
    private List<Fantasma> fantasmas;
    private Laberinto laberinto;
    private double tiempo;
    private List<String> trofeos;
//    private Map<String, Integer> estadisticas;
    private int nivel;
    
    private Timeline animacionInicialFantasmasHilo;
    private Timeline comeFantasmasConsecutivosHilo;
    private Timeline powerPelletHilo;
    private Timeline encierroHilo;
    private Timeline superVelocidadHilo;
    private boolean hiloAnimacionInicialFantasmas = false;
    private boolean hiloComeFantasmasConsecutivos = false;
    private boolean hiloPowerPellets = false;
    private boolean hiloEncierro = false;
    private boolean hiloSuperVelocidad = false;
    public double incrementoVelocidad;
    public int multiplicadorPuntaje;
    public int fantasmasConsecutivos = 0;
    public int puntosTotales;
    public int puntosActuales;
    public boolean pacmanMurio;
    public boolean encierroUsado;
    public boolean pausa;

    public Juego(PacMan pacMan, List<Fantasma> fantasmas, int rows, int columns, int nivel) {
//    public Juego(PacMan pacMan, List<Fantasma> fantasmas, Laberinto laberinto) {
        this.pacMan = pacMan;
        this.fantasmas = fantasmas;
        this.laberinto = new Laberinto(rows, columns);
        this.tiempo = 0.0;
        this.trofeos = new ArrayList<>();
//        this.estadisticas = new HashMap<>();
        this.nivel = nivel;
//        estadisticas.put("puntosTotales", 0);
//        estadisticas.put("vidasPerdidas", 0);
//        estadisticas.put("fantasmasComidos", 0);
        this.incrementoVelocidad = 0;
        this.multiplicadorPuntaje = 1;
        this.puntosTotales = 0;
        this.puntosActuales = 0;
        this.pacmanMurio = false;
        this.encierroUsado = false;
        this.pausa = false;
    }

    //     Getters y Setters para todos los atributos
    public PacMan getPacMan() {
        return pacMan;
    }

    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
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

    public List<String> getTrofeos() {
        return trofeos;
    }

    public void setTrofeos(List<String> trofeos) {
        this.trofeos = trofeos;
    }

    public void verificarTrofeos() {
        Trofeo clasico = new Trofeo("Clásico", 2023, "Trofeo para el ganador del torneo Clásico");
        Trofeo cazador = new Trofeo("Cazador", 2023, "Trofeo para el jugador con más bajas en el torneo Cazador");
        Trofeo experto = new Trofeo("Experto", 2023, "Trofeo para el jugador con más puntos en el torneo Experto");
        Trofeo encierro = new Trofeo("Encierro", 2023, "Trofeo para el ganador del torneo Encierro");
        Trofeo flash = new Trofeo("Flash", 2023, "Trofeo para el jugador más rápido en el torneo Flash");
        Trofeo rey = new Trofeo("Rey del Pac-Man", 2023, "Trofeo para el ganador del torneo Rey del Pac-Man");
    }

//    public Map<String, Integer> getEstadisticas() {
//        return estadisticas;
//    }
//
//    public void setEstadisticas(Map<String, Integer> estadisticas) {
//        this.estadisticas = estadisticas;
//    }
    
    public int getNivel() {
        return nivel;
    }
    
    public void setNivel(int nivel) {    
        this.nivel = nivel;
    }

    public boolean isHiloAnimacionInicialFantasmas() {
        return hiloAnimacionInicialFantasmas;
    }

    public void setHiloAnimacionInicialFantasmas(boolean hiloAnimacionInicialFantasmas) {
        this.hiloAnimacionInicialFantasmas = hiloAnimacionInicialFantasmas;
    }

    public boolean isHiloPowerPellets() {
        return hiloPowerPellets;
    }

    public void setHiloPowerPellets(boolean hiloPowerPellets) {
        this.hiloPowerPellets = hiloPowerPellets;
    }

    public void posIniFantasmas() {
        if (!this.getFantasmas().get(0).isEncerrado()) {
            this.getFantasmas().get(0).setX((COLUMNS * SIZE) / 2);
            this.getFantasmas().get(0).setY(((ROWS * SIZE) / 2) - SIZE * 2);
        }

        this.getFantasmas().get(1).setX((COLUMNS * SIZE) / 2 + SIZE);
        this.getFantasmas().get(1).setY((ROWS * SIZE) / 2);

        this.getFantasmas().get(2).setX((COLUMNS * SIZE) / 2 - SIZE);
        this.getFantasmas().get(2).setY((ROWS * SIZE) / 2);

        this.getFantasmas().get(3).setX((COLUMNS * SIZE) / 2);
        this.getFantasmas().get(3).setY((ROWS * SIZE) / 2);
    }

    public void generarLaberinto() {
        Random random = new Random();

        for (int i = 1; i < ROWS; i++) { // Se inicializa toda la matriz con " "
            for (int j = 1; j < COLUMNS; j++) {
                laberinto.setMatrizCelda(' ', i, j);
            }
        }
        for (int i = 0; i < ROWS; i++) {
            laberinto.setMatrizCelda('n', i, 0);
        }
        for (int j = 0; j < COLUMNS; j++) { // Se ponen las filas y columnas 0 en "n" para evitar bugs
            laberinto.setMatrizCelda('n', 0, j);
        }

        for (int i = 1; i < COLUMNS; i++) {
            laberinto.setMatrizCelda('#', 1, i);
            laberinto.setMatrizCelda('#', ROWS - 1, i);
        }
        for (int i = 1; i < ROWS - 1; i++) { // Se ponen los muros alrededor del laberinto
            laberinto.setMatrizCelda('#', i, 1);
            laberinto.setMatrizCelda('#', i, COLUMNS - 1);
        }

        for (int i = 2; i < ROWS - 1; i++) { // se ponen muros el las localizaciones impares y sus vecinos dependiendo la probabilidad
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (i % 2 == 1 && j % 2 == 1 && random.nextInt(10) >= 3) {
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
        for (int i = 2; i < ROWS - 1; i++) { // Se marcan todos lo campos vacios como "p" puntos
            for (int j = 2; j < COLUMNS - 1; j++) {
                if (laberinto.getMatrizCelda(i, j) == ' ') {
                    laberinto.setMatrizCelda('p', i, j);
                }
            }
        }
        for (int i = (ROWS / 2) - 2; i < (ROWS / 2) + 4; i++) { // Se crea la casa de los fantasmas
            for (int j = (COLUMNS / 2) - 3; j < (COLUMNS / 2) + 4; j++) {
                if ((i == (ROWS / 2) - 1 || i == (ROWS / 2) + 2) && (j >= (COLUMNS / 2) - 2 && j <= (COLUMNS / 2) + 2)) {
                    laberinto.setMatrizCelda('#', i, j);
                } else if ((j == (COLUMNS / 2) - 2 || j == (COLUMNS / 2) + 2) && (i >= (ROWS / 2) - 1 && i <= (ROWS / 2) + 2)) {
                    laberinto.setMatrizCelda('#', i, j);
                } else {
                    laberinto.setMatrizCelda(' ', i, j);
                }
            }
        }
        laberinto.setMatrizCelda('-', (ROWS / 2) - 1, COLUMNS / 2); // La salida de la casa de los fantasmas

        int salida = random.nextInt(2); // La salida a los exteriores del laberinto
//        int salida = 0;
        switch (salida) {
            case 0 -> {
                for (int i = ROWS / 2 - 1; i <= ROWS / 2 + 1; i++) {
                    for (int j = 1; j <= 2; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                    for (int j = COLUMNS - 2; j <= COLUMNS - 1; j++) {
                        laberinto.setMatrizCelda(' ', i, j);
                    }
                }
            }

            case 1 -> {
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
            }

            default -> {
            }
        }
        int contPP = 0;
        while (contPP < 4) { // Coloca aleatoriamente los "*" o Power Pullets
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (laberinto.getMatrizCelda(randomY, randomX) == 'p') {
                laberinto.setMatrizCelda('*', randomY, randomX);
                contPP++;
            }
        }

        posIniPacman();
//        this.getPacMan().setX(3 * SIZE);
//        this.getPacMan().setY(10 * SIZE);
    }

    public boolean revisarColision(int posY, int posX) {
        boolean comio = false;
        for (Fantasma fant : this.getFantasmas()) {
            if (fant.isVulnerable() && (fant.getY() / SIZE == posY && fant.getX() / SIZE == posX)) {
                fant.setY((ROWS * SIZE) / 2);
                fant.setX((COLUMNS * SIZE) / 2);
                comio = true;
            }
        }
        return comio;
    }

    public void fantasmasVulnerables() {
        for (Fantasma fant : this.getFantasmas()) {
            cambiarVelocidadFantasma(-0.15, fant);
            fant.setVulnerable(true);
        }
    }

    public void fantasmasNoVulnerables() {
        for (Fantasma fant : this.getFantasmas()) {
            if ("Rojo".equals(fant.getColor())) {
                cambiarVelocidadFantasma(this.incrementoVelocidad, fant);
            } else {
                cambiarVelocidadFantasma(0, fant);
            }
            fant.setVulnerable(false);
        }
        nuevaPosAleatoria(this.getFantasmas().get(3));
    }

    public void cambiarVidas(int vidas) {
        this.getPacMan().setVidas(this.getPacMan().getVidas() + vidas);
    }

    public void cambiarVelocidadPacman(double velocidad) {
        this.getPacMan().setVelocidad(this.getPacMan().getVelocidad() + velocidad);
    }

    public void cambiarVelocidadFantasma(double velocidad, Fantasma fant) {
        fant.setVelocidad(0.7 + velocidad);
    }

    int estado = 0;

    public void animacionInicialFantasmas() {
        if (!hiloAnimacionInicialFantasmas) {
            hiloAnimacionInicialFantasmas = true;
            int estadoDuration = 5000;

            getFantasmas().get(1).setEncerrado(true);
            getFantasmas().get(1).ultPosX = (COLUMNS / 2) + 1;
            getFantasmas().get(1).ultPosY = (ROWS / 2) + 1;

            getFantasmas().get(2).setEncerrado(true);
            getFantasmas().get(2).ultPosX = (COLUMNS / 2) - 1;
            getFantasmas().get(2).ultPosY = (ROWS / 2) + 1;

            getFantasmas().get(3).setEncerrado(true);
            getFantasmas().get(3).ultPosX = COLUMNS / 2;
            getFantasmas().get(3).ultPosY = (ROWS / 2) + 1;
            System.out.println("Hola Animacion Inicial Fantasmas");

            animacionInicialFantasmasHilo = new Timeline(new KeyFrame(Duration.millis(estadoDuration), (var event) -> {
                switch (estado) {
                    case 0 -> {
                        getFantasmas().get(1).setEncerrado(false);
                    }
                    case 1 -> {
                        getFantasmas().get(3).setEncerrado(false);
                    }
                    case 2 -> {
                        getFantasmas().get(2).setEncerrado(false);
                        System.out.println("Adiós Animacion Inicial Fantasmas");
                        hiloAnimacionInicialFantasmas = false;
                        estado = 0;
                        animacionInicialFantasmasHilo.stop();
                    }
                }
                estado++;
            }));
            animacionInicialFantasmasHilo.setCycleCount(Timeline.INDEFINITE);
            animacionInicialFantasmasHilo.play();
        }
    }

    public void interrumpirAnimacionInicialFantasmas() {
        getFantasmas().get(1).setEncerrado(false);
        getFantasmas().get(2).setEncerrado(false);
        getFantasmas().get(3).setEncerrado(false);
        hiloAnimacionInicialFantasmas = false;
        estado = 0;
        animacionInicialFantasmasHilo.stop();
    }

    public void comeFantasmasConsecutivos() {
        if (!hiloComeFantasmasConsecutivos) {
            hiloComeFantasmasConsecutivos = true;
            fantasmasConsecutivos = 0;

            comeFantasmasConsecutivosHilo = new Timeline();
            comeFantasmasConsecutivosHilo.setCycleCount(1); // Ejecutar una vez

            KeyFrame inicioKeyFrame = new KeyFrame(Duration.ZERO, (event) -> {
                System.out.println("Hola Consecutivos " + fantasmasConsecutivos);
            });

            KeyFrame finalKeyFrame = new KeyFrame(Duration.seconds(5), (event) -> {
                System.out.println("Adiós Consecutivos " + fantasmasConsecutivos);
                hiloComeFantasmasConsecutivos = false; // Marcar que el hilo ha terminado
            });

            comeFantasmasConsecutivosHilo.getKeyFrames().addAll(inicioKeyFrame, finalKeyFrame);
            comeFantasmasConsecutivosHilo.play();
        }
    }

    public void powerPellet() {
        if (!hiloPowerPellets) {
            hiloPowerPellets = true;

            powerPelletHilo = new Timeline();
            powerPelletHilo.setCycleCount(1); // Ejecutar una vez

            KeyFrame inicioKeyFrame = new KeyFrame(Duration.ZERO, (event) -> {
                fantasmasVulnerables();
                cambiarVelocidadPacman(0.21);
                System.out.println("Hola Poder");
            });

            KeyFrame finalKeyFrame = new KeyFrame(Duration.seconds(10), (event) -> {
                fantasmasNoVulnerables();
                cambiarVelocidadPacman(-0.21);
                System.out.println("Adiós Poder");
                hiloPowerPellets = false; // Marcar que el hilo ha terminado
                powerPelletHilo.stop();
            });

            powerPelletHilo.getKeyFrames().addAll(inicioKeyFrame, finalKeyFrame);
            powerPelletHilo.play();
        }
    }

    int random2;
    int random1;

    public void encierro() {
        if (!hiloEncierro) {
            hiloEncierro = true;

            encierroHilo = new Timeline();
            encierroHilo.setCycleCount(1); // Ejecutar una vez

            KeyFrame inicioKeyFrame = new KeyFrame(Duration.ZERO, (event) -> {
                Random random = new Random();
                random2 = random.nextInt(4);
                random1 = random.nextInt(4);
                while (random1 == random2) {
                    random2 = random.nextInt(4);
                }

                getFantasmas().get(random1).setEncerrado(true);
                getFantasmas().get(random2).setEncerrado(true);
                getFantasmas().get(random1).setMuerto(true);
                getFantasmas().get(random2).setMuerto(true);
                encierroUsado = true;

                getFantasmas().get(random1).ultPosX = (COLUMNS / 2);
                getFantasmas().get(random1).ultPosY = (ROWS / 2) + 1;
                getFantasmas().get(random1).setVelocidad(0.9);

                getFantasmas().get(random2).ultPosX = (COLUMNS / 2) + 1;
                getFantasmas().get(random2).ultPosY = (ROWS / 2) + 1;
                getFantasmas().get(random2).setVelocidad(0.9);

                System.out.println("Hola Encierro");
            });

            KeyFrame finalKeyFrame = new KeyFrame(Duration.seconds(30), (event) -> {
                getFantasmas().get(random1).setEncerrado(false);

                getFantasmas().get(random2).setEncerrado(false);
                System.out.println("Adiós Encierro");
                hiloEncierro = false; // Marcar que el hilo ha terminado
            });

            encierroHilo.getKeyFrames().addAll(inicioKeyFrame, finalKeyFrame);
            encierroHilo.play();
        }
    }

    public void superVelocidad() {
        if (!hiloSuperVelocidad && this.getPacMan().superVelocidad) {
            hiloSuperVelocidad = true;

            superVelocidadHilo = new Timeline();
            superVelocidadHilo.setCycleCount(1); // Ejecutar una vez

            KeyFrame inicioKeyFrame = new KeyFrame(Duration.ZERO, (event) -> {
                cambiarVelocidadPacman(0.1);
                this.multiplicadorPuntaje = 2;
                System.out.println("Hola Super Velocidad");
            });

            KeyFrame finalKeyFrame = new KeyFrame(Duration.seconds(6), (event) -> {
                cambiarVelocidadPacman(-0.1);
                this.multiplicadorPuntaje = 1;
                this.getPacMan().superVelocidad = false;
                System.out.println("Adiós Super Velocidad");
                hiloSuperVelocidad = false; // Marcar que el hilo ha terminado
            });

            superVelocidadHilo.getKeyFrames().addAll(inicioKeyFrame, finalKeyFrame);
            superVelocidadHilo.play();
        }
    }

    public void pausa() {
        if (hiloAnimacionInicialFantasmas) {
            animacionInicialFantasmasHilo.pause();
        }
        if (hiloComeFantasmasConsecutivos) {
            comeFantasmasConsecutivosHilo.pause();
        }
        if (hiloPowerPellets) {
            powerPelletHilo.pause();
        }
        if (hiloEncierro) {
            encierroHilo.pause();
        }
        if (hiloSuperVelocidad) {
            superVelocidadHilo.pause();
        }
        pausa = true;
    }

    public void continuar() {
        if (hiloAnimacionInicialFantasmas) {
            animacionInicialFantasmasHilo.play();
        }
        if (hiloComeFantasmasConsecutivos) {
            comeFantasmasConsecutivosHilo.play();
        }
        if (hiloPowerPellets) {
            powerPelletHilo.play();
        }
        if (hiloEncierro) {
            encierroHilo.play();
        }
        if (hiloSuperVelocidad) {
            superVelocidadHilo.play();
        }
        pausa = false;
    }

    public void nuevaPosAleatoria(Fantasma fantasma) {
        boolean Listo = false;
        while (!Listo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            char celda = this.getLaberinto().getMatrizCelda(randomY, randomX);
            int distancia = (int) Math.sqrt(Math.pow((fantasma.ultPosX - randomX), 2)
                    + Math.pow((fantasma.ultPosY - randomY), 2));

            if ((celda == ' ' || celda == 'p') && distancia > 5) {
                fantasma.ultPosX = randomX;
                fantasma.ultPosY = randomY;
                Listo = true;
            }
        }
    }

    public void nuevaPosEscape(Fantasma fantasma) {
        boolean Listo = false;
        while (!Listo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            char celda = this.getLaberinto().getMatrizCelda(randomY, randomX);
            int distancia = (int) Math.sqrt(Math.pow((this.getPacMan().getX() / SIZE - randomX), 2)
                    + Math.pow((this.getPacMan().getY() / SIZE - randomY), 2));

            if ((celda == ' ' || celda == 'p' || celda == '*')
                    && (distancia >= 5)
                    && (randomX != fantasma.ultPosX || fantasma.ultPosY != randomY)) {
                fantasma.ultPosX = randomX;
                fantasma.ultPosY = randomY;
                Listo = true;
            }
        }
    }

    public void posIniPacman() {
        this.getPacMan().setX((COLUMNS * SIZE) / 2);
        this.getPacMan().setY(((ROWS * SIZE) / 2) + SIZE * 3);
    }

    public void contarPuntosTotales() {
        for (int i = 1; i < ROWS; i++) {
            for (int j = 1; j < COLUMNS; j++) {
                if (this.getLaberinto().getMatrizCelda(i, j) == 'p') {
                    this.puntosTotales += 1;
                }
            }
        }
        puntosActuales = puntosTotales;
    }
}
