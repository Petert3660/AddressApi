package com.ptconsultancy.utilities;

import java.util.Random;

public class GenerateRandomKeys {

    static final int ALL_CHARACTERS = 0;
    static final int ALPHANUMERIC = ALL_CHARACTERS + 1;
    static final int ALPHA = ALPHANUMERIC + 1;
    static final int ALPHA_UPPER_CASE = ALPHA + 1;
    static final int ALPHA_LOWER_CASE = ALPHA_UPPER_CASE + 1;
    static final int NUMERIC = ALPHA_LOWER_CASE + 1;

    public static String generateRandomKey(int keyLength, int mode) {

        String ALLCHARS = "";
        if (mode == ALL_CHARACTERS) {
            ALLCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!£$%^&*()<>?{}[]:@#;";
        } else if (mode == ALPHANUMERIC) {
            ALLCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        } else if (mode == ALPHA) {
            ALLCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        } else if (mode == ALPHA_UPPER_CASE) {
            ALLCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        } else if (mode == ALPHA_LOWER_CASE) {
            ALLCHARS = "abcdefghijklmnopqrstuvwxyz";
        } else if (mode == NUMERIC) {
            ALLCHARS = "0123456789";
        }
        StringBuilder temp = new StringBuilder();
        Random rnd = new Random();

        while (temp.length() < keyLength) {
            int index = (int)(rnd.nextFloat() * ALLCHARS.length());
            temp.append(ALLCHARS.charAt(index));
        }

        return temp.toString();
    }
}
