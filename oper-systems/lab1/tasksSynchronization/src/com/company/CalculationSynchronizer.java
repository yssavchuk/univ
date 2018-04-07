package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class CalculationSynchronizer {
    public static void main(String[] args) {
        Function f = Function.getFunction(Integer.parseInt(args[0]));
        int res = f.execute() ? 1 : 0;
        System.exit(res);
    }
    @NotNull
    public static Process start(String arg) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = CalculationSynchronizer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className, arg);
        return builder.start();
    }
}
