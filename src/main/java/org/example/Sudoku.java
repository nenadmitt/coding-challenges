package org.example;

import java.util.HashSet;
import java.util.Set;

//https://www.codewars.com/kata/529bf0e9bdf7657179000008
// Difficulty: 4kyu?
public class Sudoku {

    public boolean check(int[][] arr) {
       return isValidCube(arr) && isValidVertical(arr) && isValidHorizontal(arr);
    }
    private boolean isValidHorizontal(int[][] arr) {
        for (int[] currentArr : arr) {
            var set = new HashSet<Integer>();

            for (int current : currentArr) {

                if (current < 1 || current > 9) {
                    return false;
                }

                if (set.contains(current)) {
                    return false;
                } else {
                    set.add(current);
                }
            }
        }
        return true;
    }

    private boolean isValidVertical(int[][] arr) {

        var arrSize = arr[0].length;

        for (var i = 0; i < arrSize; i ++) {
            var set = new HashSet<Integer>();
            for (var j = 0; j < arr.length; j++) {
                var current = arr[i][j];
                if (set.contains(current)) {
                    return false;
                } else {
                    set.add(current);
                }
            }
        }
        return true;
    }

    private boolean isValidCube(int[][]arr) {

        var x = 0;
        var y = 0;

        while (y < 9) {
            var isCubeValid = cubeRange(arr, x, x + 2, y , y + 2);
            if (!isCubeValid) {
                return false;
            }
            x += 3;
            if (x >= 9) {
                x = 0;
                y += 3;
            }
        }
        return true;
    }

    private boolean cubeRange(int[][]arr, int startX, int endX, int startY, int endY) {

        Set<Integer> set = new HashSet<>();
        for (var i = startY; i <= endY; i++) {
            for (var j = startX; j <= endX; j++) {
                var current = arr[i][j];
                if (set.contains(current)) {
                    return false;
                }else {
                    set.add(current);
                }
            }
        }
        return true;
    }
}
