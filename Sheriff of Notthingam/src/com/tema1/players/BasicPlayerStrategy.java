package com.tema1.players;

import com.tema1.goods.Goods;

import java.util.Map;

public class BasicPlayerStrategy implements PlayerStrategy {

    protected int[] createFrequency(int goodsInHand[]) {
        int[] frequency = new int[10]; // frequency vector initialized with default value of 0

        for (int i = 0; i < goodsInHand.length; ++i) {
            int id = goodsInHand[i];
            // if the good is legal add to the frequency
            if (id >= 0 && id <= 9) {
                frequency[id]++;
            }
        }

        return frequency;
    }

    protected int getMostFrequent(int[] frequency, Map<Integer, Goods> goodsById) {

        boolean isLegal = false; // i assume there is no legal good in hand
        for (int i = 0; i < frequency.length; ++i) {
            if (frequency[i] > 0) {
                isLegal = true; // there is at least a legal good in hand
            }
        }


        // the case if there is no legal good
        if (isLegal == false) {
            return -1;
        }

        // find the most frequent item
        int maxFreq = frequency[0];
        int maxId = 0;

        for (int i = 1; i < frequency.length; ++i) {
            if (frequency[i] > maxFreq) {
                maxFreq = frequency[i];
                maxId = i;
            } else if (frequency[i] == maxFreq) {
                Goods good0 = goodsById.get(maxId);
                Goods good1 = goodsById.get(i);
                if (good1.getProfit() >= good0.getProfit()) {
                    maxFreq = frequency[i];
                    maxId = i;
                }
            }
        }

        return maxId;
    }

    protected int getBestIllegalGood(int goodsInHand[], Map<Integer, Goods> goodsById) {
        int id = goodsInHand[0]; // i assume the best good is the first one

        for (int i = 1; i < goodsInHand.length; ++i) {
            Goods good0 = goodsById.get(id);
            Goods good1 = goodsById.get(goodsInHand[i]);
            if (good1.getProfit() > good0.getProfit()) {
                id = goodsInHand[i];
            }
        }

        return id;
    }

    @Override
    public Bag createBag(int[] goodsInHand, Map<Integer, Goods> goodsById) {
        Bag bag = new Bag();
        int[] frequency = createFrequency(goodsInHand);
        int id = getMostFrequent(frequency, goodsById);

        // case if there are legal goods
        if (id >= 0) {
            for (int i = 0; i < frequency[id] && bag.getSize() < 8; ++i) {
                bag.addToBag(id);
            }
            bag.setDeclaredType(id);
        } else {
            id = getBestIllegalGood(goodsInHand, goodsById);
            bag.addToBag(id);
            bag.setDeclaredType(0); // set declared type as apple id
        }
        return bag;
    }

}
