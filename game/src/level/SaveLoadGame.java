package level;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.*;
import starter.Game;

import javax.swing.plaf.PanelUI;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SaveLoadGame implements Serializable {


    public static String PATH = "game/src/level";
    public static String PLAYER_DATA = "PlayerData.txt";
    public static String ITEM_DATA = "ItemData.txt";
    public static ArrayList<String> items = new ArrayList<>();





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
        for (Entity entity : Game.getEntitiesToAdd()) {
            if (!entity.getClass().getSimpleName().equals("Hero") && !entity.getClass().getSimpleName().equals("Grave")) {
                items.add(entity.getClass().getSimpleName());

            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + ITEM_DATA);
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(items);
                objectOutputStream.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static ArrayList<Item> loadItems(){
        ArrayList<Item> temp = new ArrayList<>();
        ArrayList<String> tempName = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(PATH+ITEM_DATA);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    tempName = (ArrayList<String>) objectInputStream.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for(String st:tempName){
                    if(st.equals("Food")){
                        temp.add(new Food());
                    }else if(st.equals("Potion")){
                        temp.add(new Potion());
                    }
                    else if(st.equals("PotionBag")){
                        temp.add(new PotionBag());
                    }
                    else if(st.equals("FoodBag")){
                        temp.add(new FoodBag());
                    }
                    else if(st.equals("Zauberstab")){
                        temp.add(new Zauberstab());
                    }
                   System.out.println(st);
                }
                objectInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return temp;
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
