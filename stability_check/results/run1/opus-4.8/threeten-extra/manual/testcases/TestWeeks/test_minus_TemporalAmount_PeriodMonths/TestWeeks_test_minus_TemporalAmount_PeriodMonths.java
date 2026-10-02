package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a month-based {@link Period} from a {@link Weeks}
 * amount is rejected, because months cannot be converted to a whole number of
 * weeks.
 */
public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    @Test
    public void minus_periodOfMonths_throwsDateTimeException() {
        Weeks oneWeek = Weeks.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneWeek.minus(twoMonths));
    }
}
