import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.threeten.extra.chrono.DiscordianDate;

/**
 * Verifies {@link DiscordianDate#lengthOfMonth()} for the special cases of the
 * Discordian calendar.
 * <p>
 * In the Discordian calendar a regular month always has 73 days, while
 * St. Tib's Day (encoded as month 0, day 0) stands alone as a one-day "month".
 */
public class TestDiscordianChronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // St. Tib's Day is its own one-day month.
        assertEquals(1, DiscordianDate.of(3178, 0, 0).lengthOfMonth());
        // A regular month has 73 days, regardless of which day-of-month is referenced.
        assertEquals(73, DiscordianDate.of(3178, 1, 1).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(3178, 1, 73).lengthOfMonth());
    }
}
