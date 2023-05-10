package level.myQuest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.Item;
import level.IOnLevelLoader;
import level.elements.tile.TrapTile;
import starter.Game;

import javax.print.DocFlavor;
import java.security.Key;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class LevelManager {

    //Matteo
    int collectedNahrung = 0;
    int collectedPotion = 0;
    private int levelSurvivedWithoutDamage;

    private MyQuestConfig myQuestConfig;

    public ArrayList<MyQuest> myQuests = new ArrayList<>();

    private final static LevelManager levelManager = new LevelManager();

    public static LevelManager getInstance() {
        return levelManager;
    }


    private LevelManager() {
        myQuestConfig = MyQuestConfig.getInstance();

        questPack();

    }

    public void update() {

        //Hier wird abgefragt, ob die Aufgabe erfuellt wurde.
        //Quest 1
        if (myQuests.get(0).isAgreed() && levelAmountSurvived(5)) {
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            myQuests.get(0).setAccomplished(true);

        }
        //Quest
        if (myQuests.get(1).isAgreed() && collectedNahrung(3) && collectedNahrung(2)) {
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Trank");
            Game.hero.getMyInventory().addItems("Potion");
            Game.hero.getMyInventory().addItems("Potion");
            myQuests.get(1).setAccomplished(true);
        }


        updateHeroDamage();


    }

    private void updateHeroDamage() {

        if (Game.hero.gotHitByTrap()) {
            setLevelSurvivedWithoutDamage(0);
        }
        // weitere Abfragen koennen implementiert werden.

    }

    //Wird abgefragt wie viele Level der Spieler ueberlebt hat
    private boolean levelAmountSurvived(int amount) {
        if (amount == levelSurvivedWithoutDamage) {
            levelSurvivedWithoutDamage = 0;
            return true;
        }
        return false;
    }

    private boolean collectedPotion(int amountPotion) {
        for (Item item : Game.items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get()) &&
                item.getName().equals("Trank") && Game.hero.isCollidingWithItems(item)) {
                collectedPotion++;
            }
        }
        if (collectedPotion >= amountPotion) {
            collectedPotion = 0;
            return true;
        }
        return false;
    }


    private boolean collectedNahrung(int amountNahrung) {
        for (Item item : Game.items) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.ITEM_COLLECT.get()) &&
                item.getName().equals("Nahrung") && Game.hero.isCollidingWithItems(item)) {
                collectedNahrung++;
            }
        }
        if (collectedNahrung >= amountNahrung) {
            collectedNahrung = 0;
            return true;
        }
        return false;
    }

    public int getLevelSurvivedWithoutDamage() {
        return levelSurvivedWithoutDamage;
    }

    public void setLevelSurvivedWithoutDamage(int levelSurvivedWithoutDamage) {
        this.levelSurvivedWithoutDamage = levelSurvivedWithoutDamage;
    }

    public void questPack() {
        myQuests.add(new MyQuest());
        myQuests.add(new MyQuest());

        myQuests.get(0).setQuest(0);
        myQuests.get(1).setQuest(1);
    }

    public void printQuestInfo() {
        for (MyQuest myQuest : myQuests) {
            if (myQuest.isAccomplished()) {
                System.out.println(myQuest.getQuestID() + "|" + myQuest.getQuestDescription() + "|" + myQuest.getQuestReward() + "| Quest done!");
            } else if (myQuest.isAgreed()) {
                System.out.println(myQuest.getQuestID() + "|" + myQuest.getQuestDescription() + "|" + myQuest.getQuestReward() + "| Quest is active!");
            } else if (!myQuest.isAccomplished()) {
                System.out.println(myQuest.getQuestID() + "|" + myQuest.getQuestDescription() + "|" + myQuest.getQuestReward() + "| Quest offered!");
            }
        }
    }


}
