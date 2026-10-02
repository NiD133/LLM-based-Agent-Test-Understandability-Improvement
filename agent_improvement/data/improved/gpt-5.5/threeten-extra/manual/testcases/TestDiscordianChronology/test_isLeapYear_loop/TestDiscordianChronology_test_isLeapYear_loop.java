package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_isLeapYear_loop {

    private static final int FIRST_TESTED_YEAR = 1066;
    private static final int YEAR_AFTER_LAST_TESTED_YEAR = 1567;
    private static final int FIRST_MONTH = 1;
    private static final int FIRST_DAY = 1;
    private static final int ISO_YEAR_OFFSET = 1166;

    @Test
    public void test_isLeapYear_loop() {
        IntPredicate isLeapYear = TestDiscordianChronology_test_isLeapYear_loop::usesIsoLeapYearAfterDiscordianOffset;

        for (int year = FIRST_TESTED_YEAR; year < YEAR_AFTER_LAST_TESTED_YEAR; year++) {
            DiscordianDate firstDayOfYear = DiscordianDate.of(year, FIRST_MONTH, FIRST_DAY);

            assertEquals(isLeapYear.test(year), firstDayOfYear.isLeapYear());
            assertEquals(isLeapYear.test(year), DiscordianChronology.INSTANCE.isLeapYear(year));
        }
    }

    private static boolean usesIsoLeapYearAfterDiscordianOffset(int discordianYear) {
        int offsetYear = discordianYear - ISO_YEAR_OFFSET;
        return offsetYear % 4 == 0 && (offsetYear % 400 == 0 || offsetYear % 100 != 0);
    }
}
