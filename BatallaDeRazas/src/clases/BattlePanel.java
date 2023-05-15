package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattlePanel extends JPanel {
    private BufferedImage imgBackground, player, enemy;
    private Player usr, cpu;
    private Timer timer;
    private int animNum;
    private BufferedImage[] playersImgs;
    private ImageIcon[] actualFrame;
    private Player[] players;
    private int frameCount, currentFrame, frameSize;
    private JPanel panel;

    public BattlePanel(BufferedImage imgBackground, Player usr, Player cpu){
        this.imgBackground = imgBackground;
        this.usr = usr;
        this.cpu = cpu;
        playersImgs = new BufferedImage[2];
        players = new Player[2];
        actualFrame = new ImageIcon[2];
        players[0] = usr;
        players[1] = cpu;
        panel = this;
        animNum = 0;

        charactersAnimations();
        repaint();
    }

    public void drawnHPBars(Graphics g){
        Color red = new Color(171, 0, 0);
        Color gray = new Color(76, 79, 89, 150);
        g.setColor(red);
        float hpUsr = (float)(usr.getCurrentHP())/ usr.getTotalHP();
        float hpCpu = (float)(cpu.getCurrentHP())/ cpu.getTotalHP();
        int cpuY = 300;

        // Set diferent height for a boss
        if(!cpu.getWarrior().getplayable()) cpuY = 200;

        //Player and CPU missing HP Bar
        g.setColor(gray);
        g.fillRoundRect(150, 350, 150 , 10, 7, 7);// PLAYER
        g.fillRoundRect(620, cpuY, 150 , 10, 7, 7);// CPU

        //Player and CPU actual HP Bar
        g.setColor(red);
        g.fillRoundRect(150, 350, Math.round(150 * hpUsr) , 10, 7, 7);// PLAYER
        g.fillRoundRect(620, cpuY, Math.round(150 * hpCpu), 10, 7, 7);// CPU
    }

    public void finalText(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        try {
            g2.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(70f));
        }  catch (FontFormatException e) {
            g2.setFont(new Font("Serif", Font.ITALIC, 17));
        } catch (IOException e) {
        System.out.println("NO");
            g2.setFont(new Font("Serif", Font.ITALIC, 17));
        }

        if(usr.getCurrentHP() == 0){
            g2.setColor(new Color(129, 2, 2));
            g2.drawString("YOU DIED", 450, 320);
        } else if (cpu.getCurrentHP() == 0) {
            g2.setColor(new Color(255, 190, 48));
            g2.drawString("YOU DEFEATED", 330, 320);
        }
    }

    public void changeImg(){
        try {
            switch (animNum) {
                case 0:// 2 idle
                    playersImgs[0] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlIdle()));
                    playersImgs[1] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlIdle()));
                    break;
                case 1:// user attacks
                    playersImgs[0] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlAttack()));
                    playersImgs[1] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlIdle()));
                    break;
                case 2:// cpu attacks
                    playersImgs[0] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlIdle()));
                    playersImgs[1] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlAttack()));
                    break;
                case 3:// user death
                    playersImgs[0] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlDeath()));
                    playersImgs[1] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlIdle()));
                    break;
                case 4:// cpu death
                    playersImgs[0] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlIdle()));
                    playersImgs[1] = ImageIO.read(new File( "BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlDeath()));
                    break;
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void charactersAnimations(){
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Select the animations
                changeImg();
                for (int i = 0; i < 2; ++i) {
                    //Get number of frames for each character's animation by dividing image's width with image's height
                    frameCount = playersImgs[i].getWidth() / playersImgs[i].getHeight();
                    //Get width of each frame by dividing width of the image by number of frames
                    frameSize = playersImgs[i].getWidth() / frameCount;
                    currentFrame = (currentFrame + 1) % frameCount;
                    BufferedImage subimage = playersImgs[i].getSubimage(currentFrame * frameSize, 0, frameSize, frameSize);
                    if(i==1){// FLIP CPU IMAGE
                        AffineTransform flipImage = AffineTransform.getScaleInstance(-1, 1);
                        flipImage.translate(-subimage.getWidth(), 0);
                        AffineTransformOp op = new AffineTransformOp(flipImage, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                        subimage = op.filter(subimage, null);
                    }
                    // RESIZE THE IMAGES
                    if (players[i].getWarrior().getRace().equals("dwarf")) {
                        actualFrame[i] = new ImageIcon(subimage.getScaledInstance(350, 200, Image.SCALE_SMOOTH));
                    } else {
                        if(!cpu.getWarrior().getplayable() && i==1){
                            actualFrame[i] = new ImageIcon(subimage.getScaledInstance(800, 800, Image.SCALE_SMOOTH));
                        }else {
                            actualFrame[i] = new ImageIcon(subimage.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
                        }
                    }
                }
                animNum = 0;// Set to the idle animation
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundIcon = new ImageIcon(imgBackground.getScaledInstance(1280, 680, Image.SCALE_SMOOTH));
        backgroundIcon.paintIcon(this, g, 0, 0);
        drawnHPBars(g);
        finalText(g);
        int cpuYpos = 200, cpuXpos = 450, userXPosdwarf = 0, userYPosdwarf = 0, cpuXPosdwarf = 0, cpuYPosdwarf = 0;
        if(!cpu.getWarrior().getplayable()){
            cpuXpos = 300;
            cpuYpos = 50;
        }
        if(cpu.getWarrior().getRace().equals("dwarf") ){
            cpuXPosdwarf = 100;
            cpuYPosdwarf = 130;
        }
        if(usr.getWarrior().getRace().equals("dwarf")){
            userXPosdwarf = 100;
            userYPosdwarf = 150;
        }
        //charactersAnimations();
        if(actualFrame[0] != null && actualFrame[1] != null ) {
            actualFrame[0].paintIcon(this, g, -50+ userXPosdwarf, 250 + userYPosdwarf);
            actualFrame[1].paintIcon(this, g, cpuXpos + cpuXPosdwarf, cpuYpos + cpuYPosdwarf);
        }
    }
}
