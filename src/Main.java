import java.util.*;

/* Stuff to do: implement next stages, move most methods to their own classes, etc. */

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Initializing the tournament size
        System.out.println("How many players?");
        int noOfPlayers = s.nextInt();
        if (noOfPlayers <= 1) {
            // WIP - Proper error handling
            System.out.println("You cannot create a tournament with just yourself! That is not a tournament!");
            System.exit(0);
        }
        List<String> players = Players.createPlayers(s, noOfPlayers);

        System.out.println("Before we continue...");
        System.out.println("Do you want to see a list of the players? Y or N");
        // WIP - Show players on-demand
        Players.showPlayers(players);

        // Creating a copy of the list - will be used to avoid duplicates
        List<String> remainingPlayers = players;

        // Creating the group stage - for now the default (and only) settings are groups of 4 players
        int playersPerGroup = 4;
        Map groups = Groups.createGroups(noOfPlayers, remainingPlayers, playersPerGroup);

        System.out.println("Here are the groups:");
        System.out.println("");

        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i+1) + ":");
            System.out.println(groups.get(i));
        }
    }
}