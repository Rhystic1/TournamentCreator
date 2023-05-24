package logic;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groups extends Tournament {

    public static HashMap<Integer, ArrayList<Player>> createGroups(int noOfPlayers, ArrayList<Player> remainingPlayers, int playersPerGroup) {
        HashMap<Integer, ArrayList<Player>> groups = null;
        int noOfGroups;
        double noOfGroupsOdd;
        if (noOfPlayers % playersPerGroup == 0) {
            noOfGroups = noOfPlayers / playersPerGroup;
        } else {
            noOfGroupsOdd = Math.ceil((double) noOfPlayers / ((double) playersPerGroup + 1));
            noOfGroups = (int) noOfGroupsOdd;
        }
        groups = new HashMap<>(noOfGroups);
        for (int i = 0; i < noOfGroups; i++) {
            // Get a single player from the remaining players list
            ArrayList<Player> player = createGroup(remainingPlayers, playersPerGroup);
            // Put the player into the groups map with the index as the key
            groups.put(i, player);
        }
        return groups;
    }

    private static ArrayList<Player> createGroup(ArrayList<Player> remainingPlayers, int playersPerGroup) {
        Random rand = new Random();
        ArrayList<Player> group = new ArrayList<>();
        for (int i = 0; i < playersPerGroup; i++) {
            if ((group.size() != playersPerGroup) && (!remainingPlayers.isEmpty())) { // Avoid going out of bounds
                int randomIndex = rand.nextInt(remainingPlayers.size());
                group.add(remainingPlayers.get(randomIndex));
                remainingPlayers.remove(randomIndex);
            }
            // Handle remaining players for odd numbers
            if (((group.size() == playersPerGroup) &&
                    (remainingPlayers.size() < playersPerGroup) && remainingPlayers.size() != 0)) {
                group.add(remainingPlayers.get(0));
                remainingPlayers.remove(0);
            }
        }
        return group;
    }

    public static ArrayList<Player> unpackGroup(Integer groupNumber, HashMap<Integer, ArrayList<Player>> groups, List<Player> players) {
        ArrayList<Player> unpackedGroup = new ArrayList<>();
        unpackedGroup.addAll(groups.get(groupNumber));
        return unpackedGroup;
    }


    public static boolean isPlayerFromSameGroup(Player player1, Player player2) {
        // this assumes that the groups map is already populated with the players
        for (ArrayList<Player> group : groups.values()) {
            if (group.contains(player1) && group.contains(player2)) {
                return true;
            }
        }
        return false;
    }

}
