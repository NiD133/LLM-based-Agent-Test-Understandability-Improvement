package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Tests the {@code equals} / {@code hashCode} contract of {@link DiscordianDate}.
 */
public class TestDiscordianChronology_test_equals_and_hashCode {

    /**
     * Two {@link DiscordianDate} instances must be equal (and share a hash code)
     * exactly when their year, month and day-of-month all match.
     *
     * <p>Each equality group below holds two dates built from identical fields, so the
     * dates within a group must be equal while dates from different groups must not.
     * The groups deliberately vary one field at a time relative to the base date
     * {@code (2000, 1, 3)}:
     * <ul>
     *   <li>base date</li>
     *   <li>different day-of-month</li>
     *   <li>different month</li>
     *   <li>different year</li>
     * </ul>
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(DiscordianDate.of(2000, 1, 3), DiscordianDate.of(2000, 1, 3))
            .addEqualityGroup(DiscordianDate.of(2000, 1, 4), DiscordianDate.of(2000, 1, 4))
            .addEqualityGroup(DiscordianDate.of(2000, 2, 3), DiscordianDate.of(2000, 2, 3))
            .addEqualityGroup(DiscordianDate.of(2001, 1, 3), DiscordianDate.of(2001, 1, 3))
            .testEquals();
    }
}
