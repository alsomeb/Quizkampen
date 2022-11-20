package org.quizkampen.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class timerpanel extends JPanel {
    private final JLabel timerLabel;
    private Timer stopWatch;
    private int count;

    public timerpanel() {
        setPreferredSize(new Dimension(500,50));
        setBackground(new Color(61, 47, 21));
        timerLabel = new JLabel();
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        timerLabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        timerLabel.setForeground(Color.WHITE);
        add(timerLabel);
        startTimer(30);
    }
    public void startTimer(int timeLimit) {
        ActionListener action = e -> {
            if (count == 0) {
                stopWatch.stop();
                timerLabel.setText("You are out of time");
            } else {
                timerLabel.setText("You have " + count + " seconds remaining.");
                count--;
            }
        };
        count = timeLimit;
        stopWatch = new Timer(1000, action);
        stopWatch.setInitialDelay(0);
        stopWatch.start();
    }
}
