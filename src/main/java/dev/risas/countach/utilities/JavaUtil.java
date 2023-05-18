package dev.risas.countach.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class JavaUtil {

    public String HOUR_FORMAT = "%02d:%02d:%02d";
    public String MINUTE_FORMAT = "%02d:%02d";
    public DecimalFormat SECONDS_FORMATTER = new DecimalFormat("#0.0");

    public Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public boolean isAlphanumeric(String input) {
        return input.matches("[a-zA-Z0-9]+");
    }

    public String millisToTimer(long millis) {
        long seconds = millis / 1000L;

        if (seconds > 3600L) {
            return String.format(HOUR_FORMAT, seconds / 3600L, seconds % 3600L / 60L, seconds % 60L);
        } else {
            return String.format(MINUTE_FORMAT, seconds / 60L, seconds % 60L);
        }
    }

    public String formatDurationInt(int input) {
        return DurationFormatUtils.formatDurationWords(input * 1000L, true, true);
    }

    public String formatDurationLong(long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }

    public String formatLongMin(long time) {
        long totalSecs = time / 1000L;
        return String.format("%02d:%02d", totalSecs / 60L, totalSecs % 60L);
    }

    public String formatLongHour(long time) {
        long totalSecs = time / 1000L;

        long seconds = totalSecs % 60L;
        long minutes = totalSecs % 3600L / 60L;
        long hours = totalSecs / 3600L;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } 
        else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public long formatLong(String input) {
        if (input == null || input.isEmpty()) return -1L;

        long result = 0L;
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertLong(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private long convertLong(int value, char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }
            default: {
                return -1L;
            }
        }
    }

    public int formatInt(String input) {
        if (input == null || input.isEmpty()) return -1;

        int result = 0;
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertInt(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private int convertInt(int value, char unit) {
        switch (unit) {
            case 'd': {
                return value * 60 * 60 * 24;
            }
            case 'h': {
                return value * 60 * 60;
            }
            case 'm': {
                return value * 60;
            }
            case 's': {
                return value;
            }
            default: {
                return -1;
            }
        }
    }

    public long parseTime(String time) {
        long totalTime = 0L;
        boolean found = false;
        Matcher matcher = Pattern.compile("\\d+\\D+").matcher(time);

        while (matcher.find()) {
            String s = matcher.group();
            long value = Long.parseLong(s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0]);
            String type = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1];

            switch (type) {
                case "s":
                    totalTime += value;
                    found = true;
                    break;
                case "m":
                    totalTime += value * 60;
                    found = true;
                    break;
                case "h":
                    totalTime += value * 60 * 60;
                    found = true;
                    break;
                case "d":
                    totalTime += value * 60 * 60 * 24;
                    found = true;
                    break;
                case "w":
                    totalTime += value * 60 * 60 * 24 * 7;
                    found = true;
                    break;
                case "M":
                    totalTime += value * 60 * 60 * 24 * 30;
                    found = true;
                    break;
                case "y":
                    totalTime += value * 60 * 60 * 24 * 365;
                    found = true;
                    break;
            }
        }

        return !found ? -1 : totalTime * 1000;
    }
}
