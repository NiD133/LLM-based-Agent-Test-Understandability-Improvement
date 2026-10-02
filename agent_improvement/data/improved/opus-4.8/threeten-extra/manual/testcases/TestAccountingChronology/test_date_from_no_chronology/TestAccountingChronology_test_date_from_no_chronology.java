package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AccountingDate#from(AccountingChronology, java.time.temporal.TemporalAccessor)}
 * rejects a {@code null} chronology.
 */
public class TestAccountingChronology_test_date_from_no_chronology {

    @Test
    public void from_nullChronology_throwsNullPointerException() {
        LocalDate anyDate = LocalDate.of(2012, 1, 1);

        //noinspection DataFlowIssue - intentionally passing null to verify the null-check
        assertThrows(NullPointerException.class, () -> AccountingDate.from(null, anyDate));
    }
}
