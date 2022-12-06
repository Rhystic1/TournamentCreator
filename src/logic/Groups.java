package logic;

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

    public static HashMap<String, Integer> setGroupScores(ArrayList<String> unpackedGroup, Scanner sc, int noOfPlayersProgressing) {
        HashMap<String, Integer> groupWithScores = new HashMap<>();
        for (String player : unpackedGroup) {
            System.out.println("Set a score for " + player + ":");
            Integer score = 0;
            try {
                score = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. You must enter a valid number as score.");
                sc.next(); // discard the invalid input
                score = sc.nextInt();
            }
            groupWithScores.put(player, score);
        }
        System.out.println("Here are the standings for this group: ");
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));

        System.out.println("This means that the following players will proceed to the next round: ");
        List<String> playersProgressing = new ArrayList<>();
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .limit(noOfPlayersProgressing)
                .forEach(k -> playersProgressing.add(k.getKey()));
        System.out.println(playersProgressing);

        System.out.println("Does this look right to you? (Y or N)");
        String showPlayerAnswer;
        do {
            showPlayerAnswer = sc.next();
            if (!showPlayerAnswer.equalsIgnoreCase("y") && !showPlayerAnswer.equalsIgnoreCase("n")) {
                System.out.println("Invalid selection. Please answer either (y)es or (n)o.");
            }
        } while (!showPlayerAnswer.equalsIgnoreCase("y") && !showPlayerAnswer.equalsIgnoreCase("n"));
        if (showPlayerAnswer.equalsIgnoreCase("n")) {
            setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
        }
        return groupWithScores;
    }
}
