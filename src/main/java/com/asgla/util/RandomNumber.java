package com.asgla.util;

import java.util.Random;

public class RandomNumber {

    public static int intInRange(int min, int max) {
        return new Random().ints(min, (max + 1)).findFirst().getAsInt();
    }

    public static double doubleInRange(double min, double max) {
        return roundDouble(new Random().doubles(min, max).findFirst().getAsDouble());
    }

    public static double roundDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
