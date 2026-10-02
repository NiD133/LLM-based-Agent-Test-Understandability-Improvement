package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_OF_YEAR;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#from(java.time.temporal.TemporalAccessor)} can be used
 * as a {@code TemporalQuery} to recover a {@code Half} from parsed text.
 */
public class TestHalf_test_from_parse_CharSequence {

    @Test
    public void from_canResolveHalfParsedFromText() {
        // A formatter for text like "H2": a literal 'H' followed by the half-of-year value.
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLiteral('H')
                .appendValue(HALF_OF_YEAR, 1)
                .toFormatter();

        // Parsing "H2" and querying with Half::from should yield the H2 constant.
        Half parsed = formatter.parse("H2", Half::from);

        assertEquals(Half.H2, parsed);
    }
}
