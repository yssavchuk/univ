// run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {
  static int comptime = 0;
  static int currentProcess = 0;
  static int previousProcess = 0;
  static int nextProcess = 0;
  static int size;
  static int completed = 0;
  static int quantum = Scheduling.quantum;
  static String resultsFile = "Summary-Processes";
  static Process process;
  static Vector processVector;
  static PrintStream out;

  public static Results run(int runtime, Vector _processVector, Results result) {

    processVector = _processVector;
    size = processVector.size();
    result.schedulingType = "Batch (Preemptive)";
    result.schedulingName = "Round Robin Scheduling algorithm";
    try {
      out = new PrintStream(new FileOutputStream(resultsFile));
      process = (Process) processVector.elementAt(currentProcess);
      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")");
//      out.println("Quantum = " + quantum);
      while (comptime < runtime) {
        if (process.cpudone == process.cputime) {
          completed++;
          out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")");
//          out.println("Quantum = " + quantum);
          if (completed == size) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          chooseNewProcess();
        }
        if (quantum >= 0) {
          if (process.ioblocking == process.ionext) {
            process.numblocked++;
            out.println("Process: " + currentProcess + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")");
//            out.println("Quantum = " + quantum);


            process.ionext = 0;
            process.justBlocked = true;

            chooseNewProcess();
          }
          process.cpudone++;
          if (process.ioblocking > 0) {
            process.ionext++;
          }
          quantum--;

          comptime++;
        } else {
//          out.println("Quantum left = " + quantum + "\n");
          chooseNewProcess();
        }

      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }

  public static void chooseNewProcess() {
    int counter = 0;
    previousProcess = currentProcess;
    nextProcess = previousProcess + 1;
    if (nextProcess == processVector.size()) nextProcess = 0;
    process = (Process) processVector.elementAt(nextProcess);
    while (process.cpudone >= process.cputime || process.justBlocked) {
      process.justBlocked = false;
      nextProcess++;
      if (nextProcess == processVector.size()) {
        nextProcess = 0;
      }
      process = (Process) processVector.elementAt(nextProcess);
      counter++;
      if (counter == processVector.size()) {
        comptime++;
        counter = 0;
      }
    }
    currentProcess = nextProcess;
    process = (Process) processVector.elementAt(currentProcess);
    out.println("\nProcess: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.numblocked + ")");
    quantum = Scheduling.quantum;
//    out.println("Quantum = " + quantum);

    for (Object p : processVector) {
      Process a = (Process) p;
      a.justBlocked = false;
    }
  }
}
