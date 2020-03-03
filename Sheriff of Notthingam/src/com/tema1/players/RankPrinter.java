package com.tema1.players;

public class RankPrinter {
    private Player[] players = new Player[7];
    private int numberOfPlayers;

    // constructors


    public RankPrinter(Player[] players, int numberOfPlayers) {
        this.players = players;
        this.numberOfPlayers = numberOfPlayers;
    }

    public void sortPlayers() {
        Player aux = new Player();
        for (int i = 0; i < numberOfPlayers - 1; ++i) {
            for (int j = i + 1; j < numberOfPlayers; ++j) {
                if (players[i].getCoins() < players[j].getCoins()) {
                    aux = players[i];
                    players[i] = players[j];
                    players[j] = aux;
                } else if (players[i].getCoins() == players[j].getCoins()) {
                    if (players[i].getId() > players[j].getId()) {
                        aux = players[i];
                        players[i] = players[j];
                        players[j] = aux;
                    }
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < numberOfPlayers; ++i) {
            System.out.println(players[i]);
        }
    }
}
