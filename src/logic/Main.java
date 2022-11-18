package logic;

import javax.swing.SwingUtilities;

import ui.FrontEnd;

/* Stuff to do: implement next stages, move most methods to their own classes, etc. */

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Tournament tournament = new Tournament().generateTournament();
			FrontEnd frontEnd = new FrontEnd();
			frontEnd.addTournament(tournament);
		});
	}
}