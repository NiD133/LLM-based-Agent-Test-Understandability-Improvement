package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGetSeconds extends AbstractLangTest {

    // System property key whose value is the string "1" (one second)
    private static final String ONE_SECOND_KEY = "ONE_SECOND";
    // System property key whose value is the string representation of Long.MAX_VALUE
    private static final String MAX_SECONDS_KEY = "MAX_SECONDS";

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = "ONE_SECOND", value = "1"),
        @SetSystemProperty(key = "MAX_SECONDS", value = "9223372036854775807") // Long.MAX_VALUE
    })
    void testGetSeconds() {
        // null key falls back to the default value (0)
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds(null, 0));
        // empty key also falls back to the default value (0)
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds("", 0));
        // system property "ONE_SECOND" holds "1", so the result is 1 second
        assertEquals(Duration.ofSeconds(1), DurationUtils.getSeconds(ONE_SECOND_KEY, 0));
        // system property "MAX_SECONDS" holds Long.MAX_VALUE as a string
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds(MAX_SECONDS_KEY, 0));
    }
}
