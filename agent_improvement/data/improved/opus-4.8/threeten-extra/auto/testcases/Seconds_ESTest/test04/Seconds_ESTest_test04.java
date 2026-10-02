package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test04 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that {@link Seconds#subtractFrom(Temporal)} throws a
     * NullPointerException when given a null temporal, since the method
     * dereferences the temporal to subtract its seconds.
     */
    @Test(timeout = 4000)
    public void subtractFromNullTemporalThrowsNullPointerException() throws Throwable {
        Seconds oneSecond = Seconds.of(1);

        try {
            oneSecond.subtractFrom((Temporal) null);
            fail("Expected a NullPointerException for a null temporal");
        } catch (NullPointerException e) {
            // The exception carries no message and originates from Seconds.
            verifyException("org.threeten.extra.Seconds", e);
        }
    }
}
