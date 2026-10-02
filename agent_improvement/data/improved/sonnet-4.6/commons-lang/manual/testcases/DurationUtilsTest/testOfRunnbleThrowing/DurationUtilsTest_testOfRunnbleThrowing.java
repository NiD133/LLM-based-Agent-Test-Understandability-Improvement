package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.function.FailableRunnable;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfRunnbleThrowing extends AbstractLangTest {

    @Test
    void testOfRunnbleThrowing() {
        // Verify that DurationUtils.of() propagates the exception thrown by the runnable
        FailableRunnable<IOException> throwingRunnable = () -> {
            throw new IOException();
        };

        assertThrows(IOException.class, () -> DurationUtils.of(throwingRunnable));
    }
}
