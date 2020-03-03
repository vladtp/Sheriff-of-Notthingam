package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.LegalGoods;

import java.util.ArrayList;
import java.util.Map;


public class Game {
    private Player[] players = new Player[7];
    private int numberOfPlayers;
    private ArrayList<Integer> deckOfCards = new ArrayList<Integer>();
    private int rounds;
    private GoodsFactory goodsFactory = GoodsFactory.getInstance();

    // constructors
    public Game(ArrayList<Integer> deckOfCards, int rounds, ArrayList<String> playersStrategy) {
        this.deckOfCards = deckOfCards;
        this.rounds = rounds;
        numberOfPlayers = playersStrategy.size();
        for (int i = 0; i < numberOfPlayers; ++i) {
            players[i] = new Player(i, playersStrategy.get(i));
        }
    }

    // getters and setters

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public ArrayList<Integer> getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(ArrayList<Integer> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    // methods
    public void addToDeck(int id) {
        deckOfCards.add(id);
    }

    public int removeFromDeck() {
        int id = deckOfCards.get(0);
        deckOfCards.remove(0);
        return id;
    }

    private int[] drawCards(Player player) {
        int[] goodsInHand = new int[10];
        for (int i = 0; i < 10; ++i) {
            goodsInHand[i] = removeFromDeck();
        }
        return goodsInHand;
    }

    public void playSubRound(int sheriff, int round) {
        BasicPlayerSheriff basicPlayerSheriff = new BasicPlayerSheriff();
        BasicPlayerStrategy basicPlayerStrategy = new BasicPlayerStrategy();
        GreedyPlayerStrategy greedyPlayerStrategy = new GreedyPlayerStrategy();
        GreedyPlayerSheriff greedyPlayerSheriff = new GreedyPlayerSheriff();
        BribePlayerStrategy bribePlayerStrategy = new BribePlayerStrategy();
        BribePlayerSheriff bribePlayerSheriff = new BribePlayerSheriff();

        for (int i = 0; i < numberOfPlayers; ++i) {
            if (i != sheriff) {
                Player player = players[i];
                Bag bag = new Bag();

                // draw new cards
                player.setGoodsInHand(drawCards(player));

                if (player.getStrategy().equals("basic")) {
                    bag = basicPlayerStrategy.createBag(player.getGoodsInHand(), goodsFactory.getAllGoods());
                } else if (player.getStrategy().equals("bribed")) {
                    bag = bribePlayerStrategy.createBag(player.getGoodsInHand(), goodsFactory.getAllGoods(),
                            player.getCoins());
                } else if (player.getStrategy().equals("greedy")) {
                    bag = greedyPlayerStrategy.createBag(player.getGoodsInHand(), goodsFactory.getAllGoods(), round);
                }


                // sheriff inspection
                if (players[sheriff].getStrategy().equals("basic")) {
                    basicPlayerSheriff.inspectBag(players[sheriff], player, bag);
                } else if (players[sheriff].getStrategy().equals("bribed")) {
                    bribePlayerSheriff.inspectBag(players[sheriff], player, bag, numberOfPlayers);
                } else if (players[sheriff].getStrategy().equals("greedy")) {
                    greedyPlayerSheriff.inspectBag(players[sheriff], player, bag);
                }

                //debug
//                System.out.println(player.getId() + " " + player.getStrategy() + ":");
////                System.out.print("in bag: ");
////                for (int item : bag.getGoodsInBag()) {
////                    System.out.print(item + " ");
////                }
////                System.out.println();
////                System.out.println("on table " + player.getGoodsOnTable());
////                System.out.println();
            }
        }
    }

    public void calculateScore() {
        Map<Integer, Goods> goodsById = goodsFactory.getAllGoods();

        for (int i = 0; i < numberOfPlayers; ++i) {
            ArrayList<Integer> goodsOnTable = players[i].getGoodsOnTable();
            for (int j = 0; j < goodsOnTable.size(); ++j) {
                Goods good = goodsById.get(goodsOnTable.get(j));
                players[i].addCoins(good.getProfit());
            }
        }
    }

    public void legalBonus() {
        int[] kingBonusId = new int[10];
        int[] queenBonusId = new int[10];
        int[] maxLegalGoods = new int[10];
        int[] secondMaxLegalGoods = new int[10];
        Map<Integer, Goods> goodsById = goodsFactory.getAllGoods();

        for (int i = 0; i < 10; ++i) {
            kingBonusId[i] = queenBonusId[i] = -1; // no one has a bonus
        }

        for (int playerId = 0; playerId < numberOfPlayers; ++playerId) {
            int[] goodsFrequency = players[playerId].getFrequency();
            for (int goodId = 0; goodId < 10; ++goodId) {
                if (goodsFrequency[goodId] > maxLegalGoods[goodId]) {
                    secondMaxLegalGoods[goodId] = maxLegalGoods[goodId];
                    maxLegalGoods[goodId] = goodsFrequency[goodId];
                    queenBonusId[goodId] = kingBonusId[goodId];
                    kingBonusId[goodId] = playerId;
                } else if (goodsFrequency[goodId] > secondMaxLegalGoods[goodId]) {
                    secondMaxLegalGoods[goodId] = goodsFrequency[goodId];
                    queenBonusId[goodId] = playerId;
                }
            }
        }

        for (int i = 0; i < 10; ++i) {
            LegalGoods good = (LegalGoods) goodsById.get(i);

            if (kingBonusId[i] >= 0) {
                players[kingBonusId[i]].addCoins(good.getKingBonus());
            }
            if (queenBonusId[i] >= 0) {
                players[queenBonusId[i]].addCoins(good.getQueenBonus());
            }
        }
    }

}
