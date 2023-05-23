package level;

import ecs.components.HealthComponent;
import ecs.items.*;
import level.elements.tile.HoleTile;
import level.tools.LevelElement;
import starter.Game;

import java.io.*;
import java.util.ArrayList;

public class SaveLoadGame implements Serializable {


    public static String PATH = "game/src/level";
    public static String PLAYER_DATA = "PlayerData.txt";
    public static String ITEM_DATA = "ItemData.txt";

    public static String TRAP_DATA = "TrapData.txt";
    public static String MONSTER_DATA = "Monster_Data.txt";
    public static ArrayList<String> tempName = new ArrayList<>();


    public static void saveHeroHealth() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + PLAYER_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                if (Game.getHero().isPresent() && Game.getHero().get().getComponent(HealthComponent.class).isPresent()) {
                    HealthComponent healthComponent = (HealthComponent) Game.getHero().get().getComponent(HealthComponent.class).get();
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

    public static void saveItems() {
        ArrayList<String> itemNames = new ArrayList<>();

        for (Item item : Game.items) {
            itemNames.add(item.getClass().getSimpleName());
            System.out.println(itemNames.getClass().getSimpleName());

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

    public static void saveTraps(LevelAPI levelAPI){
        ArrayList<String> trapNames = new ArrayList<>();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH+TRAP_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(levelAPI.trapElements);
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




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
            throw new RuntimeException(e);
        }
        return false;
    }
}
