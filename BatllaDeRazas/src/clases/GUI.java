package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel mainPanel, tabsPanel, tabCharacters, tabWeapons, tabStage, tabRanking, characterPanel,
            stagePanel, fightPanel;
    private JButton button1;
    private JLabel label1, labelStage1, labelStage2, labelStage3, labelWeapon1, labelWeapon2, labelWeapon3,
            labelWeapon4, labelWeapon5, labelWeapon6, labelWeapon7, labelWeapon8, labelWeapon9;
    private JTabbedPane tabPane;
    private BufferedImage background, stage1, stage2, stage3, weapon1, weapon2, weapon3, weapon4, weapon5,
            weapon6, weapon7, weapon8, weapon9;

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
        tabCharacters = new JPanel();
        tabWeapons = new JPanel();
        tabStage = new JPanel();
        tabRanking = new JPanel();
        characterPanel = new JPanel();
        characterPanel.setBorder(new LineBorder(Color.GREEN, 1));//Border to see empty panel's location, delete later
        stagePanel = new JPanel();
        stagePanel.setLayout(new BorderLayout());
        fightPanel = new JPanel();
        fightPanel.setBorder(new LineBorder(Color.BLUE, 1));//Border to see empty panel's location, delete later

        //Define background variable with path to background.jpg
        try {
            //This has to be changed, placeholder
            background = ImageIO.read(new File("BatllaDeRazas/src/background/Summer.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Set scaled instance of background on a JLabel and add to stage's JPanel
        label1 = new JLabel(new ImageIcon(background.getScaledInstance(620, 590, BufferedImage.TYPE_INT_ARGB)));
        stagePanel.add(label1);

        //Define image paths for stages and weapons
        try {
            stage1 = ImageIO.read(new File("BatllaDeRazas/src/background/Summer.jpg"));
            stage2 = ImageIO.read(new File("BatllaDeRazas/src/background/desert.jpg"));
            stage3 = ImageIO.read(new File("BatllaDeRazas/src/background/winter.jpg"));
            weapon1 = ImageIO.read(new File("BatllaDeRazas/src/weapons/dagger.png"));
            weapon2 = ImageIO.read(new File("BatllaDeRazas/src/weapons/sword.png"));
            weapon3 = ImageIO.read(new File("BatllaDeRazas/src/weapons/axe.png"));
            weapon4 = ImageIO.read(new File("BatllaDeRazas/src/weapons/dualsword.png"));
            weapon5 = ImageIO.read(new File("BatllaDeRazas/src/weapons/scimitar.png"));
            weapon6 = ImageIO.read(new File("BatllaDeRazas/src/weapons/bow.png"));
            weapon7 = ImageIO.read(new File("BatllaDeRazas/src/weapons/katana.png"));
            weapon8 = ImageIO.read(new File("BatllaDeRazas/src/weapons/stabby.png"));
            weapon9 = ImageIO.read(new File("BatllaDeRazas/src/weapons/dualaxe.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        labelStage1 = new JLabel(new ImageIcon(stage1.getScaledInstance(700, 200, BufferedImage.TYPE_INT_ARGB)));
        labelStage2 = new JLabel(new ImageIcon(stage2.getScaledInstance(700, 200, BufferedImage.TYPE_INT_ARGB)));
        labelStage3 = new JLabel(new ImageIcon(stage3.getScaledInstance(700, 200, BufferedImage.TYPE_INT_ARGB)));
        tabStage.add(labelStage1);
        tabStage.add(labelStage2);
        tabStage.add(labelStage3);

        //Fill weapons tab with selectable weapons
        tabWeapons.setLayout(new GridLayout(3,3));
        labelWeapon1 = new JLabel(new ImageIcon(weapon1.getScaledInstance(200,200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon2 = new JLabel(new ImageIcon(weapon2.getScaledInstance(200,200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon3 = new JLabel(new ImageIcon((weapon3.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB))));
        labelWeapon4 = new JLabel(new ImageIcon(weapon4.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon5 = new JLabel(new ImageIcon(weapon5.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon6 = new JLabel(new ImageIcon(weapon6.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon7 = new JLabel(new ImageIcon(weapon7.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon8 = new JLabel(new ImageIcon(weapon8.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));
        labelWeapon9 = new JLabel(new ImageIcon(weapon9.getScaledInstance(200, 200,
                BufferedImage.TYPE_INT_ARGB)));

        tabWeapons.add(labelWeapon1);
        tabWeapons.add(labelWeapon2);
        tabWeapons.add(labelWeapon3);
        tabWeapons.add(labelWeapon4);
        tabWeapons.add(labelWeapon5);
        tabWeapons.add(labelWeapon6);
        tabWeapons.add(labelWeapon7);
        tabWeapons.add(labelWeapon8);
        tabWeapons.add(labelWeapon9);


        //Add main panel to JFrame and set visible
        add(mainPanel);
        setVisible(true);
    }
}