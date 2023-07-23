package com.digu.fivechesses.util;

import java.util.Arrays;

public class StringToIntArr {
    public int[] to(String str) {
        String[] arr = new String[19 * 19];
        int[] map = new int[19 * 19];
        if (str != null) {
            arr = str.split(",");
            map = Arrays.asList(arr).stream().mapToInt(s -> Integer.parseInt(s)).toArray();
        }
        return map;
    }
}
