package org.apache.commons.lang3.concurrent;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class TimedSemaphoreTest_testInitInvalidPeriod extends AbstractLangTest {

    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final int LIMIT = 10;

    @Test
    @SuppressWarnings("deprecation")
    void testInitInvalidPeriod() {
        assertIllegalArgumentException(() -> new TimedSemaphore(0L, TIME_UNIT, LIMIT));
    }
}
