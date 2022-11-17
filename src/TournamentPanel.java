import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Color;

public class TournamentPanel extends JPanel {
	
	private Tournament tournament;
	
	public TournamentPanel() {
		setForeground(Color.GREEN);
		setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}

	public void addTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
}
