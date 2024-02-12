package logic;

import java.util.*;

public class Knockout extends Tournament {

    public static HashMap<Integer, ArrayList<Player>> shufflePlayers(Scanner s, HashMap<Integer, ArrayList<Player>> groups, ArrayList<Player> playersProgressing, boolean avoidSameGroup) {
        // Shuffle the players in the list of players who are progressing to the next round
        Collections.shuffle(playersProgressing);

        int groupIndex = 0;
        HashMap<Integer, ArrayList<Player>> nextStageDraws = createNextStageDraws(playersProgressing);
        // Create a temporary data structure to keep track of which players have been added to each draw
        HashMap<Integer, HashSet<Integer>> drawGroups = new HashMap<>();
        for (int i = 0; i < nextStageDraws.size(); i++) {
            drawGroups.put(i, new HashSet<>());
        }

        for (Player currentPlayer : playersProgressing) {
            int currentPlayerGroup = 0;
            if (avoidSameGroup) {
                // Find the group number of the current player
                currentPlayerGroup = -1;
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).contains(currentPlayer)) {
                        currentPlayerGroup = i;
                        break;
                    }
                }
                // Check if the current draw already contains a player from the same group
                if (drawGroups.get(groupIndex).contains(currentPlayerGroup)) {
                    // If the current draw already contains a player from the same group,
                    // move to the next draw
                    groupIndex++;
                    if (groupIndex >= nextStageDraws.size()) {
                        groupIndex = 0;
                    }
                } else {
                    nextStageDraws.get(groupIndex).add(currentPlayer);
                    drawGroups.get(groupIndex).add(currentPlayerGroup);
                    groupIndex++;
                    if (groupIndex >= nextStageDraws.size()) {
                        groupIndex = 0;
                    }
                }
            } else {
                nextStageDraws.get(groupIndex).add(currentPlayer);
                drawGroups.get(groupIndex).add(currentPlayerGroup);
                groupIndex++;
                if (groupIndex >= nextStageDraws.size()) {
                    groupIndex = 0;
                }
            }
        }
        printGroups(nextStageDraws);
        return nextStageDraws;
    }


    private static HashMap<Integer, ArrayList<Player>> createNextStageDraws(ArrayList<Player> playersProgressing) {
        return Group.createGroups(playersProgressing.size(), playersProgressing, 2);
    }

    private static boolean isSameGroup(Player previousPlayer, Player currentPlayer) {
        return previousPlayer != null && Group.isPlayerFromSameGroup(previousPlayer, currentPlayer);
    }

    void playKnockoutPhase(Scanner s, HashMap<Integer, ArrayList<Player>> groups, ArrayList<Player> playersProgressing) {
        System.out.println("Do you want to avoid matching players from the same group again? (y/n)");
        String response = ConsoleLogic.answerYesOrNo(s);
        boolean avoidSameGroup = response.equalsIgnoreCase("y");
        HashMap<Integer, ArrayList<Player>> knockoutPairings = shufflePlayers(s, groups, playersProgressing, avoidSameGroup);

    }


}
