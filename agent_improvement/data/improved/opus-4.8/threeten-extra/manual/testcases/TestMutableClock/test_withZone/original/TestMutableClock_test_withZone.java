package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestMutableClock_test_withZone {

    @Test
    public void test_withZone() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        MutableClock withSameZone = withOtherZone.withZone(ZoneOffset.UTC);
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, withOtherZone.instant());
        assertEquals(Instant.MIN, withSameZone.instant());
        assertEquals(ZoneOffset.MIN, withOtherZone.getZone());
        assertEquals(ZoneOffset.UTC, withSameZone.getZone());
        assertNotEquals(clock, withOtherZone);
        assertEquals(clock, withSameZone);
    }
}
