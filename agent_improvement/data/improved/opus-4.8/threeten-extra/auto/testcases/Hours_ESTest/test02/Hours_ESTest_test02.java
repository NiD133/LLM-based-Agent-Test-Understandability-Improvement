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
public class Hours_ESTest_test02 extends Hours_ESTest_scaffolding {

    /**
     * The number of hours between an instant and itself is zero, and the
     * resulting {@code Hours} value is equal to itself.
     */
    @Test(timeout = 4000)
    public void betweenSameInstantIsZeroHoursAndEqualsItself() throws Throwable {
        Instant sameInstant = MockInstant.ofEpochSecond(1137L);

        Hours hoursBetween = Hours.between(sameInstant, sameInstant);

        assertTrue("a Hours value must equal itself", hoursBetween.equals(hoursBetween));
        assertEquals("no hours elapse between an instant and itself", 0, hoursBetween.getAmount());
    }
}
