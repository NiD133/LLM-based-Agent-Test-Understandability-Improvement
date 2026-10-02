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

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_range {

    private static final Symmetry454Chronology CHRONOLOGY = Symmetry454Chronology.INSTANCE;

    private static final long MIN_EPOCH_DAY =
            -1_000_000 * 364L - 177_474 * 7 - 719_162;
    private static final long MAX_EPOCH_DAY =
            1_000_000 * 364L + 177_474 * 7 - 719_162;

    @Test
    public void test_Chronology_range() {
        assertChronologyRange(ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7));
        assertChronologyRange(ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7));
        assertChronologyRange(ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4, 5));
        assertChronologyRange(ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52, 53));
        assertChronologyRange(DAY_OF_WEEK, ValueRange.of(1, 7));
        assertChronologyRange(DAY_OF_MONTH, ValueRange.of(1, 28, 35));
        assertChronologyRange(DAY_OF_YEAR, ValueRange.of(1, 364, 371));
        assertChronologyRange(ERA, ValueRange.of(0, 1));
        assertChronologyRange(EPOCH_DAY, ValueRange.of(MIN_EPOCH_DAY, MAX_EPOCH_DAY));
        assertChronologyRange(MONTH_OF_YEAR, ValueRange.of(1, 12));
        assertChronologyRange(PROLEPTIC_MONTH, ValueRange.of(-12_000_000L, 11_999_999L));
        assertChronologyRange(YEAR, ValueRange.of(-1_000_000L, 1_000_000));
        assertChronologyRange(YEAR_OF_ERA, ValueRange.of(-1_000_000, 1_000_000));
    }

    private static void assertChronologyRange(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, CHRONOLOGY.range(field));
    }
}
