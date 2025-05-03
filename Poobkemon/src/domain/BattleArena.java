package domain;

public class BattleArena{
    public int Turn = 0;
    public static final int MaxTime = 20;
    private boolean isFinishedBattle = false;
    private boolean isPaused = false;
    private String typeOfBattle; // 0: normal, 1: survival

    public BattleArena(String typeOfBattle) {
        this.typeOfBattle = typeOfBattle;
    }

    public void startBattle() {
        // Start the battle logic here
        // This method will be called when the battle starts
        // You can implement the battle logic here
    }

    public void 
}
