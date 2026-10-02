package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_wrongUnit_remainder {

    @Test
    public void test_from_wrongUnit_remainder() {
        Period threeDays = Period.ofDays(3);

        assertThrows(DateTimeException.class, () -> Weeks.from(threeDays));
    }
}
