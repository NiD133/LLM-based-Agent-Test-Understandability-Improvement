package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGet extends AbstractLangTest {

    // System property keys configured via @SetSystemProperties
    private static final String PROP_ONE = "Seconds1";
    private static final String PROP_MAX = "Seconds2";

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = PROP_ONE, value = "1"),
        @SetSystemProperty(key = PROP_MAX, value = "9223372036854775807") // Long.MAX_VALUE
    })
    void testGet() {
        // A null or empty key falls back to the supplied default value (0)
        assertEquals(Duration.ofSeconds(0), DurationUtils.get(null, ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.get("", ChronoUnit.SECONDS, 0));

        // A valid system-property key is read and interpreted in the requested unit
        assertEquals(Duration.ofSeconds(1),             DurationUtils.get(PROP_ONE, ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get(PROP_MAX, ChronoUnit.SECONDS, 0));

        // The same null/empty fallback behaviour applies when the unit is MILLIS
        assertEquals(Duration.ofMillis(0), DurationUtils.get(null, ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.get("", ChronoUnit.MILLIS, 0));

        // A valid system-property key is read and interpreted in milliseconds
        assertEquals(Duration.ofMillis(1),             DurationUtils.get(PROP_ONE, ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get(PROP_MAX, ChronoUnit.MILLIS, 0));
    }
}
