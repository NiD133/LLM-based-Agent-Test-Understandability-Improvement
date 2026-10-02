package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMinutes_test_addTo {

    @Test
    @DisplayName("addTo adjusts a LocalTime by the specified number of minutes")
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);

        assertEquals(LocalTime.of(11, 30), Minutes.of(0).addTo(base),
                "Adding zero minutes should leave the time unchanged");
        assertEquals(LocalTime.of(11, 36), Minutes.of(6).addTo(base),
                "Adding 6 minutes to 11:30 should yield 11:36");
    }
}
