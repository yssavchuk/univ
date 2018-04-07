package com.company;

public class Function {
    public static int EXECUTION_TIME_INFINITY = -1;

    protected boolean result;
    protected int executionTime;

    protected Function(int arg1, boolean arg2) {
        executionTime = arg1;
        result = arg2;
    }

    public boolean execute() {
        try {
            if (executionTime == EXECUTION_TIME_INFINITY) while (true);
            else Thread.sleep(executionTime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Function getFunction(int i){
        int DEFAULT_EXECUTION_TIME = 4000;
        int FAST_EXECUTION_TIME = 500;
        switch (i){
            case 1:
                return new Function(DEFAULT_EXECUTION_TIME,true);
            case 2:
                return new Function(DEFAULT_EXECUTION_TIME, false);
            case 3:
                return new Function(EXECUTION_TIME_INFINITY, true);
            case 4:
                return new Function(EXECUTION_TIME_INFINITY, false);
            case 5:
                return new Function(FAST_EXECUTION_TIME, true);
        }
        return new Function(DEFAULT_EXECUTION_TIME,true);
    }
}
