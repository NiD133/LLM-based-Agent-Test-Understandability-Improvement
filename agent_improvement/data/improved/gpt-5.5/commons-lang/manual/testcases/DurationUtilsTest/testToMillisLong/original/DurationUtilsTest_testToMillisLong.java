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

public class DurationUtilsTest_testToMillisLong extends AbstractLangTest {

    @Test
    void testToMillisLong() {
        assertEquals(0, DurationUtils.toMillisLong(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisLong(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisLong(Duration.ofMillis(-1)));
        assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofSeconds(Long.MAX_VALUE)));
    }
}
