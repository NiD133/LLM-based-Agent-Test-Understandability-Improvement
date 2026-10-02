package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test02 extends Hours_ESTest_scaffolding {

    // Verifies that Hours.between() returns zero hours when start and end are the same
    // instant, and that the resulting Hours object satisfies reflexive equality.
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Instant sameInstant = MockInstant.ofEpochSecond(1137L);
        Hours zeroHours = Hours.between(sameInstant, sameInstant);

        boolean isSelfEqual = zeroHours.equals(zeroHours);
        assertTrue(isSelfEqual);
        assertEquals(0, zeroHours.getAmount());
    }
}
