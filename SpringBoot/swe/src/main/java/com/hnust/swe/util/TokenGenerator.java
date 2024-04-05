package com.hnust.swe.util;
import java.util.Random;
public class TokenGenerator {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final int LENGTH = 6;

    public static String generateToken() {
        Random random = new Random();
        StringBuilder token = new StringBuilder(LENGTH);

        // 生成至少一个小写字母
        token.append(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));

        // 生成至少一个大写字母
        token.append(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));

        // 生成其余的字符（数字和字母）
        for (int i = 2; i < LENGTH; i++) {
            String characters = NUMBERS + LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS;
            token.append(characters.charAt(random.nextInt(characters.length())));
        }

        // 将字符随机排序
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(LENGTH);
            char temp = token.charAt(i);
            token.setCharAt(i, token.charAt(randomIndex));
            token.setCharAt(randomIndex, temp);
        }

        return token.toString();
    }
}