package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_parse_CharSequence_invalidLeapSecond {

    @Test
    @DisplayName("parse throws DateTimeException when 23:59:60 appears on a non-leap day")
    public void factory_parse_CharSequence_invalidLeapSecond() {
        // 1972-11-11 is not a leap-second day, so the :60 second notation is invalid
        assertThrows(DateTimeException.class, () -> UtcInstant.parse("1972-11-11T23:59:60Z"));
    }
}
