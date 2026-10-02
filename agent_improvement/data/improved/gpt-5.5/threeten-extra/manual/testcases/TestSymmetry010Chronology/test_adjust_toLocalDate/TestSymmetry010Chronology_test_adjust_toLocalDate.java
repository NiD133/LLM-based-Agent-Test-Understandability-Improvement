package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        Symmetry010Date originalDate = Symmetry010Date.of(2000, 1, 4);

        Symmetry010Date adjustedDate = originalDate.with(LocalDate.of(2012, 7, 6));

        assertEquals(Symmetry010Date.of(2012, 7, 5), adjustedDate);
    }
}
