package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testZeroIfNull extends AbstractLangTest {

    @Test
    void testZeroIfNull() {
        assertEquals(Duration.ZERO, DurationUtils.zeroIfNull(null),
                "null input should return Duration.ZERO");
        assertEquals(Duration.ofDays(1), DurationUtils.zeroIfNull(Duration.ofDays(1)),
                "non-null input should be returned unchanged");
    }
}
