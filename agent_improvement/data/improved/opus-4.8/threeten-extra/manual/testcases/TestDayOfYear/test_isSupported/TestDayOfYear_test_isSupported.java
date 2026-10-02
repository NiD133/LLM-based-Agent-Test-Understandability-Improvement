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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#isSupported(TemporalField)}.
 * <p>
 * A {@code DayOfYear} only carries a day-of-year value, so among the standard
 * {@link java.time.temporal.ChronoField} constants only {@code DAY_OF_YEAR} is
 * supported. For any non-{@code ChronoField}, support is delegated to the field
 * itself via {@code TemporalField.isSupportedBy(TemporalAccessor)}.
 */
public class TestDayOfYear_test_isSupported {

    /** The day-of-year under test; the exact value is irrelevant to field support. */
    private static final DayOfYear TEST = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        // A null field is never supported.
        assertFalse(TEST.isSupported((TemporalField) null));

        // The only ChronoField a DayOfYear can answer is DAY_OF_YEAR.
        assertTrue(TEST.isSupported(DAY_OF_YEAR));

        // Every other ChronoField is unsupported: time-of-day fields...
        assertFalse(TEST.isSupported(NANO_OF_SECOND));
        assertFalse(TEST.isSupported(NANO_OF_DAY));
        assertFalse(TEST.isSupported(MICRO_OF_SECOND));
        assertFalse(TEST.isSupported(MICRO_OF_DAY));
        assertFalse(TEST.isSupported(MILLI_OF_SECOND));
        assertFalse(TEST.isSupported(MILLI_OF_DAY));
        assertFalse(TEST.isSupported(SECOND_OF_MINUTE));
        assertFalse(TEST.isSupported(SECOND_OF_DAY));
        assertFalse(TEST.isSupported(MINUTE_OF_HOUR));
        assertFalse(TEST.isSupported(MINUTE_OF_DAY));
        assertFalse(TEST.isSupported(HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(HOUR_OF_DAY));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_DAY));
        assertFalse(TEST.isSupported(AMPM_OF_DAY));

        // ...other date fields...
        assertFalse(TEST.isSupported(DAY_OF_WEEK));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertFalse(TEST.isSupported(DAY_OF_MONTH));
        assertFalse(TEST.isSupported(EPOCH_DAY));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertFalse(TEST.isSupported(MONTH_OF_YEAR));
        assertFalse(TEST.isSupported(PROLEPTIC_MONTH));
        assertFalse(TEST.isSupported(YEAR_OF_ERA));
        assertFalse(TEST.isSupported(YEAR));
        assertFalse(TEST.isSupported(ERA));

        // ...and instant/offset fields.
        assertFalse(TEST.isSupported(INSTANT_SECONDS));
        assertFalse(TEST.isSupported(OFFSET_SECONDS));

        // A custom (non-ChronoField) field decides for itself; this one reports
        // supported because it delegates back to DAY_OF_YEAR.
        assertTrue(TEST.isSupported(TestingField.INSTANCE));
    }

    /**
     * A minimal custom {@link TemporalField} that considers itself supported by any
     * temporal that supports {@code DAY_OF_YEAR}. Used to exercise the non-ChronoField
     * delegation branch of {@link DayOfYear#isSupported(TemporalField)}.
     */
    private static class TestingField implements TemporalField {

        public static final TestingField INSTANCE = new TestingField();

        @Override
        public TemporalUnit getBaseUnit() {
            return ChronoUnit.DAYS;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return ChronoUnit.YEARS;
        }

        @Override
        public ValueRange range() {
            return ValueRange.of(1, 365, 366);
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return temporal.isSupported(DAY_OF_YEAR);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return range();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(DAY_OF_YEAR);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return (R) temporal.with(DAY_OF_YEAR, newValue);
        }
    }
}
