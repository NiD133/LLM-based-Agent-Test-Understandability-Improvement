package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AccountingDate#now(java.time.chrono.Chronology)} rejects a null chronology argument.
 */
public class TestAccountingChronology_test_date_now_no_chronology {

    @Test
    public void test_date_now_no_chronology() {
        // AccountingDate.now(Chronology) must not accept null — it should throw NullPointerException
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AccountingDate.now(null));
    }
}
