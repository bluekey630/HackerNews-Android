package com.hacker.news.Utils;

public class Common {
    public static String convertTimeStampToGeneralTime(long timestamp) {
        long hr = timestamp / 3600;
        long min = (timestamp % 3600) / 60;
        long day = hr / 24;

        String str = "";
        if (hr < 1) {
            str = String.valueOf(min) + " minutes ago";
        } else if (hr < 48) {
            str = String.valueOf(hr) + " hours ago";
        } else {
            str = String.valueOf(day) + " days ago";
        }

        return str;
    }
}
