package com.asgla.util;

public class Util {

    public static double increaseStatByLevel(int level) {
        int minStat = 0;
        int maxStat = 1000;

        return Math.floor((.8 + level / 50) * level * ((maxStat - minStat) / 275.222) + minStat + .1);
    }

}
