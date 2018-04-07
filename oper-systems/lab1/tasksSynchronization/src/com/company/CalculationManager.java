package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CalculationManager implements Runnable {
    protected ArrayList<Process> processes = null;
    protected boolean calculationResult = true;
    protected boolean isFinished = false;
    protected boolean isStopped = false;
    protected boolean isSleeping = false;


    CalculationManager(String[] args) {
        processInitialization(args);
    }

    public static void main(String [] args){
        CalculationManager calculationManager = new CalculationManager(args);
        calculationManager.run();
    }

    public void run() {
        while (!processes.isEmpty()  && calculationResult && !isStopped()) {
            for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext(); ) {
                Process process = iterator.next();
                if (!calculationResult) {
                    process.destroy();
                }
                if (!process.isAlive()) {
                    if (process.exitValue() == 0)
                        calculationResult = false;
                    iterator.remove();
                }
            }

        }
        stop();
        if(processes.isEmpty() || !calculationResult)
            setFinished();
    }

    private ArrayList<Process> processInitialization(String[] args){
        int arrLength = args.length;
        processes = new ArrayList<>(arrLength);
        try {
            for (int i = 0; i < arrLength; i++) {
                Process process = CalculationSynchronizer.start(args[i]);
                processes.add(process);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return processes;
    }

    private void stopCalculation() { processes.forEach(process -> process.destroy()); }
    private void setFinished() { isFinished = true; }
    public synchronized void stop(){
        isStopped = true;
        stopCalculation();
    }
    public synchronized boolean isStopped(){
        return isStopped;
    }
    public synchronized boolean isFinished() { return isFinished; }
    public synchronized int calculationResult() { return calculationResult ? 1 : 0; }
}
