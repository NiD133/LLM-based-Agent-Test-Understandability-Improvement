package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DayOfMonth#getLong(TemporalField)} correctly delegates to a
 * non-ChronoField (derived) {@link TemporalField} by calling {@code field.getFrom(this)}.
 */
public class TestDayOfMonth_test_getLong_derivedField {

    // DayOfMonth under test: the 12th day of the month
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    /**
     * A custom TemporalField whose value is derived from DAY_OF_MONTH.
     * When getLong() receives a non-ChronoField, it must delegate to field.getFrom(this),
     * which in turn reads DAY_OF_MONTH from the DayOfMonth instance.
     */
    private static class TestingField implements TemporalField {

        public static final TestingField INSTANCE = new TestingField();

        @Override
        public TemporalUnit getBaseUnit() {
            return ChronoUnit.DAYS;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return ChronoUnit.MONTHS;
        }

        @Override
        public ValueRange range() {
            return ValueRange.of(1, 28, 31);
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
            return temporal.isSupported(DAY_OF_MONTH);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return range();
        }

        /** Returns the DAY_OF_MONTH value from the given temporal — the derivation logic. */
        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(DAY_OF_MONTH);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return (R) temporal.with(DAY_OF_MONTH, newValue);
        }
    }

    @Test
    public void test_getLong_derivedField() {
        // TestingField.INSTANCE is not a ChronoField, so getLong() must delegate to
        // field.getFrom(this), which reads DAY_OF_MONTH (12) from TEST.
        assertEquals(12L, TEST.getLong(TestingField.INSTANCE));
    }
}
