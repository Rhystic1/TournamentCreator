package logic;

import java.util.*;

public class PlayerSetup {
    static List<String> createPlayers(Scanner s, int noOfPlayers) {
        Set<String> players = new HashSet<>();
        s.nextLine();

        for (int i = 0; i < noOfPlayers; i++) {
            System.out.println(String.format("Insert the name of Player %d", i + 1));
            String playerName = s.nextLine();
            if (players.contains(playerName)) {
                System.out.println(String.format("Player %s already exists. You must assign a unique name for each player.", playerName));
                i--;
                continue;
            }
            if ((playerName == null) || (playerName.startsWith(" "))) {
                System.out.println("Invalid name: A player name must contain (and start with) at least a character.");
                i--;
                continue;
            }
            players.add(playerName);
        }
        return new ArrayList<>(players);
    }

    static void showPlayers(List<String> players) {
        System.out.println("Here are the players:");
        for (String player : players) {
            System.out.println(player);
        }
    }
}

