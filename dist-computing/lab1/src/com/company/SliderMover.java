package com.company;

import javax.swing.*;

public class SliderMover {
    private JSlider slider;
    private int INITIAL_PRIORITY = 1;
    private int MAX_PRIORITY = 10;
    private int MIN_PRIORITY = 1;
    private Thread threadDecreaser;
    private Thread threadIncreaser;

    public SliderMover(JSlider s) {
        slider = s;
    }

    public void startTesting() {
        threadIncreaser = new Thread(() -> {
            while(true) {
                synchronized (slider) {
                    slider.setValue(slider.getValue() + 1);
                }
            }
        });

        threadDecreaser = new Thread(() -> {
            while(true) {
                synchronized (slider) {
                    slider.setValue(slider.getValue() - 1);
                }
            }
        });

        threadIncreaser.setPriority(INITIAL_PRIORITY);
        threadDecreaser.setPriority(INITIAL_PRIORITY);
        threadDecreaser.start();
        threadIncreaser.start();
    }

    public void incDecPrior() {
        int current = threadDecreaser.getPriority();
        if(current < MAX_PRIORITY) {
            current++;
            System.out.println(current);
            threadDecreaser.setPriority(current);
        }
        else {
            System.out.println("priority > MAX_PRIORITY");
        }
    }
    public void decDecPrior() {
        int current = threadDecreaser.getPriority();
        if(current > MIN_PRIORITY) {
            current--;
            System.out.println(current);
            threadDecreaser.setPriority(current);
        }
        else {
            System.out.println("priority < MIN_PRIORITY");
        }

    }
    public void incIncPrior() {
        int current = threadIncreaser.getPriority();
        if(current < MAX_PRIORITY) {
            current++;
            System.out.println(current);
            threadIncreaser.setPriority(current);
        }
        else {
            System.out.println("priority > MAX_PRIORITY");
        }
    }
    public void decIncPrior() {
        int current = threadDecreaser.getPriority();
        if(current > MIN_PRIORITY) {
            current--;
            System.out.println(current);
            threadDecreaser.setPriority(current);
        }
        else {
            System.out.println("priority < MIN_PRIORITY");
        }
    }
}
