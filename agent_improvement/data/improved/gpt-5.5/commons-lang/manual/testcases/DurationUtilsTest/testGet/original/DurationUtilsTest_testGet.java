package org.apache.commons.lang3.time;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGet extends AbstractLangTest {

    @Test
    @SetSystemProperties({ @SetSystemProperty(key = "Seconds1", value = "1"), // Long.MAX_VALUE
    @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") })
    void testGet() {
        // ChronoUnit.SECONDS
        assertEquals(Duration.ofSeconds(0), DurationUtils.get(null, ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.get("", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(1), DurationUtils.get("Seconds1", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.SECONDS, 0));
        // ChronoUnit.MILLIS
        assertEquals(Duration.ofMillis(0), DurationUtils.get(null, ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.get("", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(1), DurationUtils.get("Seconds1", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.MILLIS, 0));
    }
}
