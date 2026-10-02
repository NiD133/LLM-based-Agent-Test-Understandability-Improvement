package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisIntOverflowToMaxInteger extends AbstractLangTest {

    @Test
    void testToMillisIntOverflowToMaxInteger() {
        final long secondsThatOverflowWhenConvertedToMillis = Long.MAX_VALUE / 1000 + 1;

        assertEquals(
                Integer.MAX_VALUE,
                DurationUtils.toMillisInt(Duration.ofSeconds(secondsThatOverflowWhenConvertedToMillis)));
    }
}
