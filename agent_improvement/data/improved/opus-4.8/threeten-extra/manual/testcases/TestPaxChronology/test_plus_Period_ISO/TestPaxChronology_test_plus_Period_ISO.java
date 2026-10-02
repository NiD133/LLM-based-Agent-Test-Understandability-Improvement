package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_Period_ISO {

    /**
     * Adding an ISO {@link Period} to a {@link PaxDate} must fail, because the
     * period belongs to a different chronology than the Pax date.
     */
    @Test
    public void plus_isoPeriod_throwsDateTimeException() {
        PaxDate paxDate = PaxDate.of(2014, 5, 26);
        Period isoPeriod = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> paxDate.plus(isoPeriod));
    }
}
