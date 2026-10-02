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

public class TestMutableClock_test_add_amountAndUnit {

    @Test
    public void test_add_amountAndUnit() {
        MutableClock clock = MutableClock.epochUTC();
        clock.add(3, ChronoUnit.NANOS);
        clock.add(2, ChronoUnit.MONTHS);
        clock.add(-5, ChronoUnit.SECONDS);
        clock.add(-7, ChronoUnit.WEEKS);
        clock.add(0, ChronoUnit.MILLIS);
        clock.add(0, ChronoUnit.YEARS);
        assertEquals(ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).plusNanos(3).plusMonths(2).minusSeconds(5).minusWeeks(7).toInstant(), clock.instant());
    }
}
