package logic;

import java.util.*;

public class Group extends Tournament {

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

    // create a function that takes unpackedGroup, Scanner sc and int noOfPlayersProgressing
    // called setGroupScores which iterates through all the players in the unpackedGroup
    // prints the standings, asks for confirmation and returns the same group but with scores

    public static HashMap<String, Integer> setGroupScores(ArrayList<Player> unpackedGroup, Scanner sc, int noOfPlayersProgressing) {
        HashMap<String, Integer> groupWithScores = new HashMap<>();
        for (Player player : unpackedGroup) {
            System.out.println("Set a score for " + player.getName() + ":");
            int score = setPlayerScore(sc);
            player.setPlayerScore(score);
            groupWithScores.put(player.getName(), score);
        }
        printStandings(groupWithScores);
        System.out.println("This means that the following players will proceed to the next round: ");
        ArrayList<Player> playersProgressing = new ArrayList<>();
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .limit(noOfPlayersProgressing)
                .forEach(k -> {
                    // Find and add a reference to the original Player object with the same name
                    for (Player player : unpackedGroup) {
                        if (player.getName().equals(k.getKey())) {
                            playersProgressing.add(player);
                            break;
                        }
                    }
                });
        System.out.println(playersProgressing);
        boolean repeatProcess;
        do {
            System.out.println("Does this look right to you? (Y or N)");
            String showPlayerAnswer = ConsoleLogic.answerYesOrNo(sc);
            repeatProcess = showPlayerAnswer.equalsIgnoreCase("n");
            if (repeatProcess) {
                setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
            }
        } while (repeatProcess);

        // If confirmed, set eliminated to true for all players not in playersProgressing
        for (Player player : unpackedGroup) {
            if (!playersProgressing.contains(player)) {
                player.setEliminated(true);
            }
        }
        return groupWithScores;
    }

    private static int setPlayerScore(Scanner sc) {
        int score = 0;
        try {
            score = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. You must enter a valid number as score.");
            sc.next(); // discard the invalid input
            score = sc.nextInt();
        }
        return score;
    }

    private static void printStandings(HashMap<String, Integer> groupWithScores) {
        System.out.println("Here are the standings for this group: ");
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
    }
}
