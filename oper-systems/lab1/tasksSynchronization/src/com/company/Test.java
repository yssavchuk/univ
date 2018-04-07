package com.company;

public class Test {
    public static void main(String [] args){
        //For interrupt cancellation:
        //For periodic prompt cancellation do additionally.
        interruptCancellation();
        promptCancellation();

    }

    private static void interruptCancellation(){
        Manager.setUserPrompt(Integer.MAX_VALUE);
        System.out.println("-------------------------------------------------");
        System.out.println("1. f finishes before g with non-zero value. Verify the result.");
        System.out.println("Test 1 f(4000,false) * g(-1,true)");
        Manager.main(new String[]{"2","3"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------

        System.out.println("-------------------------------------------------");
        System.out.println("2. g finishes before f with non-zero value. Verify the result.");
        System.out.println("Test 2 f(-1,true) * g(4000,false)");
        Manager.main(new String[]{"3","2"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------

        System.out.println("-------------------------------------------------");
        System.out.println("3. f finishes with zero value, g hangs. Verify result/time.");
        System.out.println("Test 3 f(4000,false) * g(-1,true)");
        Manager.main(new String[]{"2","3"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------

        System.out.println("-------------------------------------------------");
        System.out.println("4. g finishes with zero value, f hangs. Verify result/time.");
        System.out.println("Test 4 f(-1,true) * g(4000,false)");
        Manager.main(new String[]{"3","2"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------

        System.out.println("-------------------------------------------------");
        System.out.println("5. f finishes with non-zero value, g hangs. Check cancellation, verify status.");
        System.out.println("Test 5 f(4000,true) * g(-1,true)");
        Manager.main(new String[]{"1","3"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------

        System.out.println("-------------------------------------------------");
        System.out.println("6. g finishes with non-zero value, f hangs. Check cancellation, verify status.");
        System.out.println("Test 6 f(-1,true) * g(4000,true)");
        Manager.main(new String[]{"3","1"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void promptCancellation(){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("PROMPT CANCELLATION");
        Manager.setUserPrompt(6000);

        System.out.println("1. Computation finishes before first prompt. Verify the result/time.");
        System.out.println("Test 1 f(4000,true) * g(4000,true)");
        Manager.main(new String[]{"1","1"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Manager.setUserPrompt(3000);
        System.out.println("2. Computation finishes during the prompt. Verify noninterruption of dialog,\n" +
                "correct report of result despite cancellation, the result/time.");
        System.out.println("Test 2 f(4000,true) * g(4000,true)");
        Manager.main(new String[]{"1","1"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("3. f finishes with non-zero value, g hangs. Verify time between answer “con-"+
                "tinue” and next prompt.");
        System.out.println("Test 1 f(4000,true) * g(-1,true)");
        Manager.main(new String[]{"1","3"});
        System.out.println("status = " + Manager.getStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
