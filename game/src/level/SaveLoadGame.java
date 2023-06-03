package level;

import ecs.components.HealthComponent;
import ecs.entities.Chort;
import ecs.entities.Demon;
import ecs.entities.Entity;
import ecs.entities.Mouse;
import ecs.entities.items.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import level.myQuest.LevelManager;
import level.tools.LevelElement;
import starter.Game;

public class SaveLoadGame implements Serializable {

    public static String PATH = "game/src/level/saves/";
    public static String PLAYER_DATA = "PlayerData.txt";
    public static String ITEM_DATA = "ItemData.txt";

    public static String TRAP_DATA = "TrapData.txt";
    public static String MONSTER_DATA = "MonsterData.txt";
    public static ArrayList<String> tempName = new ArrayList<>();

    public static ArrayList<LevelElement> tempTraps = new ArrayList<>();

    public static final Logger saveLoadGame_logger = Logger.getLogger(SaveLoadGame.class.getName());

    /** Speichert die Gesundheit des Helden. */
    public static void saveHeroHealth() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + PLAYER_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                if (Game.getHero().isPresent()
                        && Game.getHero().get().getComponent(HealthComponent.class).isPresent()) {
                    HealthComponent healthComponent =
                            (HealthComponent)
                                    Game.getHero().get().getComponent(HealthComponent.class).get();
                    objectOutputStream.writeInt(healthComponent.getCurrentHealthpoints());
                }
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lädt die Gesundheit des Helden.
     *
     * @return Die geladene Gesundheit des Helden.
     */
    public static int loadHeroHealth() {
        int health;
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + PLAYER_DATA);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                health = objectInputStream.readInt();
                objectInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return health;
    }

    /** Speichert die Gegenstände des Spiels. */
    public static void saveItems() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : Game.items) {
            itemNames.add(item.getClass().getSimpleName());
            saveLoadGame_logger.info(item.getClass().getSimpleName() + " was saved");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + ITEM_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(itemNames);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lädt die Gegenstände des Spiels.
     *
     * @return Die geladenen Gegenstände des Spiels.
     */
    public static ArrayList<Item> loadItems() {
        ArrayList<Item> newItems = new ArrayList<>();
        ObjectInputStream objectInputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + ITEM_DATA);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    tempName = (ArrayList<String>) objectInputStream.readObject();
                    objectInputStream.close();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (String st : tempName) {
                    if (st.equals("Food")) {
                        newItems.add(new Food());
                    } else if (st.equals("Potion")) {
                        newItems.add(new Potion());
                    } else if (st.equals("PotionBag")) {
                        newItems.add(new PotionBag());
                    } else if (st.equals("FoodBag")) {
                        newItems.add(new FoodBag());
                    } else if (st.equals("Zauberstab")) {
                        newItems.add(new Zauberstab());
                    }
                }
                objectInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return newItems;
    }

    /**
     * Speichert die Fallen des Levels.
     *
     * @param levelAPI Die LevelAPI, die die Fallen enthält.
     */
    public static void saveTraps(LevelAPI levelAPI) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + TRAP_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(levelAPI.trapElements);
                for (LevelElement levelElement : levelAPI.trapElements) {
                    saveLoadGame_logger.info(levelElement + "TRAP" + " was saved!");
                }
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Speichert die Monster des Levels.
     *
     * @param levelAPI Die LevelAPI, die die Monster enthält.
     */
    public static void saveMonsters(LevelAPI levelAPI) {
        ArrayList<String> monsterNames = new ArrayList<>();
        LevelManager levelManager = LevelManager.getInstance();
        for (Entity entity : levelManager.monster) {
            monsterNames.add(entity.getClass().getSimpleName());
            saveLoadGame_logger.info(entity.getClass().getSimpleName() + " was saved");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + MONSTER_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(monsterNames);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lädt die Monster des Levels.
     *
     * @return Die geladenen Monster des Levels.
     */
    public static ArrayList<Entity> loadMonsters() {
        ArrayList<Entity> newMonsters = new ArrayList<>();
        ObjectInputStream objectInputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + MONSTER_DATA);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    tempName = (ArrayList<String>) objectInputStream.readObject();
                    objectInputStream.close();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (String st : tempName) {
                    if (st.equals("Mouse")) {
                        newMonsters.add(new Mouse());
                    } else if (st.equals("Chort")) {
                        newMonsters.add(new Chort());
                    } else if (st.equals("Demon")) {
                        newMonsters.add(new Demon());
                    }
                }
                objectInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return newMonsters;
    }

    /**
     * Lädt die Fallen des Levels.
     *
     * @return Die geladenen Fallen des Levels.
     */
    public static ArrayList<LevelElement> loadTraps() {
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + TRAP_DATA);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    tempTraps = (ArrayList<LevelElement>) objectInputStream.readObject();
                    for (LevelElement levelElement : tempTraps) {
                        saveLoadGame_logger.info(levelElement + " TRAP " + " was loaded");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tempTraps;
    }

    /**
     * Überprüft, ob eine Datei leer ist.
     *
     * @param path Der Pfad zur Datei.
     * @param data Der Dateiname.
     * @return True, wenn die Datei leer ist, ansonsten false.
     */
    public static boolean isEmpty(String path, String data) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + data);
            try {
                byte[] bytes = fileInputStream.readAllBytes();
                if (bytes.length == 0) {
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            saveLoadGame_logger.info("No Saves present.");
            return true;
        }
        return false;
    }
}
