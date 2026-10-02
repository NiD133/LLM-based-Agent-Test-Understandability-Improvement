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

public class DurationUtilsTest_testOfConsumer extends AbstractLangTest {

    @Test
    void testOfConsumer() {
        assertTrue(DurationUtils.of(start -> assertTrue(start.compareTo(Instant.now()) <= 0)).compareTo(Duration.ZERO) >= 0);
        final Instant before = Instant.now();
        DurationUtils.of(start -> assertTrue(start.compareTo(before) >= 0));
    }
}
