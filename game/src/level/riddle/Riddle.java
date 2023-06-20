package level.riddle;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * class to create a riddle
 *
 * @author Moritz Luetzkendorf
 */
public class Riddle {
    // Logger for the class Riddle
    private final Logger riddle_logger = Logger.getLogger(this.getClass().getName());
    /** constructor for class Riddle */
    public Riddle() {
        ghostRiddle();
    }

    /**
     * method to implement a riddle
     *
     * <p>only to use with entity ghost!
     *
     * @return boolean value if a riddle was solved or not
     */
    public boolean ghostRiddle() {
        riddle_logger.info("Riddle was loaded.");
        boolean isSolved = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many we are both?");
        String input = scanner.nextLine();
        if (input.matches("10")) {
            System.out.println("You are a worthy hero.");
            isSolved = true;
        } else if (input.matches("help")) {
            System.out.println("\n");
            System.out.println("You are on your own. Good Luck.");
            ghostRiddle();
        } else {
            System.out.println(
                    "Oh, you fool. This dungeon is not for little kids like you. Last chance...");
            String input2 = scanner.nextLine();
            if (input2.matches("10")) {
                isSolved = true;
            } else if (input2.matches("help")) {
                ghostRiddle();
            } else {
                System.out.println("You have to die.");

                isSolved = false;
            }
        }
        riddle_logger.info("Riddle solved: " + isSolved + "!");
        return isSolved;
    }
}
