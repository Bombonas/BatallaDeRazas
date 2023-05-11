package clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BattleGUI extends JFrame {
    private JTextArea console;
    private JPanel buttonsPanel;
    private BattlePanel combatPanel;
    private JLabel labelBack;
    private JButton speed, hideShow;
    private BufferedImage background;
    private boolean tAreaVisible;
    private JScrollPane scrollPane;
    private Player usr, cpu;

    public BattleGUI(Player usr, Player cpu){
        this.usr = usr;
        this.cpu = cpu;
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setButtonsPanel();
        setCombatPanel();
        tAreaVisible = true;

        add(buttonsPanel, BorderLayout.NORTH);
        add(combatPanel);


        setVisible(true);
    }

    public boolean isOpaque() {
        return true;
    }

    public void setCombatPanel(){
        combatPanel = new BattlePanel();
        combatPanel.setPreferredSize(new Dimension(1280, 680));
        combatPanel.setLayout(new BorderLayout());

        console = new JTextArea();
        console.setPreferredSize(new Dimension(300, 680));
        console.setEditable(true);
        console.setOpaque(true);
        console.setBackground(new Color(88, 24, 69, 255));
        console.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(console);
        scrollPane.setBackground(new Color(88, 24, 69, 255));
        scrollPane.getVerticalScrollBar().setBackground(new Color(88, 24, 69, 255));
        scrollPane.getHorizontalScrollBar().setBackground(new Color(88, 24, 69, 255));

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(255, 195, 0, 255);
            }
        });

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(255, 195, 0, 255);
            }
        });

        scrollPane.setViewportView(console);

        combatPanel.add(scrollPane, BorderLayout.EAST);
    }

    public JTextArea getConsole() {
        return console;
    }

    public void setButtonsPanel(){
        speed = new JButton("Speed");
        hideShow = new JButton("Hide/Show console");

        speed.setBackground(new Color(255, 195, 0, 255));
        hideShow.setBackground(new Color(255, 195, 0, 255));

        hideShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(tAreaVisible){
                    console.setVisible(false);
                    scrollPane.setVisible(false);
                    tAreaVisible = false;
                }else {
                    console.setVisible(true);
                    scrollPane.setVisible(true);
                    tAreaVisible = true;
                }
            }
        });
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(88, 24, 69, 255));
        buttonsPanel.setPreferredSize(new Dimension(1280, 40));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(speed);
        buttonsPanel.add(hideShow);
    }


}
