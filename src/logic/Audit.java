package logic;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class Audit {

	public ArrayList<String> history;

	public Audit() {
		history = new ArrayList<>();
	}

	public void append(String text) {
		history.add(text);
	}

	public void writeToFile() {
		try {
			Files.write(Paths.get("./history" + new Date().toString()), history.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
