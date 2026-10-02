package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.Quarter.Q3;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#from(java.time.temporal.TemporalAccessor)} can be used
 * as a {@code TemporalQuery} to obtain a {@code Quarter} from parsed text.
 */
public class TestQuarter_test_from_parse_CharSequence {

    @Test
    public void from_isUsableAsParseQuery() {
        // A formatter whose pattern is the literal 'Q' followed by the quarter number.
        DateTimeFormatter quarterFormatter = DateTimeFormatter.ofPattern("'Q'Q");

        // Parsing "Q3" and querying with Quarter::from should yield the Q3 constant.
        Quarter parsed = quarterFormatter.parse("Q3", Quarter::from);

        assertEquals(Q3, parsed);
    }
}
