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

public class DurationUtilsTest_testToMillisIntUnderflowToMinInteger extends AbstractLangTest {

    // Seconds value whose millisecond equivalent (seconds * 1000) overflows Long.MIN_VALUE,
    // so toMillisLong() saturates to Long.MIN_VALUE, and toMillisInt() then clamps to Integer.MIN_VALUE.
    private static final long SECONDS_CAUSING_MILLIS_LONG_UNDERFLOW = Long.MIN_VALUE / 1000 - 1;

    @Test
    void testToMillisIntUnderflowToMinInteger() {
        Duration extremelyNegativeDuration = Duration.ofSeconds(SECONDS_CAUSING_MILLIS_LONG_UNDERFLOW);
        int result = DurationUtils.toMillisInt(extremelyNegativeDuration);
        assertEquals(Integer.MIN_VALUE, result,
            "toMillisInt should clamp to Integer.MIN_VALUE when the duration's milliseconds underflow Long.MIN_VALUE");
    }
}
