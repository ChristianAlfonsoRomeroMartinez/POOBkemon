package presentation;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class ReproductorMusica {
    private Clip clip;
    private boolean estaReproduciendo = true;
    private FloatControl gainControl; // Control de volumen

    public ReproductorMusica(String rutaArchivo) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Obtener el control de volumen (si está disponible)
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(50); // Volumen al 50% por defecto
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir música: " + e.getMessage());
        }
    }

    /**
     * Ajusta el volumen (porcentaje de 0 a 100).
     * @param percent : 0 = mudo, 100 = máximo.
     */
    public void setVolume(int percent) {
        if (gainControl == null) return; // Si no hay control de volumen, salir

        // Convertir porcentaje a decibeles (rango típico: -80dB a 6dB)
        float min = gainControl.getMinimum();
        float max = gainControl.getMaximum();
        float range = max - min;
        float gain = (range * percent / 100.0f) + min;

        gainControl.setValue(gain);
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            estaReproduciendo = false;
        }
    }

    public void reproducir() {
        if (clip != null) {
            clip.start();
            estaReproduciendo = true;
        }
    }

    public boolean estaReproduciendo() {
        return estaReproduciendo;
    }
}
