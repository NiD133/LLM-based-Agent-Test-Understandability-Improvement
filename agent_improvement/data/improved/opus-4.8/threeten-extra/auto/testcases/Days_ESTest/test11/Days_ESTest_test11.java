package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test11 extends Days_ESTest_scaffolding {

    /**
     * The number of days between an instant and itself is zero, and dividing a
     * zero-day amount by any divisor leaves it zero. Because {@code Days} reuses
     * the {@link Days#ZERO} singleton for any zero result, the division returns
     * the very same instance it started from.
     */
    @Test(timeout = 4000)
    public void dividingZeroDaysKeepsTheSameZeroInstance() throws Throwable {
        Instant sameInstant = MockInstant.ofEpochSecond(-2086L, -2086L);

        Days zeroDays = Days.between(sameInstant, sameInstant);
        Days quotient = zeroDays.dividedBy(-507);

        assertTrue(quotient.isZero());
        assertSame(zeroDays, quotient);
    }
}
