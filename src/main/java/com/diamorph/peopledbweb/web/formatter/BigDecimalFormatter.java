package com.diamorph.peopledbweb.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Component
public class BigDecimalFormatter implements Formatter<BigDecimal> {
    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        return new BigDecimal((Double) NumberFormat.getCurrencyInstance(locale).parse(text));
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        return NumberFormat.getCurrencyInstance(locale).format(object);
    }
}
