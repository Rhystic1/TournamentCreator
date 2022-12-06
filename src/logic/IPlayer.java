package logic;

public interface IPlayer {
    String getName();

    void setName(String name);

    int getPlayerScore();

    void setPlayerScore(int score);

    int getStartingGroup();

    public void setStartingGroup(int startingGroup);

    boolean isEliminated();

    void setEliminated(boolean isEliminated);
}
