package level.myQuest;

import ecs.entities.Hero;
import level.IOnLevelLoader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyQuestConfig {

    private final String PATH ="game/src/level/myQuest/MyQuestData.txt";
    private boolean isAgreed;

    //QuestID ist die eigentliche Quest, da man mit der ID eindeutig die Quest ermitteln kann
    public ArrayList<Integer> questID = new ArrayList<>();
    public ArrayList<String> questDescription = new ArrayList<>();
    public ArrayList<String> questReward = new ArrayList<>();

    private final static MyQuestConfig myQuestConfig = new MyQuestConfig();


    private MyQuestConfig() {
        readData();
    }

    public static MyQuestConfig getInstance() {
        return myQuestConfig;
    }

    public void readData() {


        List<String> files;




        try {
            files = Files.readAllLines(Path.of(PATH));

            //1 da wir die Ueberschriften nicht uebernehmen wollen
            for (int j = 1; j < files.size(); j++) {
                String[] z = files.get(j).split("@");
                //Alle Elemente aus der ersten Spalte werden uebernommen
                questID.add(Integer.valueOf(z[0]));
                //Alle Elemente aus der zweiten Spalte werden uebernommen
                questDescription.add(z[1]);
                //Alle Elemente aus der dritten Spalte werden uebernommen
                questReward.add(z[2]);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAgreed() {
        return isAgreed;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

}
