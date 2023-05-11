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
    private JPanel mainPanel, tabsPanel, tabStage, tabRanking, characterPanel, fightPanel;
    private EventPanel stagePanel, tabCharacters;
    private EventWeapons tabWeapons;
    private JButton fightButton;
    private JLabel label1, labelCharacterPanel1;
    private JLabel[] labelStages, labelCharacters, weaponLabel;
    private JTabbedPane tabPane;
    private BufferedImage[] stages, characters;
    private int currentFrame = 0, numFrames = 5;
    private Timer timer;
    private ActionListener currentListener = null;
    private Player usr, cpu;
    private WarriorContainer wc;
    private String[] paths;

    public GUI(Player usr, Player cpu, WarriorContainer wc) {

        this.usr = usr;
        this.cpu = cpu;
        this.wc = wc;

        //Define JFrame properties: size, close operation, title, location
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Race Wars");
        setResizable(false);
        setLocationRelativeTo(null);

        //Initialize JPanels
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        tabsPanel = new JPanel();
        tabsPanel.setLayout(new BoxLayout(tabsPanel, BoxLayout.Y_AXIS));
        tabWeapons = new EventWeapons();
        tabWeapons.addMouseListener(tabWeapons);
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
        //Label will include selected character
        labelCharacterPanel1 = new JLabel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.X_AXIS));
        for (int i = 0; i < labelCharacters.length; i++) {
            labelCharacters[i] = new JLabel();
            tabCharacters.add(labelCharacters[i]);
        }
        //Set a timer to update frames for the animations
        timer = new Timer(150, new ActionListener() {
            //ActionListener navigates through the sprite sheet on the X axis to get a new image every 64 pixels
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % numFrames;
                //Navigate through the sprite sheet for all 9 characters
                for (int i = 0; i < characters.length; i++) {
                    BufferedImage subimage = characters[i].getSubimage(currentFrame*64, 0, 64, 32);
                    labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(200,100,
                            BufferedImage.TYPE_INT_ARGB)));
                }
                if (currentListener == null) {
                    BufferedImage subimage2 = characters[0].getSubimage(currentFrame * 64, 0, 64, 32);
                    labelCharacterPanel1.setIcon(new ImageIcon(subimage2.getScaledInstance(200, 100,
                            BufferedImage.TYPE_INT_ARGB)));
                    characterPanel.add(labelCharacterPanel1);
                }
            }
        });
        //Add mouse listener to characters tab and start timer
        tabCharacters.addMouseListener(tabCharacters);
        timer.start();
        currentFrame = 0;

        //Set default stage
        label1 = new JLabel(new ImageIcon(stages[0].getScaledInstance(620, 590,
                BufferedImage.TYPE_INT_ARGB)));
        stagePanel.add(label1);


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

        //Fill weapons tab with selectable weapons from user's character and remove previous ones
        tabWeapons.setLayout(new FlowLayout());
        removeImageWeapons();
        setWarriorWeaponsImages();

        //Initialize fight button and add it to fightPanel
        fightButton = new JButton("Fight!");
        fightPanel.add(fightButton);



        //Add main panel to JFrame and set visible
        add(mainPanel);
        setVisible(true);
    }

    //Remove weapon images from previous character
    public void removeImageWeapons() {
        Component[] previousWeapons = tabWeapons.getComponents();
        for (Component pw : previousWeapons) {
            tabWeapons.remove(pw);
        }
    }
    //Load weapons tab images with character's weapons
    public void setWarriorWeaponsImages() {
        int i = 0;
        weaponLabel = new JLabel[usr.getWarrior().getWeapons().size()];
        paths = new String[usr.getWarrior().getWeapons().size()];
        for (Weapon w: usr.getWarrior().getWeapons()) {
            weaponLabel[i] = new JLabel();
            paths[i] = w.getUrl();
            try {
                File path = new File(w.getUrl());
                BufferedImage weaponImage = ImageIO.read(path);
                weaponLabel[i].setIcon(new ImageIcon(weaponImage.getScaledInstance(200, 200,
                        BufferedImage.TYPE_INT_ARGB)));
                tabWeapons.add(weaponLabel[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class EventPanel extends JPanel implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            Component clickedCharacter = getComponentAt(e.getPoint());
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
            //If multiple characters are selected in the characters tab, remove previous actionListeners to avoid
            //having multiple animations loading
            if (currentListener != null && !e.getSource().equals(labelStages[0])
            && !e.getSource().equals(labelStages[1])
            && !e.getSource().equals(labelStages[2])) {
                timer.removeActionListener(currentListener);
            }
            //Change selected character and animate it
            if (clickedCharacter.equals(labelCharacters[0])) {
                usr.setWarrior(wc.getWarriors().get(0));
                removeImageWeapons();
                setWarriorWeaponsImages();
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
            else if (clickedCharacter.equals(labelCharacters[1])) {
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
            else if (clickedCharacter.equals(labelCharacters[2])) {
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
            else if (clickedCharacter.equals(labelCharacters[3])) {
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
            else if (clickedCharacter.equals(labelCharacters[4])) {
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
            else if (clickedCharacter.equals(labelCharacters[5])) {
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
            else if (clickedCharacter.equals(labelCharacters[6])) {
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
            else if (clickedCharacter.equals(labelCharacters[7])) {
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
            else if (clickedCharacter.equals(labelCharacters[8])) {
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
    //Define JPanel with MouseListener for Weapons tab
    class EventWeapons extends JPanel implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            //clickedWeapon gets the component at the point the mouse is clicked
            Component clickedWeapon = getComponentAt(e.getPoint());
            if (e.getSource().equals(tabWeapons)) {
                //Iterate through weapon labels and compare their paths with every weapon available for the warrior
                for (int i = 0; i < weaponLabel.length; i++) {
                    if (clickedWeapon.equals(weaponLabel[i])) {
                        if (paths[i].equals(usr.getWarrior().getWeapons().get(i).getUrl())) {
                            System.out.println(usr.getWeapon());
                            usr.setWeapon(usr.getWarrior().getWeapons().get(i));
                            System.out.println(usr.getWeapon());
                        }
                    }
                }
            }
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
}