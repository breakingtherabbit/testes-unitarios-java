package br.ce.wcaquino.matchers;

import static java.util.Calendar.*;

public class MyMatchers {

    public static WeekdayMatcher itsOn(Integer weekday) {
        return new WeekdayMatcher(weekday);
    }

    public static WeekdayMatcher itsOnSunday() {
        return new WeekdayMatcher(SUNDAY);
    }

    public static WeekdayMatcher itsOnMonday() {
        return new WeekdayMatcher(MONDAY);
    }

    public static WeekdayMatcher itsOnTuesday() {
        return new WeekdayMatcher(TUESDAY);
    }

    public static WeekdayMatcher itsOnWednesday() {
        return new WeekdayMatcher(WEDNESDAY);
    }

    public static WeekdayMatcher itsOnThursday() {
        return new WeekdayMatcher(THURSDAY);
    }

    public static WeekdayMatcher itsOnFriday() {
        return new WeekdayMatcher(FRIDAY);
    }

    public static WeekdayMatcher itsOnSaturday() {
        return new WeekdayMatcher(SATURDAY);
    }

}
