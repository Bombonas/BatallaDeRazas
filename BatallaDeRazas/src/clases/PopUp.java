package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUp extends JDialog {
    //private JFrame frame;
    private JButton yes, no;
    private JLabel text;
    private JPanel globalPanel, buttonPanel;

    public PopUp(BattleGUI frame, Color background, Color buttons){
        super(frame, true);
        setSize(400, 150);
        setLocation(500, 500);
        globalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //new BoxLayout(buttonPanel, BoxLayout.X_AXIS)
        buttonPanel = new JPanel();
        yes = new JButton("Yes");
        no = new JButton("No");
        text = new JLabel("Do you want to keep fighting?");
        text.setForeground(Color.WHITE);

        globalPanel.setBackground(background);
        buttonPanel.setOpaque(false);
        text.setOpaque(false);
        yes.setBackground(buttons);
        no.setBackground(buttons);

        buttonPanel.add(yes);
        buttonPanel.add(no);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        globalPanel.add(text);
        globalPanel.add(buttonPanel);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(globalPanel);
        setVisible(true);
    }
}
