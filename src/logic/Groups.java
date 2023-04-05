package logic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groups extends Tournament {

    public static HashMap<Integer, ArrayList<String>> createGroups(int noOfPlayers, ArrayList<Player> remainingPlayers, int playersPerGroup) {
        HashMap<Integer, ArrayList<String>> groups = null;
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
            groups.put(i, createGroup(remainingPlayers, playersPerGroup));
        }
        return groups;
    }

    private static ArrayList<String> createGroup(ArrayList<Player> remainingPlayers, int playersPerGroup) {
        Random rand = new Random();
        ArrayList<String> group = new ArrayList<>();
        for (int i = 0; i < playersPerGroup; i++) {
            if ((group.size() != playersPerGroup) && (!remainingPlayers.isEmpty())) { // Avoid going out of bounds
                int randomIndex = rand.nextInt(remainingPlayers.size());
                group.add(remainingPlayers.get(randomIndex).getName());
                remainingPlayers.remove(randomIndex);
            }
            // Handle remaining players for odd numbers
            if (((group.size() == playersPerGroup) &&
                    (remainingPlayers.size() < playersPerGroup) && remainingPlayers.size() != 0)) {
                group.add(remainingPlayers.get(0).getName());
                remainingPlayers.remove(0);
            }
        }
        return group;
    }

    public static ArrayList<Player> unpackGroup(Integer groupNumber, HashMap<Integer, ArrayList<String>> groups, List<Player> players) {
        ArrayList<String> originalGroup = new ArrayList<>();
        ArrayList<Player> unpackedGroup = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        originalGroup.add(groups.get(groupNumber).toString());
        for (Player player : players) {
            pattern = Pattern.compile(player.getName());
            matcher = pattern.matcher(originalGroup.get(0));
            if (matcher.find()) {
                unpackedGroup.add(player);
            }
        }
        return unpackedGroup;
    }

    public static boolean isPlayerFromSameGroup(String player1, Player player2) {
        // Check if the two players are from the same group
        // (this assumes that the groups map is already populated with the players)
        for (ArrayList<String> group : groups.values()) {
            if (group.contains(player1) && group.contains(player2)) {
                return true;
            }
        }
        return false;
    }

}
