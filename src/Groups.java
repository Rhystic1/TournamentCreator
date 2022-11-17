import java.util.*;

public class Groups {
    static Map createGroups(int noOfPlayers, List<String> remainingPlayers, int playersPerGroup) {
        Map<Integer, List<String>> groups = null;
        if (noOfPlayers % playersPerGroup == 0) {
            int noOfGroups = noOfPlayers / 4;
            groups = new HashMap<>(noOfGroups);
            for (int i = 0; i < noOfGroups; i++) {
                groups.put(i, createGroup(remainingPlayers));
            }
        } else {
            // WIP - handle odd numbers for groups
        }
        return groups;
    }

    private static List<String> createGroup(List<String> remainingPlayers) {
        Random rand = new Random();
        int noOfPlayers = 4;
        List<String> group = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++)
        {
            int randomIndex = rand.nextInt(remainingPlayers.size());
            group.add(remainingPlayers.get(randomIndex));
            remainingPlayers.remove(randomIndex);
        }
        return group;
    }
}
