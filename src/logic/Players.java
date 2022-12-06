package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Players {
    static List<String> createPlayers(Scanner s, int noOfPlayers) {
        List<String> players = new ArrayList<>();
        s.nextLine();

        for (int i = 0; i < noOfPlayers; i++) {
            System.out.println("Insert the name of Player " + (i + 1));
            String playerName = s.nextLine();
            if (players.contains(playerName)) {
                System.out.println("Player " + "\"" + playerName + "\"" + " already exists. You must assign a unique name for each player.");
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
        return players;
    }

    static void showPlayers(List<String> players) {
        System.out.println("Here are the players:");
        for (String player :
                players) {
            System.out.println(player);
        }
    }
}
