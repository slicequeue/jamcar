package com.slicequeue.jamcar.common.utils;

import java.util.regex.Pattern;

public final class StringPatternMatchingUtil {

    public enum RegexPattern {
        passwordValidRegexLv3("비밀번호 체크 Lv3 8~20자리의 영문, 숫자, 특수문자 조합",
                "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$"),
        emailValidRegex("이메일 주소 형식", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),

        ;

        private final String description;
        private final String regex;

        RegexPattern(String description, String regex) {
            this.description = description;
            this.regex = regex;
        }
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(RegexPattern.passwordValidRegexLv3.regex);
        return pattern.matcher(password).matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(RegexPattern.emailValidRegex.regex);
        return pattern.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        return !name.isBlank();
    }

    private StringPatternMatchingUtil() {
        throw new IllegalStateException("no StringPatternMatchingUtil");
    }

}
