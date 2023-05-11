package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattlePanel extends JPanel {
    private BufferedImage background, player, enemy;

    public BattlePanel(String pathBackground){
        try {
            background = ImageIO.read(new File(pathBackground));
        }catch (IOException e) {
            System.out.println("Imagen no encontrada");
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}
