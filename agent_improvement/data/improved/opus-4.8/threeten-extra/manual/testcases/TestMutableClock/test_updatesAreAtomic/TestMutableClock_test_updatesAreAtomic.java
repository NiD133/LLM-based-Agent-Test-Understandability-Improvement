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

/**
 * Verifies that {@link MutableClock#add(java.time.temporal.TemporalAmount)} is
 * thread-safe: when many threads each add a fixed amount concurrently, no update
 * is lost, so the final instant equals the sum of every individual increment.
 */
public class TestMutableClock_test_updatesAreAtomic {

    @Test
    public void test_updatesAreAtomic() throws Exception {
        // Each task advances the shared clock by the same fixed amount.
        MutableClock clock = MutableClock.epochUTC();
        Duration increment = Duration.ofSeconds(1);
        Callable<@Nullable Void> addOneIncrement = () -> {
            clock.add(increment);
            return null;
        };

        // Run the same update many times across a pool sized to oversubscribe
        // the CPU, maximising the chance of exposing a lost-update race.
        int updateCount = 10000;
        List<Callable<Void>> tasks = Collections.nCopies(updateCount, addOneIncrement);
        int threadCount = Runtime.getRuntime().availableProcessors() * 4;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        try {
            executor.invokeAll(tasks);
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
        }

        // If every increment was applied atomically, the clock advanced by the
        // full sum of all increments from the epoch.
        Instant expected = Instant.EPOCH.plus(increment.multipliedBy(updateCount));
        assertEquals(expected, clock.instant());
    }
}
