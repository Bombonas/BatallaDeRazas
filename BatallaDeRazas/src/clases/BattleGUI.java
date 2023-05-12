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
import java.util.ArrayList;

public class BattleGUI extends JFrame implements ActionListener{
    private JTextArea console;
    private JPanel buttonsPanel;
    private BattlePanel combatPanel;
    private JLabel labelBack;
    private JButton turn, hideShow, finish, fastMode;
    private BufferedImage background;
    private boolean tAreaVisible;
    private JScrollPane scrollPane;
    private Color colorButton, colorBackground;
    private BufferedImage imgBackground;
    private Player user, cpu;
    private WarriorContainer wc;
    private ArrayList<Player> orderTurns;
    private int turnNum;
    private RoundsInfo ri;

    public BattleGUI(Player user, Player cpu, WarriorContainer wc,BufferedImage imgBackground){
        setSize(1280, 720);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        orderTurns = new ArrayList<Player>();
        orderTurns.add(user);
        orderTurns.add(cpu);
        turnNum = 0;

        this.user = user;
        this.cpu = cpu;
        this.wc = wc;
        this.imgBackground = imgBackground;

        colorBackground = new Color(53, 32, 112, 255);
        colorButton = new Color(206, 187, 128, 255);

        setButtonsPanel();
        setCombatPanel();
        tAreaVisible = true;

        add(buttonsPanel, BorderLayout.NORTH);
        add(combatPanel);

        ri = new RoundsInfo(cpu, user);

        startRound();

        setVisible(true);
    }

    public void setCombatPanel(){
        combatPanel = new BattlePanel(imgBackground);
        combatPanel.setPreferredSize(new Dimension(1280, 680));
        combatPanel.setLayout(new BorderLayout());

        console = new JTextArea(2000, 30);
        console.setPreferredSize(new Dimension(300, 680));
        console.setEditable(false);
        console.setOpaque(true);
        console.setBackground(colorBackground);


        console.setFont(new Font("Serif", Font.ITALIC, 17));
        console.setForeground(Color.WHITE);

        scrollPane = new JScrollPane(console);
        scrollPane.setBackground(colorBackground);
        scrollPane.getVerticalScrollBar().setBackground(colorBackground);
        scrollPane.getHorizontalScrollBar().setBackground(colorBackground);

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = colorButton;
            }
        });

        scrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = colorButton;
            }
        });

        scrollPane.setViewportView(console);

        combatPanel.add(scrollPane, BorderLayout.EAST);
    }

    public JTextArea getConsole() {
        return console;
    }

    public void setButtonsPanel(){
        turn = new JButton("Next Turn");
        fastMode = new JButton("Fast Mode");
        hideShow = new JButton("Hide/Show console");
        finish = new JButton("Finish");

        turn.setBackground(colorButton);
        fastMode.setBackground(colorButton);
        hideShow.setBackground(colorButton);
        finish.setBackground(colorButton);
        finish.setVisible(false);

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

        turn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg;
                if(cpu.getCurrentHP() > 0 & user.getCurrentHP() > 0) {
                    msg = orderTurns.get(turnNum).atack(orderTurns.get((turnNum + 1) % 2));
                    turnNum = (turnNum + 1) % 2;
                    console.append(msg);
                }else{
                    console.append("BATALLA FINALIZADA");
                    finish.setVisible(true);
                    turn.setVisible(false);
                    fastMode.setVisible(false);
                }
            }
        });

        fastMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg;
                while(cpu.getCurrentHP() > 0 & user.getCurrentHP() > 0){
                    msg = orderTurns.get(turnNum).atack(orderTurns.get((turnNum + 1) % 2));
                    turnNum = (turnNum + 1) % 2;
                    console.append(msg);
                }
                console.append("BATALLA FINALIZADA");
                finish.setVisible(true);
                turn.setVisible(false);
                fastMode.setVisible(false);
            }
        });

        finish.addActionListener(this);

        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(colorBackground);
        buttonsPanel.setPreferredSize(new Dimension(1280, 40));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(turn);
        buttonsPanel.add(fastMode);
        buttonsPanel.add(hideShow);
        buttonsPanel.add(finish);
    }
    public void newOpponent(){
        cpu.setWarrior(wc.getRandomWarrior());
        cpu.setWeapon();
        ri.setIdOpponent(cpu.getWarrior().getIdWarrior());
        ri.setIdOpponentWeapon(cpu.getWeapon().getIdWeapon());
    }

    public void startRound(){
        turnNum = 0;
        fastMode.setVisible(true);
        turn.setVisible(true);
        finish.setVisible(false);
        cpu.setCurrentHP(cpu.getWarrior().getHp());
        user.setCurrentHP(user.getWarrior().getHp());
        console.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(user.getCurrentHP() > 0){// WIN
            ri.setInjuriesCaused(cpu.getWarrior().getHp() - cpu.getCurrentHP());
            ri.setInjuriesSuffered(user.getWarrior().getHp() - user.getCurrentHP());

            ri.updateData(cpu.getWarrior().getDefeatPoints() + cpu.getWeapon().getDefeatPoints());

        }else{// LOSE
            ri.setInjuriesCaused(cpu.getWarrior().getHp() - cpu.getCurrentHP());
            ri.setInjuriesSuffered(user.getWarrior().getHp() - user.getCurrentHP());

            ri.updateData();
        }

        PopUp p = new PopUp(this, colorBackground, colorButton);
        if(user.getCurrentHP() > 0){// WIN
            newOpponent();
            startRound();
        }else{// LOSE
            user.setWeapon(null);
            new GUI(user, cpu, wc);
            dispose();
        }
    }
}
