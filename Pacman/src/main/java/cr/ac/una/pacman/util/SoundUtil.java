package cr.ac.una.pacman.util;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Luvara
 */
public class SoundUtil {

    public static void mouseHoverSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/hoverMouse.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    public static void mouseEnterSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/enterSound.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    public static void pressButtonSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/pressButton.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    public static void errorSound() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/error.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    public static void keyTyping() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/typing.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    private static Clip soundSiren;
    public static void pacmanSiren() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/siren_1.wav");

            if (soundSiren != null && soundSiren.isRunning()) {
                soundSiren.stop();
                soundSiren.close();
                return;
            }

            soundSiren = AudioSystem.getClip();
            soundSiren.open(AudioSystem.getAudioInputStream(soundFile));
            soundSiren.loop(Clip.LOOP_CONTINUOUSLY);
            soundSiren.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
        }
    }

    private static Clip soundMunch;
    public static void pacmanMunch() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/munch_1.wav");

            if (soundMunch != null && soundMunch.isRunning()) {
                soundMunch.stop();
                soundMunch.setFramePosition(0);
                soundMunch.close();
                return;
            }

            soundMunch = AudioSystem.getClip();
            soundMunch.open(AudioSystem.getAudioInputStream(soundFile));
            soundMunch.loop(Clip.LOOP_CONTINUOUSLY);
            soundMunch.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }

    public static void pacmanFruit() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/eat_fruit.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
    public static void pacmanMusic() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/game_start.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
    public static void ghostDeath() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/eat_ghost.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
    public static void powerPellet() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/power_pellet.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
    
    public static void pacmanMunchUniq() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/munch_1.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
    
    public static void pacmanDeath() {
        try {
            File soundFile = new File("src/main/resources/cr/ac/una/pacman/resources/audios/death_1.wav");

            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(soundFile));
            sound.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(); // Manejo de errores: imprime la traza de errores
        }
    }
}
