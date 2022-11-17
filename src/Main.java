/* Stuff to do: implement next stages, move most methods to their own classes, etc. */

public class Main {
	public static void main(String[] args) {
		Tournament tournament = new Tournament().generateTournament();
		FrontEnd frontEnd = new FrontEnd(tournament);
	}
}