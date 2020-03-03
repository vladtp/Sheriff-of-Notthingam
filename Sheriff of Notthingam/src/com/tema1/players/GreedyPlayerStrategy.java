package com.tema1.players;

import com.tema1.goods.Goods;

import java.util.Map;

public class GreedyPlayerStrategy extends BasicPlayerStrategy {

    public Bag createBag(int[] goodsInHand, Map<Integer, Goods> goodsById, int round) {


        Bag bag = super.createBag(goodsInHand, goodsById);
        if (round % 2 == 0 && bag.getSize() < 8) {
            int id = getBestIllegalGood(goodsInHand, goodsById);
            if (id > 9) {
                // if good is illegal
                bag.addToBag(id);
            }
        }

        return bag;
    }
}
