package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_Period_ISO {

    /**
     * Subtracting an ISO {@link Period} from a {@link Symmetry454Date} is not
     * supported and must fail: the two calendar systems are incompatible, so the
     * operation is expected to raise a {@link DateTimeException}.
     */
    @Test
    public void test_minus_Period_ISO() {
        Symmetry454Date date = Symmetry454Date.of(2014, 5, 26);
        Period isoTwoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> date.minus(isoTwoMonths));
    }
}
