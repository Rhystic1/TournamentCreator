package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import logic.Tournament;
import javax.swing.JButton;

public class TournamentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Tournament tournament;
	private int currentGroup = 0;

	private final int X_MARGIN = 200;
	private String debugStr;

	private ArrayList<Marker> playerMarkers = new ArrayList<>();

	public TournamentPanel() {
		super();
		setForeground(Color.GREEN);
		setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 268, 0, 100, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 71, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		DarkButton btnPreviousGroup = new DarkButton("Previous Group");
		GridBagConstraints gbc_btnPreviousGroup = new GridBagConstraints();
		gbc_btnPreviousGroup.fill = GridBagConstraints.BOTH;
		btnPreviousGroup.addActionListener(l -> {
			if (currentGroup > 0) {
				currentGroup -= 1;
				generateMarkers();
			}
			repaint();
		});
		gbc_btnPreviousGroup.insets = new Insets(0, 0, 5, 5);
		gbc_btnPreviousGroup.gridx = 1;
		gbc_btnPreviousGroup.gridy = 2;
		add(btnPreviousGroup, gbc_btnPreviousGroup);

		DarkButton btnNextGroup = new DarkButton("Next Group");
		GridBagConstraints gbc_btnNextGroup = new GridBagConstraints();
		gbc_btnNextGroup.fill = GridBagConstraints.BOTH;
		gbc_btnNextGroup.insets = new Insets(0, 0, 5, 5);
		btnNextGroup.addActionListener(l -> {
			if (tournament.getGroup(currentGroup + 1) != null) {
				currentGroup += 1;
				generateMarkers();
			}
			repaint();
		});
		gbc_btnNextGroup.gridx = 2;
		gbc_btnNextGroup.gridy = 2;
		add(btnNextGroup, gbc_btnNextGroup);

		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				debugStr = "Clicked at "+e.getX()+","+e.getY();
				for(Marker m : playerMarkers) {
					if(m.getBounds().contains(e.getX(), e.getY()))
						m.mark();
				}
				repaint();
			}
		});
	}

	public void addTournament(Tournament tournament) {
		this.tournament = tournament;
		int noOfPlayers = tournament.noOfPlayers();
		int groupSize = tournament.getGroupSize();
		generateMarkers();
	}

	public void generateMarkers() {
		playerMarkers.clear();
		for (int i = 0; i <= tournament.getGroupSize(); i++) {
			int startX = X_MARGIN + (i * 100) - 10;
			int startY = 190;
			playerMarkers.add(new Marker(startX, startY, 50, 50));
			debugStr = "Marker at "+startX+","+startY;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		renderTournament(g);
	}

	private void renderTournament(Graphics g) {
		boolean isOdd = tournament.noOfPlayers() % 2 != 0;
		ArrayList<String> names = tournament.getGroup(currentGroup);
		int lastGroup = tournament.getNoOfGroups();
		g.drawString("Group " + (currentGroup + 1), 100, 100);
		for (int i = 0; i < names.size(); i++) {
			g.drawString(names.get(i), X_MARGIN + (i * 100), 200);
			drawBoxes(g, i);
		}
		g.drawString(debugStr, 0, 20);
	}
	
	private void drawBoxes(Graphics g, int index) {
		if(playerMarkers.isEmpty())
			return;
		Marker marker;
		try {
			marker = playerMarkers.get(index);
		}
		catch (IndexOutOfBoundsException ex) {
			marker = playerMarkers.get(index-1);
		}
		Rectangle bounds = marker.getBounds();
		if (marker.marked()) {
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		} else {
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}
