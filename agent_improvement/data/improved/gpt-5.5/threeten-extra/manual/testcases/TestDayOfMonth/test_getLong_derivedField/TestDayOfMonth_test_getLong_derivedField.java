package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_getLong_derivedField {

    private static final int REPRESENTED_DAY_OF_MONTH = 12;
    private static final DayOfMonth TEST_DAY = DayOfMonth.of(REPRESENTED_DAY_OF_MONTH);
    private static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        int expectedDayOfMonth = LocalDate.now(TOKYO).getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now(TOKYO).getValue());
    }

    @Test
    public void test_getLong_derivedField() {
        assertEquals(REPRESENTED_DAY_OF_MONTH, TEST_DAY.getLong(TestingField.INSTANCE));
    }

    private enum TestingField implements TemporalField {
        INSTANCE;

        @Override
        public TemporalUnit getBaseUnit() {
            return ChronoField.DAY_OF_MONTH.getBaseUnit();
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return ChronoField.DAY_OF_MONTH.getRangeUnit();
        }

        @Override
        public ValueRange range() {
            return ChronoField.DAY_OF_MONTH.range();
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
            return temporal.isSupported(ChronoField.DAY_OF_MONTH);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return temporal.range(ChronoField.DAY_OF_MONTH);
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(ChronoField.DAY_OF_MONTH);
        }

        @Override
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
            return ChronoField.DAY_OF_MONTH.adjustInto(temporal, newValue);
        }
    }
}
