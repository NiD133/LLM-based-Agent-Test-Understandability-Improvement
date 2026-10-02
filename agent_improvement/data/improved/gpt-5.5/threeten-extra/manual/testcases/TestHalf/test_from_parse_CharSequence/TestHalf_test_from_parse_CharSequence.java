package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_parse_CharSequence {

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter halfFormatter = new DateTimeFormatterBuilder()
                .appendLiteral('H')
                .appendValue(HALF_OF_YEAR, 1)
                .toFormatter();

        assertEquals(Half.H2, halfFormatter.parse("H2", Half::from));
    }
}
