package br.ce.wcaquino.matchers;

import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

@AllArgsConstructor
public class DateWithDifferenceDaysMatcher extends TypeSafeMatcher<Date> {

    private Integer days;

    @Override
    protected boolean matchesSafely(Date date) {
        return isMesmaData(date, obterDataComDiferencaDias(days));
    }

    @Override
    public void describeTo(Description description) {
        Date expectedDate = obterDataComDiferencaDias(days);
        DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        description.appendText(format.format(expectedDate));
    }

}
