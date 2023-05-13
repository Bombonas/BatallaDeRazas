package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattlePanel extends JPanel {
    private BufferedImage player, enemy;
    private Image imgBackground;

    public BattlePanel(BufferedImage imgBackground){
        this.imgBackground = imgBackground.getScaledInstance(1280, 680, BufferedImage.SCALE_DEFAULT);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBackground, 0, 0, this);
    }
}
