package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test04 extends Minutes_ESTest_scaffolding {

    /**
     * The number of minutes between an instant and itself is zero, and subtracting
     * the ZERO amount from a temporal leaves the original Minutes value unchanged.
     */
    @Test(timeout = 4000)
    public void betweenSameInstantIsZeroAndSubtractFromLeavesItZero() throws Throwable {
        OffsetDateTime sameInstant = MockOffsetDateTime.now();

        Minutes minutesBetween = Minutes.between(sameInstant, sameInstant);
        Minutes.ZERO.subtractFrom(sameInstant);

        assertTrue("Minutes between an instant and itself should be zero", minutesBetween.isZero());
        assertEquals(0, minutesBetween.getAmount());
    }
}
