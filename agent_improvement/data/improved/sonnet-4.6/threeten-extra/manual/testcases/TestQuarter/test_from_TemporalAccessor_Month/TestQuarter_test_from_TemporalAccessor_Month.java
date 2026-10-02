package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestQuarter_test_from_TemporalAccessor_Month {

    /**
     * Verifies that Quarter.from(Month) maps each calendar month to its correct quarter:
     *   Q1 = January–March, Q2 = April–June, Q3 = July–September, Q4 = October–December.
     */
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "JANUARY,   Q1",
        "FEBRUARY,  Q1",
        "MARCH,     Q1",
        "APRIL,     Q2",
        "MAY,       Q2",
        "JUNE,      Q2",
        "JULY,      Q3",
        "AUGUST,    Q3",
        "SEPTEMBER, Q3",
        "OCTOBER,   Q4",
        "NOVEMBER,  Q4",
        "DECEMBER,  Q4"
    })
    public void test_from_TemporalAccessor_Month(Month month, Quarter expectedQuarter) {
        assertEquals(expectedQuarter, Quarter.from(month));
    }
}
