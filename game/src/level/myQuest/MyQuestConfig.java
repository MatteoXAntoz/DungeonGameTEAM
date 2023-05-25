package level.myQuest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Die Klasse MyQuestConfig enthält die Konfigurationsdaten für die Quests im Spiel "MyQuest". */
public class MyQuestConfig {

    /** Der Pfad zur Datei, die die Quest-Daten enthält. */
    private final String PATH = "game/src/level/myQuest/MyQuestData.txt";

    /** Gibt an, ob der Spieler den Bedingungen der Quest zugestimmt hat. */
    private boolean isAgreed;

    /** Die IDs der Quests. */
    public ArrayList<Integer> questID = new ArrayList<>();

    /** Die Beschreibungen der Quests. */
    public ArrayList<String> questDescription = new ArrayList<>();

    /** Die Belohnungen der Quests. */
    public ArrayList<String> questReward = new ArrayList<>();

    /** Die einzige Instanz der MyQuestConfig-Klasse (Singleton). */
    private static final MyQuestConfig myQuestConfig = new MyQuestConfig();

    /** Privater Konstruktor zur Erstellung der Singleton-Instanz. */
    private MyQuestConfig() {
        readData();
    }

    /**
     * Gibt die einzige Instanz der MyQuestConfig-Klasse zurück.
     *
     * @return Die einzige Instanz der MyQuestConfig-Klasse.
     */
    public static MyQuestConfig getInstance() {
        return myQuestConfig;
    }

    /** Liest die Quest-Daten aus der Konfigurationsdatei ein. */
    public void readData() {
        List<String> files;
        try {
            files = Files.readAllLines(Path.of(PATH));

            // 1, da wir die Überschriften nicht übernehmen wollen
            for (int j = 1; j < files.size(); j++) {
                String[] z = files.get(j).split("@");
                // Alle Elemente aus der ersten Spalte werden übernommen
                questID.add(Integer.valueOf(z[0]));
                // Alle Elemente aus der zweiten Spalte werden übernommen
                questDescription.add(z[1]);
                // Alle Elemente aus der dritten Spalte werden übernommen
                questReward.add(z[2]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gibt an, ob der Spieler den Bedingungen der Quest zugestimmt hat.
     *
     * @return true, wenn der Spieler zugestimmt hat; false, sonst.
     */
    public boolean isAgreed() {
        return isAgreed;
    }

    /**
     * Setzt den Zustimmungsstatus der Quest.
     *
     * @param agreed Der Zustimmungsstatus der Quest.
     */
    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }
}
