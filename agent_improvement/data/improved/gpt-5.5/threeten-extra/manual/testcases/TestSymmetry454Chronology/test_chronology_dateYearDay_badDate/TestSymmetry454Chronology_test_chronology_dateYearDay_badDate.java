package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestSymmetry454Chronology_test_chronology_dateYearDay_badDate {

    @Test
    public void test_chronology_dateYearDay_badDate() {
        assertThrows(DateTimeException.class, () -> Symmetry454Chronology.INSTANCE.dateYearDay(2000, 365));
    }
}
