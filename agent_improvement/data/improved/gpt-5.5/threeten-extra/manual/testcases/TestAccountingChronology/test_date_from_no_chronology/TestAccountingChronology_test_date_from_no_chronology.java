package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_date_from_no_chronology {

    @Test
    public void test_date_from_no_chronology() {
        //noinspection DataFlowIssue - testing null chronology handling
        assertThrows(NullPointerException.class, () -> AccountingDate.from(null, LocalDate.of(2012, 1, 1)));
    }
}
