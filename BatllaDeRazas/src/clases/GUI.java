package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JPanel mainPanel, tabsPanel, tabCharacters, tabWeapons, tabStage, tabRanking, characterPanel,
    stagePanel, fightPanel;
    private JButton button1;
    private JLabel label1;
    private JTabbedPane tabPane;
    private BufferedImage background;

    public GUI() {
        //Define JFrame properties: size, close operation, title, location
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hola");
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
            background = ImageIO.read(new File("src/background/Summer.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Set scaled instance of background on a JLabel and add to stage's JPanel
        label1 = new JLabel(new ImageIcon(background.getScaledInstance(620, 590, BufferedImage.TYPE_INT_ARGB)));
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


        //Add main panel to JFrame and set visible
        add(mainPanel);
        setVisible(true);
    }
}