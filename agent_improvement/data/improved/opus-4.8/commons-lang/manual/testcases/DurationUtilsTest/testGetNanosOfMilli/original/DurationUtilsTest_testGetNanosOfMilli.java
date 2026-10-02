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

public class DurationUtilsTest_testGetNanosOfMilli extends AbstractLangTest {

    @Test
    void testGetNanosOfMilli() {
        assertEquals(0, DurationUtils.getNanosOfMilli(null));
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ZERO));
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1)));
        assertEquals(10, DurationUtils.getNanosOfMilli(Duration.ofNanos(10)));
        assertEquals(100, DurationUtils.getNanosOfMilli(Duration.ofNanos(100)));
        assertEquals(1_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)));
        assertEquals(10_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)));
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)));
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)));
    }
}
