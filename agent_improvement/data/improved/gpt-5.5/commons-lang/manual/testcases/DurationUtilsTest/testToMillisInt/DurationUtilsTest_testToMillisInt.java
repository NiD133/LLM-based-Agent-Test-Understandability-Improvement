package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisInt extends AbstractLangTest {

    @Test
    void testToMillisInt() {
        assertEquals(0, DurationUtils.toMillisInt(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisInt(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisInt(Duration.ofMillis(-1)));

        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2)));

        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
    }
}
