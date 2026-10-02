package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGetMilliseconds extends AbstractLangTest {

    private static final String PROP_ONE_MILLIS = "millis.one";
    private static final String PROP_MAX_MILLIS = "millis.max";

    @Test
    @DisplayName("getMillis returns default Duration for null/empty keys, and reads system-property value as milliseconds")
    @SetSystemProperties({
        @SetSystemProperty(key = PROP_ONE_MILLIS, value = "1"),
        @SetSystemProperty(key = PROP_MAX_MILLIS, value = "9223372036854775807") // Long.MAX_VALUE
    })
    void testGetMilliseconds() {
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, 0),
            "null key should fall back to the default and return a zero-millisecond Duration");
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", 0),
            "empty key should fall back to the default and return a zero-millisecond Duration");
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis(PROP_ONE_MILLIS, 0),
            "should read system property value '1' and return a Duration of 1 millisecond");
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis(PROP_MAX_MILLIS, 0),
            "should read Long.MAX_VALUE from the system property and return the corresponding Duration");
    }
}
