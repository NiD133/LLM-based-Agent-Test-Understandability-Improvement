package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        Symmetry010Date januaryDate = Symmetry010Date.of(2000, 1, 4);

        assertThrows(DateTimeException.class, () -> januaryDate.with(Month.APRIL));
    }
}
