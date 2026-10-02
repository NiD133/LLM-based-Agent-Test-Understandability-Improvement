package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_date_ofEpochDay_no_chronology {

    @Test
    public void test_date_ofEpochDay_no_chronology() {
        // AccountingDate.ofEpochDay requires a non-null chronology as its first argument
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AccountingDate.ofEpochDay(null, 0));
    }
}
