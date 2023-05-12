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
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel mainPanel, tabsPanel, tabStage, tabRanking, characterPanel, fightPanel;
    private EventPanel stagePanel, tabCharacters, tabWeapons;
    private JButton fightButton;
    private JLabel label1, labelCharacterPanel, labelSelectedWeapon, labelWeaponBackground;
    private JLabel[] labelStages, labelCharacters, weaponLabel;
    private JTabbedPane tabPane;
    private BufferedImage[] stages, characters;
    private BufferedImage selectedBackground;
    private Timer timer;
    private Player usr, cpu;
    private WarriorContainer wc;
    private ArrayList<CharacterAnimationDetails> characterAnim;
    private String[] paths;
    private Font pixelFont;

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
        tabWeapons = new EventPanel() {
            //Draw background for weapons tab
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage weaponsBackground = ImageIO.read(new File(
                            "BatallaDeRazas/src/background/store.png"));
                    Image scaledBackground = weaponsBackground.getScaledInstance(this.getWidth(),
                            this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    g.drawImage(scaledBackground, 0, 0, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        tabWeapons.addMouseListener(tabWeapons);
        tabStage = new JPanel();
        tabRanking = new JPanel();
        characterPanel = new JPanel();
        characterPanel.setPreferredSize(new Dimension(300, 100));
        stagePanel = new EventPanel();
        stagePanel.setLayout(new BorderLayout());
        fightPanel = new JPanel();

        //Define image paths for stages and characters
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(32f);
            stages = new BufferedImage[3];
            stages[0] = ImageIO.read(new File("BatallaDeRazas/src/background/Summer.jpg"));
            stages[1] = ImageIO.read(new File("BatallaDeRazas/src/background/desert.jpg"));
            stages[2] = ImageIO.read(new File("BatallaDeRazas/src/background/winter.jpg"));
            selectedBackground = stages[0];
            int i = 0;
            characters = new BufferedImage[wc.getWarriors().size()];
            for (Warrior w: wc.getWarriors()) {
                characters[i] = ImageIO.read(new File("BatallaDeRazas/src/characters/"+w.getUrlIdle()));
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
        //Initialize arrayList to give frameCount value, X width and Y height values to animate characters according
        //to their needs
        characterAnim = new ArrayList<CharacterAnimationDetails>();
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(0).getName(), characters[0], 4,
                200, 190));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(1).getName(), characters[1], 10,
                162, 150));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(2).getName(), characters[2], 10,
                140, 120));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(3).getName(), characters[3], 8,
                150, 140));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(4).getName(), characters[4], 10,
                100, 100));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(5).getName(), characters[5], 10,
                126, 116));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(6).getName(), characters[6], 5,
                64, 32));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(7).getName(), characters[7], 5,
                64, 32));
        characterAnim.add(new CharacterAnimationDetails(wc.getWarriors().get(8).getName(), characters[8], 5,
                64, 32));

        //Initialize Character panel and prepare it for animations
        tabCharacters = new EventPanel() {
            //Draw background image and string for selecting character
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                try {
                    BufferedImage characterBackground = ImageIO.read(new File(
                            "BatallaDeRazas/src/background/characterSelection.jpg"));
                    Image scaledBackground = characterBackground.getScaledInstance(this.getWidth(), this.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    String text = "SELECT YOUR CHARACTER";
                    g2d.setFont(pixelFont);
                    g.drawImage(scaledBackground,0, 0, null);
                    g2d.drawString(text, 100, 40);
                    g2d.setColor(new Color(141, 148, 148));
                    g2d.drawString(text, 102, 42);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        tabCharacters.setLayout(new GridLayout(3, 3));
        labelCharacters = new JLabel[9];
        //Label will include selected character
        labelCharacterPanel = new JLabel();
        labelSelectedWeapon = new JLabel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.X_AXIS));
        characterPanel.add(labelCharacterPanel);
        characterPanel.add(labelSelectedWeapon);
        //Fill characters panel with empty labels that will be replaced with animated characters images
        for (int i = 0; i < labelCharacters.length; i++) {
            labelCharacters[i] = new JLabel();
            tabCharacters.add(labelCharacters[i]);
        }
        //Set a timer to update frames for the animations
        timer = new Timer(150, new ActionListener() {
            //ActionListener calls to the updateFrame method from the CharacterAnimationDetails class
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                for (CharacterAnimationDetails ch: characterAnim) {
                    BufferedImage subimage = ch.updateFrame();
                    //Set the default selected character by getting player's current warrior
                    if (ch.getName().equals(usr.getWarrior().getName())) {
                        if (i > 5) {
                            //Lower height value for dwarf characters
                            labelCharacterPanel.setIcon(new ImageIcon(subimage.getScaledInstance(250, 100,
                                    BufferedImage.TYPE_INT_ARGB)));
                        }else {
                            labelCharacterPanel.setIcon(new ImageIcon(subimage.getScaledInstance(250, 250,
                                    BufferedImage.TYPE_INT_ARGB)));
                        }
                    }
                    //Fill character tab with every available character
                    if (i <= 5) {
                        labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(250, 250,
                                BufferedImage.TYPE_INT_ARGB)));
                    }else{
                        //Lowe height value for dwarf characters
                        labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(250, 100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }
                    i++;
                }
            }
        });
        //Add mouse listener to characters tab and start timer
        tabCharacters.addMouseListener(tabCharacters);
        timer.start();

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

        //Fill weapons tab with selectable weapons from user's warrior
        tabWeapons.setLayout(new FlowLayout());
        setWarriorWeaponsImages();
        setSelectedWeaponImage();
        //Initialize fight button and add it to fightPanel
        fightButton = new JButton("Fight!");
        fightPanel.add(fightButton);
        fightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //Condition to check if player has selected a weapon
                    if (usr.getWeapon() == null) {
                        //If the player hasn't selected a weapon, throw exception
                        throw new NoWeaponSelected();
                    }else{
                        //GUI.super.dispose();
                        new BattleGUI(usr, cpu, wc, selectedBackground);
                    }
                } catch (NoWeaponSelected ex) {
                    //Exception shows an error window
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                } {

                }
            }
        });


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
                weaponLabel[i].setIcon(new ImageIcon(weaponImage.getScaledInstance(120, 120,
                        BufferedImage.TYPE_INT_ARGB)));
                tabWeapons.add(weaponLabel[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setSelectedWeaponImage() {
        BufferedImage selectedWeapon;
        if (usr.getWeapon() == null) {
            try {
                selectedWeapon = ImageIO.read(new File("BatallaDeRazas/src/weapons/noweapon.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                selectedWeapon = ImageIO.read(new File(usr.getWeapon().getUrl()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        labelSelectedWeapon.setIcon(new ImageIcon(selectedWeapon.getScaledInstance(70, 70,
                BufferedImage.TYPE_INT_ARGB)));
    }
    //Method to return true value if the player's current warrior can use the player's current weapon
    public boolean checkAvailableWeapons() {
        if (usr.getWeapon()!= null) {
            for (Weapon w: usr.getWarrior().getWeapons()) {
                if (usr.getWeapon().equals(w)){
                    return true;
                }
            }
        }
        return false;
    }
    //Internal JPanel implementation with MouseListener events for selected stages, weapons and characters
    class EventPanel extends JPanel implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            Component clickedComponent = getComponentAt(e.getPoint());
            //Change to stage 1
            if (e.getSource().equals(labelStages[0])) {
                selectedBackground = stages[0];
                label1.setIcon(new ImageIcon(stages[0].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            //Change to stage 2
            else if (e.getSource().equals(labelStages[1])) {
                selectedBackground = stages[1];
                label1.setIcon(new ImageIcon(stages[1].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            //Change to stage 3
            else if (e.getSource().equals(labelStages[2])) {
                selectedBackground = stages[2];
                label1.setIcon(new ImageIcon(stages[2].getScaledInstance(620, 590,
                        BufferedImage.TYPE_INT_ARGB)));
            }
            //Remove weapon images from previous character and set the images available for the current
            //selected character
            for (int i = 0; i < 9; i++) {
                if (clickedComponent.equals(labelCharacters[i])) {
                    usr.setWarrior(wc.getWarriors().get(i));
                    removeImageWeapons();
                    setWarriorWeaponsImages();
                    //Condition to reset weapon to null if new selected character can't use previous weapon
                    if (!checkAvailableWeapons()) {
                        usr.setWeapon(null);
                        setSelectedWeaponImage();
                    }
                    break;
                }
            }
            if (e.getSource().equals(tabWeapons)) {
                //Iterate through weapon labels and compare their paths with every weapon available for the warrior
                for (int i = 0; i < weaponLabel.length; i++) {
                    if (clickedComponent.equals(weaponLabel[i])) {
                        //Change player's weapon to the selected one
                        if (paths[i].equals(usr.getWarrior().getWeapons().get(i).getUrl())) {
                            System.out.println(usr.getWeapon());
                            usr.setWeapon(usr.getWarrior().getWeapons().get(i));
                            System.out.println(usr.getWeapon());
                            setSelectedWeaponImage();
                            break;
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
class NoWeaponSelected extends Exception {

    public NoWeaponSelected() {
        super("No weapon selected");
    }
}