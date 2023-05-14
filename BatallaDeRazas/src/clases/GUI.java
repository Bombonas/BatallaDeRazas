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
    private JButton fightButton;
    private JLabel label1, labelCharacterPanel, labelSelectedWeapon, labelCPUwarrior, labelCPUWeapon;
    private JLabel[] labelStages, labelCharacters, weaponLabel;
    private JTabbedPane tabPane;
    private BufferedImage[] stages, characters;
    private BufferedImage selectedBackground;
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        tabsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(87, 54, 44));
                g.fillRect(0, 0,getWidth(), getHeight());
            }
        };
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
        stagePanel = new EventPanel();
        stagePanel.setLayout(new BorderLayout());
        fightPanel = new JPanel();

        //Define image paths for stages and characters
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(32f);
            rankingFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(16f);
            rankingInfoFont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "BatallaDeRazas/src/font/pixelart.ttf")).deriveFont(11f);
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
        labelCharacters = new JLabel[9];
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
        UIManager.put("info",Color.BLUE);
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
            labelCharacters[i] = new JLabel();
            /*
            labelCharacters[i].setToolTipText("<html>"+wc.getWarriors().get(i).getName()+"<br>"+
                                              "Race: "+wc.getWarriors().get(i).getRace()+"<br>"+
                                              "HP: "+wc.getWarriors().get(i).getHp()+ "<br>"+
                                              "Strength: "+wc.getWarriors().get(i).getStrength()+"<br>"+
                                              "Defense: "+wc.getWarriors().get(i).getDefense() + "<br>"+
                                              "Speed: "+wc.getWarriors().get(i).getSpeed()+"<br>"+
                                              "Agility: "+wc.getWarriors().get(i).getAgility()+"</html>");

             */
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
        //tabCharacters.addMouseMotionListener(tabCharacters);
        timer.start();

        //Set default stage
        label1 = new JLabel(new ImageIcon(stages[0].getScaledInstance(620, 590,
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
                "SELECT players.id, players.name, " +
                "warriors.name as warrior, weapons.name, count(rounds.id) as rounds\n" +
                "FROM players\n" +
                "JOIN warriors ON warriors.id = players.warrior_id\n" +
                "JOIN weapons ON weapons.id = players.weapon_id\n" +
                "JOIN battles ON battles.player_id = players.id\n" +
                "JOIN rounds ON rounds.battle_id = battles.id\n" +
                "WHERE rounds.battle_points > 0 \n" +
                "GROUP BY players.id\n" +
                "ORDER BY count(rounds.id)DESC;");
        try {
            for (int i = 1; i < 11; ++i) {
                rs.next();
                //if (rs.wasNull()) break;
                for (int j = 0; j < 5; ++j) {
                    labelMatrix[i][j] = (rs.getString(j + 1));
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        conn.closeConn();

        // Paint de background and columns
        tabRanking = new EventPanel() {
            //Draw background image and string for selecting character
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
                    g2d.drawString(title, 100, 40);
                    g2d.setColor(new Color(225, 159, 159));
                    g2d.drawString(title, 102, 42);
                    g2d.setFont(rankingFont);

                    // Loop into the matrix to pint the columns
                    for (int i = 0; i < 11; ++i) {
                        int xAxis = 20;
                        int yAxis = 80;
                        if (labelMatrix[i][0] == null) break;
                        if (i == 1) g2d.setFont(rankingInfoFont);
                        for (int j = 0; j < 5; ++j) {
                            String columns = labelMatrix[i][j];
                            g2d.drawString(columns, j * 120 + xAxis, i * 40 + yAxis);
                            g2d.setColor(new Color(194, 167, 167));
                            //g2d.drawString(columns, j * 100 + xAxis + 2, i * 40 + yAxis + 2);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        //Initialize JTabbedPane with tabs for character, weapon, stage and ranking, as well as size of the tab pane
        UIManager.put("TabbedPane.font", pixelFont.deriveFont(25f));
        //TODO check this UIManager.put("TabbedPane:TabbedPaneTab[Focused+MouseOver+Selected].backgroundPainter", null);
        tabPane = new JTabbedPane();
        tabPane.setPreferredSize(new Dimension(650, 580));
        tabPane.addTab("Character", tabCharacters);
        tabPane.addTab("Weapons", tabWeapons);
        //tabPane.setIconAt(1, new ImageIcon(stages[0].getScaledInstance(100, 20,
        //        BufferedImage.TYPE_INT_ARGB)));
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
                        boolean comp = false;
                        while(!comp) {
                            String name = JOptionPane.showInputDialog("Chose your name");
                            if (name != null && !name.isBlank()) comp = true;
                        }

                        new BattleGUI(usr, cpu, wc, selectedBackground);
                        GUI.super.dispose();
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
                    label1.setIcon(new ImageIcon(stages[i].getScaledInstance(620, 590,
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