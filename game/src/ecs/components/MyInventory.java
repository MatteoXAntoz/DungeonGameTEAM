package ecs.components;

import com.badlogic.gdx.utils.reflect.ArrayReflection;
import configuration.ConfigKey;
import ecs.entities.Hero;
import ecs.items.Item;
import starter.Game;

import java.util.ArrayList;

public class MyInventory {

//Moritz
    private int maxSpace;
    private ArrayList<String> inventoryItems;

    public MyInventory(){
        inventoryItems = new ArrayList<>();
    }



    public int getItemAmount(){
        return inventoryItems.size();
    }
    public void addItems(String itemName){

        if(getItemAmount()<maxSpace)
            inventoryItems.add(itemName);

    }

    public int getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(int maxSpace) {
        this.maxSpace = maxSpace;
    }

    public void showInventory(){
        System.out.println(inventoryItems.size());
        for(int i = 0;i<inventoryItems.size();i++){
            System.out.println(inventoryItems.get(i));
        }
    }

    public ArrayList<String> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(ArrayList<String> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }


    public boolean isInInventory(String name){
        for(int i = 0;i<inventoryItems.size();i++){
            if(inventoryItems.get(i).equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean isFull(){
        if(getItemAmount()==getMaxSpace()){
            return true;
        }
        return false;
    }





}
