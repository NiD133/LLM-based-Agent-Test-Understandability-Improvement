package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link DayOfYear#getLong(TemporalField)} for a field that is <em>not</em> a
 * {@link java.time.temporal.ChronoField}.
 * <p>
 * For such "derived" fields, {@code getLong} must delegate to the field itself by
 * calling {@code field.getFrom(this)}, rather than handling the value internally.
 */
public class TestDayOfYear_test_getLong_derivedField {

    /** The day-of-year under test; its value (12) is what we expect to read back. */
    private static final DayOfYear DAY_12 = DayOfYear.of(12);

    @Test
    public void getLong_withDerivedField_delegatesToFieldAndReturnsDayOfYearValue() {
        long actual = DAY_12.getLong(DayOfYearField.INSTANCE);

        assertEquals(12L, actual);
    }

    /**
     * A minimal custom {@link TemporalField} (i.e. not a {@code ChronoField}) whose value is
     * derived from a temporal's standard {@code DAY_OF_YEAR} field. It exists solely to exercise
     * the delegation branch of {@link DayOfYear#getLong(TemporalField)}.
     */
    private static final class DayOfYearField implements TemporalField {

        static final DayOfYearField INSTANCE = new DayOfYearField();

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
