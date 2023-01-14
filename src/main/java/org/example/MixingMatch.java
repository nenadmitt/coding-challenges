package org.example;

public class MixingMatch {

    int oneOrTwo;
    boolean same;
    char name;
    int value;

    public int getOneOrTwo() {
        return oneOrTwo;
    }

    public void setOneOrTwo(int oneOrTwo) {
        this.oneOrTwo = oneOrTwo;
    }

    public boolean isSame() {
        return same;
    }

    public void setSame(boolean same) {
        this.same = same;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MixingMatch{" +
                "oneOrTwo=" + oneOrTwo +
                ", same=" + same +
                ", name=" + name +
                ", value=" + value +
                '}';
    }
}
