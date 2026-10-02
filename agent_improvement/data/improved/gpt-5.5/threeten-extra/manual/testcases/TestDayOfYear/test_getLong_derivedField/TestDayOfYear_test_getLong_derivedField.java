package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_getLong_derivedField {

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
    public void test_getLong_derivedField() {
        assertEquals(12L, TEST.getLong(TestingField.INSTANCE));
    }

    private enum TestingField implements TemporalField {
        INSTANCE;

        @Override
        public TemporalUnit getBaseUnit() {
            return DAY_OF_YEAR.getBaseUnit();
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return DAY_OF_YEAR.getRangeUnit();
        }

        @Override
        public ValueRange range() {
            return DAY_OF_YEAR.range();
        }

        @Override
        public boolean isDateBased() {
            return DAY_OF_YEAR.isDateBased();
        }

        @Override
        public boolean isTimeBased() {
            return DAY_OF_YEAR.isTimeBased();
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return temporal.isSupported(DAY_OF_YEAR);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return temporal.range(DAY_OF_YEAR);
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(DAY_OF_YEAR);
        }

        @Override
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return DAY_OF_YEAR.adjustInto(temporal, newValue);
        }
    }
}
