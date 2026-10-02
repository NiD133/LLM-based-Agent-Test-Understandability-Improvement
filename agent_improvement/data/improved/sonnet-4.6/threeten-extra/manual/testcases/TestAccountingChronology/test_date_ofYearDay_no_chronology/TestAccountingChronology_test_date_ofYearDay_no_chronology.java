package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_date_ofYearDay_no_chronology {

    @Test
    public void test_date_ofYearDay_no_chronology() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AccountingDate.ofYearDay(null, 0, 1));
    }
}
