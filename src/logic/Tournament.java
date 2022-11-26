package logic;
import java.util.*;

public class Tournament {

	private List<String> players;

	private HashMap<Integer, ArrayList<String>> groups;
	
	private Audit auditHistory = new Audit();
	
	public Tournament generateTournament() {
		auditHistory.append("Started at " + new Date().toString());
		int noOfPlayers;
		int playersPerGroup;
		try (Scanner s = new Scanner(System.in)) {

			// Initializing the tournament size
			noOfPlayers = setPlayerCount(s);
			askForPlayerList(s);

			// Creating a copy of the list - will be used to avoid duplicates
			ArrayList<String> remainingPlayers = new ArrayList<>(players);

			groupStageSetup(noOfPlayers, s, remainingPlayers);
			printGroups();

			System.out.println("After your first group has played a match, press any key to continue and set the scores...");
			setScores();
		}
		return this;
	}

	private int setPlayerCount(Scanner s) {
		int noOfPlayers;
		System.out.println("How many players?");
		noOfPlayers = s.nextInt();
		auditHistory.append("Created tournament of "+ noOfPlayers + " players.");
		if (noOfPlayers <= 1) {
			// WIP - Proper error handling
			System.out.println("You cannot create a tournament with just yourself!");
			System.exit(0);
		}

		players = Collections.unmodifiableList(Players.createPlayers(s, noOfPlayers));
		return noOfPlayers;
	}

	private void printGroups() {
		System.out.println("Here are the groups:");
		System.out.println("");

		for (int i = 0; i < groups.size(); i++) {
			System.out.println("Group " + (i + 1) + ":");
			System.out.println(groups.get(i));
		}
	}

	private void groupStageSetup(int noOfPlayers, Scanner s, ArrayList<String> remainingPlayers) {
		int playersPerGroup;
		System.out.println("How many players would you like per group?");
		System.out.println("Note that if you have an odd number of players, some groups may not respect this setting.");
		playersPerGroup = s.nextInt();
		groups = Groups.createGroups(noOfPlayers, remainingPlayers, playersPerGroup);
	}

	private void setScores() {
		Scanner sc = new Scanner(System.in);
		sc.next();

		HashMap<Integer, HashMap> groupsWithScores = new HashMap<>();

		for (int i = 0; i < groups.size(); i++)
		{
			ArrayList<String> unpackedGroup = Groups.unpackGroup(i, groups, players);
			HashMap<String, Integer> groupWithScore = Groups.setGroupScores(unpackedGroup, sc);
			groupsWithScores.put(i, groupWithScore);
		}
	}

	private void askForPlayerList(Scanner s) {
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
	}

	public void addAudit(String text) {
		auditHistory.append(text);
	}

	public List<String> getPlayers() { return players;}
	public ArrayList<String> getGroup(int index) {
		return groups.get(index);
	}

	public int getNoOfGroups(){
		return groups.size();
	}
	
	public int getGroupSize(){
		return groups.get(0).size();
	}

	public int noOfPlayers(){
		return players.size();
	}
}
