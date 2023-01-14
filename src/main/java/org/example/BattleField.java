package org.example;

import java.util.*;

public class BattleField {

    class Point{
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

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    private final int[][] fields;
    private final int FIELD_SIZE = 10;

    public BattleField(int[][] matrix) {
        fields = matrix;
        print();
    }

    private void print() {
        for (var c : fields) {
            System.out.println(Arrays.toString(c));
        }
        System.out.println("----");
    }

    public static boolean fieldValidator(int[][] fields) {
        return new BattleField(fields).verify();
    }

    public boolean verify() {

        var expectedSpaces = 1 * 4 + 2 * 3 + 3 * 2 + 4 * 1;
        var takenSpaces = 0;
        for (var i = 0; i < FIELD_SIZE; i++) {
            for (var j = 0; j < FIELD_SIZE;j ++) {
                if (fields[i][j] == 1) {
                    takenSpaces++;
                }
            }
        }

//        if (expectedSpaces != takenSpaces) {
//            return false;
//        }

        var battleShipCount = checkHorizontal(4) + checkVertical(4);
        System.out.println(battleShipCount + " battleship");
        var destroyerCount = checkHorizontal(3) + checkVertical(3);
        System.out.println(destroyerCount + " cruiser");
        var cruiserCount = checkHorizontal(2) + checkVertical(2);
        System.out.println(cruiserCount + " desctroyer");
        var submarineCount = checkHorizontal(1);
        System.out.println(submarineCount + " submarine");
        return battleShipCount == 1 && destroyerCount == 2 && cruiserCount == 3 && submarineCount == 4;
    }

    public int checkVertical(int shipLength) {
        var sequence = 0;
        var sequenceStarted = false;
        var validShipCount = 0;

        LinkedList<Point> pointList = new LinkedList<>();
        for (var y = 0; y < FIELD_SIZE; y++) {
            for (var x = 0; x < FIELD_SIZE; x++) {
                var current = fields[x][y];
                var isLastElement = x == FIELD_SIZE - 1;
                var isTaken = current == 1;
                var isFree = current != 1;

                if (isTaken && !sequenceStarted) {
                    sequenceStarted = true;
                }

                if (isTaken && sequenceStarted) {
                    System.out.println("adding sequence");
                    System.out.println(new Point(y, x));
                    sequence++;
                    pointList.addLast(new Point(y, x));
                }

                if (sequenceStarted && (isFree || isLastElement)) {
                    if (sequence == shipLength) {
                        if (verifyNoAdjustmentSpaces(pointList)) {
                            System.out.println("adding ship x:" + x + " y:" + y);
                            validShipCount++;
                        }
                    }
                    pointList.clear();
                    sequenceStarted = false;
                    sequence = 0;
                }

            }
        }
        return validShipCount;
    }

    public int checkHorizontal(int shipLength) {

        var sequence = 0;
        var sequenceStarted = false;
        var validShipCount = 0;
        LinkedList<Point> pointList = new LinkedList<>();

        for (var y = 0; y < FIELD_SIZE; y++) {
            for (var x = 0; x < FIELD_SIZE; x++) {
                var current = fields[y][x];
                var isLastElement = x == FIELD_SIZE - 1;
                var isTaken = current == 1;
                var isFree = current != 1;

                if (isTaken && !sequenceStarted) {
                    sequenceStarted = true;
                }

                if (isTaken && sequenceStarted) {
                    sequence++;
                    pointList.addLast(new Point(x, y));
                }
                if (sequenceStarted && (isFree || isLastElement)) {
                    if (sequence == shipLength) {
                        if (verifyNoAdjustmentSpaces(pointList)) {
                            validShipCount++;
                        }
                    }
                    pointList.clear();
                    sequenceStarted = false;
                    sequence = 0;
                }
            }
        }
        return validShipCount;
    }

    private boolean verifyNoAdjustmentSpaces(LinkedList<Point> list) {

        var startPoint = list.getFirst();
        var endPoint = list.getLast();
        var shipSet = new HashSet<>(list);

        var startY = startPoint.y > 0 ? startPoint.y - 1 : 0;
        var startX = startPoint.x > 0 ? startPoint.x - 1 : 0;
        var endY = endPoint.y == FIELD_SIZE - 1 ? endPoint.y : endPoint.y + 1;
        var endX = endPoint.x == FIELD_SIZE - 1 ? endPoint.x : endPoint.x + 1;

        for (var y = startY; y <= endY; y++) {
            for (var x = startX; x <= endX; x++) {

                var currentPoint = new Point(x,y);
                if (!shipSet.contains(currentPoint)) {

                    var current = fields[y][x];
                    if (current == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
