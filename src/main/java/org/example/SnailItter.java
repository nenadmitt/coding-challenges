package org.example;

//https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1
//Difficulty: 4kyu
public class SnailItter {

    public static int[] snail(int[][] arr) {

        if (arr.length == 0) {
            return new int[]{};
        }

        var lastY = arr.length / 2;
        var lastX = (arr[0].length - 1) / 2;
        var depth = (arr.length - 1 ) / 2;
        var result = new int[arr.length * arr[0].length];
        var index = 0;

        for (var i = 0; i <= depth; i++) {

            var endX = arr[0].length - i;
            var endY = arr.length - i;

            var finished = false;

            var posX = i;
            var posY = i;
            byte step = 0;

            while(!finished) {


                var current = arr[posY][posX];
                result[index++] = current;

                if (posY == lastY && posX == lastX) {
                    break;
                }

                if (step == 0) {
                    if (posX == endX - 1) {
                        step = 1;
                    }else {
                        posX++;
                    }
                }

                if (step == 1) {
                    if (posY == endY - 1) {
                        step = 2;
                    }else {
                        posY++;
                    }
                }

                if (step == 2) {
                    if (posX == i) {
                        step = 3;
                    }else {
                        posX--;
                    }
                }

                if (step == 3) {
                    if (posY == i + 1) {
                        finished = true;
                    }else {
                        posY--;
                    }
                }
            }
        }

        return result;
    }
}
