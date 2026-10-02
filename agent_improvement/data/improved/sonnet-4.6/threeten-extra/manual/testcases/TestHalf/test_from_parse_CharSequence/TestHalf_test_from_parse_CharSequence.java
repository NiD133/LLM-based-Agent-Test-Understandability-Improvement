package org.threeten.extra;

import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_parse_CharSequence {

    @Test
    public void test_from_parse_CharSequence() {
        // Build a formatter that expects a literal 'H' followed by the half-of-year digit (e.g. "H2")
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLiteral('H')
                .appendValue(HALF_OF_YEAR, 1)
                .toFormatter();

        Half result = formatter.parse("H2", Half::from);

        assertEquals(Half.H2, result);
    }
}
