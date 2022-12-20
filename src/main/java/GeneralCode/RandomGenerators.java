package GeneralCode;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomGenerators {

    public static int getRandomInt(int max) {
        return (int)(Math.random() * (max + 1));
    }

    public static float getRandomFloat(int max) {
        return (float)(Math.random() * (max + 1));
    }

    public static String getRandomString(int maxLength) {
        return RandomStringUtils.randomAlphabetic(maxLength);
    }
}
