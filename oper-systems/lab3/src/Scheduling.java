// This file contains the main() function for the Scheduling
// simulation.  init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import java.io.*;
import java.util.*;

public class Scheduling {

  private static String confFile = "scheduling.conf";
  public static int quantum = 100;

  private static int processnum = 5;
  private static int meanDev = 1000;
  private static int standardDev = 100;
  private static int runtime = 1000;
  private static Vector processVector = new Vector();
  private static Results result = new Results("null","null",0);
  private static String resultsFile = "Summary-Results";

  private static void init(String file) {
    File f = new File(file);
    String line;
    int cputime;
    int ioblocking;
    double X;

    try {   
      DataInputStream in = new DataInputStream(new FileInputStream(f));
      while ((line = in.readLine()) != null) {
        if (line.startsWith("numprocess")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          processnum = Common.s2i(st.nextToken());
        }
        if (line.startsWith("meandev")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          meanDev = Common.s2i(st.nextToken());
        }
        if (line.startsWith("standdev")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          standardDev = Common.s2i(st.nextToken());
        }
        if (line.startsWith("quantum")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          quantum = Common.s2i(st.nextToken());
        }
        if (line.startsWith("process")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          ioblocking = Common.s2i(st.nextToken());
          X = Common.R1();
          while (X == -1.0) {
            X = Common.R1();
          }
          X = X * standardDev;
          cputime = (int) X + meanDev;
          processVector.addElement(new Process(cputime, ioblocking, 0, 0, 0));
        }
        if (line.startsWith("runtime")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          runtime = Common.s2i(st.nextToken());
        }
      }
      in.close();
    } catch (IOException e) { /* Handle exceptions */ }
  }

  private static void debug() {
    int i;

    System.out.println("processnum " + processnum);
    System.out.println("meandevm " + meanDev);
    System.out.println("standdev " + standardDev);
    int size = processVector.size();
    for (i = 0; i < size; i++) {
      Process process = (Process) processVector.elementAt(i);
      System.out.println("process " + i + " " + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked);
    }
    System.out.println("runtime " + runtime);
  }

  public static void main(String[] args) {
    int i;

//    if (args.length != 1) {
//      System.out.println("Usage: 'java Scheduling <INIT FILE>'");
//      System.exit(-1);
//    }

//    File f = new File(args[0]);
    File f = new File(confFile);
    if (!(f.exists())) {
      System.out.println("Scheduling: error, file '" + f.getName() + "' does not exist.");
      System.exit(-1);
    }  
    if (!(f.canRead())) {
      System.out.println("Scheduling: error, read of " + f.getName() + " failed.");
      System.exit(-1);
    }
    System.out.println("Working...");

//    init(args[0]);
    init(confFile);

    if (processVector.size() < processnum) {
      i = 0;
      while (processVector.size() < processnum) {       
          double X = Common.R1();
          while (X == -1.0) {
            X = Common.R1();
          }
          X = X * standardDev;
        int cputime = (int) X + meanDev;
        processVector.addElement(new Process(cputime,i*100,0,0,0));
        i++;
      }
    }
    result = SchedulingAlgorithm.run(runtime, processVector, result);
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      out.println("Scheduling Type: " + result.schedulingType);
      out.println("Scheduling Name: " + result.schedulingName);
      out.println("Simulation Run Time: " + result.compuTime);
      out.println("Mean: " + meanDev);
      out.println("Standard Deviation: " + standardDev);
      out.println("\nProcess #\t\tCPU Time\t\tIO Blocking\t\tCPU Completed\t\tCPU Blocked");
      for (i = 0; i < processVector.size(); i++) {
        Process process = (Process) processVector.elementAt(i);
        out.print("\t\t" + Integer.toString(i));
        if (i < 100) { out.print("\t\t"); } else { out.print("\t"); }
        out.print(Integer.toString(process.cputime));
        if (process.cputime < 100) { out.print(" (ms)\t\t\t"); } else { out.print(" (ms)\t\t"); }
        out.print(Integer.toString(process.ioblocking));
        if (process.ioblocking < 100) { out.print(" (ms)\t\t\t"); } else { out.print(" (ms)\t\t"); }
        out.print(Integer.toString(process.cpudone));
        if (process.cpudone < 100) { out.print(" (ms)\t\t\t"); } else { out.print(" (ms)\t\t\t"); }
        out.println(process.numblocked + " times");
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
  System.out.println("Completed.");
  }
}

