package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_date_now_no_chronology {

    @Test
    public void test_date_now_no_chronology() {
        // AccountingDate.now requires a non-null clock; passing null must fail fast.
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AccountingDate.now(null));
    }
}
