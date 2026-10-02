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
    private static final Duration INCREMENT = Duration.ofSeconds(1);

    @Test
    public void test_updatesAreAtomic() throws Exception {
        MutableClock clock = MutableClock.epochUTC();
        Callable<@Nullable Void> applyOneUpdate = () -> {
            clock.add(INCREMENT);
            return null;
        };
        List<Callable<Void>> updateTasks = Collections.nCopies(UPDATE_COUNT, applyOneUpdate);

        int threads = Runtime.getRuntime().availableProcessors() * 4;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        try {
            service.invokeAll(updateTasks);
            service.shutdown();
            service.awaitTermination(1, TimeUnit.MINUTES);
        } finally {
            if (!service.isTerminated()) {
                service.shutdownNow();
            }
        }

        Instant expectedFinalInstant = Instant.EPOCH.plus(INCREMENT.multipliedBy(UPDATE_COUNT));
        assertEquals(expectedFinalInstant, clock.instant());
    }
}
