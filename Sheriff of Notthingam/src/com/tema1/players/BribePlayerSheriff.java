package com.tema1.players;

public class BribePlayerSheriff extends BasicPlayerSheriff {
    public void inspectBag(Player sheriff, Player inspectedPlayer, Bag bag, int numberOfPlayers) {
        int sheriffId = sheriff.getId();
        int leftId = sheriffId - 1;
        if (leftId < 0) {
            leftId = numberOfPlayers - 1;
        }
        int rightId = sheriffId + 1;
        if (rightId == numberOfPlayers) {
            rightId = 0;
        }

        if (inspectedPlayer.getId() == leftId || inspectedPlayer.getId() == rightId) {
            super.inspectBag(sheriff, inspectedPlayer, bag);
        } else {
            int bribe = bag.getBribe();

            sheriff.addCoins(bribe);
            inspectedPlayer.removeCoins(bribe);
        }

    }
}
