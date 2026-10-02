package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.Quarter.Q3;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_parse_CharSequence {

    /**
     * Verifies that {@link Quarter#from} can be used as a {@link java.time.temporal.TemporalQuery}
     * to extract a {@code Quarter} from a parsed {@link java.time.temporal.TemporalAccessor}.
     * The formatter pattern {@code 'Q'Q} matches literals like "Q3", where the second
     * {@code Q} is the ISO quarter-of-year field.
     */
    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Q'Q");
        assertEquals(Q3, formatter.parse("Q3", Quarter::from));
    }
}
