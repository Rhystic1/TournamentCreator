package logic;

import java.util.*;

public class ProgressingPlayersAndGroups {
    private final HashMap<String, Integer> groupWithScores;
    private final ArrayList<Player> playersProgressing;

    public ProgressingPlayersAndGroups(HashMap<String, Integer> groupWithScores, ArrayList<Player> playersProgressing) {
        this.groupWithScores = groupWithScores;
        this.playersProgressing = playersProgressing;
    }

    public ProgressingPlayersAndGroups() {
        this.groupWithScores = getGroupWithScores();
        this.playersProgressing = getPlayersProgressing();
    }

    public ProgressingPlayersAndGroups setGroupScores(ArrayList<Player> unpackedGroup, Scanner sc, int noOfPlayersProgressing) {
        HashMap<String, Integer> groupWithScores = new HashMap<>();
        for (Player player : unpackedGroup) {
            System.out.println("Set a score for " + player.getName() + ":");
            int score = getScoreFromUser(sc);
            player.setPlayerScore(score);
            groupWithScores.put(player.getName(), score);
        }
        printStandings(groupWithScores);

        System.out.println("This means that the following players will proceed to the next round: ");
        ProgressingPlayersAndGroups playersProgressing = selectProgressingPlayers(noOfPlayersProgressing, groupWithScores);

        boolean repeatProcess;
        do {
            System.out.println("Does this look right to you? (Y or N)");
            String showPlayerAnswer = Tournament.answerYesOrNo(sc);
            repeatProcess = showPlayerAnswer.equalsIgnoreCase("n");
            if (repeatProcess) {
                setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
            }
        } while (repeatProcess);

        // Update players' elimination status
        for (Player player : unpackedGroup) {
            if (!playersProgressing.getPlayersProgressing().contains(player.getName())) {
                player.setEliminated(true);
            }
        }

        return new ProgressingPlayersAndGroups(groupWithScores, playersProgressing.getPlayersProgressing());
    }

    private static int getScoreFromUser(Scanner sc) {
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

    ProgressingPlayersAndGroups selectProgressingPlayers(int noOfPlayersProgressing, HashMap<String, Integer> groupWithScores) {
        ArrayList<Player> playersProgressing = new ArrayList<>();
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .limit(noOfPlayersProgressing)
                .forEach(k -> playersProgressing.add(new Player(k.getKey())));
        System.out.println(playersProgressing);
        Scanner s = new Scanner(System.in);
        return new ProgressingPlayersAndGroups(groupWithScores, playersProgressing);
    }

    public HashMap<String, Integer> getGroupWithScores() {
        return groupWithScores;
    }

    public ArrayList<Player> getPlayersProgressing() {
        return this.playersProgressing;
    }
}

