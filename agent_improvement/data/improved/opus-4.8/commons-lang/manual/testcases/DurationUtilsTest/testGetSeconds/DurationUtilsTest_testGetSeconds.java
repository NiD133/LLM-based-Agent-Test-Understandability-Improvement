package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

/**
 * Tests {@link DurationUtils#getSeconds(String, long)}.
 *
 * <p>{@code getSeconds(key, def)} reads the system property named {@code key} as a {@code long}
 * and returns a {@link Duration} of that many seconds. When the key is blank or the property is
 * not set, the supplied default {@code def} is used instead.</p>
 */
public class DurationUtilsTest_testGetSeconds extends AbstractLangTest {

    /** Default value (in seconds) passed to every call below. */
    private static final long DEFAULT_SECONDS = 0;

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = "Seconds1", value = "1"),
        // 9223372036854775807 == Long.MAX_VALUE
        @SetSystemProperty(key = "Seconds2", value = "9223372036854775807")
    })
    void testGetSeconds() {
        // A null key has no backing property, so the default is returned.
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds(null, DEFAULT_SECONDS));

        // A blank key likewise falls back to the default.
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds("", DEFAULT_SECONDS));

        // "Seconds1" resolves to the property value 1, overriding the default.
        assertEquals(Duration.ofSeconds(1), DurationUtils.getSeconds("Seconds1", DEFAULT_SECONDS));

        // "Seconds2" resolves to Long.MAX_VALUE, the largest value a long property can hold.
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds("Seconds2", DEFAULT_SECONDS));
    }
}
