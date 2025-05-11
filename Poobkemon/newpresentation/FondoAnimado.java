package presentation;

import javax.swing.*;
import java.awt.*;

class FondoAnimado extends JPanel {
        private final ImageIcon gif;
        private Image imagenFija; // Para la segunda pantalla
    
        public FondoAnimado(String rutaGIF) {
            gif = new ImageIcon(rutaGIF);
        }
    
        // Método para cambiar a imagen fija
        public void setImagenFija(String rutaImagen) {
            this.imagenFija = new ImageIcon(rutaImagen).getImage();
            repaint();
        }
    
        // Método para volver al GIF animado
        public void resetToGif() {
            this.imagenFija = null;
            repaint();
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFija != null) {
                // Dibuja la imagen fija
                g.drawImage(imagenFija, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Dibuja el GIF animado
                g.drawImage(gif.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
}

   