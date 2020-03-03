package com.tema1.players;

public class GreedyPlayerSheriff extends BasicPlayerSheriff{

    @Override
    public void inspectBag(Player sheriff, Player inspectedPlayer, Bag bag) {
        int goodsInBag[] = bag.getGoodsInBag();
        int declaredType = bag.getDeclaredType();
        int bribe = bag.getBribe();

        if (bribe != 0) {

            sheriff.addCoins(bribe);
            inspectedPlayer.removeCoins(bribe);
            for (int i = 0; i < bag.getSize(); ++i) {
                int id = goodsInBag[i];
                inspectedPlayer.addOnTable(id);
                illegalBonus(id, inspectedPlayer);
            }
        } else {
            super.inspectBag(sheriff, inspectedPlayer, bag);
        }
    }
}
