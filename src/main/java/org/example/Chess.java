package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chess {

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point point)) return false;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static int knight(String start, String finish) {
       return new Chess().simulate(start, finish);
    }

    public int simulate(String start, String end) {
        var minMoves = Integer.MAX_VALUE;
        for (var i = 0; i < 100; i++) {
            var totalMoves = moves(start, end);
            if (totalMoves < minMoves) {
                minMoves = totalMoves;
            }
        }
        return minMoves;
    }

    private int moves(String start, String finish) {
        var startPoint = getFromString(start);
        var endPoint = getFromString(finish);

        var currentPosition = startPoint;
        var counter = 0;
        var rand = new Random();

        while (!currentPosition.equals(endPoint)) {

            var availableMoves = possibleMoves(currentPosition, currentPosition);
            var randomMoveIndex = rand.nextInt(availableMoves.size());
            var currentMove = availableMoves.get(randomMoveIndex);

            currentPosition = new Point(currentMove.x, currentMove.y);
            counter++;
        }
        return counter;
    }

    private List<Point> possibleMoves(Point current, Point previousPosition) {

        var leftPointUp = new Point(current.x - 2, current.y - 1);
        var leftPointDown = new Point(current.x - 2, current.y + 1);

        var rightPointUp = new Point(current.x + 2, current.y + 1);
        var rightPointDown = new Point(current.x + 2, current.y - 1);

        var upPointLeft = new Point(current.x + 1, current.y + 2);
        var upPointRight = new Point(current.x - 1, current.y + 2);

        var downPointLeft = new Point(current.x + 1, current.y - 2);
        var downPointRight = new Point(current.x - 1, current.y - 2);

        return Stream
                .of(leftPointUp, leftPointDown, rightPointDown, rightPointUp, upPointRight, upPointLeft, downPointRight, downPointLeft)
                .filter(i -> canMove(i) && !i.equals(previousPosition))
                .collect(Collectors.toList());
    }

    private boolean canMove(Point knight) {
        int TABLE_SIZE = 8;
        return knight.x >= 0 && knight.x < TABLE_SIZE && knight.y >= 0 && knight.y < TABLE_SIZE;
    }

    private Point getFromString(String position) {

        var positionXMap = Map.of('a', 0, 'b', 1, 'c', 2, 'd', 3, 'e', 4, 'f', 5, 'g', 6, 'h', 7);
        var positionYMap = Map.of('8', 0, '7', 1, '6', 2, '5', 3, '4', 4, '3', 5, '2', 6, '1', 7);

        if (position.length() != 2) {
            throw new IllegalArgumentException();
        }

        var axisX = position.charAt(0);
        var axisY = position.charAt(1);

        if (!positionXMap.containsKey(axisX) || !positionYMap.containsKey(axisY)) {
            throw new IllegalArgumentException();
        }

        return new Point(positionXMap.get(axisX), positionYMap.get(axisY));
    }
}
