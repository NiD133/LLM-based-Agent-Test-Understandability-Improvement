package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_adjustInto_february29_notLeapYear {

    @Test
    public void test_adjustInto_february29_notLeapYear() {
        LocalDate nonLeapYearFebruary = LocalDate.of(2007, 2, 1);
        DayOfMonth february29 = DayOfMonth.of(29);

        assertThrows(DateTimeException.class, () -> february29.adjustInto(nonLeapYearFebruary));
    }
}
