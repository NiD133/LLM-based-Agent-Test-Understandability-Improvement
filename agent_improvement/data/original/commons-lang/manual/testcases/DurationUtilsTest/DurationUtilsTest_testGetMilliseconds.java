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

public class DurationUtilsTest_testGetMilliseconds extends AbstractLangTest {

    @Test
    @SetSystemProperties({ @SetSystemProperty(key = "Seconds1", value = "1"), // Long.MAX_VALUE
    @SetSystemProperty(key = "Seconds2", value = "9223372036854775807") })
    void testGetMilliseconds() {
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, 0));
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", 0));
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis("Seconds1", 0));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis("Seconds2", 0));
    }
}
