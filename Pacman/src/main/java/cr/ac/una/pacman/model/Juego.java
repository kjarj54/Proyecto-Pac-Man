package cr.ac.una.pacman.model;

import static cr.ac.una.pacman.controller.JuegoViewController.COLUMNS;
import static cr.ac.una.pacman.controller.JuegoViewController.ROWS;
import static cr.ac.una.pacman.controller.JuegoViewController.SIZE;
import cr.ac.una.pacman.util.Cronometro;
import cr.ac.una.pacman.util.SoundUtil;
import static io.github.palexdev.materialfx.utils.RandomUtils.random;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 * @author ANTHONY
 */
public class Juego {

    private PacMan pacMan;
    private List<Fantasma> fantasmas;
    private Laberinto laberinto;
    private int tiempo;
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
    public Cronometro cronometro;

    public Juego(PacMan pacMan, List<Fantasma> fantasmas, int rows, int columns, int nivel, Laberinto laberinto) {
        this.pacMan = pacMan;
        this.fantasmas = fantasmas;
        this.laberinto = new Laberinto(rows, columns, 0, "");
        this.laberinto.actualizar(laberinto);
        this.tiempo = 0;
        this.nivel = nivel;

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

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

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
        if (!this.getFantasmas().get(3).isEncerrado()) {
            nuevaPosAleatoria(this.getFantasmas().get(3));
        }
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
                        System.out.println("Sale fantama rosa");
                        estado++;
                    }
                    case 1 -> {
                        getFantasmas().get(3).setEncerrado(false);
                        System.out.println("sale fantasma naranja");
                        estado++;
                    }
                    case 2 -> {
                        getFantasmas().get(2).setEncerrado(false);
                        System.out.println("Sale fantasma cian");
                        System.out.println("Adiós Animacion Inicial Fantasmas");
                        hiloAnimacionInicialFantasmas = false;
                        estado = 0;
                        animacionInicialFantasmasHilo.stop();
                    }
                }
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
        SoundUtil.powerPellet();
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

    public void superVelocidad(Partida partida) {
        if (!hiloSuperVelocidad && this.getPacMan().superVelocidad) {
            hiloSuperVelocidad = true;

            superVelocidadHilo = new Timeline();
            superVelocidadHilo.setCycleCount(1); // Ejecutar una vez

            KeyFrame inicioKeyFrame = new KeyFrame(Duration.ZERO, (event) -> {
                cambiarVelocidadPacman(0.1);
                this.multiplicadorPuntaje = 2;
                Trofeo trofeo = partida.obtenerTrofeo("Flash");
                if (!trofeo.isDesbloqueado()) {
                    trofeo.setCont(1);
                    if (trofeo.getCont() >= 5) {
                        trofeo.setDesbloqueado(true);
                    }
                    partida.actualizarTrofeo("Flash", trofeo);
                }
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

    public void matarHilos() {
        if (hiloAnimacionInicialFantasmas) {
            animacionInicialFantasmasHilo.stop();
        }
        if (hiloComeFantasmasConsecutivos) {
            comeFantasmasConsecutivosHilo.stop();
        }
        if (hiloPowerPellets) {
            powerPelletHilo.stop();
        }
        if (hiloEncierro) {
            encierroHilo.stop();
        }
        if (hiloSuperVelocidad) {
            superVelocidadHilo.stop();
        }
    }

    public void pausa() {
        cronometro.pauseCronometro();
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
        cronometro.startCronometro();
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
    
    public void iniciarContador(Label lnCronometro) {
        cronometro = new Cronometro(lnCronometro);
        cronometro.startCronometro();
    }
}
