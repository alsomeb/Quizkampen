package org.quizkampen.timerpanel;

import org.quizkampen.static_variable.CustomCollors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerPanel extends JPanel implements ActionListener {

    private final JLabel timerLabel;
    private Timer stopWatch;
    private int count;

    public TimerPanel() {
        setPreferredSize(new Dimension(500,50));
        setBackground(CustomCollors.timer_collor);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("nu är tiden slut");
    }
}
