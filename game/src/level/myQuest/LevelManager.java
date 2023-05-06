package level.myQuest;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import configuration.KeyboardConfig;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.IOnLevelLoader;
import level.elements.tile.TrapTile;
import starter.Game;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class LevelManager {


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
        if (myQuests.get(0).isAgreed() && levelAmountSurvied(5)) {
            System.out.println("Spieler bekommt die Belohnung");
        }


        updateHeroDamage();



    }

    private void updateHeroDamage() {
        Hero hero = Game.hero;
        if (hero.gotHitByTrap()){
            setLevelSurvivedWithoutDamage(0);
        }
        // weitere Abfragen koennen implementiert werden.
    }

    //Wird abgefragt wie viele Level der Spieler ueberlebt hat
    private boolean levelAmountSurvied(int amount) {
        if (amount == levelSurvivedWithoutDamage) {
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
            }
            else if (myQuest.isAgreed()) {
                System.out.println(myQuest.getQuestID() + "|" + myQuest.getQuestDescription() + "|" + myQuest.getQuestReward() + "| Quest is active!");
            }
           else if (!myQuest.isAccomplished()) {
                System.out.println(myQuest.getQuestID() + "|" + myQuest.getQuestDescription() + "|" + myQuest.getQuestReward() + "| Open Quest");
            }
        }
    }



}
