package org.example;

import java.util.*;

//https://www.codewars.com/kata/58c5577d61aefcf3ff000081
//Dificulty: 3kyu
public class RailFenceCipher {


    static String encode(String s, int n) {

        var rails = convert(s, n);

        System.out.println(rails + " original rails");

        StringBuilder result = new StringBuilder();
        for (var res: rails) {
            result.append(res);
        }

        return result.toString();
    }

    static String decode(String s, int n) {

        var rails = convert(s, n);
        var decodedRails = new ArrayList<String>();

        for (var i = 0; i< n; i++) {
            decodedRails.add("");
        }

        var startIndex = 0;
        var endIndex = 0;
        for (var i = 0; i < rails.size(); i++) {
            var current = rails.get(i);
            endIndex += current.length();
            var value = s.substring(startIndex, endIndex);
            decodedRails.set(i, value);
            startIndex = endIndex;
        }

        return revert(decodedRails, s.length());
    }

    static String revert(List<String> rails, int len) {

        var result = new StringBuilder();
        var counter = 0;
        var charCounter = 0;
        var desc = true;
        var n = rails.size();
        System.out.println(rails + " decode rails");


        for (var i = 0; i < len; i++) {
            var current = rails.get(counter);
            if (current.length() > 0) {
                result.append(current.charAt(0));
                rails.set(counter, current.substring(1));
            }

            if (counter == n - 1 && desc) {
                counter--;
                desc = false;
            }else if(counter == 0 && !desc) {
                counter ++;
                desc = true;
            }else {
                counter = desc ? counter + 1 : counter - 1;
            }
        }
        return result.toString();
    }
    static List<String> convert(String s, int n) {
        List<String> rails = new ArrayList<>();

        for (var i = 0; i < n; i++) {
            rails.add("");
        }

        var counter = 0;
        var desc = true;
        for (var i = 0; i < s.length(); i++) {;
            rails.set(counter, rails.get(counter) + s.charAt(i));
            if (counter == n - 1 && desc) {
                counter --;
                desc = false;
            }else if (counter == 0 && !desc) {
                counter ++;
                desc = true;
            }else {
                counter = desc ? counter + 1 : counter - 1;
            }
        }
        return rails;
    }
}
