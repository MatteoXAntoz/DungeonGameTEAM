package ecs.components;

import java.util.ArrayList;

/** Diese Klasse repräsentiert das Inventar eines Helden im Spiel. */
public class MyInventory {

    private int maxSpace; // Maximale Anzahl von Gegenständen im Inventar
    private ArrayList<String> inventoryItems; // Liste der Gegenstände im Inventar

    /** Konstruktor für das Inventar. */
    public MyInventory() {
        inventoryItems = new ArrayList<>();
    }

    /**
     * Gibt die Anzahl der Gegenstände im Inventar zurück.
     *
     * @return Anzahl der Gegenstände im Inventar
     */
    public int getItemAmount() {
        return inventoryItems.size();
    }

    /**
     * Fügt einen Gegenstand zum Inventar hinzu, falls noch Platz vorhanden ist.
     *
     * @param itemName Name des hinzuzufügenden Gegenstands
     */
    public void addItems(String itemName) {
        if (getItemAmount() < maxSpace) inventoryItems.add(itemName);
    }

    /**
     * Gibt die maximale Anzahl von Gegenständen im Inventar zurück.
     *
     * @return Maximale Anzahl von Gegenständen im Inventar
     */
    public int getMaxSpace() {
        return maxSpace;
    }

    /**
     * Legt die maximale Anzahl von Gegenständen im Inventar fest.
     *
     * @param maxSpace Maximale Anzahl von Gegenständen im Inventar
     */
    public void setMaxSpace(int maxSpace) {
        this.maxSpace = maxSpace;
    }

    /** Zeigt den Inhalt des Inventars an. */
    public void showInventory() {
        System.out.println(inventoryItems.size());
        for (int i = 0; i < inventoryItems.size(); i++) {
            System.out.println(inventoryItems.get(i));
        }
    }

    /**
     * Gibt die Liste der Gegenstände im Inventar zurück.
     *
     * @return Liste der Gegenstände im Inventar
     */
    public ArrayList<String> getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Legt die Liste der Gegenstände im Inventar fest.
     *
     * @param inventoryItems Liste der Gegenstände im Inventar
     */
    public void setInventoryItems(ArrayList<String> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    /**
     * Überprüft, ob ein bestimmter Gegenstand im Inventar vorhanden ist.
     *
     * @param name Name des zu überprüfenden Gegenstands
     * @return true, wenn der Gegenstand im Inventar vorhanden ist, ansonsten false
     */
    public boolean isInInventory(String name) {
        for (int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob das Inventar voll ist.
     *
     * @return true, wenn das Inventar voll ist, ansonsten false
     */
    public boolean isFull() {
        return getItemAmount() == getMaxSpace();
    }
}
