package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PawnTracker {

    String[][] board = new String[8][8];
    // 0 - white, 1 - black
    byte player = 0;
    Map<Character, Integer> cords = new HashMap<>();
    //   a b c d e f g h
    // 8 . . . . . . . .
    // 7 p . . . p p p p
    // 6 . p . . . . . .
    // 5 . . p p . . . .
    // 4 . . . P . P . .
    // 3 . . . . P . . .
    // 2 P P P . . . P P
    // 1 . . . . . . . .

    //   0 1 2 3 4 5 6 7
    // 0 . . . . . . . .
    // 1 p p p p p p p p
    // 2 . . . . . . . .
    // 3 . . . . . . . .
    // 4 . . . . . . . .
    // 5 . . . . . . . .
    // 6 P P P P P P P P
    // 7 . . . . . . . .

    public PawnTracker() {
        var xCords = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (var i = 0; i < 8; i++) {
            cords.put(xCords[i], i);
            var strValue = String.valueOf(Math.abs(8 - i));
            cords.put(strValue.charAt(0), i);
            for (var j = 0; j < 8; j++) {
                if (i == 6 || i == 1) {
                    board[i][j] = i == 6 ? "P" : "p";
                } else {
                    board[i][j] = ".";
                }
            }
        }

        System.out.println(cords);
        print();
    }

    void print() {
        for (var a : board) {
            System.out.println(Arrays.toString(a));
        }
    }


    public static String[][] movePawns(String[] moves) {
        // Your code here!
        return new PawnTracker().move(moves);
    }

    private String[][] move(String[] moves) {

        System.out.println(moves);
        for (var m : moves) {

            var currentPlayer = player == 0 ? "P" : "p";
            var emptySpace = ".";

            // plain move
            if (m.length() == 2) {

                if (!cords.containsKey(m.charAt(0)) || !cords.containsKey(m.charAt(1))) {
                    return new String[][]{{m + " is invalid"}};
                }

                var jumpX = cords.get(m.charAt(0));
                var jumpY = cords.get(m.charAt(1));

                var indexY  = player == 0 ? jumpY + 1 : jumpY - 1;;
                var pawnExists = board[indexY][jumpX].equals(currentPlayer);;
                var isPathFree = board[jumpY][jumpX].equals(emptySpace);
                var isInside = jumpY < 8;

                //check for double jump
                if (jumpY == 4 || jumpY == 3) {
                    if (!pawnExists) {
                        indexY = player == 0 ? jumpY + 2 : jumpY - 2;
                        pawnExists = board[indexY][jumpX].equals(currentPlayer);;
                    }
                }
                if (!pawnExists || !isPathFree || !isInside) {
                    return new String[][]{{m + " is invalid"}};
                }

                board[jumpY][jumpX] = currentPlayer;
                board[indexY][jumpX] = emptySpace;
                print();
                // capture
            }else {
                var takerY = cords.get(m.charAt(0));
                var eatenX = cords.get(m.charAt(2));
                var eatenY = cords.get(m.charAt(3));
            }
            player = player == 0 ? (byte) 1 : (byte) 0;
        }

        return null;
    }
}
