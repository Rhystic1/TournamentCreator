package logic;

public class Player implements IPlayer {
    private String name;
    private int score;
    private int startingGroup = Integer.parseInt(null); // Workaround as primitives cannot be nullable
    private Phase lastPlayerPhase;
    private boolean isEliminated = false;

    public Player(String name, int startingGroup, Phase lastPlayerPhase) {
        this.name = name;
        this.startingGroup = startingGroup;
        this.lastPlayerPhase = lastPlayerPhase;
    }

    public Player(String name, Phase lastPlayerPhase) {
        this.name = name;
        this.lastPlayerPhase = lastPlayerPhase;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerScore() {
        return this.score;
    }

    public void setPlayerScore(int score) {
        this.score = score;
    }

    public int getStartingGroup() {
        return this.startingGroup;
    }

    public void setStartingGroup(int startingGroup) {
        this.startingGroup = startingGroup;
    }

    public void setLastPlayerPhase(Phase lastPlayerPhase) { this.lastPlayerPhase = lastPlayerPhase; }

    public Phase getLastPlayerPhase() { return this.lastPlayerPhase = lastPlayerPhase; }


    public boolean isEliminated() {
        return this.isEliminated;
    }

    public void setEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

}
