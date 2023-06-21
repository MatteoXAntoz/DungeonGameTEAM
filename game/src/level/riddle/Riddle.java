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
    public Riddle() {}

    /**
     * method to implement a riddle
     *
     * <p>only to use with entity ghost!
     *
     * @return integer value if a riddle was solved or not
     *     <p>0 means solved, 1 means not solved
     */
    public int ghostRiddle() {
        riddle_logger.info("Riddle was loaded.");
        // default value, riddle is not solved yet
        int valueToReturnForRiddle = 1;
        boolean riddleIsOver = false;

        Scanner scanner = new Scanner(System.in);

        do {

        System.out.println("How many we are both?");

        String input = scanner.nextLine();

        if (input.matches("10")) {

            System.out.println("You are a worthy hero.");

            riddle_logger.info("Riddle solved!");

            valueToReturnForRiddle = 0;
            riddleIsOver = true;




        } else if (input.matches("help")) {

            System.out.println("\n");

            System.out.println("You are on your own. Good Luck.");


        } else {

            System.out.println(
                    "Oh, you fool. This dungeon is not for little kids like you. Last chance...");
            String input2 = scanner.nextLine();

            if (input2.matches("10")) {

                riddle_logger.info("Riddle solved!");

                valueToReturnForRiddle = 0;
                riddleIsOver = true;

            } else if (input2.matches("help")) {


            } else {
                System.out.println("You have to die.");
                riddle_logger.info("Riddle not solved!");
                valueToReturnForRiddle = 1;
                riddleIsOver = true;

            }
        }
        } while (!riddleIsOver);
        if (valueToReturnForRiddle != 0) {
            valueToReturnForRiddle = 1;
        }
        return valueToReturnForRiddle;
    }
}
