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

public class TestMutableClock_test_of {

    @Test
    public void test_of() {
        assertEquals(Instant.EPOCH, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).instant());
        assertEquals(Instant.MIN, MutableClock.of(Instant.MIN, ZoneOffset.UTC).instant());
        assertEquals(Instant.MAX, MutableClock.of(Instant.MAX, ZoneOffset.UTC).instant());
        assertEquals(ZoneOffset.UTC, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).getZone());
        assertEquals(ZoneOffset.MIN, MutableClock.of(Instant.EPOCH, ZoneOffset.MIN).getZone());
        assertEquals(ZoneOffset.MAX, MutableClock.of(Instant.EPOCH, ZoneOffset.MAX).getZone());
    }
}
