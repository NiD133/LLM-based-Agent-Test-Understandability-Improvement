package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#from(java.time.temporal.TemporalAmount)} folds the
 * months component of a {@link Period} into whole years.
 */
public class TestYears_test_from_yearsAndMonths {

    @Test
    public void from_periodWithYearsAndMonths_combinesMonthsIntoYears() {
        // A period of 3 years plus 24 months equals 3 + 2 = 5 whole years.
        Period threeYearsAndTwentyFourMonths = Period.of(3, 24, 0);

        Years result = Years.from(threeYearsAndTwentyFourMonths);

        assertEquals(Years.of(5), result);
    }
}
