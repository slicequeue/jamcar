package com.slicequeue.jamcar.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public final class StringPatternMatchingUtil {

    public static boolean isValidPassword(String password) {
        // 비밀번호는 8~20자리의 영문, 숫자, 특수문자 조합이어야 함
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        return !name.isBlank();
    }

    private StringPatternMatchingUtil() {
        throw new IllegalStateException("no StringPatternMatchingUtil");
    }

}
