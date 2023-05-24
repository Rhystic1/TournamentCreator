package logic;

import java.util.*;

public class PlayerSetup extends Tournament {
    static List<Player> createPlayers(Scanner s, int noOfPlayers) {
        Set<Player> players = new HashSet<>();
        s.nextLine();

        for (int i = 0; i < noOfPlayers; i++) {
            System.out.println(String.format("Insert the name of Player %d", i + 1));
            String playerName = s.nextLine();
            Player player = new Player(playerName);
            if (players.contains(player)) {
                System.out.println(String.format("Player %s already exists. You must assign a unique name for each player.", playerName));
                i--;
                continue;
            }
            if ((playerName == null) || (playerName.startsWith(" "))) {
                System.out.println("Invalid name: A player name must contain (and start with) at least a character.");
                i--;
                continue;
            }
            players.add(player);
        }
        return new ArrayList<>(players);
    }

    static void showPlayers(List<Player> players) {
        System.out.println("Here are the players:");
        for (Player player : players) {
            System.out.println(player.getName());
        }
    }
}

