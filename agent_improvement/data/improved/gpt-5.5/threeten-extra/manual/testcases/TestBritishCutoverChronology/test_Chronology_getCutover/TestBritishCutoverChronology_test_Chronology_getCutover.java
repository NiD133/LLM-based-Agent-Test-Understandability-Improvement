package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_getCutover {

    @Test
    public void test_Chronology_getCutover() {
        assertEquals(LocalDate.of(1752, 9, 14), BritishCutoverChronology.INSTANCE.getCutover());
    }
}
