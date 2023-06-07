package level.myQuest;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.entities.*;
import ecs.entities.items.Item;
import java.util.ArrayList;
import starter.Game;

/** Der LevelManager ist für das Verwalten der Level und Quests zuständig. */
public class LevelManager {

    public ArrayList<Entity> monster = new ArrayList<>();
    int collectedNahrung = 0; // Anzahl der gesammelten Nahrung
    int collectedPotion = 0; // Anzahl der gesammelten Tränke
    private int levelSurvivedWithoutDamage; // Anzahl der Level, die ohne Schaden überlebt wurden

    private MyQuestConfig myQuestConfig;

    public ArrayList<MyQuest> myQuests = new ArrayList<>(); // Liste der Quests

    private static final LevelManager levelManager = new LevelManager();

    /**
     * Gibt eine Instanz des LevelManagers zurück.
     *
     * @return Instanz des LevelManagers
     */
    public static LevelManager getInstance() {
        return levelManager;
    }

    /** Privater Konstruktor für den LevelManager. */
    private LevelManager() {
        myQuestConfig = MyQuestConfig.getInstance();

        questPack();
    }

    /** Aktualisiert den LevelManager. */
    public void update() {

        // Hier wird abgefragt, ob die Aufgabe erfüllt wurde.
        // Quest 1
        if (myQuests.get(0).isAgreed() && levelAmountSurvived(5)) {
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            myQuests.get(0).setAccomplished(true);
        }
        // Quest 2
        if (myQuests.get(1).isAgreed() && collectedPotion(3)) {
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            myQuests.get(1).setAccomplished(true);
        }

        updateHeroDamage();
    }

    /**
     * Setzt die Monster für das angegebene Level.
     *
     * @param level Das Level, für das die Monster gesetzt werden sollen.
     */
    public void setMonster(int level) {
        monster.clear();
        addMonster(level);
    }

    /**
     * Fügt Monster basierend auf dem angegebenen Wert hinzu.
     *
     * @param value Der Wert, der die Anzahl und Art der hinzuzufügenden Monster bestimmt.
     */
    public void addMonster(int value) {
        int maxAmount = 10;
        new MonsterChest();
        // for each level a fireWorm is added
        monster.add(new FireWorm());
        if (value > 0 && value <= 2) {
            for (int i = 0; i < Math.random() * value; i++) {
                monster.add(new Mouse());
            }
        } else if (value > 2 && value <= 4) {
            for (int i = 0; i < Math.random() * value; i++) {
                monster.add(new Chort());
            }
        } else if (value > 4 && value <= 6) {
            for (int i = 0; i < Math.random() * value; i++) {
                monster.add(new Demon());
            }
        } else {
            for (int i = 0; i < Math.random() * maxAmount; i++) {
                monster.add(Monster.getRandomMonster());
            }
        }
    }

    /** Aktualisiert den Schaden am Helden. */
    private void updateHeroDamage() {
        if (Game.hero.gotHitByTrap()) {
            setLevelSurvivedWithoutDamage(0);
        }
        // Weitere Abfragen können implementiert werden.
    }

    /**
     * Überprüft, ob eine bestimmte Anzahl von Leveln ohne Schaden überlebt wurde.
     *
     * @param amount Anzahl der Level ohne Schaden
     * @return true, wenn die Anzahl erreicht wurde, ansonsten false
     */
    private boolean levelAmountSurvived(int amount) {
        if (amount == levelSurvivedWithoutDamage) {
            levelSurvivedWithoutDamage = 0;
            return true;
        }
        return false;
    }

    /**
     * Überprüft, ob eine bestimmte Anzahl von Tränken gesammelt wurde.
     *
     * @param amountPotion Anzahl der zu sammelnden Tränke
     * @return true, wenn die Anzahl erreicht wurde, ansonsten false
     */
    private boolean collectedPotion(int amountPotion) {
        for (Item item : Game.items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get())
                    && item.getName().equals("Trank")
                    && Game.hero.isCollidingWithItems(item)) {
                collectedPotion++;
            }
        }
        if (collectedPotion >= amountPotion) {
            collectedPotion = 0;
            return true;
        }
        return false;
    }

    /**
     * Überprüft, ob eine bestimmte Anzahl von Nahrung gesammelt wurde.
     *
     * @param amountNahrung Anzahl der zu sammelnden Nahrung
     * @return true, wenn die Anzahl erreicht wurde, ansonsten false
     */
    private boolean collectedNahrung(int amountNahrung) {
        for (Item item : Game.items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get())
                    && item.getName().equals("Nahrung")
                    && Game.hero.isCollidingWithItems(item)) {
                collectedNahrung++;
            }
        }
        if (collectedNahrung >= amountNahrung) {
            collectedNahrung = 0;
            return true;
        }
        return false;
    }

    /**
     * Gibt die Anzahl der Level zurück, die ohne Schaden überlebt wurden.
     *
     * @return Anzahl der Level ohne Schaden
     */
    public int getLevelSurvivedWithoutDamage() {
        return levelSurvivedWithoutDamage;
    }

    /**
     * Legt die Anzahl der Level ohne Schaden fest.
     *
     * @param levelSurvivedWithoutDamage Anzahl der Level ohne Schaden
     */
    public void setLevelSurvivedWithoutDamage(int levelSurvivedWithoutDamage) {
        this.levelSurvivedWithoutDamage = levelSurvivedWithoutDamage;
    }

    /** Erzeugt eine Gruppe von Quests. */
    public void questPack() {
        myQuests.add(new MyQuest());
        myQuests.add(new MyQuest());

        myQuests.get(0).setQuest(0);
        myQuests.get(1).setQuest(1);
    }

    /** Gibt Informationen zu den Quests aus. */
    public void printQuestInfo() {
        for (MyQuest myQuest : myQuests) {
            if (myQuest.isAccomplished()) {
                System.out.println(
                        myQuest.getQuestID()
                                + "|"
                                + myQuest.getQuestDescription()
                                + "|"
                                + myQuest.getQuestReward()
                                + "| Quest done!");
            } else if (myQuest.isAgreed()) {
                System.out.println(
                        myQuest.getQuestID()
                                + "|"
                                + myQuest.getQuestDescription()
                                + "|"
                                + myQuest.getQuestReward()
                                + "| Quest is active!");
            } else if (!myQuest.isAccomplished()) {
                System.out.println(
                        myQuest.getQuestID()
                                + "|"
                                + myQuest.getQuestDescription()
                                + "|"
                                + myQuest.getQuestReward()
                                + "| Quest offered!");
            }
        }
    }
}
