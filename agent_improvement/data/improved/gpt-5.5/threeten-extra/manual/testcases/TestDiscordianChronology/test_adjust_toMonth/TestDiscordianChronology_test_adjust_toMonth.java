package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> discordian.with(Month.APRIL));
    }
}
