package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 4, 5), Symmetry010Chronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 52, 53), Symmetry010Chronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));

        assertEquals(ValueRange.of(1, 7), Symmetry010Chronology.INSTANCE.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 30, 37), Symmetry010Chronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 364, 371), Symmetry010Chronology.INSTANCE.range(DAY_OF_YEAR));

        assertEquals(ValueRange.of(0, 1), Symmetry010Chronology.INSTANCE.range(ERA));
        assertEquals(
                ValueRange.of(
                        -1_000_000 * 364L - 177_474 * 7 - 719_162,
                        1_000_000 * 364L + 177_474 * 7 - 719_162),
                Symmetry010Chronology.INSTANCE.range(EPOCH_DAY));
        assertEquals(ValueRange.of(1, 12), Symmetry010Chronology.INSTANCE.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), Symmetry010Chronology.INSTANCE.range(PROLEPTIC_MONTH));
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), Symmetry010Chronology.INSTANCE.range(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), Symmetry010Chronology.INSTANCE.range(YEAR_OF_ERA));
    }
}
