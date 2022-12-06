package logic;

import java.util.*;

public class Tournament {

    private List<String> players;

    private HashMap<Integer, ArrayList<String>> groups;

    private Audit auditHistory = new Audit();

    public Tournament generateTournament() {
        auditHistory.append("Started at " + new Date().toString());
        int noOfPlayers = 0;
        int playersPerGroup;
        int noOfPlayersProgressing;
        try (Scanner s = new Scanner(System.in)) {

            // Initializing the tournament size
            while (noOfPlayers < 2) {
                noOfPlayers = setPlayerCount(s);
            }
            askForPlayerList(s);

            // Creating a copy of the list - will be used to avoid duplicates
            ArrayList<String> remainingPlayers = new ArrayList<>(players);

            playersPerGroup = groupStageSetup(noOfPlayers, s, remainingPlayers);
            noOfPlayersProgressing = noOfPlayersProgressing(s, playersPerGroup);
            printGroups();

            System.out.println("After your first group has played a match, press any key to continue and set the scores...");
            setScores(noOfPlayersProgressing);
        }
        return this;
    }

    private int setPlayerCount(Scanner s) {
        int noOfPlayers = 0;
        while (noOfPlayers < 2) {
            System.out.println("How many players?");
            try {
                noOfPlayers = s.nextInt();
                if (noOfPlayers < 2) {
                    System.out.println("Invalid number. You must enter at least 2 players.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number. You must enter a valid number to proceed.");
                s.nextLine(); // consume the invalid input
            }
        }
        auditHistory.append("Created tournament of " + noOfPlayers + " players.");
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

    private int groupStageSetup(int noOfPlayers, Scanner s, ArrayList<String> remainingPlayers) {
        int playersPerGroup = 0;
        System.out.println("How many players would you like per group?");
        System.out.println("Note that if you have an odd number of players, some groups may not respect this setting.");
        while ((playersPerGroup >= noOfPlayers) || (playersPerGroup == 0)) {
            try {
                playersPerGroup = s.nextInt();
                if (playersPerGroup >= noOfPlayers) {
                    System.out.println("Invalid number. The number of players per group must be less than the number of total players.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number. You must input a valid integer number.");
                s.nextLine(); // consume the invalid input
            }
        }
        groups = Groups.createGroups(noOfPlayers, remainingPlayers, playersPerGroup);
        return playersPerGroup;
    }


    public int noOfPlayersProgressing(Scanner s, int playersPerGroup) {
        int noOfPlayersProgressing;
        System.out.println("And how many players should proceed to the next round?");
        while (true) {
            try {
                noOfPlayersProgressing = s.nextInt();
                if (noOfPlayersProgressing < playersPerGroup) {
                    break;
                } else {
                    System.out.println("Invalid selection. The number must be less than the number of players per group.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number. You must input a valid integer number.");
                s.nextLine();
            }
        }
        return noOfPlayersProgressing;
    }


    private void setScores(int noOfPlayersProgressing) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        HashMap<Integer, HashMap> groupsWithScores = new HashMap<>();

        for (int i = 0; i < groups.size(); i++) {
            ArrayList<String> unpackedGroup = Groups.unpackGroup(i, groups, players);
            HashMap<String, Integer> groupWithScore = Groups.setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
            groupsWithScores.put(i, groupWithScore);
        }
    }

    private void askForPlayerList(Scanner s) {
        System.out.println("Before we continue...");
        System.out.println("Do you want to see a list of the players? Y or N");
        String showPlayerAnswer = s.next().toLowerCase();
        boolean isValidAnswer = false;
        while (!isValidAnswer) {
            switch (showPlayerAnswer) {
                case "y":
                    Players.showPlayers(players);
                    isValidAnswer = true;
                    break;
                case "n":
                    isValidAnswer = true;
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

    public List<String> getPlayers() {
        return players;
    }

    public ArrayList<String> getGroup(int index) {
        return groups.get(index);
    }

    public int getNoOfGroups() {
        return groups.size();
    }

    public int getGroupSize() {
        return groups.get(0).size();
    }

    public int noOfPlayers() {
        return players.size();
    }
}
