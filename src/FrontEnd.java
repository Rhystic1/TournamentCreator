import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class FrontEnd extends JFrame {

	private TournamentPanel tournamentPanel;
	
	public FrontEnd(Tournament tournament) {
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setForeground(Color.GREEN);
		
		tournamentPanel = new TournamentPanel();
		tournamentPanel.addTournament(tournament);
		getContentPane().add(tournamentPanel, BorderLayout.CENTER);
		this.setBounds(0, 0, 800, 600);
		this.setVisible(true);
	}
	
}
