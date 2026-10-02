package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(2));
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(0));
    }
}
