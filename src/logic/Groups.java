package logic;
import java.util.*;

public class Groups {
	
    public static HashMap<Integer, ArrayList<String>> createGroups(int noOfPlayers, ArrayList<String> remainingPlayers, int playersPerGroup) {
    	HashMap<Integer, ArrayList<String>> groups = null;
        int noOfGroups;
        double noOfGroupsOdd;
        if (noOfPlayers % playersPerGroup == 0) {
            noOfGroups = noOfPlayers / playersPerGroup;
        } else {
            noOfGroupsOdd = Math.ceil((double)noOfPlayers / ((double)playersPerGroup + 1));
            noOfGroups = (int) noOfGroupsOdd;
        }
        groups = new HashMap<>(noOfGroups);
        for (int i = 0; i < noOfGroups; i++) {
            groups.put(i, createGroup(remainingPlayers));
        }
        return groups;
    }

    private static ArrayList<String> createGroup(ArrayList<String> remainingPlayers) {
        Random rand = new Random();
        int noOfPlayers = 4;
        ArrayList<String> group = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            if ((group.size() != noOfPlayers) && (remainingPlayers.size() != 0)) {
                int randomIndex = rand.nextInt(remainingPlayers.size());
                group.add(remainingPlayers.get(randomIndex));
                remainingPlayers.remove(randomIndex);
            }
        }
        return group;
    }
}
