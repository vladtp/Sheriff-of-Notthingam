package com.tema1.players;

import com.tema1.goods.Goods;

public class Bag {
    int[] goodsInBag = new int[8];
    int bribe = 0;
    int declaredType;
    int size = 0;

    public int getBribe() {
        return bribe;
    }

    public void setBribe(int bribe) {
        this.bribe = bribe;
    }

    public int getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(int declaredType) {
        this.declaredType = declaredType;
    }

    public int getSize() {
        return size;
    }

    public int[] getGoodsInBag() {
        return goodsInBag;
    }

    public void addToBag(int id) {
        goodsInBag[size] = id;
        size++;
    }
}
