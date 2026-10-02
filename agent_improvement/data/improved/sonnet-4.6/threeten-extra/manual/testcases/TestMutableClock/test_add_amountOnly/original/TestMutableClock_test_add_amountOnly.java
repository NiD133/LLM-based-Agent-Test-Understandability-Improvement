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

public class TestMutableClock_test_add_amountOnly {

    @Test
    public void test_add_amountOnly() {
        MutableClock clock = MutableClock.epochUTC();
        clock.add(Duration.ofNanos(3));
        clock.add(Period.ofMonths(2));
        clock.add(Duration.ofSeconds(-5));
        clock.add(Period.ofWeeks(-7));
        clock.add(Duration.ZERO);
        clock.add(Period.ZERO);
        assertEquals(ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).plusNanos(3).plusMonths(2).minusSeconds(5).minusWeeks(7).toInstant(), clock.instant());
    }
}
