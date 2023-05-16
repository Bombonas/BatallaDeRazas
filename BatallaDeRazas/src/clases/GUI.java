package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.UID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel mainPanel, tabsPanel, tabRanking, characterPanel, fightPanel;
    private EventPanel stagePanel, tabCharacters, tabWeapons, tabStage;
    private JButton fightButton, fleeButton;
    private JLabel label1, labelCharacterPanel, labelSelectedWeapon, labelCPUwarrior, labelCPUWeapon;
    private JLabel[] labelStages, weaponLabel;
    private CharacterLabel[] labelCharacters;
    private JTabbedPane tabPane;
    private BufferedImage[] stages, characters;
    private BufferedImage selectedBackground, fightButtonImage, fleeButtonImage;
    private Timer timer;
    private Player usr, cpu;
    private WarriorContainer wc;
    private String[] paths;
    private Font pixelFont, rankingFont, rankingInfoFont;
    private String[][] labelMatrix;
    private int currentFrame = 0, frameCount, frameSize;

    public GUI(Player usr, Player cpu, WarriorContainer wc) {

        this.usr = usr;
        this.cpu = cpu;
        this.wc = wc;

        //Define JFrame properties: size, close operation, title, location
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setUndecorated(true);
        setTitle("Race Wars");
        setResizable(false);
        setLocationRelativeTo(null);

        //Initialize JPanels
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                try {
                    BufferedImage bg = ImageIO.read(new File("BatallaDeRazas/src/background/frameBackground.png"));
                    Image scaledBg = bg.getScaledInstance(1280,720,BufferedImage.TYPE_INT_ARGB);
                    g2d.drawImage(scaledBg, 0, 0, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        tabsPanel = new JPanel();
        tabsPanel.setOpaque(false);
        tabsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
        tabsPanel.setPreferredSize(new Dimension(650, 500));
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
        tabStage = new EventPanel();
        tabRanking = new JPanel();
        characterPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                try {
                    BufferedImage characterPanelBackground = ImageIO.read(new File(
                            "BatallaDeRazas/src/background/charactersSelectedBackground.jpg"));
                    Image scaledBackground = characterPanelBackground.getScaledInstance(this.getWidth(),
                            this.getHeight(),BufferedImage.TYPE_INT_ARGB);
                    g2d.drawImage(scaledBackground, 0, 0, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                g2d.setColor(new Color(185, 61, 61));
                String text = "YOU";
                String text2 = "CPU";
                g2d.setFont(pixelFont);
                g2d.drawString(text, 80, 70);
                g2d.drawString(text2, 480, 70);
            }
        };
        characterPanel.setPreferredSize(new Dimension(650, 100));
        stagePanel = new EventPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                try {
                    BufferedImage frameStage = ImageIO.read(new File("BatallaDeRazas/src/background/stageFrame.png"));
                    Image scaledFrame = frameStage.getScaledInstance(this.getWidth(), this.getHeight()-60,
                            BufferedImage.TYPE_INT_ARGB);
                    g2d.drawImage(scaledFrame, 3, -15, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        stagePanel.setOpaque(false);
        stagePanel.setLayout(new BorderLayout());
        fightPanel = new JPanel();
        fightPanel.setLayout(new BoxLayout(fightPanel, BoxLayout.X_AXIS));
        fightPanel.setOpaque(false);

        //Define image paths for stages and characters
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(32f);
            rankingFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(16f);
            rankingInfoFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(11.75f);
            fightButtonImage = ImageIO.read(new File("BatallaDeRazas/src/button/fight.png"));
            fleeButtonImage = ImageIO.read(new File("BatallaDeRazas/src/button/flee.png"));
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
        labelCharacters = new CharacterLabel[9];
        //Label will include selected character
        labelCharacterPanel = new JLabel();
        labelCPUwarrior = new JLabel();
        labelSelectedWeapon = new JLabel();
        labelCPUWeapon = new JLabel();
        characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.LINE_AXIS));
        characterPanel.add(labelSelectedWeapon);
        characterPanel.add(labelCharacterPanel);
        characterPanel.add(labelCPUwarrior);
        characterPanel.add(labelCPUWeapon);
        Painter<JComponent> woodenTab = new Painter<JComponent>() {
            @Override
            public void paint(Graphics2D g, JComponent object, int w, int h) {
                Color lightBrown = new Color(215, 187, 131);
                Color darkBrown = new Color(131, 90, 29);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create wood grain texture
                int grainSize = 10;
                BufferedImage texture = new BufferedImage(grainSize, grainSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D textureGraphics = texture.createGraphics();
                textureGraphics.setColor(lightBrown);
                textureGraphics.fillRect(0, 0, grainSize, grainSize);
                textureGraphics.setColor(darkBrown);
                textureGraphics.drawLine(0, grainSize / 2, grainSize, grainSize / 2);
                textureGraphics.dispose();

                // Paint wooden background
                TexturePaint texturePaint = new TexturePaint(texture, new Rectangle(0, 0, grainSize, grainSize));
                g.setPaint(texturePaint);
                g.fillRoundRect(2, 2, w - 4, h - 4, 8, 8);

                // Draw wooden border
                g.setColor(darkBrown);
                g.setStroke(new BasicStroke(2));
                g.drawRoundRect(2, 2, w - 4, h - 4, 8, 8);
            }
        };

        //Set every tab's state to the wooden style Painter
        UIManager.put("TabbedPane:TabbedPaneTab[Enabled].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[Enabled+MouseOver].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[Focused+Pressed+Selected].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[Focused+Selected].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[Selected].backgroundPainter", woodenTab);
        UIManager.put("TabbedPane:TabbedPaneTab[MouseOver+Selected].backgroundPainter", woodenTab);

        //UIManager.put("info", pixelFont);
        UIManager.put("ToolTip.font", pixelFont.deriveFont(18f));
        //Fill characters panel with empty labels that will be replaced with animated characters images
        for (int i = 0; i < labelCharacters.length; i++) {
            labelCharacters[i] = new CharacterLabel();
            tabCharacters.add(labelCharacters[i]);
        }
        //Set a timer to update frames for the animations

        timer = new Timer(150, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < characters.length; i++) {
                    //Get number of frames for each character's animation by dividing image's width with image's height
                    frameCount = characters[i].getWidth()/characters[i].getHeight();
                    //Get width of each frame by dividing width of the image by number of frames
                    frameSize = characters[i].getWidth()/frameCount;
                    currentFrame = (currentFrame + 1) % frameCount;
                    BufferedImage subimage = characters[i].getSubimage(currentFrame*frameSize, 0, frameSize, frameSize);
                    //Set selected character's animation in the selected character panel
                    if (wc.getWarriors().get(i).getName().equals(usr.getWarrior().getName())) {
                        //Different values for dwarfs
                        if (i > 5) {
                            //Lower height value for dwarf characters
                            labelCharacterPanel.setIcon(new ImageIcon(subimage.getScaledInstance(250, 100,
                                    Image.SCALE_SMOOTH)));
                        }else{
                            labelCharacterPanel.setIcon(new ImageIcon(subimage.getScaledInstance(250, 250,
                                    Image.SCALE_SMOOTH)));
                        }
                    }
                    if (i > 5) {
                        //Different values for dwarfs
                        labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(250, 100,
                                BufferedImage.TYPE_INT_ARGB)));
                    }else {
                        labelCharacters[i].setIcon(new ImageIcon(subimage.getScaledInstance(250, 250,
                                BufferedImage.TYPE_INT_ARGB)));

                    }

                    //Set cpu's character animation and flip image to face player's character
                    if (wc.getWarriors().get(i).getName().equals(cpu.getWarrior().getName())) {
                        BufferedImage cpuFlip = new BufferedImage(subimage.getWidth(), subimage.getHeight(),
                                subimage.getType());
                        Graphics2D g2d = cpuFlip.createGraphics();
                        AffineTransform flipImage = AffineTransform.getScaleInstance(-1, 1);
                        flipImage.translate(-subimage.getWidth(), 0);
                        g2d.transform(flipImage);
                        g2d.drawImage(subimage, 0, 0, null);
                        g2d.dispose();
                        if (i > 5) {
                            //Lower height value for dwarf characters
                            labelCPUwarrior.setIcon(new ImageIcon(cpuFlip.getScaledInstance(250, 100,
                                    Image.SCALE_SMOOTH)));
                        } else {
                            labelCPUwarrior.setIcon(new ImageIcon(cpuFlip.getScaledInstance(250, 250,
                                    Image.SCALE_SMOOTH)));
                        }
                    }
                }

            }
        });
        //Add mouse listener to characters tab and start timer
        tabCharacters.addMouseListener(tabCharacters);
        timer.start();

        //Set default stage
        label1 = new JLabel(new ImageIcon(stages[0].getScaledInstance(468, 470,
                BufferedImage.TYPE_INT_ARGB)));
        stagePanel.add(label1);

        //Initialize Ranking panel
        tabRanking.setLayout(new GridLayout(11, 5));
        labelMatrix = new String[11][5];

        //Initialize headers columns
        labelMatrix[0][0] = "PLAYER ID";
        labelMatrix[0][1] = "NAME";
        labelMatrix[0][2] = "WARRIOR";
        labelMatrix[0][3] = "WEAPON";
        labelMatrix[0][4] = "WON COMBATS";

        // DDBB QUERY
        DataBaseConn conn = new DataBaseConn();
        ResultSet rs = conn.getQueryRS(
                """
                        SELECT players.name, warriors.name as warrior, weapons.name as weapon, sum(rounds.battle_points) as points, count(rounds.id) as rounds
                        FROM players
                        JOIN warriors ON warriors.id = players.warrior_id
                        JOIN races ON races.id = warriors.race_id
                        JOIN weapons ON weapons.id = players.weapon_id
                        JOIN battles ON battles.player_id = players.id
                        JOIN rounds ON rounds.battle_id = battles.id
                        WHERE rounds.battle_points > 0\s
                        GROUP BY players.id
                        ORDER BY count(rounds.id)DESC;""");
        try {
            for (int i = 1; i < 11; ++i) {
                if (rs.next()) {
                    for (int j = 0; j < 5; ++j) {
                        labelMatrix[i][j] = (rs.getString(j + 1));
                    }
                }else{
                    break;
                }
            }
        }catch (SQLException e){
            System.out.println("Error executing ranking query");
        }

        conn.closeConn();

        // Paint de background and columns
        tabRanking = new EventPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                try {
                    BufferedImage characterBackground = ImageIO.read(new File(
                            "BatallaDeRazas/src/background/ranking.png"));
                    Image scaledBackground = characterBackground.getScaledInstance(this.getWidth(), this.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    String title = "HALL OF FAME";
                    g2d.setFont(pixelFont);
                    g.drawImage(scaledBackground,0, 0, null);
                    g2d.setColor(new Color(79, 57, 57));
                    g2d.drawString(title, 200, 40);
                    g2d.setColor(new Color(197, 124, 124));
                    g2d.drawString(title, 202, 42);
                    g2d.setFont(rankingFont);

                    // Variable to set the x min value and y min value
                    int xAxis = 20;
                    int yAxis = 80;

                    // Loop into the matrix to pint the columns
                    for (int i = 0; i < 11; ++i) {
                        if (labelMatrix[i][0] == null) break;
                        if (i == 1) g2d.setFont(rankingInfoFont);
                        for (int j = 0; j < 5; ++j) {
                            String column = labelMatrix[i][j];
                            if (i == 0) {
                                g2d.setColor(new Color(56, 46, 46));
                                g2d.drawString(column, j * 122 + xAxis, yAxis);
                                g2d.setColor(new Color(197, 124, 124));
                                g2d.drawString(column, j * 122 + xAxis + 2, yAxis + 2);
                            } else {
                                g2d.setColor(new Color(194, 167, 167));
                                g2d.drawString(column, j * 122 + xAxis, i * 40 + yAxis);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        //Initialize JTabbedPane with tabs for character, weapon, stage and ranking, as well as size of the tab pane
        UIManager.put("TabbedPane.font", pixelFont.deriveFont(25f));
        tabPane = new JTabbedPane();
        tabPane.setPreferredSize(new Dimension(650, 580));
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
            tabStage.add(labelStages[i]);
        }
        tabStage.addMouseListener(tabStage);

        //Fill weapons tab with selectable weapons from user's warrior
        tabWeapons.setLayout(new FlowLayout());
        setWarriorWeaponsImages();
        setSelectedWeaponImage();
        //Initialize fight button and add it to fightPanel, as well as flee button
        fightButton = new JButton("FIGHT!");
        fleeButton = new JButton("FLEE?");
        fleeButton.setIcon(new ImageIcon(fleeButtonImage.getScaledInstance(76, 76,
                BufferedImage.TYPE_INT_ARGB)));
        fightPanel.add(Box.createHorizontalGlue());
        fightPanel.add(fightButton);
        fightPanel.add(Box.createHorizontalGlue());
        fightPanel.add(fleeButton);
        fightPanel.add(Box.createHorizontalGlue());
        for (int i = 0; i < fightPanel.getComponentCount(); i++) {
            if (fightPanel.getComponent(i) instanceof JButton) {
                fightPanel.getComponent(i).setForeground(new Color(255, 216, 216));
                fightPanel.getComponent(i).setFont(pixelFont.deriveFont(14f));
                ((JButton) fightPanel.getComponent(i)).setHorizontalTextPosition(SwingConstants.CENTER);
                ((JButton) fightPanel.getComponent(i)).setVerticalTextPosition(SwingConstants.TOP);
                fightPanel.getComponent(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                ((JButton) fightPanel.getComponent(i)).setBorderPainted(false);
                ((JButton) fightPanel.getComponent(i)).setContentAreaFilled(false);
            }
        }
        fightButton.setIcon(new ImageIcon(fightButtonImage.getScaledInstance(76, 76,
                BufferedImage.TYPE_INT_ARGB)));
        fightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //Condition to check if player has selected a weapon
                    if (usr.getWeapon() == null) {
                        //If the player hasn't selected a weapon, throw exception
                        throw new NoWeaponSelected();
                    }else{
                        boolean comp = false;
                        String name = "";
                        //Check name isn't null or longer than 13 characters
                        while(!comp) {
                            name = JOptionPane.showInputDialog(null, "Chose your name",
                                    "Name Input", JOptionPane.PLAIN_MESSAGE);
                            if (name == null) {
                                comp = true;
                                JOptionPane.getRootFrame();
                            }else {
                                if (name.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Name cannot be empty",
                                            "Invalid Name", JOptionPane.ERROR_MESSAGE);
                                }else if (name.length() > 13) {
                                    JOptionPane.showMessageDialog(null, "Name cannot be longer than 13",
                                            "Invalid Name", JOptionPane.ERROR_MESSAGE);
                                }else{
                                    comp = true;
                                }
                            }
                        }
                        //If user has a valid name, start combat
                        if (name != null) {
                            usr.setName(name);
                            new BattleGUI(usr, cpu, wc, selectedBackground);
                            GUI.super.dispose();
                        }
                    }
                } catch (NoWeaponSelected ex) {
                    //Exception shows an error window
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                } {

                }
            }
        });
        fleeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"I'm a coward...", "I'll show you who's the coward!"};
                int choice = JOptionPane.showOptionDialog(null, "You would shame your ancestors?",
                        "Fleeing?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, null);
                if (choice == JOptionPane.YES_OPTION) System.exit(0);
                else if (choice == JOptionPane.NO_OPTION) JOptionPane.getRootFrame();
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
    //Method will display current selected weapon for both players
    public void setSelectedWeaponImage() {
        BufferedImage selectedWeapon;
        BufferedImage cpuWeapon;
        if (usr.getWeapon() == null) {
            try {
                selectedWeapon = ImageIO.read(new File("BatallaDeRazas/src/weapons/noweapon.png"));
                cpuWeapon = ImageIO.read(new File(cpu.getWeapon().getUrl()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                selectedWeapon = ImageIO.read(new File(usr.getWeapon().getUrl()));
                cpuWeapon = ImageIO.read(new File(cpu.getWeapon().getUrl()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        labelSelectedWeapon.setIcon(new ImageIcon(selectedWeapon.getScaledInstance(70, 70,
                BufferedImage.TYPE_INT_ARGB)));
        labelCPUWeapon.setIcon(new ImageIcon(cpuWeapon.getScaledInstance(70, 70,
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
            for (int i = 0; i < stages.length; i++) {
                if (clickedComponent.equals(labelStages[i])) {
                    selectedBackground = stages[i];
                    label1.setIcon(new ImageIcon(stages[i].getScaledInstance(468, 470,
                            BufferedImage.TYPE_INT_ARGB)));
                }
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
                            usr.setWeapon(usr.getWarrior().getWeapons().get(i));
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
    //Set internal CharacterLabel class to display stats of every character in the character selection panel
    class CharacterLabel extends JLabel {
        public CharacterLabel() {}
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //After checking if a character is being hovered, draw stats for characters
            if (isHovered()) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(pixelFont.deriveFont(17f));
                Component characterHovered = getComponentAt(getMousePosition());
                int y = 50;
                for (int i = 0; i < tabCharacters.getComponentCount(); i++) {
                    String characterStats = wc.getWarriors().get(i).getName() + "\n" +
                                              wc.getWarriors().get(i).getRace() + "\n" +
                                              "HP " + wc.getWarriors().get(i).getHp() + "\n" +
                                              "Str " + wc.getWarriors().get(i).getStrength() + "\n" +
                                              "Def " + wc.getWarriors().get(i).getDefense() + "\n" +
                                              "Agi " + wc.getWarriors().get(i).getAgility() + "\n" +
                                              "Spd " + wc.getWarriors().get(i).getSpeed() + "\n";
                    String[] stats = characterStats.split("\n");
                    if (characterHovered.equals(labelCharacters[i])) {
                        for (String stat : stats) {
                            g2d.setColor(Color.BLACK);
                            g2d.drawString(stat, 8, y);
                            g2d.setColor(new Color(245, 154, 98));
                            g2d.drawString(stat, 10, y + 1);
                            y = y + 16;
                        }
                    }
                }
            }else{
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        //Method will check if mouse is hovering a character label
        public boolean isHovered() {
            return getMousePosition() != null;
        }
    }
}
class NoWeaponSelected extends Exception {

    public NoWeaponSelected() {
        super("No weapon selected");
    }
}