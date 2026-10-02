package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty.SetSystemProperties;

public class DurationUtilsTest_testGetSeconds extends AbstractLangTest {

    private static final String ONE_SECOND_PROPERTY = "Seconds1";
    private static final String MAX_SECONDS_PROPERTY = "Seconds2";

    @Test
    @SetSystemProperties({
        @SetSystemProperty(key = ONE_SECOND_PROPERTY, value = "1"),
        @SetSystemProperty(key = MAX_SECONDS_PROPERTY, value = "9223372036854775807")
    })
    void testGetSeconds() {
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds(null, 0));
        assertEquals(Duration.ofSeconds(0), DurationUtils.getSeconds("", 0));
        assertEquals(Duration.ofSeconds(1), DurationUtils.getSeconds(ONE_SECOND_PROPERTY, 0));
        assertEquals(Duration.ofSeconds(Long.MAX_VALUE), DurationUtils.getSeconds(MAX_SECONDS_PROPERTY, 0));
    }
}
