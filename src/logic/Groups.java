package logic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groups extends Tournament {

    public static HashMap<Integer, ArrayList<String>> createGroups(int noOfPlayers, ArrayList<String> remainingPlayers, int playersPerGroup) {
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

    private static ArrayList<String> createGroup(ArrayList<String> remainingPlayers, int playersPerGroup) {
        Random rand = new Random();
        ArrayList<String> group = new ArrayList<>();
        for (int i = 0; i < playersPerGroup; i++) {
            if ((group.size() != playersPerGroup) && (!remainingPlayers.isEmpty())) {
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

    public static ArrayList<String> unpackGroup(Integer groupNumber, HashMap<Integer, ArrayList<String>> groups, List<String> players) {
        ArrayList<String> originalGroup = new ArrayList<>();
        ArrayList<String> unpackedGroup = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        originalGroup.add(groups.get(groupNumber).toString());
        for (String player :
                players) {
            pattern = Pattern.compile(player);
            matcher = pattern.matcher(originalGroup.get(0));
            if (matcher.find()) {
                unpackedGroup.add(player);
            }
        }
        return unpackedGroup;
    }

    public static HashMap<Integer, ArrayList<String>> shufflePlayers(Scanner s, HashMap<Integer, ArrayList<String>> groups, List<String> playersProgressing) {
        System.out.println("Do you want to avoid matching players from the same group again? (y/n)");
        String response = Tournament.answerYesOrNo(s);
        boolean avoidSameGroup = response.equalsIgnoreCase("y");

        // Shuffle the players in the list of players who are progressing to the next round
        Collections.shuffle(playersProgressing);

        int groupIndex = 0;
        HashMap<Integer, ArrayList<String>> nextStageDraws = null;
        for (String player : playersProgressing) {
            // Check if the current player is from the same group as the
            // previous player assigned to the same match
            if (avoidSameGroup) {
                String previousPlayer = groups.get(groupIndex).get(groups.get(groupIndex).size() - 1);
                if (previousPlayer != null && isPlayerFromSameGroup(previousPlayer, player)) {
                    // If the current player is from the same group as the previous player,
                    // shuffle the list of players and try again
                    Collections.shuffle(playersProgressing);
                    shufflePlayers(s, groups, playersProgressing);
                    return nextStageDraws;
                }
            }
            nextStageDraws = new HashMap<>();
            nextStageDraws.get(groupIndex).add(player);
            groupIndex++;
            if (groupIndex >= nextStageDraws.size()) {
                groupIndex = 0;
            }
        }
        printGroups(nextStageDraws);
        return nextStageDraws;
    }

    private static boolean isPlayerFromSameGroup(String player1, String player2) {
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
