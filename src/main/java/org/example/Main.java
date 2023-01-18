package org.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        var moves = new String[] {"d4", "d5", "f3", "c6", "f4"};
        var m = new String[]{"e3", "d6", "e4", "a6"};
        var res = PawnTracker.movePawns(moves);

    }
}