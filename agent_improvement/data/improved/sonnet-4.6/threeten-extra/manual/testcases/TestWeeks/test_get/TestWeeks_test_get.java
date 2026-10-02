package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_get {

    @Test
    @DisplayName("get(ChronoUnit.WEEKS) returns the week count used to create the instance")
    public void test_get() {
        assertEquals(6, Weeks.of(6).get(ChronoUnit.WEEKS));
    }
}
