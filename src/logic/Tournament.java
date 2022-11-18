package logic;
import java.util.*;

public class Tournament {

	private List<String> players;
	private ArrayList<String> remainingPlayers;

	private HashMap<Integer, ArrayList<String>> groups;
	
	public Tournament generateTournament() {
		try (Scanner s = new Scanner(System.in)) {

			// Initializing the tournament size
			System.out.println("How many players?");
			int noOfPlayers = s.nextInt();
			if (noOfPlayers <= 1) {
				// WIP - Proper error handling
				System.out.println("You cannot create a tournament with just yourself! That is not a tournament!");
				System.exit(0);
			}

			players = Collections.unmodifiableList(Players.createPlayers(s, noOfPlayers));

			System.out.println("Before we continue...");
			System.out.println("Do you want to see a list of the players? Y or N");
			String showPlayerAnswer = s.next().toLowerCase();
			boolean validAnswer = false;
			while(!validAnswer) {
				switch (showPlayerAnswer) {
					case "y":
						Players.showPlayers(players);
						validAnswer = true;
						break;
					case "n":
						validAnswer = true;
						break;
					default:
						System.out.println("Invalid selection. Please answer either (y)es or (n)o.");
						showPlayerAnswer = s.next().toLowerCase();
						break;
				}
			}

			// Creating a copy of the list - will be used to avoid duplicates
			remainingPlayers = new ArrayList<>(players);

			// Creating the group stage - for now the default (and only) settings are groups
			// of 4 players
			int playersPerGroup = 4;
			groups = Groups.createGroups(noOfPlayers, remainingPlayers, playersPerGroup);

			System.out.println("Here are the groups:");
			System.out.println("");

			for (int i = 0; i < groups.size(); i++) {
				System.out.println("Group " + (i + 1) + ":");
				System.out.println(groups.get(i));
			}
		}
		return this;
	}
	
	public ArrayList<String> getGroup(int index) {
		return groups.get(index);
	}
}