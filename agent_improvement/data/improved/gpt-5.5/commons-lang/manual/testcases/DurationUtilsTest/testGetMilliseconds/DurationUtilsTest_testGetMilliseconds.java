package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGetMilliseconds extends AbstractLangTest {

    private static final String ONE_MILLISECOND_PROPERTY = "Seconds1";
    private static final String MAX_MILLISECONDS_PROPERTY = "Seconds2";
    private static final long DEFAULT_MILLISECONDS = 0;

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = ONE_MILLISECOND_PROPERTY, value = "1"),
        @SetSystemProperty(key = MAX_MILLISECONDS_PROPERTY, value = "9223372036854775807")
    })
    void testGetMilliseconds() {
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis(null, DEFAULT_MILLISECONDS));
        assertEquals(Duration.ofMillis(0), DurationUtils.getMillis("", DEFAULT_MILLISECONDS));
        assertEquals(Duration.ofMillis(1), DurationUtils.getMillis(ONE_MILLISECOND_PROPERTY, DEFAULT_MILLISECONDS));
        assertEquals(Duration.ofMillis(Long.MAX_VALUE), DurationUtils.getMillis(MAX_MILLISECONDS_PROPERTY, DEFAULT_MILLISECONDS));
    }
}
