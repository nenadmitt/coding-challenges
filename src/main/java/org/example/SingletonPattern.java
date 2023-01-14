package org.example;

public class SingletonPattern {

    private static FizzBuzzChallenge instance;

    public static FizzBuzzChallenge getInstance() {
        if (instance == null) {
            instance = new FizzBuzzChallenge();
        }
        return instance;
    }
}
