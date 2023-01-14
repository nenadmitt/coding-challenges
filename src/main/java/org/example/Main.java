package org.example;

public class Main {

    public static void main(String[] args) {

        var start = "f7";
        var end = "b1";

        var res = Chess.knight(start, end);
        System.out.println("res " + res);
    }
}