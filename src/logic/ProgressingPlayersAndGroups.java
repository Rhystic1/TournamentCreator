package logic;

import java.util.*;

public class ProgressingPlayersAndGroups {
    private final HashMap<String, Integer> groupWithScores;
    private final List<String> playersProgressing;

    public ProgressingPlayersAndGroups(HashMap<String, Integer> groupWithScores, List<String> playersProgressing) {
        this.groupWithScores = groupWithScores;
        this.playersProgressing = playersProgressing;
    }

    public static ProgressingPlayersAndGroups setGroupScores(ArrayList<String> unpackedGroup, Scanner sc, int noOfPlayersProgressing) {
        HashMap<String, Integer> groupWithScores = new HashMap<>();
        for (String player : unpackedGroup) {
            System.out.println("Set a score for " + player + ":");
            int score = 0;
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
        ProgressingPlayersAndGroups playersProgressing = selectProgressingPlayers(noOfPlayersProgressing, groupWithScores);

        System.out.println("Does this look right to you? (Y or N)");
        String showPlayerAnswer = Tournament.answerYesOrNo(sc);
        if (showPlayerAnswer.equalsIgnoreCase("n")) {
            setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
        }
        return new ProgressingPlayersAndGroups(groupWithScores, playersProgressing.getPlayersProgressing());
    }

    static ProgressingPlayersAndGroups selectProgressingPlayers(int noOfPlayersProgressing, HashMap<String, Integer> groupWithScores) {
        List<String> playersProgressing = new ArrayList<>();
        groupWithScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .limit(noOfPlayersProgressing)
                .forEach(k -> playersProgressing.add(k.getKey()));
        System.out.println(playersProgressing);
        Scanner s = new Scanner(System.in);
        return new ProgressingPlayersAndGroups(groupWithScores, playersProgressing);
    }

    public HashMap<String, Integer> getGroupWithScores() {
        return groupWithScores;
    }

    public List<String> getPlayersProgressing() {
        return playersProgressing;
    }
}

