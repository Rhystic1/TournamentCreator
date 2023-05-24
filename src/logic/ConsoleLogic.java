package logic;

import java.util.Scanner;

public class ConsoleLogic {
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
}
