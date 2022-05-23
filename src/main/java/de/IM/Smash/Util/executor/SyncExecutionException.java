package de.IM.Smash.Util.executor;

public class SyncExecutionException extends Exception {
    private final String info;

    public SyncExecutionException(String info) {
        this.info = info;
    }

    public void printInfo() {
        //TODO
        System.out.println("[CraftAttack] Reporting... " + info + " Stack Trace is next");
        printStackTrace();
        System.out.println("[CraftAttack] Finished reporting");
    }
}
