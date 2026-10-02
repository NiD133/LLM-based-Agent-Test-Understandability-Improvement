package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisIntOverflowToMaxInteger extends AbstractLangTest {

    @Test
    void testToMillisIntOverflowToMaxInteger() {
        // A duration this large will overflow Long.MAX_VALUE when converted to milliseconds,
        // so toMillisInt() should clamp the result to Integer.MAX_VALUE.
        Duration overflowingDuration = Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1);

        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(overflowingDuration));
    }
}
