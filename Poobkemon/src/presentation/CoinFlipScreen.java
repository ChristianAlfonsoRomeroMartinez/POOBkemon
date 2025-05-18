package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class CoinFlipScreen extends JPanel {
    private JFrame parentFrame;
    private Consumer<String> onResult; // Recibe el resultado ("cara" o "sello")
    private String result;

    public CoinFlipScreen(JFrame parentFrame, Consumer<String> onResult) {
        this.parentFrame = parentFrame;
        this.onResult = onResult;
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel introGif = new JLabel(new ImageIcon("Poobkemon/mult/intro.gif"));
        introGif.setHorizontalAlignment(SwingConstants.CENTER);
        add(introGif, BorderLayout.CENTER);

        Timer introTimer = new Timer(2000, e -> showCoinAnimation());
        introTimer.setRepeats(false);
        introTimer.start();
    }

    private void showCoinAnimation() {
        removeAll();
        JLabel coinGif = new JLabel(new ImageIcon("Poobkemon/mult/coin_flip.gif"));
        coinGif.setHorizontalAlignment(SwingConstants.CENTER);
        add(coinGif, BorderLayout.CENTER);
        revalidate();
        repaint();

        Timer coinTimer = new Timer(2000, e -> showResult());
        coinTimer.setRepeats(false);
        coinTimer.start();
    }

    private void showResult() {
        removeAll();
        result = new Random().nextBoolean() ? "cara" : "sello";
        String imagePath = result.equals("cara") ? "Poobkemon/mult/cara.png" : "Poobkemon/mult/sello.png";
        String mensaje = result.equals("cara") ? "¡Cayó CARA!" : "¡Cayó SELLO!";

        JLabel resultLabel = new JLabel(new ImageIcon(imagePath));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel textLabel = new JLabel(mensaje, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 28));
        textLabel.setForeground(Color.YELLOW);

        add(resultLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);

        revalidate();
        repaint();

        Timer continueTimer = new Timer(1500, e -> {
            if (onResult != null) onResult.accept(result);
        });
        continueTimer.setRepeats(false);
        continueTimer.start();
    }
}