package ui;
import javax.swing.JPanel;

import logic.Tournament;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

public class TournamentPanel extends JPanel {

	private Tournament tournament;
	private int currentGroup = 0;

	public TournamentPanel() {
		super();
		setForeground(Color.GREEN);
		setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 277, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 71, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
				DarkButton btnPreviousGroup = new DarkButton("Previous Group");
				GridBagConstraints gbc_btnPreviousGroup = new GridBagConstraints();
				btnPreviousGroup.addActionListener(l -> {
					if(currentGroup > 0)
						currentGroup -= 1;
					repaint();
				});
				gbc_btnPreviousGroup.insets = new Insets(0, 0, 5, 5);
				gbc_btnPreviousGroup.gridx = 1;
				gbc_btnPreviousGroup.gridy = 1;
				add(btnPreviousGroup, gbc_btnPreviousGroup);
		
				DarkButton btnNextGroup = new DarkButton("Next Group");
				GridBagConstraints gbc_btnNextGroup = new GridBagConstraints();
				gbc_btnNextGroup.insets = new Insets(0, 0, 5, 5);
				btnNextGroup.addActionListener(l -> {
					if (tournament.getGroup(currentGroup + 1) != null)
						currentGroup += 1;
					repaint();
				});
				gbc_btnNextGroup.gridx = 2;
				gbc_btnNextGroup.gridy = 1;
				add(btnNextGroup, gbc_btnNextGroup);
	}

	public void addTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		renderTournament(g);
	}

	private void renderTournament(Graphics g) {
		ArrayList<String> names = tournament.getGroup(currentGroup);
		g.drawString("Group "+ (currentGroup + 1), 100, 100);
		for(int i = 0; i < names.size(); i++) {
			g.drawString(names.get(i), (i*100) + 200, 200);
		}
	}

}
