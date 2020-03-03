package com.tema1.players;

import com.tema1.goods.Goods;

import java.util.Map;

public interface PlayerStrategy {
    public Bag createBag(int goodsInHand[], Map<Integer, Goods> goodsById);
}
