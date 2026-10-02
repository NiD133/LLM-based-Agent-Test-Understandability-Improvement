package org.apache.commons.lang3.concurrent;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that constructing a {@link TimedSemaphore} with a non-positive time period
 * throws an {@link IllegalArgumentException}.
 */
public class TimedSemaphoreTest_testInitInvalidPeriod extends AbstractLangTest {

    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    /**
     * A period value of zero is invalid; the semaphore requires a strictly positive period.
     * Verifies that the constructor rejects it with an IllegalArgumentException.
     */
    @Test
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(0L, UNIT, LIMIT));
    }
}
