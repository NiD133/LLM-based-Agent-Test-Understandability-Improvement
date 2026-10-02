package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestDiscordianChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // Same year, same season, same day — these two should be equal
                .addEqualityGroup(DiscordianDate.of(2000, 1, 3), DiscordianDate.of(2000, 1, 3))
                // Same year and season, different day
                .addEqualityGroup(DiscordianDate.of(2000, 1, 4), DiscordianDate.of(2000, 1, 4))
                // Same year and day, different season
                .addEqualityGroup(DiscordianDate.of(2000, 2, 3), DiscordianDate.of(2000, 2, 3))
                // Different year — otherwise identical date components
                .addEqualityGroup(DiscordianDate.of(2001, 1, 3), DiscordianDate.of(2001, 1, 3))
                .testEquals();
    }
}
