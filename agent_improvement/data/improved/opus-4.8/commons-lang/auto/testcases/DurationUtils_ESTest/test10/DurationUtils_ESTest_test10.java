package org.apache.commons.lang3.time;

import org.junit.Test;
import java.time.Duration;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test10 extends DurationUtils_ESTest_scaffolding {

    /**
     * When the consumer passed to {@link DurationUtils#accept} is null, the method
     * should be a no-op and return normally without throwing (the consumer is never
     * invoked, even for a non-null duration).
     */
    @Test(timeout = 4000)
    public void acceptWithNullConsumerDoesNothing() throws Throwable {
        FailableBiConsumer<Long, Integer, Throwable> nullConsumer = null;
        Duration zeroDuration = Duration.ZERO;

        // Should complete silently because the null consumer guard short-circuits.
        DurationUtils.accept(nullConsumer, zeroDuration);
    }
}
