package com.example.top.util;

public class GeneralUtil {

    public static boolean isQualifiedString(String str) {
        return !(str == null || str.isEmpty() || str.isBlank());
    }
}
