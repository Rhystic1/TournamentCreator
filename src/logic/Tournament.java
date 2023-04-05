package logic;

import java.util.*;

public class Tournament {

    // make players of type Player
    private List<Player> players;

    static HashMap<Integer, ArrayList<String>> groups;

    private final Audit auditHistory = new Audit();

    private static Phase tournamentPhase;

    public static Phase getTournamentPhase() {
        return tournamentPhase;
    }

    public Tournament generateTournament() {
        auditHistory.append("Started at " + new Date().toString());
        tournamentPhase = Phase.PRELIMINARY_ROUND;
        int noOfPlayers = 0;
        int playersPerGroup;
        Scanner s = new Scanner(System.in);

        // Initializing the tournament size
        while (noOfPlayers < 2) {
            noOfPlayers = setPlayerCount(s);
        }
        askForPlayerList(s);

        // Creating a copy of the list - will be used to avoid duplicates
        ArrayList<Player> remainingPlayers = new ArrayList<Player>(players);

        tournamentPhase = Phase.GROUP; // TODO: For now, we are going to assume the user will start straight from the group stage - prelim rounds will be implemented later
        boolean hasAdditionalGroupStages = setAdditionalGroupStages(s);
        playGroupStage(noOfPlayers, s, remainingPlayers);
        if (hasAdditionalGroupStages) {
            playGroupStage(noOfPlayers, s, remainingPlayers);
        }

        // Begin next phase
        tournamentPhase.nextPhase();
        Knockout knockout = new Knockout();
        ProgressingPlayersAndGroups ppg = new ProgressingPlayersAndGroups();
        ArrayList<Player> playersProgressing = ppg.getPlayersProgressing();
        knockout.playKnockoutPhase(s, groups, playersProgressing, remainingPlayers);

        return this;
    }

    private boolean setAdditionalGroupStages(Scanner s) {
        System.out.println("Do you want to have an additional group stage after the first one? (y/n)");
        String response = answerYesOrNo(s);
        return response.equalsIgnoreCase("y");
    }

    private void playGroupStage(int noOfPlayers, Scanner s, ArrayList<Player> remainingPlayers) {
        int playersPerGroup;
        playersPerGroup = groupStageSetup(noOfPlayers, s, remainingPlayers);
        int noOfPlayersProgressing = noOfPlayersProgressing(s, playersPerGroup);
        int playersPerMatch = setPlayersPerMatch(noOfPlayers, s);
        printGroups(groups);

        System.out.println("After your first group has played a match, press any key to continue and set the scores...");
        setScores(noOfPlayersProgressing);

        System.out.println("If you're ready for the next round, press any key to continue!");
        s.nextLine();
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
        players = Collections.unmodifiableList(PlayerSetup.createPlayers(s, noOfPlayers));
        return noOfPlayers;
    }

    static void printGroups(HashMap<Integer, ArrayList<String>> groups) {
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

    private int groupStageSetup(int noOfPlayers, Scanner s, ArrayList<Player> remainingPlayers) {
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


    private void setScores(int noOfPlayersProgressing) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        HashMap<Integer, HashMap> groupsWithScores = new HashMap<>();

        for (int i = 0; i < groups.size(); i++) {
            // fix this
            ArrayList<Player> unpackedGroup = Groups.unpackGroup(i, groups, players);
            ProgressingPlayersAndGroups p = new ProgressingPlayersAndGroups();
            HashMap<String, Integer> groupWithScore = p.setGroupScores(unpackedGroup, sc, noOfPlayersProgressing).getGroupWithScores();
            groupsWithScores.put(i, groupWithScore);
        }
    }

    private void askForPlayerList(Scanner s) {
        System.out.println("Before we continue...");
        System.out.println("Do you want to see a list of the players? Y or N");
        String showPlayerAnswer = answerYesOrNo(s);
        if (showPlayerAnswer.equalsIgnoreCase("y")) {
            PlayerSetup.showPlayers(players);
        }
    }

    public static String answerYesOrNo(Scanner s) {
        String showPlayerAnswer;
        do {
            showPlayerAnswer = s.next();
            if (!showPlayerAnswer.equalsIgnoreCase("y") && !showPlayerAnswer.equalsIgnoreCase("n")) {
                System.out.println("Invalid selection. Please answer either (y)es or (n)o.");
            }
        } while (!showPlayerAnswer.equalsIgnoreCase("y") && !showPlayerAnswer.equalsIgnoreCase("n"));
        return showPlayerAnswer;
    }

    public void addAudit(String text) {
        auditHistory.append(text);
    }

    public List<Player> getPlayers() {
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
