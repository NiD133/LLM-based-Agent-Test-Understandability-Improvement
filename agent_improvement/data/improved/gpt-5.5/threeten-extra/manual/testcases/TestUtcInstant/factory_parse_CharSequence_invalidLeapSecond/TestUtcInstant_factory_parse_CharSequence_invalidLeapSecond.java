package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_parse_CharSequence_invalidLeapSecond {

    @Test
    public void factory_parse_CharSequence_invalidLeapSecond() {
        assertThrows(DateTimeException.class, () -> UtcInstant.parse("1972-11-11T23:59:60Z"));
    }
}
