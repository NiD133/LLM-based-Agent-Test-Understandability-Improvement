package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.function.FailableRunnable;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#of(FailableRunnable)}.
 */
public class DurationUtilsTest_testOfRunnbleThrowing extends AbstractLangTest {

    /**
     * When the timed runnable throws a checked exception, {@code DurationUtils.of}
     * must propagate that same exception to the caller rather than swallowing it.
     */
    @Test
    void testOfRunnableThrowing() {
        final FailableRunnable<IOException> throwingRunnable = () -> {
            throw new IOException();
        };

        assertThrows(IOException.class, () -> DurationUtils.of(throwingRunnable));
    }
}
