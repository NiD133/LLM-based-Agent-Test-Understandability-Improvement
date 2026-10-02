package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

/**
 * Tests that a freshly constructed {@link TimedSemaphore} exposes the values it
 * was created with and starts out in an inactive, non-shut-down state.
 */
public class TimedSemaphoreTest_testInit extends AbstractLangTest {

    /** The time period used to configure the semaphore under test. */
    private static final long PERIOD_MILLIS = 500;

    /** The time unit matching {@link #PERIOD_MILLIS}. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** The permit limit used to configure the semaphore under test. */
    private static final int LIMIT = 10;

    /**
     * A brand-new semaphore should remember its constructor arguments, report no
     * collected statistics yet, and not be shut down.
     */
    @Test
    void testInit() {
        // The semaphore only schedules a timer task on the first acquire(), so a
        // strict mock with no expected calls verifies that construction alone
        // never touches the executor service.
        final ScheduledExecutorService service = EasyMock.createMock(ScheduledExecutorService.class);
        EasyMock.replay(service);

        final TimedSemaphore semaphore = new TimedSemaphore(service, PERIOD_MILLIS, UNIT, LIMIT);

        EasyMock.verify(service);

        // The constructor arguments are exposed unchanged.
        assertEquals(service, semaphore.getExecutorService(), "Wrong service");
        assertEquals(PERIOD_MILLIS, semaphore.getPeriod(), "Wrong period");
        assertEquals(UNIT, semaphore.getUnit(), "Wrong unit");
        assertEquals(LIMIT, semaphore.getLimit(), "Wrong limit");

        // No acquire() has happened yet, so no statistics are available.
        assertEquals(0, semaphore.getLastAcquiresPerPeriod(), "Statistic available");
        assertEquals(0.0, semaphore.getAverageCallsPerPeriod(), .05, "Average available");

        assertFalse(semaphore.isShutdown(), "Already shutdown");
    }
}
