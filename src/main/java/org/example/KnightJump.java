package org.example;

import java.util.*;
import java.util.stream.Stream;

//https://www.codewars.com/kata/549ee8b47111a81214000941
// Difficulty: 4kyu
public class KnightJump {

    class Move {
        int x;
        int y;
        int depth;

        public Move(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }


    public static int knight(String start, String finish) {
       return new KnightJump().getMinMoves(start, finish);
    }

    public int getMinMoves(String start, String end) {

        var startX = start.charAt(0) - 96;
        var startY = start.charAt(1) - 48;
        var endX = end.charAt(0) - 96;
        var endY = end.charAt(1) - 48;

        var startM = new Move(startX, startY, 0);

        Queue<Move> q = new LinkedList<>();
        var depth = 0;
        q.add(startM);
        while(!q.isEmpty()) {
            var elem = q.remove();
            if (elem.x == endX && elem.y == endY) {
                return elem.depth;
            }
            depth++;
            addMoves(q, elem, depth);
        }

        return 0;
    }

    public void addMoves(Queue<Move> q, Move current, int depth) {

        var leftPointUp = new Move(current.x - 2, current.y - 1, depth);
        var leftPointDown = new Move(current.x - 2, current.y + 1, depth);

        var rightPointUp = new Move(current.x + 2, current.y + 1, depth);
        var rightPointDown = new Move(current.x + 2, current.y - 1,depth);

        var upPointLeft = new Move(current.x + 1, current.y + 2 ,depth);
        var upPointRight = new Move(current.x - 1, current.y + 2, depth);

        var downPointLeft = new Move(current.x + 1, current.y - 2, depth);
        var downPointRight = new Move(current.x - 1, current.y - 2, depth);

        var availableMoves = Stream
                .of(leftPointUp, leftPointDown, rightPointDown, rightPointUp, upPointRight, upPointLeft, downPointRight, downPointLeft)
                .filter(this::canMove).toList();

        q.addAll(availableMoves);
    }

    private boolean canMove(Move v) {
        return v.x > 0 && v.x < 9 && v.y > 0 && v.y < 9;
    }
}
