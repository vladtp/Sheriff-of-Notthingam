package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.common.Constants;

import java.util.Map;

public class BribePlayerStrategy extends BasicPlayerStrategy {
    Constants constants = new Constants();

    public void sortByProfit(int[] goodsInHand, Map<Integer, Goods> goodsById) {
        for (int i = 0; i < goodsInHand.length - 1; ++i) {
            for (int j = i + 1; j < goodsInHand.length; ++j) {
                Goods good0 = goodsById.get(goodsInHand[i]);
                Goods good1 = goodsById.get(goodsInHand[j]);
                if (good0.getProfit() < good1.getProfit()) {
                    int aux = goodsInHand[i];
                    goodsInHand[i] = goodsInHand[j];
                    goodsInHand[j] = aux;
                } else if (good0.getProfit() == good1.getProfit()) {
                    if (goodsInHand[i] < goodsInHand[j]) {
                        int aux = goodsInHand[i];
                        goodsInHand[i] = goodsInHand[j];
                        goodsInHand[j] = aux;
                    }
                }
            }
        }
    }

    public Bag createBag(int[] goodsInHand, Map<Integer, Goods> goodsById, int coins) {
        Bag bag = new Bag();
        int neededCoins = 0;

        if (coins <= 4) {
            return super.createBag(goodsInHand, goodsById);
        }

        sortByProfit(goodsInHand, goodsById);
        int i = 0;
        int illegalGoodsCounter = 0;
        // look at all illegal goods
        while (i < goodsInHand.length && bag.getSize() < 8 && goodsInHand[i] > 9) {
            int id = goodsInHand[i];
            int extraCoins = 0;
            extraCoins += constants.ILLEGAL_PENALITY;

            if (neededCoins + extraCoins < coins) {
                neededCoins += extraCoins;
                bag.addToBag(id);
                illegalGoodsCounter++;
            }
            ++i;
        }
        if (illegalGoodsCounter > 2) {
            bag.setBribe(10);
        } else if (illegalGoodsCounter > 0) {
            bag.setBribe(5);
        }

        if (illegalGoodsCounter > 0) {
            // look at all legal goods
            while (i < goodsInHand.length && bag.getSize() < 8 && goodsInHand[i] <= 9) {
                int id = goodsInHand[i];
                if (id != 0) { // if it isn't apple
                    if (neededCoins + constants.LLEGAL_PENALITY < coins) {
                        neededCoins += constants.ILLEGAL_PENALITY;
                        bag.addToBag(id);
                    }
                } else {
                    bag.addToBag(id);
                }
                ++i;
            }

            bag.setDeclaredType(0);
        } else {
            return super.createBag(goodsInHand, goodsById);
        }
        return bag;
    }
}
