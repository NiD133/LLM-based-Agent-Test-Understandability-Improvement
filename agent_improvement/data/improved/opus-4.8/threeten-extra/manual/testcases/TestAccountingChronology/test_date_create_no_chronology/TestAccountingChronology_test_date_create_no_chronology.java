package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingDate#create} rejects a missing chronology.
 */
public class TestAccountingChronology_test_date_create_no_chronology {

    @Test
    public void test_date_create_rejectsNullChronology() {
        // The chronology argument is required; passing null must fail fast with an NPE.
        assertThrows(NullPointerException.class,
                () -> AccountingDate.create(null, 2012, 1, 1));
    }
}
