package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestDiscordianChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(
                        DiscordianDate.of(2000, 1, 3),
                        DiscordianDate.of(2000, 1, 3))
                .addEqualityGroup(
                        DiscordianDate.of(2000, 1, 4),
                        DiscordianDate.of(2000, 1, 4))
                .addEqualityGroup(
                        DiscordianDate.of(2000, 2, 3),
                        DiscordianDate.of(2000, 2, 3))
                .addEqualityGroup(
                        DiscordianDate.of(2001, 1, 3),
                        DiscordianDate.of(2001, 1, 3))
                .testEquals();
    }
}
