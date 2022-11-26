package logic;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groups {

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

    public static HashMap<String, Integer> setGroupScores(ArrayList<String> unpackedGroup, Scanner sc) {
        HashMap<String, Integer> groupWithScores = new HashMap<>();
        for (String player :
                unpackedGroup) {
            System.out.println("Set a score for " + player + ":");
            Integer score = sc.nextInt();
            groupWithScores.put(player, score);
        }

        System.out.println("Here are the standings for this group: ");
        for (int i = 0; i < groupWithScores.size(); i++)
        {
            System.out.println(i);
        }
        return groupWithScores;
    }
}
