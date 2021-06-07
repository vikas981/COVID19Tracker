package com.viksingh.covid19tracker.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CommonUtil {

    public static String getFormatedNumber(Object obj) {
        try {
            if (obj instanceof Integer) {
                return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(String.valueOf(obj)));
            } else if (obj instanceof String) {
                return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(String.valueOf(obj)));
            } else {
                return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(String.valueOf(obj)));
            }
        }
        catch (NumberFormatException e) {
            return NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(String.valueOf(obj)));
        }
    }
}
