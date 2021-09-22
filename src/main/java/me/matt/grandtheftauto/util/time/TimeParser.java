package me.matt.grandtheftauto.util.time;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class TimeParser {

    private final Map<Pattern, TimeUnit> TIME_UNITS = new HashMap<>();

    static {
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(milliseconds|millisecond|ms))"), TimeUnit.MILLISECOND);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(seconds|second|sec|s))"), TimeUnit.SECOND);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(minutes|minute|min|m))"), TimeUnit.MINUTE);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(hours|hour|h))"), TimeUnit.HOUR);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(days|day|d))"), TimeUnit.DAY);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(weeks|week|w))"), TimeUnit.WEEK);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(months|month|mo))"), TimeUnit.MONTH);
        TIME_UNITS.put(Pattern.compile("((\\d+) ?(years|year|y))"), TimeUnit.YEAR);
    }

    public long toMillis(String string) {
        long result = 0;

        for (Map.Entry<Pattern, TimeUnit> entry : TIME_UNITS.entrySet()) {
            final Matcher matcher = entry.getKey().matcher(string);
            if (!matcher.find())
                continue;

            result += Long.parseLong(matcher.group(2)) * entry.getValue().getMillis();
        }

        return result;
    }

}
