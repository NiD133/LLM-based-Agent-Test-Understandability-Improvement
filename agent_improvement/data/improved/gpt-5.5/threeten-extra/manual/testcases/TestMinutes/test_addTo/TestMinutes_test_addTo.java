package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime baseTime = LocalTime.of(11, 30);

        LocalTime unchangedTime = LocalTime.of(11, 30);
        assertEquals(unchangedTime, Minutes.of(0).addTo(baseTime));

        LocalTime sixMinutesLater = LocalTime.of(11, 36);
        assertEquals(sixMinutesLater, Minutes.of(6).addTo(baseTime));
    }
}
