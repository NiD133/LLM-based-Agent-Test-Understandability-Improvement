package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingDate#ofYearDay} rejects a missing chronology.
 */
public class TestAccountingChronology_test_date_ofYearDay_no_chronology {

    /**
     * The chronology argument is mandatory, so passing {@code null} must fail
     * fast with a {@link NullPointerException} (year/day values are irrelevant
     * because the null check happens first).
     */
    @Test
    public void ofYearDay_withNullChronology_throwsNullPointerException() {
        AccountingChronology nullChronology = null;
        assertThrows(
                NullPointerException.class,
                () -> AccountingDate.ofYearDay(nullChronology, 0, 1));
    }
}
