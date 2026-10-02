package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.Quarter.Q3;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_parse_CharSequence {

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Q'Q");

        assertEquals(Q3, formatter.parse("Q3", Quarter::from));
    }
}
