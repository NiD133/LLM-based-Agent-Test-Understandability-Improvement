package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        // Adjust a Discordian date to match a given ISO LocalDate using with()
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);
        DiscordianDate adjusted = discordian.with(LocalDate.of(2012, 7, 6));
        assertEquals(DiscordianDate.of(3178, 3, 41), adjusted);
    }
}
