package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding an ISO {@link Period} to a {@link Symmetry454Date} is rejected.
 *
 * <p>A Symmetry454 date may only be combined with a Symmetry454 period. Passing an
 * ISO-chronology period to {@code plus(...)} mixes incompatible chronologies, which the
 * Symmetry454 calendar refuses by throwing a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_Period_ISO {

    @Test
    public void test_plus_Period_ISO() {
        Symmetry454Date date = Symmetry454Date.of(2014, 5, 26);

        // Adding an ISO period (rather than a Symmetry454 period) is not allowed.
        assertThrows(DateTimeException.class, () -> date.plus(Period.ofMonths(2)));
    }
}
