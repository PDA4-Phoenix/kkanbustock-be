package com.bull4jo.kkanbustock.common;

import java.util.Random;

public class RandomNumberGenerator {
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static double random(double min, double max) {
        Random random = new Random();

        return random.nextDouble(max - min + 1) + min;
    }
}
