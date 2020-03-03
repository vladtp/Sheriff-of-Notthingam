package com.tema1.players;


import java.util.Map;

public class BasicPlayerSheriff implements SheriffStrategy {
    private static final int ILLEGAL_PENALTY = 4;
    private static final int LEGAL_PENALTY = 2;

    public void illegalBonus(int id, Player inspectedPlayer) {
        if (id > 9) { // if good is legal
            if (id == 20) {
                for (int i = 0; i < 3; ++i) {
                    inspectedPlayer.addOnTable(1); // add cheese 3 times
                }
            }
            if (id == 21) {
                for (int i = 0; i < 2; ++i) {
                    inspectedPlayer.addOnTable(3); // add chicken 2 times
                }
            }
            if (id == 22) {
                for (int i = 0; i < 2; ++i) {
                    inspectedPlayer.addOnTable(2); // add bread 2 times
                }
            }
            if (id == 23) {
                for (int i = 0; i < 4; ++i) {
                    inspectedPlayer.addOnTable(7); // add wine 4 times
                }
            }
            if (id == 24) {
                for (int i = 0; i < 2; ++i) {
                    inspectedPlayer.addOnTable(4); // add tomato 2 times
                }
                for (int i = 0; i < 3; ++i) {
                    inspectedPlayer.addOnTable(6); // add potato 3 times
                }
                for (int i = 0; i < 1; ++i) {
                    inspectedPlayer.addOnTable(3); // add chicken 1 times
                }
            }

        }
    }

    @Override
    public void inspectBag(Player sheriff, Player inspectedPlayer, Bag bag) {
        int goodsInBag[] = bag.getGoodsInBag();
        int declaredType = bag.getDeclaredType();
        boolean isLying = false;
        int penaltyToSheriff = 0;
        int penaltyToPlayer = 0;

        if (sheriff.getCoins() >= 16) {
            for (int i = 0; i < bag.getSize(); ++i) {
                int id = goodsInBag[i];
                if (id != declaredType) {
                    isLying = true;
                    if (id >= 0 && id <= 9) { // if it is a legal good
                        penaltyToSheriff += LEGAL_PENALTY;
                    } else {
                        penaltyToSheriff += ILLEGAL_PENALTY;
                    }
                    //TO DO: move card to deck
                } else {
                    penaltyToPlayer += LEGAL_PENALTY;
                    inspectedPlayer.addOnTable(id);
                }
            }
            if (isLying) {
                inspectedPlayer.removeCoins(penaltyToSheriff);
                sheriff.addCoins(penaltyToSheriff);
            } else {
                inspectedPlayer.addCoins(penaltyToPlayer);
                sheriff.removeCoins(penaltyToPlayer);
            }
        } else {
            for (int i = 0; i < bag.getSize(); ++i) {
                int id = goodsInBag[i];
                inspectedPlayer.addOnTable(id);
                illegalBonus(id, inspectedPlayer);
            }
        }
    }
}
