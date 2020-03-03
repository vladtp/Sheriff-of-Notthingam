package com.tema1.main;

import com.tema1.players.Game;
import com.tema1.players.RankPrinter;

import java.util.ArrayList;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        Game game = new Game((ArrayList<Integer>) gameInput.getAssetIds(),
                gameInput.getRounds(),
                (ArrayList<String>) gameInput.getPlayerNames());

        // error case
        if (!gameInput.isValidInput()) {
            return;
        }

        // play game
        for (int round = 1; round <= game.getRounds(); ++round) {

            //debug
             //System.out.println("ROUND NUMBER:" + round);

            int sheriff = 0; // the first player is the sheriff

            for (int subround = 0; subround < game.getNumberOfPlayers(); ++subround) {

                //debug
//                RankPrinter rankPrinterTest = new RankPrinter(game.getPlayers(), game.getNumberOfPlayers());
//                rankPrinterTest.print();
//                System.out.println("Subround number:" + subround);

                game.playSubRound(sheriff, round);
                sheriff++;
            }
        }

        // print ranking
        game.calculateScore();
        game.legalBonus();
        RankPrinter rankPrinter = new RankPrinter(game.getPlayers(), game.getNumberOfPlayers());
        rankPrinter.sortPlayers();
        rankPrinter.print();
    }
}
