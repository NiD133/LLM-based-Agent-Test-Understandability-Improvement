package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

/**
 * Tests {@link DurationUtils#get(String, java.time.temporal.TemporalUnit, long)}.
 * <p>
 * {@code get} reads a {@code long} amount from the system property named by {@code key}
 * (falling back to the default when the key is {@code null}/empty or the property is
 * missing) and wraps that amount in a {@link Duration} measured in the supplied unit.
 * </p>
 */
public class DurationUtilsTest_testGet extends AbstractLangTest {

    /** Default amount used when a property cannot be resolved. */
    private static final long DEFAULT_AMOUNT = 0;

    /** Name of a system property holding the amount {@code 1}; see {@link SetSystemProperty} below. */
    private static final String KEY_AMOUNT_ONE = "Seconds1";

    /** Name of a system property holding {@link Long#MAX_VALUE}; see {@link SetSystemProperty} below. */
    private static final String KEY_AMOUNT_MAX = "Seconds2";

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = "Seconds1", value = "1"),
        @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") // Long.MAX_VALUE
    })
    void testGet() {
        // --- Unit = SECONDS: the resolved amount is interpreted as seconds ---

        // null key -> default amount (0)
        assertEquals(Duration.ofSeconds(0),
            DurationUtils.get(null, ChronoUnit.SECONDS, DEFAULT_AMOUNT));
        // empty key -> default amount (0)
        assertEquals(Duration.ofSeconds(0),
            DurationUtils.get("", ChronoUnit.SECONDS, DEFAULT_AMOUNT));
        // key resolves to property value 1
        assertEquals(Duration.ofSeconds(1),
            DurationUtils.get(KEY_AMOUNT_ONE, ChronoUnit.SECONDS, DEFAULT_AMOUNT));
        // key resolves to property value Long.MAX_VALUE
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE),
            DurationUtils.get(KEY_AMOUNT_MAX, ChronoUnit.SECONDS, DEFAULT_AMOUNT));

        // --- Unit = MILLIS: the same resolved amounts are now interpreted as milliseconds ---

        // null key -> default amount (0)
        assertEquals(Duration.ofMillis(0),
            DurationUtils.get(null, ChronoUnit.MILLIS, DEFAULT_AMOUNT));
        // empty key -> default amount (0)
        assertEquals(Duration.ofMillis(0),
            DurationUtils.get("", ChronoUnit.MILLIS, DEFAULT_AMOUNT));
        // key resolves to property value 1
        assertEquals(Duration.ofMillis(1),
            DurationUtils.get(KEY_AMOUNT_ONE, ChronoUnit.MILLIS, DEFAULT_AMOUNT));
        // key resolves to property value Long.MAX_VALUE
        assertEquals(Duration.ofMillis(Long.MAX_VALUE),
            DurationUtils.get(KEY_AMOUNT_MAX, ChronoUnit.MILLIS, DEFAULT_AMOUNT));
    }
}
