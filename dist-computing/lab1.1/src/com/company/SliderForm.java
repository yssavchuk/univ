package com.company;

import javax.swing.*;

public class SliderForm extends JFrame {
    private JPanel mainPanel;

    private JButton increasePriorityButton1;
    private JButton decreasePriorityButton1;

    private JButton increasePriorityButton2;
    private JButton decreasePriorityButton2;

    private JSlider slider;

    public SliderForm() {
        super("Slider Mover");
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(400, 200));
        setResizable(false);
        setVisible(true);
        pack();

        SliderMover cvt = new SliderMover(slider);
        cvt.startTesting();

        increasePriorityButton1.addActionListener(e -> cvt.incIncPrior());

        decreasePriorityButton1.addActionListener(e -> cvt.decIncPrior());

        increasePriorityButton2.addActionListener(e -> cvt.incDecPrior());

        decreasePriorityButton2.addActionListener(e -> cvt.decDecPrior());
    }
}
