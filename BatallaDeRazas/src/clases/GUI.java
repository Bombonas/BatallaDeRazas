package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel mainPanel, tabsPanel, tabWeapons, tabStage, tabRanking, characterPanel, fightPanel;
    private EventPanel stagePanel, tabCharacters;
    private JButton button1;
    private JLabel label1, labelCharacterPanel1, labelCharacterPanel2;
    private JLabel[] labelStages, labelWeapons, labelCharacters;
    private JTabbedPane tabPane;
    private BufferedImage[] stages, weapons, characters, idleAnim;
    private int currentFrame = 0, numFrames = 5;
    private Timer timer;
    private ActionListener currentListener = null;
    private Player user, cpu;
    private WarriorContainer wc;

    // Player user, Player cpu, WarriorContainer wc
    public GUI() {
        //Define JFrame properties: size, close operation, title, location
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hola");
        setResizable(false);
        setLocationRelativeTo(null);

        //Initialize JPanels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        tabsPanel = new JPanel();
        tabsPanel.setLayout(new BoxLayout(tabsPanel, BoxLayout.Y_AXIS));
        tabWeapons = new JPanel();
        tabStage = new JPanel();
        tabRanking = new JPanel();
        characterPanel = new JPanel();
        characterPanel.setPreferredSize(new Dimension(300, 100));
        stagePanel = new EventPanel();
        stagePanel.setLayout(new BorderLayout());
        fightPanel = new JPanel();
        fightPanel.setBorder(new LineBorder(Color.BLUE, 1));//Border to see empty panel's location, delete later

        //Define image paths for stages, characters and weapons
        try {
            stages = new BufferedImage[3];
            stages[0] = ImageIO.read(new File("BatallaDeRazas/src/background/Summer.jpg"));
            stages[1] = ImageIO.read(new File("BatallaDeRazas/src/background/desert.jpg"));
            stages[2] = ImageIO.read(new File("BatallaDeRazas/src/background/winter.jpg"));
            weapons = new BufferedImage[9];
            weapons[0] = ImageIO.read(new File("BatallaDeRazas/src/weapons/dagger.png"));
            weapons[1] = ImageIO.read(new File("BatallaDeRazas/src/weapons/sword.png"));
            weapons[2] = ImageIO.read(new File("BatallaDeRazas/src/weapons/axe.png"));
            weapons[3] = ImageIO.read(new File("BatallaDeRazas/src/weapons/dualsword.png"));
            weapons[4] = ImageIO.read(new File("BatallaDeRazas/src/weapons/scimitar.png"));
            weapons[5] = ImageIO.read(new File("BatallaDeRazas/src/weapons/bow.png"));
            weapons[6] = ImageIO.read(new File("BatallaDeRazas/src/weapons/katana.png"));
            weapons[7] = ImageIO.read(new File("BatallaDeRazas/src/weapons/stabby.png"));
            weapons[8] = ImageIO.read(new File("BatallaDeRazas/src/weapons/dualaxe.png"));
            characters = new BufferedImage[9];
            characters[0] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[1] = ImageIO.read(new File("BatallaDeRazas/src/characters/human1.png"));
            characters[2] = ImageIO.read(new File("BatallaDeRazas/src/characters/human1.png"));
            characters[3] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[4] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[5] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[6] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[7] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
            characters[8] = ImageIO.read(new File("BatallaDeRazas/src/characters/dwarf1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Initialize Character panel and prepare it for animations
        tabCharacters = new EventPanel();
        tabCharacters.setLayout(new GridLayout(3, 3));
        labelCharacters = new JLabel[9];
        labelCharacters[0] = new JLabel();
        for (int i = 0; i < labelCharacters.length; i++) {
            labelCharacters[i] = new JLabel();
            tabCharacters.add(labelCharacters[i]);
        }
        //Set a timer to update frames for the animations
        timer = new Timer(150, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % numFrames;
                for (int i = 0; i < characters.length; i++) {
                    BufferedImage subimage = characters[i].getSubimage(currentFrame*64, 0, 64, 32);
                    labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                            BufferedImage.TYPE_INT_ARGB)));
                    labelCharacters[i].addMouseListener(tabCharacters);
                }
            }
        });
        timer.start();
        currentFrame = 0;

        //Set default stage
        label1 = new JLabel(new ImageIcon(stages[0].getScaledInstance(620, 590,
                BufferedImage.TYPE_INT_ARGB)));
        stagePanel.add(label1);

        //Label will include selected character
        labelCharacterPanel1 = new JLabel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.X_AXIS));
        characterPanel.add(labelCharacterPanel1);

        //Initialize JTabbedPane with tabs for character, weapon, stage and ranking, as well as size of the tab pane
        tabPane = new JTabbedPane();
        tabPane.setPreferredSize(new Dimension(650, 500));
        tabPane.addTab("Character", tabCharacters);
        tabPane.addTab("Weapons", tabWeapons);
        tabPane.addTab("Stage", tabStage);
        tabPane.addTab("Ranking", tabRanking);
        fightPanel.setPreferredSize(new Dimension(200, 100));

        //Add content to panels
        tabsPanel.add(tabPane);
        tabsPanel.add(characterPanel);
        stagePanel.add(fightPanel, BorderLayout.PAGE_END);
        mainPanel.add(tabsPanel, BorderLayout.WEST);
        mainPanel.add(stagePanel, BorderLayout.CENTER);

        //Fill stage tab with selectable stages
        tabStage.setLayout(new BoxLayout(tabStage, BoxLayout.Y_AXIS));
        labelStages = new JLabel[3];
        for (int i = 0; i < labelStages.length; i++) {
            labelStages[i] = new JLabel(new ImageIcon(stages[i].getScaledInstance(700, 200,
                    BufferedImage.TYPE_INT_ARGB)));
            labelStages[i].addMouseListener(stagePanel);
            tabStage.add(labelStages[i]);
        }

        //Fill weapons tab with selectable weapons
        tabWeapons.setLayout(new GridLayout(3,3));
        labelWeapons = new JLabel[9];
        for (int i = 0; i < labelWeapons.length; i++) {
            labelWeapons[i] = new JLabel(new ImageIcon(weapons[i].getScaledInstance(200, 200,
                    BufferedImage.TYPE_INT_ARGB)));
            tabWeapons.add(labelWeapons[i]);
        }



        //Add main panel to JFrame and set visible
        add(mainPanel);
        setVisible(true);
    }

    class EventPanel extends JPanel implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            //Change to stage 1
            if (e.getSource().equals(labelStages[0])) {
                label1.setIcon(new ImageIcon(stages[0].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            //Change to stage 2
            else if (e.getSource().equals(labelStages[1])) {
                label1.setIcon(new ImageIcon(stages[1].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            //Change to stage 3
            else if (e.getSource().equals(labelStages[2])) {
                label1.setIcon(new ImageIcon(stages[2].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            if (currentListener != null && !e.getSource().equals(labelStages[0])
            && !e.getSource().equals(labelStages[1])
            && !e.getSource().equals(labelStages[2])) {
                timer.removeActionListener(currentListener);
            }
            //Change selected character
            if (e.getSource().equals(labelCharacters[0])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[0].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[1])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[1].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[2])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[2].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[3])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[3].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[4])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[4].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[5])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[5].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[6])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[6].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[7])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[7].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
            else if (e.getSource().equals(labelCharacters[8])) {
                currentListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % numFrames;
                        BufferedImage subimage = characters[8].getSubimage(currentFrame*64, 0, 64, 32);
                        labelCharacterPanel1.setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                };
                timer.addActionListener(currentListener);
            }
        }
        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }
}