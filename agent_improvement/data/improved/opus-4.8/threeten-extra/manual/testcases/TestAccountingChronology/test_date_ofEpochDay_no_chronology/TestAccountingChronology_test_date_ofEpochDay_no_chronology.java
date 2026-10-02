package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingDate#ofEpochDay} rejects a null chronology.
 */
public class TestAccountingChronology_test_date_ofEpochDay_no_chronology {

    @Test
    public void test_date_ofEpochDay_no_chronology() {
        // A null chronology argument must be rejected with a NullPointerException.
        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> AccountingDate.ofEpochDay(null, 0));
    }
}
