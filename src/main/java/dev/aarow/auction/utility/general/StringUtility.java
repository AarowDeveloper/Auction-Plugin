package dev.aarow.auction.utility.general;

import java.text.NumberFormat;

public class StringUtility {

    public static String formatNumber(Object number){
        return NumberFormat.getInstance().format(number);
    }

    public static String getTimeString(long time) {
        if (time >= 24 * 60 * 60 * 1000) {
            long days = time / (24 * 60 * 60 * 1000);
            return days + "d";
        } else {
            long hours = time / (60 * 60 * 1000);
            long minutes = (time % (60 * 60 * 1000)) / (60 * 1000);
            long seconds = (time % (60 * 1000)) / 1000;
            if (hours > 0) {
                return hours + "h " + minutes + "m " + seconds + "s";
            } else if (minutes > 0) {
                return minutes + "m " + seconds + "s";
            } else {
                return seconds + "s";
            }
        }
    }
}
