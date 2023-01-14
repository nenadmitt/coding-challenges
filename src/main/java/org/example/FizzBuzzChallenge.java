package org.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuzzChallenge {

    public List<String> fizzBuzz(int n) {

        if (n < 1 || n > 10000) {
            throw new IllegalArgumentException();
        }
        return IntStream.range(1, n + 1).boxed().map(this::toFizzBuzz).collect(Collectors.toList());
    }

    private String toFizzBuzz(int n) {
        if (n % 5 == 0 && n % 3 == 0) {
            return "FizzBuzz";
        }else if (n % 3 == 0) {
            return "Buzz";
        }else if (n % 5 == 0) {
            return "Fizz";
        }
        return String.valueOf(n);
    }
}
