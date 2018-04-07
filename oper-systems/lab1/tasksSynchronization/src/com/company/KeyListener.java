package com.company;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {

    CalculationManager calculationManager = null;

    KeyListener(CalculationManager manager) {
        calculationManager = manager;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_Q) {
            calculationManager.stop();
            Manager.setStatus(130);
            //System.exit(130);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) { }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { }
}