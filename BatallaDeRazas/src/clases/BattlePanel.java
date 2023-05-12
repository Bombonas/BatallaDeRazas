package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattlePanel extends JPanel {
    private BufferedImage imgBackground, player, enemy;

    public BattlePanel(BufferedImage imgBackground){
        this.imgBackground = imgBackground;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBackground, 0, 0, this);
    }
}
