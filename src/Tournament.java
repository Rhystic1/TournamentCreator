import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Tournament {

	private List<String> players;
	private ArrayList<String> remainingPlayers;

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
			players = Collections.unmodifiableList(createPlayers(s, noOfPlayers));
			System.out.println("Before we continue...");
			System.out.println("Do you want to see a list of the players? Y or N");
			// WIP - Show players on-demand
			showPlayers(players);

			// Creating a copy of the list - will be used to avoid duplicates
			remainingPlayers = new ArrayList<>(players);

			// Creating the group stage - for now the default (and only) settings are groups
			// of 4 players
			int playersPerGroup = 4;
			HashMap<Integer, ArrayList<String>> groups = createGroups(noOfPlayers, remainingPlayers, playersPerGroup);

			System.out.println("Here are the groups:");
			System.out.println("");

			for (int i = 0; i < groups.size(); i++) {
				System.out.println("Group " + (i + 1) + ":");
				System.out.println(groups.get(i));
			}
		}
		return this;
	}

	private HashMap<Integer, ArrayList<String>> createGroups(int noOfPlayers, ArrayList<String> remainingPlayers,
			int playersPerGroup) {
		HashMap<Integer, ArrayList<String>> groups = null;
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

	private ArrayList<String> createPlayers(Scanner s, int noOfPlayers) {
		ArrayList<String> players = new ArrayList<>();
		s.nextLine();

		for (int i = 0; i < noOfPlayers; i++) {
			System.out.println("Insert the name of Player " + (i + 1));
			String playerName = s.nextLine();
			players.add(playerName);
		}
		return players;
	}

	private void showPlayers(List<String> players) {
		System.out.println("Here are the players:");
		System.out.println(players);
	}

	private ArrayList<String> createGroup(ArrayList<String> remainingPlayers) {
		Random rand = new Random();
		int noOfPlayers = 4;
		ArrayList<String> group = new ArrayList<>();
		for (int i = 0; i < noOfPlayers; i++) {
			int randomIndex = rand.nextInt(remainingPlayers.size());
			group.add(remainingPlayers.get(randomIndex));
			remainingPlayers.remove(randomIndex);
		}
		return group;
	}

}
