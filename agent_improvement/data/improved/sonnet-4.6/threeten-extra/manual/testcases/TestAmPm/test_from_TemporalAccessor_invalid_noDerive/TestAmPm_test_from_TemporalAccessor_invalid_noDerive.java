package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_from_TemporalAccessor_invalid_noDerive {

    @Test
    @DisplayName("AmPm.from() throws DateTimeException when the temporal has no AMPM_OF_DAY field and cannot derive one")
    public void test_from_TemporalAccessor_invalid_noDerive() {
        // LocalDate carries no time-of-day information, so AMPM_OF_DAY cannot be extracted or derived
        assertThrows(DateTimeException.class, () -> AmPm.from(LocalDate.of(2007, 7, 30)));
    }
}
