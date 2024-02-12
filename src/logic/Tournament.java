package logic;

import java.util.*;

public class Tournament {

    public int noOfPlayers;
    public int playersPerMatch;
    private List<Player> players;

    static HashMap<Integer, ArrayList<Player>> groups;

    public int playersPerGroup;

    private final Audit auditHistory = new Audit();

    private static Phase tournamentPhase;

    public static Phase getTournamentPhase() {
        return tournamentPhase;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public Tournament generateTournament() {
        auditHistory.append("Started at " + new Date().toString());
        tournamentPhase = Phase.PRELIMINARY_ROUND;
        this.noOfPlayers = 0;
        this.playersPerGroup = 0;
        Scanner s = new Scanner(System.in);

        // Initializing the tournament size
        while (noOfPlayers < 2) {
            this.noOfPlayers = setNoOfPlayers(s);
        }
        askForPlayerList(s);

        // Creating a copy of the list - will be used to avoid duplicates
        ArrayList<Player> remainingPlayers = new ArrayList<>(players);

        tournamentPhase = Phase.GROUP; // TODO: For now, we are going to assume the user will start straight from the group stage - prelim rounds will be implemented later
        boolean hasAdditionalGroupStages = setAdditionalGroupStages(s);
        ArrayList<Player> playersProgressing = playGroupStage(noOfPlayers, s, remainingPlayers);
        if (hasAdditionalGroupStages) {
            playGroupStage(noOfPlayers, s, remainingPlayers);
        }

        // Begin next phase
        tournamentPhase.nextPhase();
        Knockout knockout = new Knockout();

        knockout.playKnockoutPhase(s, groups, playersProgressing);

        return this;
    }

    private boolean setAdditionalGroupStages(Scanner s) {
        System.out.println("Do you want to have an additional group stage after the first one? (y/n)");
        String response = ConsoleLogic.answerYesOrNo(s);
        return response.equalsIgnoreCase("y");
    }

    private ArrayList<Player> playGroupStage(int noOfPlayers, Scanner s, ArrayList<Player> remainingPlayers) {
        int playersPerGroup;
        playersPerGroup = GroupStage.groupStageSetup(noOfPlayers, s, remainingPlayers);
        int noOfPlayersProgressing = noOfPlayersProgressing(s, playersPerGroup);
        this.playersPerMatch = setPlayersPerMatch(noOfPlayers, s);
        printGroups(groups);

        System.out.println("After your first group has played a match, press any key to continue and set the scores...");
        ArrayList<Player> playersProgressing = setScores(noOfPlayersProgressing);

        System.out.println("If you're ready for the next round, press any key to continue!");
        s.nextLine();

        return playersProgressing;
    }

    private int setNoOfPlayers(Scanner s) {
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
        players = Collections.unmodifiableList(PlayerSetup.createPlayers(s, noOfPlayers));
        return noOfPlayers;
    }

    static void printGroups(HashMap<Integer, ArrayList<Player>> groups) {
        // TODO: Modify this so that after the Group Stage this will display "Matches" instead of "groups"
        if (tournamentPhase == Phase.GROUP) {
            System.out.println("Here are the groups:");
        } else {
            System.out.println("Here are the matches:");
        }
        System.out.println("");

        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i + 1) + ":");
            System.out.println(groups.get(i));
        }
    }

    private int setPlayersPerMatch(int noOfPlayers, Scanner s) {
        int playersPerMatch = 0;
        System.out.println("One last thing: in the next round, how many players per match should be matched together?");
        while ((playersPerMatch >= noOfPlayers) || (playersPerMatch == 0)) {
            try {
                playersPerMatch = s.nextInt();
                if (playersPerMatch >= noOfPlayers) {
                    System.out.println("Invalid number. The number of players per match cannot be higher than the number of total players.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number. You must input a valid integer number.");
                s.nextLine(); // consume the invalid input
            }
        }
        return playersPerMatch;
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

    private ArrayList<Player> setScores(int noOfPlayersProgressing) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        HashMap<Integer, HashMap> groupsWithScores = new HashMap<>();

        for (int i = 0; i < groups.size(); i++) {
            ArrayList<Player> unpackedGroup = Group.unpackGroup(i, groups, players);
            Group p = new Group();
            HashMap<String, Integer> groupWithScore = Group.setGroupScores(unpackedGroup, sc, noOfPlayersProgressing);
            groupsWithScores.put(i, groupWithScore);
        }

        ArrayList<Player> playersProgressing = new ArrayList<>();

        for (int i = 0; i < groupsWithScores.size(); i++) {
            // Convert into a list of map entries
            HashMap<String, Integer> group = groupsWithScores.get(i);

            List<Map.Entry<String, Integer>> list = new ArrayList<>(group.entrySet());
            // Sort list by score
            list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

            // Get the highest scoring players

            for (int j = 0; j < noOfPlayersProgressing; j++) {
                String playerName = list.get(j).getKey();
                for (Player player : players) {
                    if (player.getName().equals(playerName)) {
                        playersProgressing.add(player);
                    }
                }
            }
        }
        return playersProgressing;
    }

    private void askForPlayerList(Scanner s) {
        System.out.println("Before we continue...");
        System.out.println("Do you want to see a list of the players? Y or N");
        String showPlayerAnswer = ConsoleLogic.answerYesOrNo(s);
        if (showPlayerAnswer.equalsIgnoreCase("y")) {
            PlayerSetup.showPlayers(players);
        }
    }

    public void addAudit(String text) {
        auditHistory.append(text);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getGroup(int index) {
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
