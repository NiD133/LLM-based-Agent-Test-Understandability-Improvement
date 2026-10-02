package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that creating an {@link AccountingDate} without a chronology is rejected.
 */
public class TestAccountingChronology_test_date_of_no_chronology {

    @Test
    public void of_withNullChronology_throwsNullPointerException() {
        // The first argument (the chronology) is mandatory; passing null must fail fast.
        assertThrows(NullPointerException.class,
                () -> AccountingDate.of(null, 2012, 1, 1));
    }
}
