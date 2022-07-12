package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;

public class WeekdayMatcher extends TypeSafeMatcher<Date> {

    private Integer weekday;

    public WeekdayMatcher(Integer weekday) {
        this.weekday = weekday;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return verificarDiaSemana(date, weekday);
    }

    @Override
    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();
        data.set(DAY_OF_WEEK, weekday);
        String dataExtensive = data.getDisplayName(DAY_OF_WEEK, LONG, new Locale("pt", "BR"));
        description.appendText(dataExtensive);
    }

}
