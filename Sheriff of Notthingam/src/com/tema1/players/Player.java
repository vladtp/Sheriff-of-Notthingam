package com.tema1.players;

import com.tema1.goods.Goods;

import java.util.ArrayList;

public class Player {
    private int coins = 80;
    private int id;
    private String strategy;
    private int[] goodsInHand = new int[10];
    private ArrayList<Integer> goodsOnTable = new ArrayList<Integer>();

    // constructors
    public Player() {
    }

    public Player(int id, String strategy) {
        this.id = id;
        this.strategy = strategy;
    }

    // getters and setters

    public ArrayList<Integer> getGoodsOnTable() {
        return goodsOnTable;
    }

    public void setGoodsOnTable(ArrayList<Integer> goodsOnTable) {
        this.goodsOnTable = goodsOnTable;
    }

    public int[] getGoodsInHand() {
        return goodsInHand;
    }

    public void setGoodsInHand(int[] goodsInHand) {
        this.goodsInHand = goodsInHand;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    // methods

    @Override
    public String toString() {
        return id + " " + strategy.toUpperCase() + " " + coins;
    }

    public void addCoins(int coinsToAdd) {
        coins += coinsToAdd;
    }

    public void removeCoins(int coinsToRemove) {
        coins -= coinsToRemove;
    }

    public void addOnTable(int id) {
        goodsOnTable.add(id);
    }

    public int[] getFrequency() {
        int[] frequency = new int[10];
        int id;

        for (int i = 0; i < goodsOnTable.size(); ++i) {
            id = goodsOnTable.get(i);
            if (id >= 0 && id <= 9) {
                frequency[id]++;
            }
        }

        return frequency;
    }
}
