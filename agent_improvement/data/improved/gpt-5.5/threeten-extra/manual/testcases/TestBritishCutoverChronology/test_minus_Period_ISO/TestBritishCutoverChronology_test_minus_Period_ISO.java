package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_minus_Period_ISO {

    @Test
    public void test_minus_Period_ISO() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.minus(isoPeriod));
    }
}
