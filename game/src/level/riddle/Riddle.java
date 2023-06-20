package level.riddle;

import ecs.entities.NPCs.Ghost;

import java.util.Scanner;

public class Riddle {
    public Riddle() {
        loadRiddleFromFile();
        ghostRiddle();
    }

    public void loadRiddleFromFile() {

    }

    public boolean ghostRiddle() {
        boolean isSolved = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many we are both?");
        String input = scanner.nextLine();
        if(input.matches("10")) {
            System.out.println("You are a worthy hero.");
            isSolved = true;
        } else if(input.matches("help")) {
            System.out.println("\n");
            System.out.println("You are on your own. Good Luck.");
            ghostRiddle();
        } else {
            System.out.println("Oh, you fool. This dungeon is not for little kids like you. Last chance...");
            String input2 = scanner.nextLine();
            if(input2.matches("10")) {
                isSolved = true;
            } else if(input2.matches("help")) {
                ghostRiddle();
            } else {
                System.out.println("You have to die.");
                isSolved = false;
            }
        }
        return isSolved;
    }
}
