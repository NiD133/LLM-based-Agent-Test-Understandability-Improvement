package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToDuration extends AbstractLangTest {

    @Nested
    class TimeUnitConversions {

        @Test
        void convertsDays() {
            assertEquals(Duration.ofDays(1), DurationUtils.toDuration(1, TimeUnit.DAYS));
        }

        @Test
        void convertsHours() {
            assertEquals(Duration.ofHours(1), DurationUtils.toDuration(1, TimeUnit.HOURS));
        }

        @Test
        void convertsMinutes() {
            assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1, TimeUnit.MINUTES));
        }

        @Test
        void convertsSeconds() {
            assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1, TimeUnit.SECONDS));
        }

        @Test
        void convertsMilliseconds() {
            assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1, TimeUnit.MILLISECONDS));
        }

        @Test
        void convertsMicroseconds_1000MicrosecondsEqualsOneMillisecond() {
            assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));
        }

        @Test
        void convertsNanoseconds() {
            assertEquals(Duration.ofNanos(1), DurationUtils.toDuration(1, TimeUnit.NANOSECONDS));
        }
    }

    @Nested
    class MillisecondValuePreservation {

        @Test
        void preservesPositiveValue() {
            assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
        }

        @Test
        void preservesNegativeValue() {
            assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        }

        @Test
        void preservesZeroValue() {
            assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
        }
    }
}
