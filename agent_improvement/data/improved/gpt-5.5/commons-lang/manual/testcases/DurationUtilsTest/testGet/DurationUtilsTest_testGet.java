package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGet extends AbstractLangTest {

    @Test
    @SetSystemProperties({
            @SetSystemProperty(key = "Seconds1", value = "1"),
            @SetSystemProperty(key = "Seconds2", value = "9223372036854775807")
    })
    void testGet() {
        assertDurationsReadAsSeconds();
        assertDurationsReadAsMillis();
    }

    private void assertDurationsReadAsSeconds() {
        assertEquals(Duration.ofSeconds(0), DurationUtils.get(null, ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.get("", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(1), DurationUtils.get("Seconds1", ChronoUnit.SECONDS, 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.SECONDS, 0));
    }

    private void assertDurationsReadAsMillis() {
        assertEquals(Duration.ofMillis(0), DurationUtils.get(null, ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.get("", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(1), DurationUtils.get("Seconds1", ChronoUnit.MILLIS, 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.get("Seconds2", ChronoUnit.MILLIS, 0));
    }
}
