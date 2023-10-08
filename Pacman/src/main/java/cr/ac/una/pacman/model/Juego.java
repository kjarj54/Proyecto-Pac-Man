/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import static io.github.palexdev.materialfx.utils.RandomUtils.random;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ANTHONY
 */
public class Juego {

    private PacMan pacMan;
    private List<Fantasma> fantasmas;
    private Laberinto laberinto;
    private double tiempo;
//    private List<String> trofeos;
//    private Map<String, Integer> estadisticas;
    private boolean hiloAnimacionInicialFantasmas = false;
    private boolean hiloComeFantasmasConsecutivos = false;
    private boolean hiloPowerPellets = false;
    private boolean hiloEncierro = false;
    private boolean hiloSuperVelocidad = false;
    public int fantasmasConsecutivos = 0;

    public Juego(PacMan pacMan, List<Fantasma> fantasmas, int rows, int columns) {
//    public Juego(PacMan pacMan, List<Fantasma> fantasmas, Laberinto laberinto) {
        this.pacMan = pacMan;
        this.fantasmas = fantasmas;
        this.laberinto = new Laberinto(rows, columns);
        this.tiempo = 0.0;
//        this.trofeos = new ArrayList<>();
//        this.estadisticas = new HashMap<>();
//        estadisticas.put("puntosTotales", 0);
//        estadisticas.put("vidasPerdidas", 0);
//        estadisticas.put("fantasmasComidos", 0);
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
    
    public void posIniFantasmas() {
        this.getFantasmas().get(0).setX((COLUMNS * SIZE) / 2);
        this.getFantasmas().get(0).setY(((ROWS * SIZE) / 2) - SIZE * 2);
        
        this.getFantasmas().get(1).setX((COLUMNS * SIZE) / 2 + SIZE);
        this.getFantasmas().get(1).setY((ROWS * SIZE) / 2);
        
        this.getFantasmas().get(2).setX((COLUMNS * SIZE) / 2);
        this.getFantasmas().get(2).setY((COLUMNS * SIZE) / 2 - SIZE);
        
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
//        int vecino = 0;
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
        
        nuevaPosIniPacman();
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
            cambiarVelocidadFantasma(-0.2, fant);
            fant.setVulnerable(true);
            nuevaPosEscape(fant);
        }
        nuevaPosAleatoria(this.getFantasmas().get(3));
    }

    public void fantasmasNoVulnerables() {
        for (Fantasma fant : this.getFantasmas()) {
            cambiarVelocidadFantasma(0.2, fant);
            fant.setVulnerable(false);
        }
    }

    public void cambiarVelocidadPacman(double velocidad) {
        getPacMan().setVelocidad(getPacMan().getVelocidad() + velocidad);
    }

    public void cambiarVelocidadFantasma(double velocidad, Fantasma fant) {
        fant.setVelocidad(fant.getVelocidad() + velocidad);
    }

    public void animacionInicialFantasmas() { // Implementar la animacion inicial de los fantasma en la jaula
        if (!hiloAnimacionInicialFantasmas) {
            hiloAnimacionInicialFantasmas = true;
            Thread hilo = new Thread(() -> {
                System.out.println("Hola Animacion Inicial Fantasmas");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Adiós Animacion Inicial Fantasmas");
                hiloAnimacionInicialFantasmas = false; // Marcar que el hilo ha terminado
            });
            hilo.start();
        }
    }
    
    public void comeFantasmasConsecutivos() {
        if (!hiloComeFantasmasConsecutivos) {
            fantasmasConsecutivos = 0;
            hiloComeFantasmasConsecutivos = true;
            Thread hilo = new Thread(() -> {
                System.out.println("Hola Consecutivos " + fantasmasConsecutivos);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Adiós Consecutivos " + fantasmasConsecutivos);
                hiloComeFantasmasConsecutivos = false; // Marcar que el hilo ha terminado
            });
            hilo.start();
        }
    }

    public void powerPellet() {
        if (!hiloPowerPellets) {
            hiloPowerPellets = true;
            Thread hilo = new Thread(() -> {
                fantasmasVulnerables();
                cambiarVelocidadPacman(0.21);
                System.out.println("Hola Poder");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fantasmasNoVulnerables();
                cambiarVelocidadPacman(-0.21);
                System.out.println("Adiós Poder");
                hiloPowerPellets = false; // Marcar que el hilo ha terminado
            });
            hilo.start();
        }
    }
    
    public void encierro() { // Implementar el codigo del encierro de los fantasmas
        if (!hiloEncierro) {
            hiloEncierro = true;
            Thread hilo = new Thread(() -> {

                System.out.println("Hola Encierro");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Adiós Encierro");
                hiloEncierro = false; // Marcar que el hilo ha terminado
            });
            hilo.start();
        }
    }
    
    public void superVelocidad() {
        if (!hiloSuperVelocidad && this.getPacMan().superVelocidad) {
            hiloSuperVelocidad = true;
            Thread hilo = new Thread(() -> {
                cambiarVelocidadPacman(0.1);
                System.out.println("Hola Super Velocidad");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cambiarVelocidadPacman(-0.1);
                this.getPacMan().superVelocidad = false;
                System.out.println("Adiós Super Velocidad");
                hiloSuperVelocidad = false; // Marcar que el hilo ha terminado
            });
            hilo.start();
        }
    }

    public void nuevaPosAleatoria(Fantasma fantasma) {
        boolean Listo = false;
        while (!Listo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            char celda = this.getLaberinto().getMatrizCelda(randomY, randomX);

            if (celda == ' ' || celda == 'p') {
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
    
    public void nuevaPosIniPacman() {
        boolean pacmanListo = false;
        while (!pacmanListo) { // Coloca a pacman aleatoriamente en el mapa
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (laberinto.getMatrizCelda(randomY, randomX) == 'p') {
                this.getPacMan().setX(randomX * SIZE);
                this.getPacMan().setY(randomY * SIZE);
                pacmanListo = true;
            }
        }
    }
}
