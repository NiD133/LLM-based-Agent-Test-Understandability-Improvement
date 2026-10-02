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

public class TestMutableClock_test_toString {

    @Test
    public void test_toString() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals("MutableClock[1970-01-01T00:00:00Z,Z]", clock.toString());
        clock.add(Period.ofYears(30));
        assertEquals("MutableClock[2000-01-01T00:00:00Z,Z]", clock.toString());
        MutableClock withOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals("MutableClock[2000-01-01T00:00:00Z,-18:00]", withOtherZone.toString());
    }
}
