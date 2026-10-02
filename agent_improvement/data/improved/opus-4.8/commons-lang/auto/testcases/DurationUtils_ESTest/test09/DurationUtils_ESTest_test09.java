package org.apache.commons.lang3.time;

import org.junit.Test;
import java.time.Duration;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test09 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#accept(FailableBiConsumer, Duration)} tolerates a null
     * Duration: when the duration is null, the consumer must not be invoked and no exception is
     * thrown. A no-op consumer is used so the call simply returns normally.
     */
    @Test(timeout = 4000)
    public void acceptWithNullDurationDoesNothing() throws Throwable {
        FailableBiConsumer<Long, Integer, Throwable> noOpConsumer = FailableBiConsumer.nop();

        // A null Duration short-circuits inside accept(), so this returns without calling the consumer.
        DurationUtils.accept(noOpConsumer, (Duration) null);
    }
}
