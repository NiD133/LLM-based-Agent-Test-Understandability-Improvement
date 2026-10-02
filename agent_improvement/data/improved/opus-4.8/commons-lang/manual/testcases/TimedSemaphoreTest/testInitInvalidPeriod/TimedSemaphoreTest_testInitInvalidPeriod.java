package org.apache.commons.lang3.concurrent;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInitInvalidPeriod extends AbstractLangTest {

    /** Time unit used for the semaphore's period. */
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    /** Default permit limit for the semaphore. */
    private static final int LIMIT = 10;

    /** An invalid (non-positive) period that should be rejected by the constructor. */
    private static final long INVALID_PERIOD = 0L;

    /**
     * Constructing a {@link TimedSemaphore} with a non-positive period must fail
     * with an {@link IllegalArgumentException}, because the period has to be greater than 0.
     */
    @Test
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(INVALID_PERIOD, UNIT, LIMIT));
    }
}
