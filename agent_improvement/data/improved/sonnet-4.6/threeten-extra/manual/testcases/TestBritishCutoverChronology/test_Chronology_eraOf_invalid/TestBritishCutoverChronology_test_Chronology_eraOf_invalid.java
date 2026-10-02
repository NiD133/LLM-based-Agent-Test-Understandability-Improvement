package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Era values 0 (BC) and 1 (AD) are valid; 2 is out of range and must throw
        assertThrows(DateTimeException.class, () -> BritishCutoverChronology.INSTANCE.eraOf(2));
    }
}
