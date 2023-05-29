package logic;
import java.util.*;

public class GroupStage extends Tournament {
    protected static int groupStageSetup(int noOfPlayers, Scanner s, ArrayList<Player> remainingPlayers) {
        int playersPerGroup = 0;
        System.out.println("How many players would you like per group?");
        System.out.println("Note that if you have an odd number of players, some groups may not respect this setting.");
        while ((playersPerGroup >= noOfPlayers) || (playersPerGroup == 0)) {
            try {
                playersPerGroup = s.nextInt();
                if (playersPerGroup >= noOfPlayers) {
                    System.out.println("Invalid number. The number of players per group must be less than the number of total players.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number. You must input a valid integer number.");
                s.nextLine(); // consume the invalid input
            }
        }
        groups = Group.createGroups(noOfPlayers, remainingPlayers, playersPerGroup);
        return playersPerGroup;
    }
}
