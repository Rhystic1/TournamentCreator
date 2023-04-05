package logic;

import java.util.*;

public class Knockout extends Tournament {

    public static HashMap<Integer, ArrayList<String>> shufflePlayers(Scanner s, HashMap<Integer, ArrayList<String>> groups, ArrayList<Player> playersProgressing, boolean avoidSameGroup) {


        // Shuffle the players in the list of players who are progressing to the next round
        Collections.shuffle(playersProgressing);

        int groupIndex = 0;
        HashMap<Integer, ArrayList<String>> nextStageDraws = null;
        for (Player player : playersProgressing) {
            // Check if the current player is from the same group as the
            // previous player assigned to the same match
            if (avoidSameGroup) {
                String previousPlayer = groups.get(groupIndex).get(groups.get(groupIndex).size() - 1);
                if (previousPlayer != null && Groups.isPlayerFromSameGroup(previousPlayer, player)) {
                    // If the current player is from the same group as the previous player,
                    // shuffle the list of players and try again
                    Collections.shuffle(playersProgressing);
                    shufflePlayers(s, groups, playersProgressing, avoidSameGroup);
                    printGroups(nextStageDraws);
                    return nextStageDraws;
                }
            } else {
                nextStageDraws = new HashMap<>();
                nextStageDraws.get(groupIndex).add(String.valueOf(player));
                groupIndex++;
                if (groupIndex >= nextStageDraws.size()) {
                    groupIndex = 0;
                }
            }
        }
        printGroups(nextStageDraws);
        return nextStageDraws;
    }

    void playKnockoutPhase(Scanner s, HashMap<Integer, ArrayList<String>> groups, ArrayList<Player> playersProgressing, ArrayList<Player> groupResults) {
        System.out.println("Do you want to avoid matching players from the same group again? (y/n)");
        String response = Tournament.answerYesOrNo(s);
        boolean avoidSameGroup = response.equalsIgnoreCase("y");
        HashMap<Integer, ArrayList<String>> knockoutPairings = shufflePlayers(s, groups, playersProgressing, avoidSameGroup);

    }


}
