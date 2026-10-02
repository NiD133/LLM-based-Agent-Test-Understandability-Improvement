package org.threeten.extra;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MICRO_OF_DAY;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

/**
 * Tests {@link Quarter#isSupported(TemporalField)}.
 * <p>
 * A {@code Quarter} only carries quarter-of-year information, so the single
 * supported field is {@link java.time.temporal.IsoFields#QUARTER_OF_YEAR}.
 * A {@code null} field and every {@code ChronoField} (which all describe
 * date/time information finer-grained than a quarter) are unsupported.
 */
public class TestQuarter_test_isSupported {

    private final Quarter test = Quarter.Q1;

    /**
     * The quarter-of-year field is the only field a {@code Quarter} can answer.
     */
    @Test
    public void isSupported_returnsTrue_forQuarterOfYear() {
        assertTrue(test.isSupported(QUARTER_OF_YEAR));
    }

    /**
     * Neither {@code null} nor any standard {@code ChronoField} is supported.
     */
    @ParameterizedTest
    @NullSource
    @MethodSource("unsupportedFields")
    public void isSupported_returnsFalse_forNullAndChronoFields(TemporalField field) {
        assertFalse(test.isSupported(field));
    }

    /**
     * Every {@code ChronoField}; none of them are supported by a {@code Quarter}.
     */
    static List<TemporalField> unsupportedFields() {
        return Arrays.asList(
                NANO_OF_SECOND,
                NANO_OF_DAY,
                MICRO_OF_SECOND,
                MICRO_OF_DAY,
                MILLI_OF_SECOND,
                MILLI_OF_DAY,
                SECOND_OF_MINUTE,
                SECOND_OF_DAY,
                MINUTE_OF_HOUR,
                MINUTE_OF_DAY,
                HOUR_OF_AMPM,
                CLOCK_HOUR_OF_AMPM,
                HOUR_OF_DAY,
                CLOCK_HOUR_OF_DAY,
                AMPM_OF_DAY,
                DAY_OF_WEEK,
                ALIGNED_DAY_OF_WEEK_IN_MONTH,
                ALIGNED_DAY_OF_WEEK_IN_YEAR,
                DAY_OF_MONTH,
                DAY_OF_YEAR,
                EPOCH_DAY,
                ALIGNED_WEEK_OF_MONTH,
                ALIGNED_WEEK_OF_YEAR,
                MONTH_OF_YEAR,
                PROLEPTIC_MONTH,
                YEAR_OF_ERA,
                YEAR,
                ERA,
                INSTANT_SECONDS,
                OFFSET_SECONDS);
    }
}
