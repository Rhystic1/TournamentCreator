package logic;

public interface IPlayer {
    String getName();

    void setName(String name);

    int getPlayerScore();

    void setPlayerScore(int score);

    int getStartingGroup();

    void setStartingGroup(int startingGroup);

    void setLastPlayerPhase(Phase lastPlayerPhase);
    Phase getLastPlayerPhase();

    boolean isEliminated();

    void setEliminated(boolean isEliminated);
}
