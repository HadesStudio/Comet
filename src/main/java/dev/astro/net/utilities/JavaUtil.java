package dev.astro.net.utilities;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class JavaUtil {

    public String formatMillis(long millis) {
        if (millis < 0L) return "0s";

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder builder = new StringBuilder();

        if (days > 0L) {
            builder.append(days).append("d ");
        }
        if (hours > 0L) {
            builder.append(hours).append("h ");
        }
        if (minutes > 0L) {
            builder.append(minutes).append("m ");
        }
        if (seconds > 0L) {
            builder
                    .append(seconds)
                    .append(".")
                    .append(millis - TimeUnit.SECONDS.toMillis(seconds))
                    .append("s ");
        }
        return builder.toString().trim();
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

    public int[] getRGB(String color) {
        int[] rgb = new int[3];
        try {
            String[] split = color.replace(" ", "").split(",");
            for (int i = 0; i < split.length; i++) {
                rgb[i] = Integer.parseInt(split[i]);
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < 3; i++) {
                rgb[i] = 255;
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid RGB color format, please fix it or contact with the developer.");
        }
        return rgb;
    }
}
