package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import com.google.common.testing.EqualsTester;

public class TestDayOfMonth_test_equals_and_hashCode {

    private static final int FIRST_VALID_DAY = 1;
    private static final int LAST_VALID_DAY = 31;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();
        addEqualityGroupForEachValidDay(equalsTester);
        equalsTester.testEquals();
    }

    private static void addEqualityGroupForEachValidDay(EqualsTester equalsTester) {
        for (int day = FIRST_VALID_DAY; day <= LAST_VALID_DAY; day++) {
            equalsTester.addEqualityGroup(DayOfMonth.of(day), DayOfMonth.of(day));
        }
    }
}
