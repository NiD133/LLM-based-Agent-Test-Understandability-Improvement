import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;
import org.threeten.extra.DayOfMonth;

/**
 * Tests how {@link DayOfMonth#atYearMonth(YearMonth)} combines a day-of-month
 * with a year-month, clamping to the last valid day when the month is shorter.
 */
public class TestDayOfMonth_test_atYearMonth_31 {

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Day 31 is the highest possible day-of-month, so it exercises the clamping
     * rule for every month. When 31 exceeds the month's length the result is
     * pinned to the last day of that month; otherwise it stays on the 31st.
     */
    @Test
    public void test_atYearMonth_31() {
        DayOfMonth dayThirtyFirst = DayOfMonth.of(31);

        // 2012 is a leap year, so February is clamped to the 29th.
        assertEquals(LocalDate.of(2012, 1, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 1)));
        assertEquals(LocalDate.of(2012, 2, 29), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 2)));
        assertEquals(LocalDate.of(2012, 3, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 3)));
        assertEquals(LocalDate.of(2012, 4, 30), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 4)));
        assertEquals(LocalDate.of(2012, 5, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 5)));
        assertEquals(LocalDate.of(2012, 6, 30), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 6)));
        assertEquals(LocalDate.of(2012, 7, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 7)));
        assertEquals(LocalDate.of(2012, 8, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 8)));
        assertEquals(LocalDate.of(2012, 9, 30), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 9)));
        assertEquals(LocalDate.of(2012, 10, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 10)));
        assertEquals(LocalDate.of(2012, 11, 30), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 11)));
        assertEquals(LocalDate.of(2012, 12, 31), dayThirtyFirst.atYearMonth(YearMonth.of(2012, 12)));

        // 2011 is not a leap year, so February is clamped to the 28th.
        assertEquals(LocalDate.of(2011, 2, 28), dayThirtyFirst.atYearMonth(YearMonth.of(2011, 2)));
    }
}
