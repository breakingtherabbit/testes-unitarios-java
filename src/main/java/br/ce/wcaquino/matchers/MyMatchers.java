package br.ce.wcaquino.matchers;

import java.util.Calendar;

import static java.util.Calendar.MONDAY;

public class MyMatchers {

    public static WeekdayMatcher itsOn(Integer weekday) {
        return new WeekdayMatcher(weekday);
    }

    public static WeekdayMatcher itsOnMonday() {
        return new WeekdayMatcher(MONDAY);
    }
}
