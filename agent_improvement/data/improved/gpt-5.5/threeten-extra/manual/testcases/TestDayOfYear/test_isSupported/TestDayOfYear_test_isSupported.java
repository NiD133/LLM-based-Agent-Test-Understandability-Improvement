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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_isSupported {

    private static final DayOfYear TEST = DayOfYear.of(12);

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_isSupported() {
        assertUnsupported((TemporalField) null);

        assertUnsupported(NANO_OF_SECOND);
        assertUnsupported(NANO_OF_DAY);
        assertUnsupported(MICRO_OF_SECOND);
        assertUnsupported(MICRO_OF_DAY);
        assertUnsupported(MILLI_OF_SECOND);
        assertUnsupported(MILLI_OF_DAY);
        assertUnsupported(SECOND_OF_MINUTE);
        assertUnsupported(SECOND_OF_DAY);
        assertUnsupported(MINUTE_OF_HOUR);
        assertUnsupported(MINUTE_OF_DAY);
        assertUnsupported(HOUR_OF_AMPM);
        assertUnsupported(CLOCK_HOUR_OF_AMPM);
        assertUnsupported(HOUR_OF_DAY);
        assertUnsupported(CLOCK_HOUR_OF_DAY);
        assertUnsupported(AMPM_OF_DAY);
        assertUnsupported(DAY_OF_WEEK);
        assertUnsupported(ALIGNED_DAY_OF_WEEK_IN_MONTH);
        assertUnsupported(ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertUnsupported(DAY_OF_MONTH);

        assertSupported(DAY_OF_YEAR);

        assertUnsupported(EPOCH_DAY);
        assertUnsupported(ALIGNED_WEEK_OF_MONTH);
        assertUnsupported(ALIGNED_WEEK_OF_YEAR);
        assertUnsupported(MONTH_OF_YEAR);
        assertUnsupported(PROLEPTIC_MONTH);
        assertUnsupported(YEAR_OF_ERA);
        assertUnsupported(YEAR);
        assertUnsupported(ERA);
        assertUnsupported(INSTANT_SECONDS);
        assertUnsupported(OFFSET_SECONDS);

        assertSupported(TestingField.INSTANCE);
    }

    private static void assertSupported(TemporalField field) {
        assertEquals(true, TEST.isSupported(field));
    }

    private static void assertUnsupported(TemporalField field) {
        assertEquals(false, TEST.isSupported(field));
    }

    private enum TestingField implements TemporalField {
        INSTANCE;

        @Override
        public TemporalUnit getBaseUnit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TemporalUnit getRangeUnit() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueRange range() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isDateBased() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isTimeBased() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return true;
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
            throw new UnsupportedOperationException();
        }
    }
}
