package com.company;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.Level;


public class Manager {

    protected CalculationManager calculationManager = null;
    protected Thread calculationManagerThread = null;
    protected boolean isStopped = false;
    protected Scanner reader = new Scanner(System.in);
    protected static long PROMPT_SLEEP_TIME = 5000;
    protected static int status = -1;
    protected long start = System.currentTimeMillis();

    public static void main(String[] args){
     Manager man = new Manager(args);
     man.execute();
 }

 Manager(String[] args){
     calculationManager = new CalculationManager(args);
     calculationManagerThread = new Thread(calculationManager);
 }

 public void execute(){
     keyListenerSetup();
     calculationManagerThread.start();
     startUserPrompt();
     try {
         calculationManagerThread.join();
     } catch (InterruptedException e) {

     }
     try {
         GlobalScreen.unregisterNativeHook();
     } catch (NativeHookException e) {
         e.printStackTrace();
     }
     System.out.println("Execution time = " + (System.currentTimeMillis() - start));
     if (calculationManager.isFinished()) {
         System.out.println("\nFunction calculation finished with result " + calculationManager.calculationResult());
         setStatus(calculationManager.calculationResult());
         //System.exit(calculationManager.calculationResult());
     }
     else if (calculationManager.isStopped()){
         System.out.println("\nCalculation was interrupted");
         setStatus(131);
         //System.exit(131);
     }
 }

 private void startUserPrompt(){
     long lastPrompt = System.currentTimeMillis();
     while (!userPromptStopped() && !calculationManager.isStopped()) {
         while (!calculationManager.isStopped() && !userPromptStopped() && System.currentTimeMillis() - lastPrompt < PROMPT_SLEEP_TIME) {}
         if (calculationManager.isStopped() || userPromptStopped())
             return;
         System.out.println("Between prompt time = " + (System.currentTimeMillis() - lastPrompt));
         System.out.println("\n\n\nWant to control program execution ? \nSelect one option and press \'Enter\'" +
                 "\n1) Continue\n2) Continue without prompt\n3) Cancel\n");
         try {
             int input = reader.nextInt();
             switch (input) {
                 case 1:
                     System.out.println("1) calculation continue till next prompt");
                     break;
                 case 2:
                     System.out.println("2) calculation continue without further prompts.");
                     setUserPromptStop();
                     break;
                 case 3:
                     calculationManager.stop();
                     setUserPromptStop();
                     break;
                 default:
                     break;
             }
         } catch (InputMismatchException e) {
         }
         lastPrompt = System.currentTimeMillis();
     }
 }

 public  void keyListenerSetup(){
     LogManager.getLogManager().reset();
     Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
     logger.setLevel(Level.OFF);
     try {
         GlobalScreen.registerNativeHook();
     }
     catch (NativeHookException ex) {
         System.err.println("Register native hook error");
         System.err.println(ex.getMessage());
         setStatus(1);
        // System.exit(1);
     }
     GlobalScreen.addNativeKeyListener(new KeyListener(calculationManager));
 }

 private synchronized boolean userPromptStopped(){return isStopped;}
 private synchronized void setUserPromptStop() {isStopped = true;}
 public static synchronized void setStatus(int st) { status = st; }
 public static synchronized int getStatus() {return status;}
 public static void setUserPrompt(int t){ PROMPT_SLEEP_TIME = t;}
}
