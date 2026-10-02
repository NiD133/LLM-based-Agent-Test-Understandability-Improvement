package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisIntUnderflowToMinInteger extends AbstractLangTest {

    @Test
    void testToMillisIntUnderflowToMinInteger() {
        final Duration durationBelowLongMinimumMillis = Duration.ofSeconds(Long.MIN_VALUE / 1000 - 1);

        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(durationBelowLongMinimumMillis));
    }
}
