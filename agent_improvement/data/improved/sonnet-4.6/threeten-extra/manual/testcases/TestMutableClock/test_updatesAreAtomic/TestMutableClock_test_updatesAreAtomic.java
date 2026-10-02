package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

public class TestMutableClock_test_updatesAreAtomic {

    private static final int UPDATE_COUNT = 10000;
    private static final Duration ONE_SECOND = Duration.ofSeconds(1);

    @Test
    public void test_updatesAreAtomic() throws Exception {
        MutableClock clock = MutableClock.epochUTC();

        // Each task advances the clock by one second; all tasks run concurrently.
        Callable<@Nullable Void> addOneSecond = () -> {
            clock.add(ONE_SECOND);
            return null;
        };
        List<Callable<Void>> tasks = Collections.nCopies(UPDATE_COUNT, addOneSecond);

        int threadCount = Runtime.getRuntime().availableProcessors() * 4;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        try {
            service.invokeAll(tasks);
            service.shutdown();
            service.awaitTermination(1, TimeUnit.MINUTES);
        } finally {
            if (!service.isTerminated()) {
                service.shutdownNow();
            }
        }

        // If all updates were atomic, the final instant equals EPOCH + (UPDATE_COUNT seconds).
        Instant expectedInstant = Instant.EPOCH.plus(ONE_SECOND.multipliedBy(UPDATE_COUNT));
        assertEquals(expectedInstant, clock.instant());
    }
}
