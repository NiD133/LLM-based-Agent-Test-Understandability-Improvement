package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInit extends AbstractLangTest {

    /** The time period in milliseconds used when constructing the semaphore under test. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit paired with {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The permit limit passed to the semaphore constructor. */
    private static final int LIMIT = 10;

    /**
     * Verifies that a newly constructed {@link TimedSemaphore} stores its constructor
     * arguments and reports zeroed-out statistics, with no interaction on the supplied
     * executor service (the timer does not start until the first {@code acquire()} call).
     */
    @Test
    void testInit() {
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        // Confirm no methods were called on the executor during construction
        EasyMock.verify(service);

        // Constructor arguments are stored correctly
        assertEquals(service, semaphore.getExecutorService(), "Wrong service");
        assertEquals(PERIOD_MILLIS, semaphore.getPeriod(), "Wrong period");
        assertEquals(UNIT, semaphore.getUnit(), "Wrong unit");
        assertEquals(LIMIT, semaphore.getLimit(), "Wrong limit");

        // Statistics are at their initial zero state
        assertEquals(0, semaphore.getLastAcquiresPerPeriod(), "Statistic available");
        assertEquals(0.0, semaphore.getAverageCallsPerPeriod(), .05, "Average available");

        // Semaphore has not been shut down
        assertFalse(semaphore.isShutdown(), "Already shutdown");
    }
}
