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
    private BufferedImage imgBackground;
    private Player usr, cpu;
    private Timer timer;
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

    // This method draw the final combat text
    public void finalText(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        // SET THE FONT
        try {
            g2.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(70f));
        }  catch (FontFormatException e) {
            g2.setFont(new Font("Serif", Font.ITALIC, 17));
        } catch (IOException e) {
        System.out.println("NO");
            g2.setFont(new Font("Serif", Font.ITALIC, 17));
        }

        if(usr.getCurrentHP() == 0){// TEXT IF YOU LOSE
            g2.setColor(new Color(129, 2, 2));
            g2.drawString("YOU DIED", 250, 200);
        } else if (cpu.getCurrentHP() == 0) {// TEXT IF YOU WIN
            g2.setColor(new Color(255, 190, 48));
            g2.drawString("YOU DEFEATED", 130, 200);
        }
    }
    //This method checks each player's current state to change the animations for the warriors
    public void changeImg(){
        try {
            //Change user's character to attacking animation
            if (usr.isAttacking()) {
                playersImgs[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlAttack()));
                playersImgs[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlIdle()));
            //Change cpu's character to attacking animation
            } else if (cpu.isAttacking()) {
                playersImgs[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + usr.getWarrior().getUrlIdle()));
                playersImgs[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + cpu.getWarrior().getUrlAttack()));
            //Change user's character to death animation
            }else if (usr.isDead()) {
                playersImgs[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + usr.getWarrior().getUrlDeath()));
                playersImgs[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + cpu.getWarrior().getUrlIdle()));
            //Change cpu's character to death animation
            }else if (cpu.isDead()) {
                playersImgs[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + usr.getWarrior().getUrlIdle()));
                playersImgs[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/" + cpu.getWarrior().getUrlDeath()));
            //Change both players to idle animation
            } else{
                playersImgs[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/"+usr.getWarrior().getUrlIdle()));
                playersImgs[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/"+cpu.getWarrior().getUrlIdle()));
            }
        } catch (IOException e) {
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
                            if(cpu.getWarrior().getName().equals("finalBoss")){
                                actualFrame[i] = new ImageIcon(subimage.getScaledInstance(600, 600, Image.SCALE_SMOOTH));
                            }
                            else{
                                actualFrame[i] = new ImageIcon(subimage.getScaledInstance(800, 800, Image.SCALE_SMOOTH));
                            }
                        }else {
                            actualFrame[i] = new ImageIcon(subimage.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
                        }                    }
                }
                repaint();
            }
        });
        timer.start();
    }

    // This method draw the user items
    public void drawnItems(Graphics g){
        if(usr.getItems().size() > 0){
            int space = 0;
            for(Weapon i : usr.getItems()){
                try {
                    BufferedImage imgItem = ImageIO.read(new File(i.getUrl()));
                    ImageIcon iconItem = new ImageIcon(imgItem.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                    iconItem.paintIcon(this, g, 30 + space, 20);
                    space += 70;
                }catch (IOException e){
                    System.out.println(e);
                }

            }
        }
    }

    // This method draw the stats of both players
    public void drawnStats(Graphics g){
        try {
            //SET COLORS
            Color strength = new Color(220, 101, 4);
            Color defense = new Color(0, 98, 129);
            Color agility = new Color(136, 243, 5);
            Color speed = new Color(152, 0, 231);

            // Set paper image
            BufferedImage paper = ImageIO.read(new File("BatallaDeRazas/src/background/oldPaper.png"));
            ImageIcon paperIcon = new ImageIcon(paper.getScaledInstance(380, 300, Image.SCALE_SMOOTH));

            //PAINT THE PAPERS
            paperIcon.paintIcon(this, g, 890, 20);
            paperIcon.paintIcon(this, g, 890, 340);

            int posX = 990, posY = 90;

            // Iterate for each player
            for(int i=0; i<2; ++i) {
                // Draw the stats bars
                g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                        "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(8f));

                // draw the strength bar
                g.setColor(strength);
                g.drawString("STR", posX - 5, posY - 10);
                g.fillRoundRect(posX, posY, 10, Math.round(10 * players[i].getTotalStrength())/2, 7, 7);

                // draw the defense bar
                g.setColor(defense);
                g.drawString("DEF", posX + 25, posY - 10);
                g.fillRoundRect(posX + 30, posY, 10, Math.round(10 * players[i].getTotalDefense())/2, 7, 7);

                // draw the agility bar
                g.setColor(agility);
                g.drawString("AGI", posX + 55, posY - 10);
                g.fillRoundRect(posX + 60, posY, 10, Math.round(10 * players[i].getTotalAgility())/2, 7, 7);

                // draw the speed bar
                g.setColor(speed);
                g.drawString("SPE", posX + 85, posY - 10);
                g.fillRoundRect(posX + 90, posY, 10, Math.round(10 * players[i].getTotalSpeed())/2, 7, 7);


                // Draw the player name and warrior name
                g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                        "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(12f));
                g.setColor(Color.BLACK);
                g.drawString(players[i].getName(), 1110, posY);
                g.drawString(players[i].getWarrior().getName(), 1110, posY+20);

                // Draw the weapon image
                BufferedImage imgItem = ImageIO.read(new File(players[i].getWeapon().getUrl()));
                ImageIcon iconItem = new ImageIcon(imgItem.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                iconItem.paintIcon(this, g, 1110, posY+ 30);


                posY = 410;
            }

        }catch (IOException e){
            System.out.println(e);
        }catch (FontFormatException e) {
            g.setFont(new Font("Serif", Font.ITALIC, 17));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if you have defeated the boss
        if(cpu.getWarrior().getName().equals("finalBoss") && cpu.getCurrentHP() == 0){
            // Show the final image
            try {
                BufferedImage winBackground = ImageIO.read(new File(
                        "BatallaDeRazas/src/background/win.jpg"));
                ImageIcon winIcon = new ImageIcon(winBackground.getScaledInstance(1280, 680, Image.SCALE_SMOOTH));
                winIcon.paintIcon(this, g, 0, 0);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }else {
            // draw the background
            ImageIcon backgroundIcon = new ImageIcon(imgBackground.getScaledInstance(1280, 680, Image.SCALE_SMOOTH));
            backgroundIcon.paintIcon(this, g, 0, 0);

            drawnHPBars(g);
            finalText(g);

            // set the pos of the players
            int cpuYpos = 200, cpuXpos = 450, userXPosdwarf = 0, userYPosdwarf = 0, cpuXPosdwarf = 0, cpuYPosdwarf = 0;

            if (!cpu.getWarrior().getplayable()) {// Positions for a boss
                cpuXpos = 300;
                cpuYpos = 50;
                if (cpu.getWarrior().getName().equals("finalBoss")) {
                    cpuXpos = 350;
                    cpuYpos = 150;
                }
            }
            if (cpu.getWarrior().getRace().equals("dwarf")) {// Positions for a cpu dwarf
                cpuXPosdwarf = 100;
                cpuYPosdwarf = 130;
            }
            if (usr.getWarrior().getRace().equals("dwarf")) {// Positions for a user dwarf
                userXPosdwarf = 100;
                userYPosdwarf = 150;
            }

            // Draw the characters frame
            if (actualFrame[0] != null && actualFrame[1] != null) {
                actualFrame[0].paintIcon(this, g, -50 + userXPosdwarf, 250 + userYPosdwarf);
                actualFrame[1].paintIcon(this, g, cpuXpos + cpuXPosdwarf, cpuYpos + cpuYPosdwarf);
            }
            drawnStats(g);
            drawnItems(g);
        }
    }
}
