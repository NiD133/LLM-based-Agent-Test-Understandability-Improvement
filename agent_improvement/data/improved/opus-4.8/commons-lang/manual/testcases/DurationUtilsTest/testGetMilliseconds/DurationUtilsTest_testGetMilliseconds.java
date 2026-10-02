package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

/**
 * Tests {@link DurationUtils#getMillis(String, long)}.
 *
 * <p>{@code getMillis(key, def)} reads the system property named {@code key} and interprets its
 * value as a number of milliseconds. When the key is missing/blank or the property is not set, the
 * supplied default ({@code def}, also in milliseconds) is used instead.</p>
 */
public class DurationUtilsTest_testGetMilliseconds extends AbstractLangTest {

    /** Default returned by {@code getMillis} when no usable property value is found. */
    private static final long DEFAULT_MILLIS = 0;

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = "Seconds1", value = "1"),
        // Long.MAX_VALUE expressed as milliseconds.
        @SetSystemProperty(key = "Seconds2", value = "9223372036854775807")
    })
    void testGetMilliseconds() {
        // A null key cannot name a property, so the default is returned.
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, DEFAULT_MILLIS));

        // An empty key cannot name a property, so the default is returned.
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", DEFAULT_MILLIS));

        // "Seconds1" is set to "1", so the result is 1 millisecond.
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis("Seconds1", DEFAULT_MILLIS));

        // "Seconds2" is set to Long.MAX_VALUE, so the result is Long.MAX_VALUE milliseconds.
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis("Seconds2", DEFAULT_MILLIS));
    }
}
